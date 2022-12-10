package com.karma.compass.repository;

import com.karma.compass.domain.store.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreEntityRepository extends JpaRepository<StoreEntity, Long> {
}
