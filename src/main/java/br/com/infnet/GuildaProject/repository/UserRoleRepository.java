package br.com.infnet.GuildaProject.repository;
import br.com.infnet.GuildaProject.model.UserRole;
import br.com.infnet.GuildaProject.model.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
}
