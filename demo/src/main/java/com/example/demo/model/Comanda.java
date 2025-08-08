package com.example.demo.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comandas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comanda {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    
    private Date data;
    
    @ManyToMany
    private List<Produto> produtos;
}