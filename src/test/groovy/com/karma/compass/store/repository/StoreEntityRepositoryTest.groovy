package com.karma.compass.store.repository

import com.karma.compass.AbstractIntegrationBaseTest
import com.karma.compass.domain.store.StoreEntity
import com.karma.compass.repository.StoreEntityRepository
import org.springframework.beans.factory.annotation.Autowired

class StoreEntityRepositoryTest extends AbstractIntegrationBaseTest {
    @Autowired StoreEntityRepository storeEntityRepository;

    def setup(){
        storeEntityRepository.deleteAll();
    }

    def "Repository 정상동작 test"(){
        given:
            String address = "서울특별시 동작구 상도동"
            String name = "할리스커피"
            Double latitude = 12
            Double longitude = 12
            def store = StoreEntity.of(name, address, latitude, longitude);

        when:
            def saved = storeEntityRepository.save(store)

        then:
            saved.getAddress() == store.getAddress()
            saved.getName() == store.getName()
            saved.getLatitude() == store.getLatitude()
            saved.getLongitude() == store.getLongitude()
    }
}
