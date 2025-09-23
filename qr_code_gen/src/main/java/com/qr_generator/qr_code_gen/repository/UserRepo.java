package com.qr_generator.qr_code_gen.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qr_generator.qr_code_gen.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User,Long>{

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}
