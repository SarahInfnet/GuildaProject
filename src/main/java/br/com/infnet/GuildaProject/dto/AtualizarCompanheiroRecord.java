package br.com.infnet.GuildaProject.dto;

import br.com.infnet.GuildaProject.model.ClasseAventureiro;
import br.com.infnet.GuildaProject.model.EspecieCompanheiro;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AtualizarCompanheiroRecord(
        @NotBlank
        String nome,

        @NotNull
        EspecieCompanheiro especie,

        @NotNull @Min(0) @Max(100)
        Integer lealdade){

}
