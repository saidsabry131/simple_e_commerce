package com.example.e_commerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.e_commerce.entity.Product;
import com.example.e_commerce.service.ProductService;
import com.example.e_commerce.util.GlobalData;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CartController {
    
    private final ProductService productService;

    public CartController(ProductService productService) {
        this.productService = productService;
    }
    

    @GetMapping("/addToCart/{id}")
    public String getMethodName(@PathVariable int id) {

        GlobalData.addProduct(productService.getProductById(id).get());

        return "redirect:/shop";
    }
    
    @GetMapping("/cart")
    public String getCart(Model model) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", GlobalData.total);
        model.addAttribute("cart", GlobalData.cart);
        return "cart";
    }
    

    @GetMapping("/cart/removeItem/{id}")
    public String removeItem(@PathVariable int id) {
        GlobalData.cart.remove(id);
        return "redirect:/cart";
    }
    
}
