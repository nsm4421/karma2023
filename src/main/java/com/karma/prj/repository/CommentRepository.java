package com.karma.prj.repository;

import com.karma.prj.model.entity.CommentEntity;
import com.karma.prj.model.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Page<CommentEntity> findAllByPost(PostEntity post, Pageable pageable);
    @Modifying
    @Query(value = "UPDATE CommentEntity entity SET removed_at = NOW() where entity.post = :post", nativeQuery = true)
    void deleteAllByPost(@Param("post") PostEntity post);
}
