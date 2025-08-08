package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioRequestDTO {
    @NotEmpty(message = "Nome não pode ser vazio")
    private String nome;
    
    @NotEmpty(message = "Email não pode ser vazio")
    @Email(message = "Formato de email inválido")
    private String email;
    
    @NotEmpty(message = "Telefone não pode ser vazio")
    private String telefone;
    
    @NotEmpty(message = "Senha não pode ser vazia")
    private String senha;
}