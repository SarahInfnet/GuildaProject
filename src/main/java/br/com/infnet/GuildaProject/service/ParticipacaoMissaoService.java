package br.com.infnet.GuildaProject.service;

import br.com.infnet.GuildaProject.exception.EntityNotFoundException;
import br.com.infnet.GuildaProject.model.*;
import br.com.infnet.GuildaProject.repository.AventureiroRepository;
import br.com.infnet.GuildaProject.repository.MissaoRepository;
import br.com.infnet.GuildaProject.repository.ParticipacaoMissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ParticipacaoMissaoService {

    @Autowired
    private ParticipacaoMissaoRepository participacaoMissaoRepository;

    @Autowired
    private AventureiroRepository aventureiroRepository;

    @Autowired
    private MissaoRepository missaoRepository;

    public ParticipacaoMissao criar(Long missaoId, Long aventureiroId,
                                    PapelMissao papel, Integer recompensaOuro, Boolean mvp) {

        Missao missao = missaoRepository.findById(missaoId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Missão não encontrada com id " + missaoId));

        Aventureiro aventureiro = aventureiroRepository.findById(aventureiroId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aventureiro não encontrado com id " + aventureiroId));

        if (Boolean.FALSE.equals(aventureiro.getAtivo())) {
            throw new IllegalStateException(
                    "Aventureiro inativo não pode ser associado a novas missões");
        }

        if (missao.getStatus() != StatusMissao.PLANEJADA &&
                missao.getStatus() != StatusMissao.EM_ANDAMENTO) {
            throw new IllegalStateException(
                    "Missão não está em estado compatível para aceitar participantes");
        }

        if (!missao.getOrganizacao().getId().equals(aventureiro.getOrganizacao().getId())) {
            throw new IllegalStateException(
                    "Aventureiro e missão pertencem a organizações diferentes");
        }

        ParticipacaoMissaoId id = new ParticipacaoMissaoId(missaoId, aventureiroId);

        ParticipacaoMissao participacao = new ParticipacaoMissao();
        participacao.setId(id);
        participacao.setMissao(missao);
        participacao.setAventureiro(aventureiro);
        participacao.setPapelMissao(papel);
        participacao.setRecompensaOuro(recompensaOuro);
        participacao.setMvp(mvp);
        participacao.setCreatedAt(LocalDateTime.now());

        return participacaoMissaoRepository.save(participacao);
    }
}