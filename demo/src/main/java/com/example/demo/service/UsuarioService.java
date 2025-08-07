
package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public List<Usuario> listar() {
        return repository.findAll();
    }

    public Usuario buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));
    }
    
    public Usuario buscarPorEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));
    }

    @Transactional
    public Usuario criar(Usuario usuario) {
        usuario.setId(null); // Ensure we're creating a new user
        return repository.save(usuario);
    }

    @Transactional
    public Usuario atualizar(Long id, Usuario usuario) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Usuário não encontrado");
        }
        usuario.setId(id);
        return repository.save(usuario);
    }

    @Transactional
    public void remover(Long id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Usuário não encontrado");
        }
        repository.deleteById(id);
    }
}