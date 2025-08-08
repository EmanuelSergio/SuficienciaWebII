package com.example.demo.controller;

import com.example.demo.dto.ProdutoDTO;
import com.example.demo.dto.ProdutoRequestDTO;
import com.example.demo.model.Produto;
import com.example.demo.service.ProdutoService;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produtos")
@Tag(name = "Produtos", description = "Endpoints para gerenciamento de produtos")
@SecurityRequirement(name = "bearer-jwt")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @GetMapping
    @Operation(summary = "Listar produtos", description = "Retorna todos os produtos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de produtos recuperada com sucesso",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProdutoDTO.class))))
    public List<ProdutoDTO> listar() {
        return service.listar().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID", description = "Retorna um único produto pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto encontrado",
                  content = @Content(schema = @Schema(implementation = ProdutoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                  content = @Content)
    })
    public ProdutoDTO buscar(@Parameter(description = "ID do produto") @PathVariable Long id) {
        return convertToDto(service.buscar(id));
    }

    @PostMapping
    @Operation(summary = "Criar produto", description = "Cria um novo produto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Produto criado com sucesso",
                  content = @Content(schema = @Schema(implementation = ProdutoDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos",
                  content = @Content)
    })
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO criar(@RequestBody @Valid ProdutoRequestDTO produtoDTO) {
        Produto produto = convertToEntity(produtoDTO);
        return convertToDto(service.criar(produto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto", description = "Atualiza os dados de um produto existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso",
                  content = @Content(schema = @Schema(implementation = ProdutoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                  content = @Content),
        @ApiResponse(responseCode = "400", description = "Dados inválidos",
                  content = @Content)
    })
    public ProdutoDTO atualizar(
            @Parameter(description = "ID do produto") @PathVariable Long id, 
            @RequestBody @Valid ProdutoRequestDTO produtoDTO) {
        Produto produto = convertToEntity(produtoDTO);
        return convertToDto(service.atualizar(id, produto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover produto", description = "Remove um produto pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                  content = @Content)
    })
    public Map<String, Object> remover(@Parameter(description = "ID do produto") @PathVariable Long id) {
        service.remover(id);
        return Map.of("success", Map.of("text", "produto removido"));
    }
    
    private ProdutoDTO convertToDto(Produto produto) {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setPreco(produto.getPreco());
        return dto;
    }
    
    private Produto convertToEntity(ProdutoRequestDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        return produto;
    }
}