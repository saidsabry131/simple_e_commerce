package com.example.e_commerce.util;
import java.util.*;

import com.example.e_commerce.entity.Product;

public class GlobalData {
    public static List<Product> cart;
    public static double total=0.0;

    static{
        cart=new ArrayList<>();
        
    }

    public static void addProduct(Product product)
    {
        cart.add(product);
        total+=product.getPrice();
    }
}
