package com.example.e_commerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.e_commerce.entity.Category;
import com.example.e_commerce.entity.Product;
import com.example.e_commerce.repositroy.ProductRepo;

@Service
public class ProductService {
    
    private final ProductRepo productRepo;
    public ProductService(ProductRepo productRepo)
    {
        this.productRepo=productRepo;
    }


    public List<Product> getAllProducts()
    {
        return productRepo.findAll();
    }

    public void addProduct(Product product)
    {
        productRepo.save(product);
    }

    public void deleteProduct(int id)
    {
        productRepo.deleteById(id);
    }

    public Optional<Product> getProductById(int id)
    {
        return productRepo.findById(id);
    }


    public List<Product> getProductByCategoryId(int id)
    {
        return productRepo.findByCategoryId(id);
    }
}
