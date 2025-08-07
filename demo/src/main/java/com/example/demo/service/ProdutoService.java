package com.example.demo.service;

import com.example.demo.model.Produto;
import com.example.demo.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public List<Produto> listar() {
        return repository.findAll();
    }

    public Produto buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Produto não encontrado"));
    }

    @Transactional
    public Produto criar(Produto produto) {
        produto.setId(null); // Ensure we're creating a new product
        return repository.save(produto);
    }

    @Transactional
    public Produto atualizar(Long id, Produto produto) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Produto não encontrado");
        }
        produto.setId(id);
        return repository.save(produto);
    }

    @Transactional
    public void remover(Long id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Produto não encontrado");
        }
        repository.deleteById(id);
    }
}