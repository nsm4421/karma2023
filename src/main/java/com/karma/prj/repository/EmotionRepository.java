package com.karma.prj.repository;

import com.karma.prj.model.entity.EmotionEntity;
import com.karma.prj.model.entity.PostEntity;
import com.karma.prj.model.entity.UserEntity;
import com.karma.prj.model.util.EmotionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmotionRepository extends JpaRepository<EmotionEntity, Long> {
    Optional<EmotionEntity> findByUserAndPost(UserEntity user, PostEntity post);
    Optional<EmotionEntity> findByUserAndPostAndEmotionType(UserEntity user, PostEntity post, EmotionType emotionType);
    @Modifying
    @Query(value = "UPDATE EmotionEntity entity SET removed_at = NOW() WHERE entity.post = :post", nativeQuery = true)
    void deleteAllByPost(PostEntity post);

    // 좋아요/싫어요 개수 가져오기
    Long countByPostAndEmotionType(PostEntity post, EmotionType emotionType);
}
