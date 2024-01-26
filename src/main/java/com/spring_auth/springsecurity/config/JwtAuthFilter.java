package com.spring_auth.springsecurity.config;

import com.spring_auth.springsecurity.service.imp.jwtImp.UserDetailsServiceImp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    //decode the token and get the details to check if it matches with one stored in the db
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token =  null;
        String email = null;
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);//remove the first 6 char plus whitespace to get the token
            email = jwtUtil.extractUsername(token);

        }
        if(email != null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = userDetailsServiceImp.loadUserByUsername(email);
            if(jwtUtil.validateToken(token, userDetails)){
                // create an authentication object and set it to SecurityContextHolder
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
