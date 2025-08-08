package com.example.demo.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ComandaUpdateDTO {
    
    @NotEmpty(message = "Lista de produtos não pode ser vazia")
    private List<Long> produtoIds;

}
