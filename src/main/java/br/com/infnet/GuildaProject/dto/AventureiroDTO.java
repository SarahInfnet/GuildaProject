package br.com.infnet.GuildaProject.dto;

import br.com.infnet.GuildaProject.model.ClasseAventureiro;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AventureiroDTO {
    private String nome;
    private ClasseAventureiro classe;
    private int nivel;
}
