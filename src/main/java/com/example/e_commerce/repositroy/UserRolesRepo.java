package com.example.e_commerce.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.e_commerce.entity.UserRoles;

public interface UserRolesRepo extends JpaRepository<UserRoles,Integer>{
    
}
