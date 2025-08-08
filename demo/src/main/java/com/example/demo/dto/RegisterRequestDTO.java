package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterRequestDTO {
    
    
    @NotEmpty(message = "Nome não pode ser nulo ou vazio")
    private String nome;

    @NotEmpty(message = "Email não pode ser nulo ou vazio")
    @Email(message = "Email inválido")
    private String email;

    @NotEmpty(message = "Telefone não pode ser nulo ou vazio")
    private String telefone;

    @NotEmpty(message = "Senha não pode ser nula ou vazia")
    private String senha;
}