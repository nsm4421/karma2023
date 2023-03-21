package com.karma.community.repository;

import com.karma.community.model.entity.Article;
import com.karma.community.model.entity.UserAccount;
import com.karma.community.model.util.RoleType;
import com.karma.community.model.util.UserStatus;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DataJpaTest
class ArticleRepositoryTest {

    private final ArticleRepository sut;
    private final UserAccountRepository userAccountRepository;

    ArticleRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired UserAccountRepository userAccountRepository
    ) {
        this.sut = articleRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @BeforeEach
    private void setUp(){
        UserAccount user = UserAccount.of(
                "test_username",
                "test_password",
                "test_email",
                "test_nickname",
                RoleType.USER,
                UserStatus.ACTIVE
        );
        userAccountRepository.save(user);
    }

    @AfterEach
    private void teardown(){
        UserAccount user = userAccountRepository.findByUsername("test_username").orElseThrow();
        userAccountRepository.delete(user);
    }


    @Test
    @DisplayName("insert 테스트")
    private void addArticle(){
        UserAccount user = userAccountRepository.findByUsername("test_username").orElseThrow();
        Article article = Article.of(user, "test","test",null,null);
        sut.saveAndFlush(article);
    }
}