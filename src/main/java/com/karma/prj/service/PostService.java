package com.karma.prj.service;

import com.karma.prj.exception.CustomErrorCode;
import com.karma.prj.exception.CustomException;
import com.karma.prj.model.entity.PostEntity;
import com.karma.prj.model.entity.UserEntity;
import com.karma.prj.repository.PostRepository;
import com.karma.prj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    /**
     * 포스트 작성
     * @param title 제목
     * @param content 본문
     * @param username 작성자
     * @Return
     */
    @Transactional
    public void create(String title, String content, String username){
        UserEntity user = userRepository.findByUsername(username).orElseThrow(()->{
            throw CustomException.of(CustomErrorCode.USERNAME_NOT_FOUND);
        });
        postRepository.save(PostEntity.of(title, content, user));
    }
}
