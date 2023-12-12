package com.weekytripstravelcrm.security;

import com.weekytripstravelcrm.service.AdminDetailsServiceImp;
import com.weekytripstravelcrm.util.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class  JwtAuthenticationFilter extends OncePerRequestFilter {
    Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Autowired
    private AdminDetailsServiceImp userAuthenticationService;
    @Autowired
    private JwtUtils jwtUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

       final String requestTokenHeader = request.getHeader("Authorization");
       String username = null;
       String jwtToken = null;

       if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
           try {
               jwtToken = requestTokenHeader.substring(7);
               username = this.jwtUtils.extractUsername(jwtToken);
           } catch (ExpiredJwtException e) {
               log.error("Jwt token has expired !." + e.getMessage());
           } catch (Exception e) {
               log.error("May be system error Please check it !." + e.getMessage());
               throw  new RuntimeException("May be system error Please check it !."+ e.getMessage());
           }

       } else {
           log.error("Invalid token, not start with bearer string");
       }
       // validated token
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            final UserDetails userDetails = this.userAuthenticationService.loadUserByUsername(username);
            if (this.jwtUtils.validateToken(jwtToken, userDetails)) { // token is valid
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }
        } else {
            log.error("Token is not valid");
        }
        filterChain.doFilter(request, response);
    }
}
