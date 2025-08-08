package com.example.demo.controller;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.dto.UsuarioRequestDTO;
import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;
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
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
@SecurityRequirement(name = "bearer-jwt")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    @Operation(summary = "Listar usuários", description = "Retorna todos os usuários cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de usuários recuperada com sucesso",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioDTO.class))))
    public List<UsuarioDTO> listar() {
        return service.listar().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna um único usuário pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                  content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                  content = @Content)
    })
    public UsuarioDTO buscar(@Parameter(description = "ID do usuário") @PathVariable Long id) {
        return convertToDto(service.buscar(id));
    }
    
    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar usuário por email", description = "Retorna um único usuário pelo email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                  content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                  content = @Content)
    })
    public UsuarioDTO buscarPorEmail(@Parameter(description = "Email do usuário") @PathVariable String email) {
        return convertToDto(service.buscarPorEmail(email));
    }

    @PostMapping
    @Operation(summary = "Criar usuário", description = "Cria um novo usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                  content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos",
                  content = @Content)
    })
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO criar(@RequestBody @Valid UsuarioRequestDTO usuarioDTO) {
        Usuario usuario = convertToEntity(usuarioDTO);
        return convertToDto(service.criar(usuario));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
                  content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                  content = @Content),
        @ApiResponse(responseCode = "400", description = "Dados inválidos",
                  content = @Content)
    })
    public UsuarioDTO atualizar(
            @Parameter(description = "ID do usuário") @PathVariable Long id, 
            @RequestBody @Valid UsuarioRequestDTO usuarioDTO) {
        Usuario usuario = convertToEntity(usuarioDTO);
        return convertToDto(service.atualizar(id, usuario));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover usuário", description = "Remove um usuário pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                  content = @Content)
    })
    public Map<String, Object> remover(@Parameter(description = "ID do usuário") @PathVariable Long id) {
        service.remover(id);
        return Map.of("success", Map.of("text", "usuário removido"));
    }
    
    private UsuarioDTO convertToDto(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setTelefone(usuario.getTelefone());
        return dto;
    }
    
    private Usuario convertToEntity(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefone(dto.getTelefone());
        usuario.setSenha(dto.getSenha());
        return usuario;
    }
}