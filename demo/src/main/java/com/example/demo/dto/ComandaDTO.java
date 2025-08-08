package com.example.demo.dto;

import java.util.Date;
import java.util.List;

import com.example.demo.model.Produto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComandaDTO {
    private Long id;
    private String nome;
    private Date data;
    private List<Produto> produtos;
}