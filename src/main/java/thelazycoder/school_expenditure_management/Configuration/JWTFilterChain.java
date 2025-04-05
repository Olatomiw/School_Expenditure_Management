package thelazycoder.school_expenditure_management.Configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import thelazycoder.school_expenditure_management.ServiceImpl.CustomUserDetailsService;

import java.io.IOException;

@Component
public class JWTFilterChain extends OncePerRequestFilter {

    private final JWTConfig jwtConfig;
    private final CustomUserDetailsService customUserDetailsService;

    public JWTFilterChain(JWTConfig jwtConfig, CustomUserDetailsService customUserDetailsService) {
        this.jwtConfig = jwtConfig;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getJwt(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        String username = jwtConfig.extractUsername(token);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            if (userDetails != null && jwtConfig.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
    }

    public String getJwt(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            return token;
        }
        return null;
    }


}
