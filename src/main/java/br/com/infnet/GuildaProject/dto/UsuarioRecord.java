package br.com.infnet.GuildaProject.dto;
import java.util.List;

public record UsuarioRecord(Long id, String organizacao, String nome, String email, List<String> userRoles) {
}
