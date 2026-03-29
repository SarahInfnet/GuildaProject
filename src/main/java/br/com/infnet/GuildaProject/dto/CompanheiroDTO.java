package br.com.infnet.GuildaProject.dto;

import br.com.infnet.GuildaProject.model.EspecieCompanheiro;

public record CompanheiroDTO(
        String nome,
        EspecieCompanheiro especie,
        Integer lealdade
) {}