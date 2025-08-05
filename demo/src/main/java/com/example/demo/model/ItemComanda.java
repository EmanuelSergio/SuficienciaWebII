package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "itens_comanda")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemComanda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comanda_id", nullable = false)
    private Comanda comanda;

//    @NotBlank
//    @Size(min = 3, max = 100)
    private String nome;

//    @DecimalMin("0.01")
    private BigDecimal preco;

    // Getters e Setters
}