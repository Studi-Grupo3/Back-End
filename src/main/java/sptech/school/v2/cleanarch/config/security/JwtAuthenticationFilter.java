package sptech.school.v2.cleanarch.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import sptech.school.v2.cleanarch.config.security.user.details.service.StudentUserDetailsService;
import sptech.school.v2.cleanarch.config.security.user.details.service.TeacherUserDetailsService;
import sptech.school.v2.cleanarch.core.application.usecases.security.JwtUseCase;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtUseCase jwtUseCase;
    private final TeacherUserDetailsService teacherUserDetailsService;
    private final StudentUserDetailsService studentUserDetailsService;

    public JwtAuthenticationFilter(JwtUseCase jwtUseCase,
                                   TeacherUserDetailsService teacherUserDetailsService,
                                   StudentUserDetailsService studentUserDetailsService) {
        this.jwtUseCase = jwtUseCase;
        this.studentUserDetailsService = studentUserDetailsService;
        this.teacherUserDetailsService = teacherUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7).trim();
        if (token.isEmpty()) {
            logger.debug("Authorization header presente mas token vazio — ignorando e seguindo a cadeia");
            chain.doFilter(request, response);
            return;
        }

        try {
            boolean valid = jwtUseCase.validateToken(token);
            if (!valid) {
                logger.debug("Token inválido ou expirado — não será setada autenticação");
                chain.doFilter(request, response);
                return;
            }

            String email = jwtUseCase.extractEmail(token);
            String role = jwtUseCase.extractRole(token);

            if (email == null || role == null) {
                logger.debug("Token válido mas email/role não encontrados — seguiremos sem autenticar");
                chain.doFilter(request, response);
                return;
            }

            UserDetails userDetails = "TEACHER".equalsIgnoreCase(role)
                    ? teacherUserDetailsService.loadUserByUsername(email)
                    : "STUDENT".equalsIgnoreCase(role)
                        ? studentUserDetailsService.loadUserByUsername(email)
                        : null;

            if (userDetails == null) {
                logger.debug("UserDetails retornou nulo para email {} — seguindo sem autenticar", email);
                chain.doFilter(request, response);
                return;
            }

            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.toUpperCase());
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, List.of(authority));
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (Exception ex) {
            logger.debug("Falha ao processar JWT: {} — seguindo sem autenticar", ex.getMessage());
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);
    }
}
