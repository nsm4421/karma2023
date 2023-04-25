package com.karma.myapp.repository;

import com.karma.myapp.domain.entity.AlarmEntity;
import com.karma.myapp.domain.entity.UserAccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<AlarmEntity, Long> {
    Page<AlarmEntity> findByUser(UserAccountEntity user, Pageable pageable);
    void deleteByUser(UserAccountEntity user);
}
