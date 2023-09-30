package com.example.letai;



import com.example.letai.model.entity.UserEntity;
import com.example.letai.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testAddUser(){
        UserEntity user = new UserEntity();
        user.setFullName("tran gian khang");
        user.setAddress("lo1,ham nghi");
        user.setEmail("tailx0913@gmai.com");
        user.setPassword("matkhau");
        UserEntity savedUser = userRepository.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }
    @Test
    public void testListAll(){
        Iterable<UserEntity> users = userRepository.findAll();
        Assertions.assertThat(users).hasSizeGreaterThan(0);
        for (UserEntity user: users) {
            System.out.println(user.toString() );
        }
    }
    @Test
    public void testUpdate(){
        long userId =5;
        Optional<UserEntity>  optionalUserEntity = userRepository.findById(userId);
        UserEntity user = optionalUserEntity.get();
        user.setFullName("nguyen quoc cuong");
        userRepository.save(user);

        UserEntity updateUser = userRepository.findById(userId).get();
        Assertions.assertThat(updateUser.getFullName()).isEqualTo("nguyen quoc cuong");
    }
    @Test
    public void testGet(){
        long userId=5;
        Optional<UserEntity>  optionalUserEntity = userRepository.findById(userId);
        Assertions.assertThat(optionalUserEntity).isPresent();
        System.out.println(optionalUserEntity.get().toString());
    }
    @Test
    public void testDelete(){
        long userId=7;
        userRepository.deleteById(userId);

        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
        Assertions.assertThat(optionalUserEntity).isNotPresent();
    }
}
