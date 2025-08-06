package com.example.demo.service;

import com.example.demo.dto.ComandaDTO;
import com.example.demo.model.Comanda;
import com.example.demo.repository.ComandaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ComandaService {
    @Autowired
    private ComandaRepository comandaRepository;

    public List<Comanda> listar() { return comandaRepository.findAll(); }

    public Comanda buscar(Long id) { return comandaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)); }


    public Comanda criar(Comanda c) { return comandaRepository.save(c); }

    public Comanda atualizar(Long id, Comanda atual) {
        Comanda existente = comandaRepository.findById(id).orElseThrow();
        existente.getProdutos().clear();
        existente.getProdutos().addAll(atual.getProdutos());
        return comandaRepository.save(existente);
    }

    public void remover(Long id) {
        if (!comandaRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        comandaRepository.deleteById(id);
    }
}