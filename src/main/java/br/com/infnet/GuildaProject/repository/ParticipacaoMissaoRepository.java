package br.com.infnet.GuildaProject.repository;

import br.com.infnet.GuildaProject.model.ParticipacaoMissao;
import br.com.infnet.GuildaProject.model.ParticipacaoMissaoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipacaoMissaoRepository
        extends JpaRepository<ParticipacaoMissao, ParticipacaoMissaoId> {
}