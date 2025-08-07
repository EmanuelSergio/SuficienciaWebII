package com.example.demo.Controller;

import com.example.demo.dto.ComandaRequestDTO;
import com.example.demo.dto.ComandaResponseDTO;
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
    public ComandaResponseDTO criar(@RequestBody @Validated ComandaRequestDTO comandaDTO) {
        return service.criar(comandaDTO);
    }
    @PutMapping("/{id}")
    public ComandaResponseDTO atualizar(@PathVariable Long id, @RequestBody ComandaRequestDTO c) {
        return service.atualizar(id, c);
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> remover(@PathVariable Long id) {
        service.remover(id);
        return Map.of("success", Map.of("text", "comanda removida"));
    }

}
