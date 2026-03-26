package br.com.infnet.GuildaProject.dto;

import java.util.List;

public record RoleRecord(Long id, String nome, List<PermissionRecord> permissions) {

}
