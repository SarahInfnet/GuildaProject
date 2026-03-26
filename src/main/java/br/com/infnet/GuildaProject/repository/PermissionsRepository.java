package br.com.infnet.GuildaProject.repository;

import br.com.infnet.GuildaProject.model.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionsRepository extends JpaRepository<Permissions, Long> {
}
