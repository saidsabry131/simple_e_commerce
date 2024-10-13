package com.example.e_commerce.dto;


import lombok.Data;

@Data
public class ProductDto {
    
    
    int id;
    private String name;
    private int categoryId;
    private double price;
    private double weight;
    private String description;
    private String imageName;
}
