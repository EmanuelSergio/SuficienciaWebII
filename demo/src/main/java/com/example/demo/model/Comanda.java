package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comandas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comanda {
    @Id @GeneratedValue
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Produto> produtos = new ArrayList<>();
}