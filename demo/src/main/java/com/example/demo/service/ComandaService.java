package com.example.demo.service;

import com.example.demo.dto.ComandaDTO;
import com.example.demo.model.Comanda;
import com.example.demo.model.ItemComanda;
import com.example.demo.model.Usuario;
import com.example.demo.repository.ComandaRepository;
import com.example.demo.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// ComandaService.java
@Service
public class ComandaService {

    @Autowired
    private ComandaRepository comandaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Comanda salvarComanda(ComandaDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Comanda comanda = new Comanda();
        comanda.setUsuario(usuario);

        for (ItemDTO itemDto : dto.getProdutos()) {
            ItemComanda item = new ItemComanda();
            item.setNome(itemDto.getNome());
            item.setPreco(itemDto.getPreco());
            item.setComanda(comanda);
            comanda.getItens().add(item);
        }

        return comandaRepository.save(comanda);
    }

    // Outros métodos (atualizar, buscar, etc)
}