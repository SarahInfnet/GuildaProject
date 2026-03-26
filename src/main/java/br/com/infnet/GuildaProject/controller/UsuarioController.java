package br.com.infnet.GuildaProject.controller;
import br.com.infnet.GuildaProject.dto.UsuarioCreateDTO;
import br.com.infnet.GuildaProject.dto.UsuarioDetailsRecord;
import br.com.infnet.GuildaProject.dto.UsuarioRecord;
import br.com.infnet.GuildaProject.model.*;
import br.com.infnet.GuildaProject.service.UsuarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Validated
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioRecord>> getAll(@PositiveOrZero @RequestHeader(value = "X-Page", defaultValue = "0") int page,
                                                      @Min(1) @Max(50) @RequestHeader(value = "X-Size" , defaultValue = "10") int size){
        System.out.println(page + "----" + size);
        List<Usuario> usuarios = usuarioService.listarTodos();

        return ResponseEntity.status(HttpStatus.OK)
                .header("X-Page", String.valueOf(page))
                .header("X-Size", String.valueOf(size))
                .header("X-Total-Pages", String.valueOf(usuarios.size() / size))
                .header("X-Total-Count", String.valueOf(usuarios.size()))
                .body( usuarioService.paginar(usuarios,page,size).stream()
                        .map(u -> new UsuarioRecord(
                            u.getId(), u.getOrganizacao().getNome(), u.getNome(), u.getEmail(), u.getUserRoles().stream().map(
                                 ur -> ur.getRole().getNome()
                            ).toList()
                        )).toList());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO){
        Usuario usuario = usuarioService.create(usuarioCreateDTO.getOrganizacaoId(), usuarioCreateDTO.getNome(), usuarioCreateDTO.getEmail(), usuarioCreateDTO.getSenha());
        UsuarioDetailsRecord usuarioDetalhes = new UsuarioDetailsRecord(usuario.getId(), usuario.getOrganizacao().getId(), usuario.getNome(), usuario.getEmail(), usuario.getUserRoles().stream().map(
                ur -> ur.getRole().getNome()
        ).toList(), usuario.getStatus(), usuario.getUltimoLoginEm(), usuario.getCreatedAt(), usuario.getUpdatedAt());
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDetalhes);
    }
}
