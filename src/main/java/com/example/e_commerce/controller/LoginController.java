package com.example.e_commerce.controller;

import java.util.ArrayList;
import java.util.List;

import javax.print.DocFlavor.STRING;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.e_commerce.entity.MyUser;
import com.example.e_commerce.entity.UserRoles;
import com.example.e_commerce.repositroy.MyUserRepo;
import com.example.e_commerce.repositroy.UserRolesRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class LoginController {
    

    private final PasswordEncoder passwordEncoder;
    private final MyUserRepo userRepo;

    private final UserRolesRepo rolesRepo;

    
    private final AuthenticationManager authenticationManager;

    public LoginController(PasswordEncoder passwordEncoder, MyUserRepo userRepo, UserRolesRepo rolesRepo,AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.rolesRepo = rolesRepo;
        this.authenticationManager=authenticationManager;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new MyUser());
        return "register";
    }
    
    @PostMapping("/register")
    public String register(@ModelAttribute("user") MyUser user,HttpServletRequest request) throws ServletException {
        String password=user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        List<UserRoles> rolesList=new ArrayList<>();
        rolesList.add(rolesRepo.findById(2).get());
        user.setUserRoles(rolesList);

        userRepo.save(user);
        // after save in database  should login method 
          /*  1 - first way 
                    request.login(user.getEmail(), user.getPassword());
                2- UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

                // Set authentication in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authToken);

            3- 
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
                    Authentication authentication = authenticationManager.authenticate(authToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);


          */

        // UsernamePasswordAuthenticationToken token=
        //                 new UsernamePasswordAuthenticationToken(user.getEmail(),password);

        // Authentication authentication = authenticationManager.authenticate(token);

        // SecurityContextHolder.getContext().setAuthentication(authentication);

        request.login(user.getEmail(), password);// login auto
        
        return "redirect:/";
    }
    


}
