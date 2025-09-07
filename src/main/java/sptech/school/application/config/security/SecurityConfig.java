package sptech.school.application.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sptech.school.application.config.CorsConfig;
import sptech.school.application.config.security.user.details.service.StudentUserDetailsService;
import sptech.school.application.config.security.user.details.service.TeacherUserDetailsService;
import sptech.school.v2.cleanarch.core.application.usecases.security.JwtUseCase;
import sptech.school.domain.exception.CustomAuthenticationEntryPoint;

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
                .headers(headers -> headers.frameOptions().disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/h2-console/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST,"/students", "/students").permitAll()
                        .requestMatchers(HttpMethod.PATCH,"/students/reset-password").permitAll()
                        .requestMatchers(HttpMethod.POST, "/teachers", "/teachers").permitAll()
                        .requestMatchers(HttpMethod.GET, "/teachers", "/teachers").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auths/login", "/auths/verify-code", "/auths/forgot-password", "/contact").permitAll()
                        .requestMatchers(HttpMethod.POST, "/payments", "/payments/preference").permitAll()
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}