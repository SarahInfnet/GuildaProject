package br.com.infnet.GuildaProject.repository;

import br.com.infnet.GuildaProject.model.AuditEntries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditEntriesRepository extends JpaRepository<AuditEntries, Long> {
}
