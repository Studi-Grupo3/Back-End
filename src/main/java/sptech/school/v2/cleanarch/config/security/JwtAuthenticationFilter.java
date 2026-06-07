package sptech.school.v2.cleanarch.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import sptech.school.v2.cleanarch.config.security.user.details.service.StudentUserDetailsService;
import sptech.school.v2.cleanarch.config.security.user.details.service.TeacherUserDetailsService;
import sptech.school.v2.cleanarch.core.application.usecases.security.JwtUseCase;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUseCase jwtUseCase;
    private final TeacherUserDetailsService teacherUserDetailsService;
    private final StudentUserDetailsService studentUserDetailsService;

    public JwtAuthenticationFilter(JwtUseCase jwtUseCase, TeacherUserDetailsService teacherUserDetailsService, StudentUserDetailsService studentUserDetailsService) {
        this.jwtUseCase = jwtUseCase;
        this.studentUserDetailsService = studentUserDetailsService;
        this.teacherUserDetailsService = teacherUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        String email = jwtUseCase.extractEmail(token);
        String role = jwtUseCase.extractRole(token);

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails;
            try {
                if ("TEACHER".equalsIgnoreCase(role)) {
                    userDetails = teacherUserDetailsService.loadUserByUsername(email);
                } else if ("STUDENT".equalsIgnoreCase(role)) {
                    userDetails = studentUserDetailsService.loadUserByUsername(email);
                } else {
                    userDetails = null;
                }
            } catch (UsernameNotFoundException e) {
                // User not found — email may have been changed; treat token as invalid
                chain.doFilter(request, response);
                return;
            }

            if (jwtUseCase.validateToken(token)) {
                GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, List.of(authority));

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        chain.doFilter(request, response);
    }
}