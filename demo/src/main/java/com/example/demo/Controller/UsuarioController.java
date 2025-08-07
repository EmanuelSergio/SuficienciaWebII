package com.example.demo.Controller;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<Usuario> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Usuario buscar(@PathVariable Long id) {
        return service.buscar(id);
    }
    
    @GetMapping("/email/{email}")
    public Usuario buscarPorEmail(@PathVariable String email) {
        return service.buscarPorEmail(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario criar(@RequestBody @Validated Usuario usuario) {
        return service.criar(usuario);
    }

    @PutMapping("/{id}")
    public Usuario atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        return service.atualizar(id, usuario);
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> remover(@PathVariable Long id) {
        service.remover(id);
        return Map.of("success", Map.of("text", "usuário removido"));
    }
}