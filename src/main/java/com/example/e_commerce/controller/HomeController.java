package com.example.e_commerce.controller;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.e_commerce.entity.Product;
import com.example.e_commerce.service.CategoryService;
import com.example.e_commerce.service.ProductService;
import com.example.e_commerce.util.GlobalData;

import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class HomeController {
    
    private final CategoryService categoryService;
    private final ProductService productService;
    public HomeController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping({"/","/home"})
    public String home(Model model) {
        System.out.println("home page////////////////////////////////////////// ");
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "index";
    }
    
    @GetMapping("/shop")
    public String shop(Model model) {

        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("products", productService.getAllProducts());
        return "shop";
    }

    @GetMapping("/shop/category/{id}")
    public String getProductByCategory(@PathVariable int id,Model model) {
       // List<Product> product = productService.getProductByCategoryId(id).iterator();
        model.addAttribute("categories",  categoryService.getAllCategories());
        model.addAttribute("products", productService.getProductByCategoryId(id));
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "shop";
    }
    
    @GetMapping("/shop/product/{id}")
    public String viewProduct(@PathVariable int id,Model  model) {
        Product product=productService.getProductById(id).get();
        model.addAttribute("product", product);
        return "viewProduct";
    }
    
    
}
