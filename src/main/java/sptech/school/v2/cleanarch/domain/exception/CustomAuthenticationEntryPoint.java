package sptech.school.v2.cleanarch.domain.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        String message = "Authentication required. Please log in to access this resource.";
        if (authException != null) {
            message = authException.getMessage();
        }
        String jsonResponse = "{\"error\": \"" + message + "\"}";
        response.getWriter().write(jsonResponse);
    }
}
