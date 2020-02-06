//package com.oisou.security
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.builders.WebSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
//import org.springframework.security.config.http.SessionCreationPolicy
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
//import org.springframework.security.crypto.password.PasswordEncoder
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//class WebSecurityConfig(val jwtTokenProvider: JwtTokenProvider) : WebSecurityConfigurerAdapter() {
//
//    override fun configure(http: HttpSecurity) {
//
//        http.csrf().disable()
//
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//
//        http.authorizeRequests()
//            .antMatchers("/users/signin").permitAll()
//            .antMatchers("/users/signup").permitAll()
//            .anyRequest().authenticated()
//
//        http.exceptionHandling().accessDeniedPage("/login")
//
//        http.apply(JwtTokenFilterConfigurer(jwtTokenProvider))
//
//        // Optional, if you want to test the API from a browser
//        // http.httpBasic();
//    }
//
//    @Throws(Exception::class)
//    override fun configure(web: WebSecurity) {
//        // Allow swagger to be accessed without authentication
//        web.ignoring().antMatchers("/v2/api-docs")//
//            .antMatchers("/swagger-resources/**")//
//            .antMatchers("/swagger-ui.html")//
//            .antMatchers("/configuration/**")//
//            .antMatchers("/webjars/**")//
//            .antMatchers("/public")
//    }
//
//    @Bean
//    fun passwordEncoder(): PasswordEncoder {
//        return BCryptPasswordEncoder(12)
//    }
//}