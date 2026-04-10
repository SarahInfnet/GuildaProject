package br.com.infnet.GuildaProject.controller;

import br.com.infnet.GuildaProject.dto.ParticipacaoMissaoRecord;
import br.com.infnet.GuildaProject.model.ParticipacaoMissao;
import br.com.infnet.GuildaProject.service.ParticipacaoMissaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/participacoes")
@Validated
public class ParticipacaoMissaoController {

    @Autowired
    private ParticipacaoMissaoService participacaoMissaoService;

    @PostMapping
    public ResponseEntity<ParticipacaoMissao> criar(
            @Valid @RequestBody ParticipacaoMissaoRecord dto) {

        ParticipacaoMissao participacao = participacaoMissaoService.criar(
                dto.missaoId(), dto.aventureiroId(),
                dto.papelMissao(), dto.recompensaOuro(), dto.mvp()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(participacao);
    }
}