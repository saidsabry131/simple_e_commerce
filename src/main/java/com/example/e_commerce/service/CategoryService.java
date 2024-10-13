package com.example.e_commerce.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import com.example.e_commerce.entity.Category;
import com.example.e_commerce.exception.CategoryNotFound;


import org.springframework.stereotype.Service;

import com.example.e_commerce.repositroy.CategoryRepo;

@Service
public class CategoryService {
    private final CategoryRepo categoryRepo;

    public CategoryService(CategoryRepo categoryRepo)
    {
        this.categoryRepo=categoryRepo;
    }

    public Category addCategory(Category category)
    {

        return categoryRepo.save(category);
    }

    public List<Category> getAllCategories()
    {
        return categoryRepo.findAll();
    }

    public void deleteCategory(int id) {
        categoryRepo.deleteById(id);
    }

    public Category updateCategory(Category category)
    {
        return categoryRepo.save(category);
    }

    public Optional<Category> getById(int id) {
        return categoryRepo.findById(id);
    }
    
}
