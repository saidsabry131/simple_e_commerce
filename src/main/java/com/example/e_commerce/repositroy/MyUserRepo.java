package com.example.e_commerce.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.e_commerce.entity.MyUser;
import java.util.List;
import java.util.Optional;


public interface MyUserRepo extends JpaRepository<MyUser,Integer> {
    Optional<MyUser>  findMyUserByEmail(String email);
}
