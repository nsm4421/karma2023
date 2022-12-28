package com.karma.prj.repository;

import com.karma.prj.model.entity.LikeEntity;
import com.karma.prj.model.entity.PostEntity;
import com.karma.prj.model.entity.UserEntity;
import com.karma.prj.model.util.LikeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByUserAndPostAndLikeType(UserEntity user, PostEntity post, LikeType likeType);
    @Modifying
    @Query(value = "UPDATE LikeEntity entity SET removed_at = NOW() WHERE entity.post = :post", nativeQuery = true)
    void deleteAllByPost(@Param("post") PostEntity post);
    // 좋아요/싫어요 개수 가져오기
    @Query(value = "SELECT COUNT(*) FROM LikeEntity entity WHERE entity.post = :post AND entity.likeType = :likeType", nativeQuery = true)
    Long getLikeCountByPostAndLikeType(@Param("post") PostEntity post, @Param("likeType") LikeType likeType);
}
