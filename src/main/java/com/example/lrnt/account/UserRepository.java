package com.example.lrnt.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<DatabaseUser, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsById(String id);

    List<DatabaseUser> findAllById(String id);
    List<DatabaseUser> findAllByEmail(String email);

    void deleteById(String key);

    @Modifying
    @Transactional
    @Query(value = "UPDATE lrnt.users SET confirmed = 1 WHERE id = :id", nativeQuery = true)
    void confirmUser(@Param("id") String id);
}
