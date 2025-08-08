package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ComandaDTO;
import com.example.demo.dto.ComandaRequestDTO;
import com.example.demo.dto.ComandaUpdateDTO;
import com.example.demo.model.Comanda;
import com.example.demo.model.Produto;
import com.example.demo.repository.ComandaRepository;
import com.example.demo.repository.ProdutoRepository;

@Service
public class ComandaService {
    
    @Autowired
    private ComandaRepository comandaRepository;
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    public List<ComandaDTO> listar() {
        return comandaRepository.findAll().stream()
               .map(this::convertToDTO)
               .collect(Collectors.toList());
    }
    
    public ComandaDTO buscar(Long id) {
        Comanda comanda = comandaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comanda não encontrada"));
        return convertToDTO(comanda);
    }
    
    public ComandaDTO criar(ComandaRequestDTO comandaRequest) {
        List<Produto> produtos = produtoRepository.findAllById(comandaRequest.getProdutoIds());
        
        if (produtos.size() != comandaRequest.getProdutoIds().size()) {
            throw new RuntimeException("Um ou mais produtos não foram encontrados");
        }
        
        Comanda comanda = new Comanda();
        comanda.setNome("Comanda #" + System.currentTimeMillis());
        comanda.setData(new Date());
        comanda.setProdutos(produtos);
        
        Comanda novaConta = comandaRepository.save(comanda);
        return convertToDTO(novaConta);
    }
    
    public ComandaDTO atualizar(Long id, ComandaUpdateDTO comandaRequest) {
        Comanda comanda = comandaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comanda não encontrada"));

        List<Produto> produtos = produtoRepository.findAllById(comandaRequest.getProdutoIds());
        

        if (produtos.size() != comandaRequest.getProdutoIds().size()) {
            throw new RuntimeException("Um ou mais produtos não foram encontrados");
        }

        comanda.setProdutos(produtos);

        Comanda comandaAtualizada = comandaRepository.save(comanda);
        return convertToDTO(comandaAtualizada);
    }
    
    public void deletar(Long id) {
        if(!comandaRepository.existsById(id)) {
            throw new RuntimeException("Comanda não encontrada");
        }
        comandaRepository.deleteById(id);
    }
    
    public double calcularTotal(Long id) {
        Comanda comanda = comandaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comanda não encontrada"));
        return comanda.getProdutos().stream()
            .mapToDouble(produto -> produto.getPreco().doubleValue())
            .sum();
    }
    
    private ComandaDTO convertToDTO(Comanda comanda) {
        ComandaDTO dto = new ComandaDTO();
        dto.setId(comanda.getId());
        dto.setNome(comanda.getNome());
        dto.setData(comanda.getData());
        dto.setProdutos(comanda.getProdutos());
        return dto;
    }
}