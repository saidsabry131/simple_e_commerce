package com.example.e_commerce.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.e_commerce.entity.MyUser;
import com.example.e_commerce.repositroy.MyUserRepo;
import com.example.e_commerce.util.MyUserDetails;
@Service
public class MyUserDetailsService implements UserDetailsService{

    private final MyUserRepo myUserRepo;
    public MyUserDetailsService(MyUserRepo myUserRepo) {
        this.myUserRepo = myUserRepo;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<MyUser> user= myUserRepo.findMyUserByEmail(username);

    user.orElseThrow(()->new UsernameNotFoundException("username not found "));

        return new MyUserDetails(user.get());
    }
    
}
