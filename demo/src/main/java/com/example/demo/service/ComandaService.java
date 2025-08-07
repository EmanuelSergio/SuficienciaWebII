package com.example.demo.service;

import com.example.demo.dto.ComandaRequestDTO;
import com.example.demo.dto.ComandaResponseDTO;
import com.example.demo.dto.ProdutoDTO;
import com.example.demo.dto.UsuarioDTO;
import com.example.demo.model.Comanda;
import com.example.demo.model.Produto;
import com.example.demo.model.Usuario;
import com.example.demo.repository.ComandaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComandaService {
    @Autowired
    private ComandaRepository comandaRepository;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private ProdutoService produtoService;

    public List<Comanda> listar() { 
        return comandaRepository.findAll(); 
    }

    public Comanda buscar(Long id) { 
        return comandaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comanda não encontrada")); 
    }

    @Transactional
    public ComandaResponseDTO criar(ComandaRequestDTO comandaDTO) {
        // Get usuario by ID
        Usuario usuario = usuarioService.buscar(comandaDTO.getUsuarioId());
        
        // Get all produtos by IDs
        List<Produto> produtos = comandaDTO.getProdutoIds().stream()
            .map(produtoId -> produtoService.buscar(produtoId))
            .collect(Collectors.toList());
        
        // Create new comanda
        Comanda comanda = new Comanda();
        comanda.setUsuario(usuario);
        comanda.setProdutos(produtos);
        
        // Save and return as DTO
        Comanda savedComanda = comandaRepository.save(comanda);
        return toResponseDTO(savedComanda);
    }

    @Transactional
    public ComandaResponseDTO atualizar(Long id, ComandaRequestDTO comandaDTO) {
        // Check if comanda exists
        Comanda existente = comandaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comanda não encontrada"));
        
        // Get usuario by ID
        Usuario usuario = usuarioService.buscar(comandaDTO.getUsuarioId());

        // Get all produtos by IDs
        List<Produto> produtos = comandaDTO.getProdutoIds().stream()
            .map(produtoId -> produtoService.buscar(produtoId))
            .collect(Collectors.toList());
        
        // Update comanda
        existente.setUsuario(usuario);
        existente.getProdutos().clear();
        existente.getProdutos().addAll(produtos);
        
        // Save and return as DTO
        Comanda savedComanda = comandaRepository.save(existente);
        return toResponseDTO(savedComanda);
    }

    @Transactional
    public void remover(Long id) {
        if (!comandaRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comanda não encontrada");
        comandaRepository.deleteById(id);
    }
    
    // Helper methods to convert between entities and DTOs
    private ComandaResponseDTO toResponseDTO(Comanda comanda) {
        ComandaResponseDTO dto = new ComandaResponseDTO();
        dto.setId(comanda.getId());
        
        // Convert Usuario to UsuarioDTO
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(comanda.getUsuario().getId());
        usuarioDTO.setNome(comanda.getUsuario().getNome());
        usuarioDTO.setEmail(comanda.getUsuario().getEmail());
        usuarioDTO.setTelefone(comanda.getUsuario().getTelefone());
        dto.setUsuario(usuarioDTO);
        
        // Convert list of Produto to list of ProdutoDTO
        List<ProdutoDTO> produtoDTOs = comanda.getProdutos().stream()
            .map(produto -> {
                ProdutoDTO produtoDTO = new ProdutoDTO();
                produtoDTO.setId(produto.getId());
                produtoDTO.setNome(produto.getNome());
                produtoDTO.setPreco(produto.getPreco());
                return produtoDTO;
            })
            .collect(Collectors.toList());
        dto.setProdutos(produtoDTOs);
        
        return dto;
    }
}