package br.com.infnet.GuildaProject.service;

import br.com.infnet.GuildaProject.dto.*;
import br.com.infnet.GuildaProject.exception.EntityNotFoundException;
import br.com.infnet.GuildaProject.model.Missao;
import br.com.infnet.GuildaProject.model.ParticipacaoMissao;
import br.com.infnet.GuildaProject.repository.MissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MissaoService {

    @Autowired
    private MissaoRepository missaoRepository;

    public Page<MissaoResumoDTO> listar(FiltroMissaoDTO filtro, Pageable pageable) {
        return missaoRepository.findByFiltros(
                filtro.status(),
                filtro.nivelPerigo(),
                filtro.dataInicio(),
                filtro.dataTermino(),
                pageable
        ).map(m -> new MissaoResumoDTO(
                m.getId(), m.getTitulo(), m.getStatus(), m.getNivelPerigo(),
                m.getCreatedAt(), m.getDataInicio(), m.getDataTermino()
        ));
    }



    public MissaoDetalheDTO getDetalhe(Long id) {
        Missao missao = missaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Missão não encontrada com id " + id));

        List<ParticipanteDTO> participantes = missao.getParticipacoes() == null
                ? List.of()
                : missao.getParticipacoes().stream()
                .map(p -> new ParticipanteDTO(
                        p.getAventureiro().getId(),
                        p.getAventureiro().getNome(),
                        p.getPapelMissao(),
                        p.getRecompensaOuro(),
                        p.getMvp()
                )).toList();

        return new MissaoDetalheDTO(
                missao.getId(), missao.getTitulo(), missao.getStatus(),
                missao.getNivelPerigo(), missao.getCreatedAt(),
                missao.getDataInicio(), missao.getDataTermino(),
                participantes
        );
    }

    public List<RelatorioMissaoDTO> gerarRelatorio(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return missaoRepository.findByPeriodo(dataInicio, dataFim).stream()
                .map(m -> {
                    List<ParticipacaoMissao> participacoes = m.getParticipacoes() == null
                            ? List.of() : m.getParticipacoes();

                    int totalRecompensas = participacoes.stream()
                            .mapToInt(p -> p.getRecompensaOuro() != null ? p.getRecompensaOuro() : 0)
                            .sum();

                    return new RelatorioMissaoDTO(
                            m.getId(),
                            m.getTitulo(),
                            m.getStatus(),
                            m.getNivelPerigo(),
                            participacoes.size(),
                            totalRecompensas
                    );
                }).toList();
    }
}