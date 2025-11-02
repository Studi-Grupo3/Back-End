package sptech.school.v2.cleanarch.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sptech.school.v2.cleanarch.config.CorsConfig;
import sptech.school.v2.cleanarch.config.security.user.details.service.StudentUserDetailsService;
import sptech.school.v2.cleanarch.config.security.user.details.service.TeacherUserDetailsService;
import sptech.school.v2.cleanarch.core.application.usecases.security.JwtUseCase;
import sptech.school.v2.cleanarch.domain.exception.CustomAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUseCase jwtUseCase;
    private final StudentUserDetailsService studentUserDetailsService;
    private final TeacherUserDetailsService teacherUserDetailsService;
    private final CorsConfig corsConfig;

    public SecurityConfig(JwtUseCase jwtUseCase, StudentUserDetailsService studentUserDetailsService, TeacherUserDetailsService teacherUserDetailsService, CorsConfig corsConfig) {
        this.jwtUseCase = jwtUseCase;
        this.studentUserDetailsService = studentUserDetailsService;
        this.teacherUserDetailsService = teacherUserDetailsService;
        this.corsConfig = corsConfig;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                )
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
                .headers(headers -> headers.disable())
                .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/api/swagger-ui/**").permitAll()
                    .requestMatchers("/api/v3/api-docs/**").permitAll()
                    .requestMatchers("/api/swagger-ui.html").permitAll()
                    .requestMatchers("/h2-console/**").permitAll()
                    .requestMatchers("/api/health").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/students").permitAll()
                    .requestMatchers(HttpMethod.PATCH, "/api/students/reset-password").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/teachers").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/auths/login").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/auths/verify-code").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/auths/forgot-password").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/contact").permitAll()
                    .anyRequest().authenticated()
            )
            .sessionManagement(sessionManagement -> sessionManagement
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(
                    new JwtAuthenticationFilter(jwtUseCase, teacherUserDetailsService, studentUserDetailsService),
                    UsernamePasswordAuthenticationFilter.class
            )
            .build();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}