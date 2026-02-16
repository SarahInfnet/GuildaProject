package br.com.infnet.GuildaProject.dto;

import br.com.infnet.GuildaProject.model.ClasseAventureiro;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AtualizarAventureiroRecord(
        @NotBlank
         String nome,

         @NotNull
        ClasseAventureiro classe,

         @NotNull
         @Min(1)
         Integer nivel) {
}
