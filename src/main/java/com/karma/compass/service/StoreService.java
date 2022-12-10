package com.karma.compass.service;

import com.karma.compass.domain.store.StoreEntity;
import com.karma.compass.repository.StoreEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreEntityRepository storeEntityRepository;

    @Transactional(readOnly = true)
    private List<StoreEntity> findAll(){
        return storeEntityRepository.findAll();
    }

    @Transactional(readOnly = true)
    private Page<StoreEntity> findAll(Pageable pageable){
        return storeEntityRepository.findAll(pageable);
    }
}