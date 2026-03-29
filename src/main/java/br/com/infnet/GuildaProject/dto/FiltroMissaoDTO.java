package br.com.infnet.GuildaProject.dto;

import br.com.infnet.GuildaProject.model.NivelPerigo;
import br.com.infnet.GuildaProject.model.StatusMissao;

import java.time.LocalDateTime;

public record FiltroMissaoDTO(
        StatusMissao status,
        NivelPerigo nivelPerigo,
        LocalDateTime dataInicio,
        LocalDateTime dataTermino
) {}
