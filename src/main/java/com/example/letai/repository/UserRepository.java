package com.example.letai.repository;

import com.example.letai.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Transactional(readOnly = true)
@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {


    Optional<UserEntity> findById(Long id);
    @Transactional
    //đảm bảo giao dịch sẽ dc mở và đóng sau khi phương thức hoàn thanh neu lỗi sẽ rollback
    @Modifying
    //dùng để chỉ định rằng phương thức này sẽ thực hiện các thay đổi
    UserEntity save(UserEntity user);

    Long countById(Long id);

    Optional<UserEntity> findByEmail(String email);


    @Modifying
    @Transactional
    @Query("UPDATE UserEntity a " +
            "SET a.enabled = TRUE " +
            "WHERE a.email = ?1")
    int enableAppUser(String username);
}

