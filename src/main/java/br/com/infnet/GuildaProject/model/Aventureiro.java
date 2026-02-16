package br.com.infnet.GuildaProject.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Aventureiro {

    private Long id;
    private String nome;
    private ClasseAventureiro classe;
    private Integer nivel;
    private Boolean ativo;
    private Companheiro companheiro;

    public Aventureiro(Long id, String nome, ClasseAventureiro classe, Integer nivel, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.classe = classe;
        this.nivel = nivel;
        this.ativo = ativo;
    }
    public Aventureiro(Long id, String nome, ClasseAventureiro classe, Integer nivel, Boolean ativo, Companheiro companheiro) {
        this.id = id;
        this.nome = nome;
        this.classe = classe;
        this.nivel = nivel;
        this.ativo = ativo;
        this.companheiro = companheiro;
    }

}