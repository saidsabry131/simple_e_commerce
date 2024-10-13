package com.example.e_commerce.repositroy;

import com.example.e_commerce.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Integer>{
    
}
