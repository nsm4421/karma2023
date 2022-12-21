package com.karma.prj.service;

import com.karma.prj.exception.CustomErrorCode;
import com.karma.prj.exception.CustomException;
import com.karma.prj.model.dto.CommentDto;
import com.karma.prj.model.dto.PostDto;
import com.karma.prj.model.entity.CommentEntity;
import com.karma.prj.model.entity.LikeEntity;
import com.karma.prj.model.entity.PostEntity;
import com.karma.prj.model.entity.UserEntity;
import com.karma.prj.model.util.LikeType;
import com.karma.prj.repository.CommentRepository;
import com.karma.prj.repository.LikeRepository;
import com.karma.prj.repository.PostRepository;
import com.karma.prj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    /**
     * 포스트 작성요청
     * @param title : 제목
     * @param content : 본문
     * @param username : 로그인한 유저명
     * @return 저장된 post id
     */
    @Transactional
    public Long createPost(String title, String content, String username){
        UserEntity user = findByUsernameOrElseThrow(username);
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
     * @param username 로그인한 유저명
     * @return 저장된 포스트 id
     */
    @Transactional
    public Long modifyPost(Long postId, String title, String content, String username){
        UserEntity user = findByUsernameOrElseThrow(username);
        PostEntity post = findByPostIdOrElseThrow(postId);
        if (!post.getUser().getUsername().equals(username)){
            // 포스트 작성자와 수정 요청한 사람이 일치하는지 확인
            throw CustomException.of(CustomErrorCode.NOT_GRANTED_ACCESS);
        }
        post.setTitle(title);
        post.setContent(content);
        return postRepository.save(PostEntity.of(title, content, user)).getId();
    }

    /**
     * 포스트 삭제요청
     * @param postId 삭제요청한 포스트 id
     * @param username 로그인한 유저명
     * @return 저장된 포스트 id
     */
    @Transactional
    public Long deletePost(Long postId, String username){
        findByUsernameOrElseThrow(username);
        PostEntity post = findByPostIdOrElseThrow(postId);
        if (!post.getUser().getUsername().equals(username)){
            // 포스트 작성자와 삭제 요청한 사람이 일치하는지 확인
            throw CustomException.of(CustomErrorCode.NOT_GRANTED_ACCESS);
        }
        postRepository.deleteById(postId);
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
     * @param postId
     * @param content
     * @param username
     * @return
     */
    @Transactional
    public CommentDto createComment(Long postId, String content, String username){
        UserEntity user = findByUsernameOrElseThrow(username);
        PostEntity post = findByPostIdOrElseThrow(postId);
        return CommentEntity.dto(commentRepository.save(CommentEntity.of(content, user, post)));
    }

    /**
     * 댓글 수정
     * @param postId
     * @param commentId
     * @param content 수정할 댓글내용
     * @param username 자기가 작성한 댓글인지 확인하기 위해 authentication에서 추출한 username
     * @return 댓글 Dto
     */
    @Transactional
    public CommentDto modifyComment(Long postId, Long commentId, String content, String username){
        findByPostIdOrElseThrow(postId);
        CommentEntity comment = findByCommentIdOrElseThrow(commentId);
        if (!comment.getUser().getUsername().equals(username)){
            throw CustomException.of(CustomErrorCode.NOT_GRANTED_ACCESS);
        }
        comment.setContent(content);
        return CommentEntity.dto(commentRepository.save(comment));
    }

    /**
     * 댓글 삭제
     * @param postId
     * @param commentId
     * @param username 자기가 작성한 댓글인지 확인하기 위해 authentication에서 추출한 username
     */
    @Transactional
    public void deleteComment(Long postId, Long commentId, String username){
        findByPostIdOrElseThrow(postId);
        CommentEntity comment = findByCommentIdOrElseThrow(commentId);
        if (!comment.getUser().getUsername().equals(username)){
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
     * @param username 유저명
     */
    @Transactional
    public void likePost(Long postId, LikeType likeType, String username){
        UserEntity user = findByUsernameOrElseThrow(username);
        PostEntity post = findByPostIdOrElseThrow(postId);
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
        likeRepository.save(LikeEntity.of(user, post, likeType));
    }

    private UserEntity findByUsernameOrElseThrow(String username){
        return userRepository.findByUsername(username).orElseThrow(()->{
            throw CustomException.of(CustomErrorCode.USERNAME_NOT_FOUND);
        });
    }
    private PostEntity findByPostIdOrElseThrow(Long postId){
        return postRepository.findById(postId).orElseThrow(()->{
            throw CustomException.of(CustomErrorCode.POST_NOT_FOUND);
        });
    }

    private CommentEntity findByCommentIdOrElseThrow(Long commentId){
        return commentRepository.findById(commentId).orElseThrow(()->{
            throw CustomException.of(CustomErrorCode.COMMENT_NOT_FOUND);
        });
    }
}