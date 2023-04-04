package com.example.lrnt.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<DatabaseUser, Long> {
    boolean existsByEmail(String email);
    boolean existsByName(String name);
    boolean existsById(String id);
    List<DatabaseUser> findAllById(String id);
    void deleteById(String key);
}
