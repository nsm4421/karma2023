package com.karma.prj.service;

import com.karma.prj.exception.CustomErrorCode;
import com.karma.prj.exception.CustomException;
import com.karma.prj.model.dto.CommentDto;
import com.karma.prj.model.dto.PostDto;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    /**
     * 포스트 작성요청
     * @param title : 제목
     * @param content : 본문
     * @param username : 로그인한 유저명
     * @return 저장된 post id
     */
    @Transactional
    public Long create(String title, String content, String username){
        UserEntity user = userRepository.findByUsername(username).orElseThrow(()->{
            throw CustomException.of(CustomErrorCode.USERNAME_NOT_FOUND);
        });
        return postRepository.save(PostEntity.of(title, content, user)).getId();
    }

    /**
     * 포스트 단건조회
     * @param postId 조회할 포스트 id
     * @return PostDto
     */
    @Transactional(readOnly = true)
    public PostDto getPost(Long postId){
        return PostEntity.dto(postRepository.findById(postId).orElseThrow(()->{throw CustomException.of(CustomErrorCode.POST_NOT_FOUND);}));
    }

    /**
     * 포스트 조회
     */
    @Transactional(readOnly = true)
    public Page<PostDto> getPosts(Pageable pageable){
        return postRepository.findAll(pageable).map(PostEntity::dto);
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
    public Long modify(Long postId, String title, String content, String username){
        UserEntity user = userRepository.findByUsername(username).orElseThrow(()->{
            throw CustomException.of(CustomErrorCode.USERNAME_NOT_FOUND);
        });
        PostEntity post = postRepository.findById(postId).orElseThrow(()->{
            throw CustomException.of(CustomErrorCode.POST_NOT_FOUND);
        });
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
    public Long delete(Long postId, String username){
        UserEntity user = userRepository.findByUsername(username).orElseThrow(()->{
            throw CustomException.of(CustomErrorCode.USERNAME_NOT_FOUND);
        });
        PostEntity post = postRepository.findById(postId).orElseThrow(()->{
            throw CustomException.of(CustomErrorCode.POST_NOT_FOUND);
        });
        if (!post.getUser().getUsername().equals(username)){
            // 포스트 작성자와 삭제 요청한 사람이 일치하는지 확인
            throw CustomException.of(CustomErrorCode.NOT_GRANTED_ACCESS);
        }
        postRepository.deleteById(postId);
        return postId;
    }
}