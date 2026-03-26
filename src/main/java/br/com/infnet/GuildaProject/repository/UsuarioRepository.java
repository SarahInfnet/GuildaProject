package br.com.infnet.GuildaProject.repository;

import br.com.infnet.GuildaProject.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
