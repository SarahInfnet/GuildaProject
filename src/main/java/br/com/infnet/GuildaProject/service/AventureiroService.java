package br.com.infnet.GuildaProject.service;

import br.com.infnet.GuildaProject.dto.AventureiroResumoDTO;
import br.com.infnet.GuildaProject.dto.RankingDTO;
import br.com.infnet.GuildaProject.exception.EntityNotFoundException;
import br.com.infnet.GuildaProject.model.*;
import br.com.infnet.GuildaProject.repository.AventureiroRepository;
import br.com.infnet.GuildaProject.repository.OrganizacaoRepository;
import br.com.infnet.GuildaProject.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AventureiroService {
    @Autowired
    private AventureiroRepository aventureiroRepository;
    @Autowired
    private OrganizacaoRepository organizacaoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Aventureiro getById(Long id){
        return aventureiroRepository.findById(id)
            .orElseThrow(() ->
                new EntityNotFoundException(
                        "Aventureiro não encontrado com id " + id
                )
            );
    }

    public Aventureiro create(String nome, ClasseAventureiro classe, Integer nivel,
                              Long organizacaoId, Long usuarioId){

        Organizacao organizacao = organizacaoRepository.findById(organizacaoId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Organização não encontrada com id " + organizacaoId));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Usuário não encontrado com id " + usuarioId));

        Aventureiro novoAventureiro = new Aventureiro();
        novoAventureiro.setNome(nome);
        novoAventureiro.setClasse(classe);
        novoAventureiro.setNivel(nivel);
        novoAventureiro.setAtivo(true);
        novoAventureiro.setOrganizacao(organizacao);
        novoAventureiro.setUsuario(usuario);
        novoAventureiro.setCreatedAt(LocalDateTime.now());
        novoAventureiro.setUpdatedAt(LocalDateTime.now());

        return aventureiroRepository.save(novoAventureiro);

    }

    public Page<Aventureiro> filtrar(ClasseAventureiro classe, Boolean ativo,
                                     Integer nivelMinimo, Pageable pageable) {
        return aventureiroRepository.findByFiltros(classe, ativo, nivelMinimo, pageable);
    }

    public Aventureiro encerrarVinculo(Long id) {
        Aventureiro aventureiro = getById(id);
        aventureiro.setAtivo(false);
        aventureiro.setUpdatedAt(LocalDateTime.now());
        return aventureiroRepository.save(aventureiro);
    }

    public Aventureiro recrutarNovamente(Long id) {
        Aventureiro aventureiro = getById(id);
        aventureiro.setAtivo(true);
        aventureiro.setUpdatedAt(LocalDateTime.now());
        return aventureiroRepository.save(aventureiro);
    }

    public Aventureiro atualizar(Long id, String nome, ClasseAventureiro classe, Integer nivel) {
        Aventureiro aventureiro = getById(id);
        aventureiro.setNome(nome);
        aventureiro.setClasse(classe);
        aventureiro.setNivel(nivel);
        aventureiro.setUpdatedAt(LocalDateTime.now());
        return aventureiroRepository.save(aventureiro);
    }

    public Aventureiro atualizarCompanheiro(Long id, String nome, EspecieCompanheiro especie, Integer lealdade) {
        Aventureiro aventureiro = getById(id);
        Companheiro companheiro = new Companheiro();
        companheiro.setAventureiro(aventureiro);
        companheiro.setNome(nome);
        companheiro.setEspecie(especie);
        companheiro.setLealdade(lealdade);

        aventureiro.setCompanheiro(companheiro);
        aventureiro.setUpdatedAt(LocalDateTime.now());
        return aventureiroRepository.save(aventureiro);
    }

    public Aventureiro deletarCompanheiro(Long id) {
        Aventureiro aventureiro = getById(id);
        aventureiro.setCompanheiro(null);
        aventureiro.setUpdatedAt(LocalDateTime.now());
        return aventureiroRepository.save(aventureiro);
    }

    public Page<AventureiroResumoDTO> buscarPorNome(String nome, Pageable pageable) {
        return aventureiroRepository.findByNomeContaining(nome, pageable)
            .map(a -> new AventureiroResumoDTO(
                    a.getId(), a.getNome(), a.getClasse(), a.getNivel(), a.getAtivo()
            )
        );
    }

    public List<RankingDTO> rankearAventureiros(String criterio,
                                                LocalDateTime dataInicio,
                                                LocalDateTime dataFim,
                                                StatusMissao statusMissao) {
        List<RankingDTO> aventureiros = aventureiroRepository.findAll().stream()
                .map(a -> {
                    List<ParticipacaoMissao> participacoes = a.getParticipacoes() == null
                            ? List.of() : a.getParticipacoes();

                    List<ParticipacaoMissao> filtradas = participacoes.stream()
                            .filter(p -> dataInicio == null || p.getCreatedAt().isAfter(dataInicio))
                            .filter(p -> dataFim == null || p.getCreatedAt().isBefore(dataFim))
                            .filter(p -> statusMissao == null || p.getMissao().getStatus() == statusMissao)
                            .toList();

                    int somaRecompensa = filtradas.stream()
                            .mapToInt(p -> p.getRecompensaOuro() != null ? p.getRecompensaOuro() : 0)
                            .sum();

                    int qtdDestaque = (int) filtradas.stream()
                            .filter(p -> Boolean.TRUE.equals(p.getMvp()))
                            .count();

                    return new RankingDTO(
                            a.getNome(), a.getClasse(), a.getNivel(),
                            a.getOrganizacao().getId(), a.getUsuario().getId(),
                            filtradas.size(), somaRecompensa, qtdDestaque
                    );
                }).toList();

        if (criterio.equals("somaRecompensa")) {
            return aventureiros.stream()
                    .sorted((r1, r2) -> Integer.compare(r2.getSomaRecompensa(), r1.getSomaRecompensa()))
                    .toList();
        } else if (criterio.equals("qtdDestaque")) {
            return aventureiros.stream()
                    .sorted((r1, r2) -> Integer.compare(r2.getQtdDestaque(), r1.getQtdDestaque()))
                    .toList();
        } else {
            return aventureiros.stream()
                    .sorted((r1, r2) -> Integer.compare(r2.getTotalParticipacoes(), r1.getTotalParticipacoes()))
                    .toList();
        }
    }
}
