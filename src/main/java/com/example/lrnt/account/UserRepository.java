package com.example.lrnt.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<DatabaseUser, Long> {
    boolean existsByEmail(String email);
    boolean existsByName(String name);
    boolean existsByKey(String key);
    List<DatabaseUser> findAllByKey(String key);
    void deleteByKey(String key);
}
