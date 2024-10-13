package com.example.e_commerce.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.print.DocFlavor.STRING;

import com.example.e_commerce.dto.ProductDto;
import com.example.e_commerce.entity.Category;
import com.example.e_commerce.entity.Product;
import com.example.e_commerce.exception.CategoryNotFound;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.e_commerce.service.CategoryService;
import com.example.e_commerce.service.ProductService;
import com.nimbusds.jose.proc.SecurityContext;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.nio.file.Path;





@Controller
@RequestMapping("/admin")
public class AdminController {
    public static String uploadDir=System.getProperty("user.dir")+"/src/main/resources/static/productImages";

    private  final CategoryService categoryService;
    private final ProductService productService;
    

    public AdminController(CategoryService categoryService,ProductService productService)
    {
        this.categoryService=categoryService;
        this.productService=productService;
        
    }
    
    @GetMapping("/")
    public String adminHome() {
        
        return "adminHome";
    }

    @GetMapping("/categories")
    public String getCategories(Model model) {

        List<Category> categories=categoryService.getAllCategories();
        
        model.addAttribute("categories",categories);
        
        return "categories";
    }

    @GetMapping("/categories/add")
    public String addCatgory(Model model) {
        com.example.e_commerce.entity.Category category=new com.example.e_commerce.entity.Category();
        model.addAttribute("category", category);
        
        return "categoriesAdd";
    }

    @PostMapping("/categories/add")
    public String addCategory(@ModelAttribute("category") com.example.e_commerce.entity.Category category) {
        
        categoryService.addCategory(category);
        
        return "redirect:/admin/categories";
    }
    

    @GetMapping("/categories/delete/{catId}")
    public String requestMethodName(@PathVariable("catId") int id) {

        categoryService.deleteCategory(id);
        return "redirect:/admin/categories";
    }
    
    @GetMapping("/categories/update/{catId}")
    public String showUpdateForm(@PathVariable("catId") int id,Model model)
    {
        Optional<Category> category=categoryService.getById(id);

        if (category.isPresent()) {
            model.addAttribute("category",category.get() );
            return "updateCategory"; 
        }
        else{
            return "404";
        }

        
    }
    

    @PostMapping("/categories/update/{catId}")
    public String updateCategory(@PathVariable("catId") int id,@ModelAttribute Category category)
    {
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }
    
    ///  products

    @GetMapping("/products")
    public String getProducts(Model model) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
    
        if (authentication != null) {
            System.out.println("User context: " + authentication.getAuthorities());
        } else {
            System.out.println("No authentication found.");
        }
    
        model.addAttribute("products", productService.getAllProducts());
        
        return "products";
    }
    
    // add products

    @GetMapping("/products/add")
    public String addProducts(Model model) {
        

        model.addAttribute("productDTO", new ProductDto());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "productsAdd";
    }

    @PostMapping("/products/add")
    public String addProducts(Model model,
                            @ModelAttribute("productDTO") ProductDto productDto,  // Retrieves form data bound to the ProductDto object
                            @RequestParam("productImage") MultipartFile file,    // Retrieves the uploaded file (image) from the form
                            @RequestParam("imgName") String imgName              // Retrieves the existing image name (if applicable)
    ) throws IOException {
        // Create a new Product entity and populate its fields from the productDto object
        Product product = new Product();
        product.setId(productDto.getId());  // Set the product ID from productDto
        product.setDescription(productDto.getDescription());  // Set the product description
        product.setName(productDto.getName());  // Set the product name
        product.setCategory(categoryService.getById(productDto.getCategoryId()).get());  // Fetch the category from the database using the category ID from productDto
        product.setPrice(productDto.getPrice());  // Set the product price
        product.setWeight(productDto.getWeight());  // Set the product weight

        String imageId;  // Variable to hold the image file name

        // Check if a new image file was uploaded
        if (!file.isEmpty()) {
            imageId = file.getOriginalFilename();  // Get the original filename of the uploaded image
            Path fileNameAndPath = Paths.get(uploadDir, imageId);  // Build the path to where the image will be saved

            // Write the file bytes to the target location
            Files.write(fileNameAndPath, file.getBytes());
        } else {
            // If no new image was uploaded, use the existing image name (imgName)
            imageId = imgName;
        }

        // Set the image name for the product (either new or existing)
        product.setImageName(imageId);

        productService.addProduct(product);
        // Redirect to the admin products page after saving the product
        return "redirect:/admin/products";
    }

    @GetMapping("/product/update/{id}")
    public String updateProduct(@PathVariable int id,Model model) {
        Product product=productService.getProductById(id).get();

        ProductDto productDto=new ProductDto();
        productDto.setId(product.getId());
        productDto.setCategoryId(product.getCategory().getCategoryId());
        productDto.setDescription(product.getDescription());
        productDto.setName(product.getName());
        productDto.setImageName(product.getImageName());
        productDto.setPrice(product.getPrice());
        productDto.setWeight(product.getWeight());

        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("productDTO", productDto);
        
        return "updateProduct";
    }

   

    @GetMapping("/product/delete/{id}")
    public String getMethodName(@PathVariable int id) {
        
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }
    
    
    
    
}
