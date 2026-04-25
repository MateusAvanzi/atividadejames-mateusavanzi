package br.com.fatec.catalogo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name = "usuarios")
public class UsuarioModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @NotBlank(message = "O username e obrigatorio")
    @Size(min = 3, max = 50, message = "O username deve ter entre 3 e 50 caracteres")
    @Pattern(
            regexp = "^[a-zA-Z0-9._-]+$",
            message = "Use apenas letras, numeros, ponto (.), underline (_) ou hifen (-)"
    )
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "A senha e obrigatoria")
    @Size(min = 8, message = "A senha deve ter no minimo 8 caracteres")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&]).+$",
            message = "A senha deve conter: 1 maiuscula, 1 minuscula, 1 numero e 1 especial"
    )
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "O papel (role) e obrigatorio")
    @Column(nullable = false)
    private String role;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
