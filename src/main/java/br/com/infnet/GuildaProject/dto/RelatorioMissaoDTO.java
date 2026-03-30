package br.com.infnet.GuildaProject.dto;

import br.com.infnet.GuildaProject.model.NivelPerigo;
import br.com.infnet.GuildaProject.model.StatusMissao;

public record RelatorioMissaoDTO(
        Long id,
        String titulo,
        StatusMissao status,
        NivelPerigo nivelPerigo,
        Integer totalParticipantes,
        Integer totalRecompensas
) {}