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

    public SecurityConfig(
            JwtUseCase jwtUseCase,
            StudentUserDetailsService studentUserDetailsService,
            TeacherUserDetailsService teacherUserDetailsService,
            CorsConfig corsConfig
    ) {
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
                        .requestMatchers(new AntPathRequestMatcher("/api/swagger-ui/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/v3/api-docs/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/swagger-ui.html")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/students", HttpMethod.POST.name())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/students/reset-password", HttpMethod.PATCH.name())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/teachers", HttpMethod.POST.name())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/teachers", HttpMethod.GET.name())).permitAll() // ✅ público para listar professores
                        .requestMatchers(new AntPathRequestMatcher("/api/auths/login", HttpMethod.POST.name())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/auths/verify-code", HttpMethod.POST.name())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/auths/forgot-password", HttpMethod.POST.name())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/contact", HttpMethod.POST.name())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/health", HttpMethod.GET.name())).permitAll()
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
}
