package com.karma.community.repository;

import com.karma.community.model.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByUsername(String username);
    Optional<UserAccount> findByNickname(String nickname);
    Optional<UserAccount> findByEmail(String email);
    void deleteByUsername(String username);
}
