// package com.company.web.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;

// @Configuration
// public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
//     @Bean 
//     @Override 
//     public AuthenticationManager authenticationManagerBean() throws Exception{
//         return super.authenticationManagerBean();
//     }

//     @Bean 
//     @Override 
//     protected UserDetailsService userDetailsService(){
//         return super.userDetailsService();
//     }

//     @Override 
//     protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//         auth.inMemoryAuthentication()
//         .withUser("xiaohei")
//         .password(new BCryptPasswordEncoder().encode("123456"))
//         .roles("admin")
//         .and()
//         .withUser("xiaohua")
//         .password(new BCryptPasswordEncoder().encode("123456"))
//         .roles("user");
//     }

//     @Override 
//     protected void configure(HttpSecurity http) throws Exception{
//         http.antMatcher("/oauth/**")
//         .authorizeRequests()
//         .antMatchers("/oauth/**")
//         .permitAll()
//         .and()
//         .csrf()
//         .disable();
//     }
// }
