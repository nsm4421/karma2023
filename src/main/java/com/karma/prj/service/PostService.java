package com.karma.prj.service;

import com.karma.prj.exception.CustomErrorCode;
import com.karma.prj.exception.CustomException;
import com.karma.prj.model.dto.CommentDto;
import com.karma.prj.model.dto.PostDto;
import com.karma.prj.model.entity.*;
import com.karma.prj.model.util.LikeType;
import com.karma.prj.model.util.NotificationEvent;
import com.karma.prj.model.util.NotificationType;
import com.karma.prj.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;

    /**
     * 포스트 작성요청
     * @param title : 제목
     * @param content : 본문
     * @param user : 로그인한 유저
     * @return 저장된 post id
     */
    @Transactional
    public Long createPost(String title, String content, UserEntity user){
        return postRepository.save(PostEntity.of(title, content, user)).getId();
    }

    /**
     * 포스트 단건조회
     * @param postId 조회할 포스트 id
     * @return PostDto
     */
    @Transactional(readOnly = true)
    public PostDto getPost(Long postId){
        return PostEntity.dto(findByPostIdOrElseThrow(postId));
    }

    /**
     * 포스트 조회
     */
    @Transactional(readOnly = true)
    public Page<PostDto> getPosts(Pageable pageable){
        return postRepository.findAll(pageable).map(PostEntity::dto);
    }

    /**
     * 포스팅 조회 by 유저
     * @param pageable
     * @param username 조회할 작성자
     * @return PostDto 페이지
     */
    @Transactional(readOnly = true)
    public Page<PostDto> getPostsByUser(Pageable pageable, String username){
        UserEntity user = findByUsernameOrElseThrow(username);
        return postRepository.findAllByUser(pageable, user).map(PostEntity::dto);
    }

    /**
     * 포스트 수정요청
     * @param postId 수정요청한 포스트 id
     * @param title 제목
     * @param content 본문
     * @param user 로그인한 유저
     * @return 저장된 포스트 id
     */
    @Transactional
    public Long modifyPost(Long postId, String title, String content, UserEntity user){
        PostEntity post = findByPostIdOrElseThrow(postId);
        if (!post.getUser().getUsername().equals(user.getUsername())){
            // 포스트 작성자와 수정 요청한 사람이 일치하는지 확인
            throw CustomException.of(CustomErrorCode.NOT_GRANTED_ACCESS);
        }
        post.setTitle(title);
        post.setContent(content);
        return postRepository.save(PostEntity.of(title, content, user)).getId();
    }

    /**
     * 포스트 삭제요청 - 포스팅, 댓글, 좋아요, 알림 삭제
     * @param postId 삭제요청한 포스트 id
     * @param userId 로그인한 유저 id
     * @return 저장된 포스트 id
     */
    @Transactional
    public Long deletePost(Long postId, Long userId){
        PostEntity post = findByPostIdOrElseThrow(postId);
        if (!post.getUser().getId().equals(userId)){
            // 포스트 작성자와 삭제 요청한 사람이 일치하는지 확인
            throw CustomException.of(CustomErrorCode.NOT_GRANTED_ACCESS);
        }
        postRepository.deleteByPostId(postId);
        commentRepository.deleteAllByPost(post);
        likeRepository.deleteAllByPost(post);
        notificationRepository.deleteAllByPost(post);
        return postId;
    }

    /**
     * 댓글 조회
     * @param pageable
     * @param postId
     * @return 댓글 Dto Page
     */
    @Transactional(readOnly = true)
    public Page<CommentDto> getComments(Long postId, Pageable pageable){
        PostEntity post = findByPostIdOrElseThrow(postId);
        return commentRepository.findAllByPost(post, pageable).map(CommentEntity::dto);
    }

    /**
     * 댓글작성
     * @return Comment Dto
     */
    @Transactional
    public CommentDto createComment(Long postId, String content, UserEntity user){
        PostEntity post = findByPostIdOrElseThrow(postId);
        UserEntity author = post.getUser();                     // user : 댓쓴이 / author : 글쓴이
        CommentDto commentDto = CommentEntity.dto(commentRepository.save(CommentEntity.of(content, user, post)));   // 댓글작성
        // 알림생성
        NotificationEntity notification = notificationRepository.save(
                NotificationEntity.of(author, post, NotificationType.NEW_COMMENT_ON_POST,
                String.format("%s님이 댓글을 달았습니다", user.getNickname())));
        // 알림전송
        notificationService.sendNotification(NotificationEvent.from(notification));
        return commentDto;
    }

    /**
     * 댓글 수정
     */
    @Transactional
    public CommentDto modifyComment(Long postId, Long commentId, String content, UserEntity user){
        findByPostIdOrElseThrow(postId);
        CommentEntity comment = findByCommentIdOrElseThrow(commentId);
        if (!comment.getUser().getId().equals(user.getId())){
            throw CustomException.of(CustomErrorCode.NOT_GRANTED_ACCESS);
        }
        comment.setContent(content);
        return CommentEntity.dto(commentRepository.save(comment));
    }

    /**
     * 댓글 삭제
     */
    @Transactional
    public void deleteComment(Long postId, Long commentId, UserEntity user){
        findByPostIdOrElseThrow(postId);
        CommentEntity comment = findByCommentIdOrElseThrow(commentId);
        if (!comment.getUser().getId().equals(user.getId())){
            throw CustomException.of(CustomErrorCode.NOT_GRANTED_ACCESS);
        }
        commentRepository.deleteById(commentId);
    }

    /**
     * 좋아요 & 싫어요 개수 가져오기
     * @param postId 좋아요 & 싫어요 포스트 id
     */
    @Transactional(readOnly = true)
    public Map<String, Long> getLikeCount(Long postId){
        PostEntity post = findByPostIdOrElseThrow(postId);
        return Map.of(
                "LIKE", likeRepository.getLikeCountByPostAndLikeType(post, LikeType.LIKE),
                "HATE", likeRepository.getLikeCountByPostAndLikeType(post, LikeType.HATE)
        );
    }

    /**
     * 좋아요 & 싫어요 요청
     * @param postId 좋아요 & 싫어요 포스트 id
     * @param likeType LIKE(좋아요), HATE(싫어요)
     * @param user like 누른사람
     */
    @Transactional
    public void likePost(Long postId, LikeType likeType, UserEntity user){
        PostEntity post = findByPostIdOrElseThrow(postId);
        UserEntity author = post.getUser();                     // 글쓴이
        likeRepository.findByUserAndPostAndLikeType(user, post, likeType).ifPresent(it->{
            if (it.getLikeType().equals(likeType)){
                // 이미 좋아요를 누르고 좋아요를 누른 경우 or 이미 싫어요를 누르고 싫어요를 누른 경우 → 에러
                throw CustomException.of(CustomErrorCode.ALREADY_LIKED);
            } else {
                // 좋아요를 누르고 싫어요를 누른 경우 → 좋아요 삭제
                // 싫어요를 누르고 좋아요를 누른 경우 → 싫어요 삭제
                likeRepository.deleteById(it.getId());
            }
        });
        // 좋아요 저장
        likeRepository.save(LikeEntity.of(user, post, likeType));
        // 알림 저장
        String message = String.format("[%s]님이 게시글 [%s]에 %s를 눌렀습니다", user.getNickname(), post.getTitle() ,likeType==LikeType.LIKE?"좋아요":"싫어요");
        NotificationEntity notification = notificationRepository.save(NotificationEntity.of(author, post, NotificationType.NEW_LIKE_ON_POST, message));
        // 알림 보내기
        notificationService.sendNotification(NotificationEvent.from(notification));
    }

    @Transactional(readOnly = true)
    private UserEntity findByUsernameOrElseThrow(String username){
        return userRepository.findByUsername(username).orElseThrow(()->{
            throw CustomException.of(CustomErrorCode.USERNAME_NOT_FOUND);
        });
    }

    @Transactional(readOnly = true)
    private PostEntity findByPostIdOrElseThrow(Long postId){
        return postRepository.findById(postId).orElseThrow(()->{
            throw CustomException.of(CustomErrorCode.POST_NOT_FOUND);
        });
    }

    @Transactional(readOnly = true)
    private CommentEntity findByCommentIdOrElseThrow(Long commentId){
        return commentRepository.findById(commentId).orElseThrow(()->{
            throw CustomException.of(CustomErrorCode.COMMENT_NOT_FOUND);
        });
    }
}