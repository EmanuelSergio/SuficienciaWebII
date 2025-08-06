package com.example.demo.Controller;

import com.example.demo.model.Comanda;
import com.example.demo.service.ComandaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comandas")
public class ComandaController {

    @Autowired
    private ComandaService service;

    @GetMapping
    public List<Comanda> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public Comanda buscar(@PathVariable Long id) { return service.buscar(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comanda criar(@RequestBody @Validated Comanda c) { return service.criar(c); }

    @PutMapping("/{id}")
    public Comanda atualizar(@PathVariable Long id, @RequestBody Comanda c) {
        return service.atualizar(id, c);
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> remover(@PathVariable Long id) {
        service.remover(id);
        return Map.of("success", Map.of("text", "comanda removida"));
    }

}
