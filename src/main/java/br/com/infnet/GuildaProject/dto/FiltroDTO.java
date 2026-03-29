package br.com.infnet.GuildaProject.dto;
import br.com.infnet.GuildaProject.model.ClasseAventureiro;

public record FiltroDTO(ClasseAventureiro classe, Boolean ativo, Integer nivelMinimo) {
}