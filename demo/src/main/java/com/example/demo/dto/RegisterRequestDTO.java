package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {
    
    
    @NotBlank(message = "Nome não pode ser nulo ou vazio")
    private String nome;

    @NotBlank(message = "Email não pode ser nulo ou vazio")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Telefone não pode ser nulo ou vazio")
    private String telefone;

    @NotBlank(message = "Senha não pode ser nula ou vazia")
    private String senha;
}