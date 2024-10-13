package com.example.e_commerce.config;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.e_commerce.entity.MyUser;
import com.example.e_commerce.entity.UserRoles;
import com.example.e_commerce.repositroy.MyUserRepo;
import com.example.e_commerce.repositroy.UserRolesRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class GoogleOAuthSuccessHandler  implements AuthenticationSuccessHandler{
    private final MyUserRepo myUserRepo;
    private final UserRolesRepo rolesRepo;


    private RedirectStrategy strategy=new DefaultRedirectStrategy();

    public GoogleOAuthSuccessHandler(MyUserRepo myUserRepo, UserRolesRepo rolesRepo) {
        this.myUserRepo = myUserRepo;
        this.rolesRepo = rolesRepo;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        

            OAuth2AuthenticationToken token=
                                    (OAuth2AuthenticationToken) authentication;

                //String email=SecurityContextHolder.getContext().getAuthentication().getName();
                String email=token.getPrincipal().getAttribute("email");
            if (!(myUserRepo.findMyUserByEmail(email).isPresent())) {
                MyUser user=new MyUser();
                user.setFirstName(token.getPrincipal().getAttributes().get("given_name").toString());
                user.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());
                user.setPassword("oauth_default_password");  // Assign a default password
                user.setEmail(email);
                List<UserRoles> rolesList=new ArrayList<>();
                rolesList.add(rolesRepo.findById(2).get());

                user.setUserRoles(rolesList);
                System.out.println("user is "+user.getFirstName());
                System.out.println("user is "+user.getEmail());
                System.out.println("user is "+user.getPassword());
                myUserRepo.save(user);
            }

            strategy.sendRedirect(request,response,"/");
    }



}
