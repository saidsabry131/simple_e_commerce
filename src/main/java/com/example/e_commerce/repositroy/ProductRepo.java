package com.example.e_commerce.repositroy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.e_commerce.entity.Product;

public interface ProductRepo extends JpaRepository<Product,Integer> {
    @Query("SELECT p FROM Product p JOIN p.category c WHERE c.categoryId = :categoryId")
    List<Product> findByCategoryId(@Param("categoryId") int id);

    // List<Product> findAllByCategory_Id(int id);
    
}
