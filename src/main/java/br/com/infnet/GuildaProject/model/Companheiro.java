package br.com.infnet.GuildaProject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Companheiro {

    private String nome;
    private EspecieCompanheiro especie;
    private Integer lealdade;
}


