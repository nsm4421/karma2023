package com.karma.prj.service;

import com.karma.prj.exception.CustomErrorCode;
import com.karma.prj.exception.CustomException;
import com.karma.prj.model.dto.CommentDto;
import com.karma.prj.model.entity.CommentEntity;
import com.karma.prj.model.entity.PostEntity;
import com.karma.prj.model.entity.UserEntity;
import com.karma.prj.repository.CommentRepository;
import com.karma.prj.repository.PostRepository;
import com.karma.prj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    /**
     * 댓글 조회
     * @param pageable
     * @param postId
     * @return 댓글 Dto Page
     */
    @Transactional(readOnly = true)
    public Page<CommentDto> getComments(Long postId, Pageable pageable){
        PostEntity post = postRepository.findById(postId).orElseThrow(()->{
            throw CustomException.of(CustomErrorCode.POST_NOT_FOUND);
        });
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
    public CommentDto create(Long postId, String content, String username){
        UserEntity user = userRepository.findByUsername(username).orElseThrow(()->{
            throw CustomException.of(CustomErrorCode.USERNAME_NOT_FOUND);
        });
        PostEntity post = postRepository.findById(postId).orElseThrow(()->{
            throw CustomException.of(CustomErrorCode.POST_NOT_FOUND);
        });
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
    public CommentDto modify(Long postId, Long commentId, String content, String username){
        PostEntity post = postRepository.findById(postId).orElseThrow(()->{
            throw CustomException.of(CustomErrorCode.POST_NOT_FOUND);
        });
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(()->{
            throw CustomException.of(CustomErrorCode.COMMENT_NOT_FOUND);
        });
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
    public void delete(Long postId, Long commentId, String username){
        PostEntity post = postRepository.findById(postId).orElseThrow(()->{
            throw CustomException.of(CustomErrorCode.POST_NOT_FOUND);
        });
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(()->{
            throw CustomException.of(CustomErrorCode.COMMENT_NOT_FOUND);
        });
        if (!comment.getUser().getUsername().equals(username)){
            throw CustomException.of(CustomErrorCode.NOT_GRANTED_ACCESS);
        }
        commentRepository.deleteById(commentId);
    }
}
