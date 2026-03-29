package br.com.infnet.GuildaProject.dto;

import br.com.infnet.GuildaProject.model.NivelPerigo;
import br.com.infnet.GuildaProject.model.StatusMissao;

import java.time.LocalDateTime;
import java.util.List;

public record MissaoDetalheDTO(
        Long id,
        String titulo,
        StatusMissao status,
        NivelPerigo nivelPerigo,
        LocalDateTime createdAt,
        LocalDateTime dataInicio,
        LocalDateTime dataTermino,
        List<ParticipanteDTO> participantes
) {}

