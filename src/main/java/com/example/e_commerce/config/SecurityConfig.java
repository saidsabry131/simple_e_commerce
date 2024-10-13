package com.example.e_commerce.config;

import org.springframework.context.annotation.Bean;
import com.example.e_commerce.service.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
@Configuration
@EnableWebSecurity
public class SecurityConfig   {


    private final MyUserDetailsService MyUserDetailsService;
    private final GoogleOAuthSuccessHandler googleOAuthSuccessHandler;

    public SecurityConfig(com.example.e_commerce.service.MyUserDetailsService myUserDetailsService,
            GoogleOAuthSuccessHandler googleOAuthSuccessHandler) {
        MyUserDetailsService = myUserDetailsService;
        this.googleOAuthSuccessHandler = googleOAuthSuccessHandler;
    }


    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity.authorizeHttpRequests(
            auth->auth.requestMatchers("/","/shop/**","/forgotpassword","/register").permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated()
            
        );

        httpSecurity.authenticationProvider(authenticationProvider());

        httpSecurity.formLogin(login->
                login.loginPage("/login")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll());


        httpSecurity.oauth2Login(
            t->
        t.loginPage("/login")
            .successHandler(googleOAuthSuccessHandler)
            
        );


        httpSecurity.logout(log->
            log. logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                
        );

        httpSecurity.csrf(csrf->csrf.disable());
        httpSecurity.cors(cors->cors.disable());

    


        return httpSecurity.build();

    }


    


    @Bean

    public PasswordEncoder encoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected AuthenticationProvider authenticationProvider()
    {

        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setUserDetailsService(MyUserDetailsService);
        provider.setPasswordEncoder(encoder());
    
        return provider;
    }




    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration ) throws Exception
    {
        return authenticationConfiguration.getAuthenticationManager();
    }





    // @Bean
    // @Primary
    // public SpringSecurityDialect springSecurityDialect(){
    //     return new SpringSecurityDialect();
    // }


    
}
