package br.com.infnet.GuildaProject.dto;

import br.com.infnet.GuildaProject.model.PapelMissao;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ParticipacaoMissaoRecord(
        @NotNull Long missaoId,
        @NotNull Long aventureiroId,
        @NotNull PapelMissao papelMissao,
        @Min(0) Integer recompensaOuro,
        @NotNull Boolean mvp
) {}
