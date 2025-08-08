package com.example.demo.Controller;

import java.util.List;

import com.example.demo.dto.ComandaRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ComandaDTO;
import com.example.demo.model.Comanda;
import com.example.demo.service.ComandaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/comandas")
@Tag(name = "Comandas", description = "Endpoints para gerenciamento de comandas")
@SecurityRequirement(name = "bearer-jwt")
public class ComandaController {
    
    @Autowired
    private ComandaService comandaService;
    
    @GetMapping
    @Operation(summary = "Listar comandas", description = "Retorna todas as comandas cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista de comandas recuperada com sucesso",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = ComandaDTO.class))))
    public ResponseEntity<List<ComandaDTO>> listarComandas() {
        return ResponseEntity.ok(comandaService.listar());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar comanda por ID", description = "Retorna uma única comanda pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comanda encontrada",
                  content = @Content(schema = @Schema(implementation = ComandaDTO.class))),
        @ApiResponse(responseCode = "404", description = "Comanda não encontrada",
                  content = @Content)
    })
    public ResponseEntity<ComandaDTO> buscarComanda(
            @Parameter(description = "ID da comanda") @PathVariable Long id) {
        return ResponseEntity.ok(comandaService.buscar(id));
    }
    
    @PostMapping
    @Operation(summary = "Criar comanda", description = "Cria uma nova comanda")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Comanda criada com sucesso",
                  content = @Content(schema = @Schema(implementation = ComandaDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos",
                  content = @Content)
    })
    public ResponseEntity<ComandaDTO> criarComanda(@RequestBody ComandaRequestDTO comanda) {
        return new ResponseEntity<>(comandaService.criar(comanda), HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar comanda", description = "Atualiza os dados de uma comanda existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comanda atualizada com sucesso",
                  content = @Content(schema = @Schema(implementation = ComandaDTO.class))),
        @ApiResponse(responseCode = "404", description = "Comanda não encontrada",
                  content = @Content)
    })
    public ResponseEntity<ComandaDTO> atualizarComanda(
            @Parameter(description = "ID da comanda") @PathVariable Long id, 
            @RequestBody ComandaRequestDTO comandaRequest) {
        return ResponseEntity.ok(comandaService.atualizar(id, comandaRequest));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Remover comanda", description = "Remove uma comanda pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Comanda removida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Comanda não encontrada",
                  content = @Content)
    })
    public ResponseEntity<Void> deletarComanda(
            @Parameter(description = "ID da comanda") @PathVariable Long id) {
        comandaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}/total")
    @Operation(summary = "Calcular total da comanda", description = "Calcula o valor total dos produtos em uma comanda")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Total calculado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Comanda não encontrada",
                  content = @Content)
    })
    public ResponseEntity<Double> calcularTotal(
            @Parameter(description = "ID da comanda") @PathVariable Long id) {
        return ResponseEntity.ok(comandaService.calcularTotal(id));
    }
}