package br.com.infnet.GuildaProject.dto;
import br.com.infnet.GuildaProject.model.ClasseAventureiro;
import java.util.Optional;

public record AventureiroPerfilDTO(
        Long id,
        String nome,
        ClasseAventureiro classe,
        Integer nivel,
        Boolean ativo,
        Optional<CompanheiroDTO> companheiro,
        Integer totalMissoes,
        Optional<Long> ultimaMissao
) {}