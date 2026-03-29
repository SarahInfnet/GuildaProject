package br.com.infnet.GuildaProject.dto;

import br.com.infnet.GuildaProject.model.PapelMissao;

public record ParticipanteDTO(
        Long aventureiroId,
        String nomeAventureiro,
        PapelMissao papel,
        Integer recompensaOuro,
        Boolean mvp
) {}
