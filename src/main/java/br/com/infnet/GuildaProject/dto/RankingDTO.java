package br.com.infnet.GuildaProject.dto;

import br.com.infnet.GuildaProject.model.ClasseAventureiro;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RankingDTO {
    String nome;
    ClasseAventureiro classe;
    Integer nivel;
    Long organizacaoId;
    Long usuarioId;
    Integer totalParticipacoes;
    Integer somaRecompensa;
    Integer qtdDestaque;
}
