package com.example.Colorful_World.repository;

import com.example.Colorful_World.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    //이메일이 db에 저장되었는지 확인
    Boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);
}
