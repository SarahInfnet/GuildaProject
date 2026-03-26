package br.com.infnet.GuildaProject.repository;

import br.com.infnet.GuildaProject.model.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
}
