package com.example.demo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ComandaRequestDTO {
    @NotNull(message = "ID do usuário não pode ser nulo")
    private Long usuarioId;
    
    @NotEmpty(message = "Lista de produtos não pode ser vazia")
    private List<Long> produtoIds;
}