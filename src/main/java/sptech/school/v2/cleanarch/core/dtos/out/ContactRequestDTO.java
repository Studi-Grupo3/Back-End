package sptech.school.v2.cleanarch.core.dtos.out;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ContactRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @Pattern(
            regexp = "^$|^\\(\\d{2}\\)\\s?\\d{4,5}-\\d{4}$",
            message = "Celular inválido. Formato esperado: (00) 00000-0000 ou vazio"
    )
    private String celular;

    @NotBlank(message = "A mensagem é obrigatória")
    @Size(max = 2000, message = "A mensagem pode ter no máximo 2000 caracteres")
    private String mensagem;

    public ContactRequestDTO() {}

    public ContactRequestDTO(String nome, String email, String celular, String mensagem) {
        this.nome = nome;
        this.email = email;
        this.celular = celular;
        this.mensagem = mensagem;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }
    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
}