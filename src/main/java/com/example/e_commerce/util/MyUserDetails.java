package com.example.e_commerce.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.e_commerce.entity.MyUser;

public class MyUserDetails extends MyUser implements UserDetails {

    public MyUserDetails(MyUser myUser)
    {
        super(myUser);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority>authorities= new ArrayList<>();

        super.getUserRoles().forEach(
            role->authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()))
        );

        return authorities;
    }

    @Override
    public String getUsername() {
        return super.getEmail();
    }

    @Override
    public String getPassword()
    {
        return super.getPassword();
    }
}
