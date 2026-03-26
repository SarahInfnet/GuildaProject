package br.com.infnet.GuildaProject.dto;

import br.com.infnet.GuildaProject.model.UsuarioStatus;

import java.time.LocalDateTime;
import java.util.List;

public record UsuarioDetailsRecord(Long id, Long organizacaoId, String nome, String email, List<String> userRoles, UsuarioStatus status, LocalDateTime ultimoLoginEm, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
