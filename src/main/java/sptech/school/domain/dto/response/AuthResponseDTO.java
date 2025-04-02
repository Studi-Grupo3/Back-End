package sptech.school.domain.dto.response;

import java.io.Serializable;

public class AuthResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String cpf;
    private String email;
    private String token;

    public AuthResponseDTO(String username, String cpf, String email, String token) {
        this.username = username;
        this.cpf = cpf;
        this.email = email;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
