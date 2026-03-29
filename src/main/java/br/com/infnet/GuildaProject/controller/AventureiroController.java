package br.com.infnet.GuildaProject.controller;

import br.com.infnet.GuildaProject.dto.*;
import br.com.infnet.GuildaProject.model.Aventureiro;
import br.com.infnet.GuildaProject.model.ClasseAventureiro;
import br.com.infnet.GuildaProject.model.Companheiro;
import br.com.infnet.GuildaProject.service.AventureiroService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/aventureiros")
@Validated
public class AventureiroController {

    private final AventureiroService aventureiroService;

    public AventureiroController(AventureiroService aventureiroService) {
        this.aventureiroService = aventureiroService;
    }


    @GetMapping
    public ResponseEntity<List<AventureiroResumoDTO>> filtrar(
            @PageableDefault(size = 10, page = 0, sort = "nome") Pageable pageable,
            FiltroDTO filtro) {

        Page<Aventureiro> page = aventureiroService.filtrar(
                filtro.classe(), filtro.ativo(), filtro.nivelMinimo(), pageable);

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(page.getTotalElements()))
                .header("X-Total-Pages", String.valueOf(page.getTotalPages()))
                .header("X-Page-Number", String.valueOf(page.getNumber()))
                .header("X-Page-Size", String.valueOf(page.getSize()))
                .body(page.getContent().stream()
                        .map(a -> new AventureiroResumoDTO(
                                a.getId(), a.getNome(), a.getClasse(), a.getNivel(), a.getAtivo()
                        )).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Aventureiro a = aventureiroService.getById(id);
        Optional<CompanheiroDTO> companheiroDTO = Optional.ofNullable(a.getCompanheiro()).map(
                c -> new CompanheiroDTO(c.getNome(), c.getEspecie(), c.getLealdade()));

        AventureiroPerfilDTO aventureiroPerfilDTO = new AventureiroPerfilDTO(
                a.getId(),
                a.getNome(),
                a.getClasse(),
                a.getNivel(),
                a.getAtivo(),
                companheiroDTO ,
                a.getParticipacoes().size(),
                a.getParticipacoes().stream().max(
                    (p1, p2) -> p1.getCreatedAt().compareTo(p2.getCreatedAt())
                ).map(ultimo -> ultimo.getId().getMissaoId())

        );
        return ResponseEntity.status(HttpStatus.OK).body(aventureiroPerfilDTO);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody AventureiroRecord aventureiroRecord){
        Aventureiro aventureiro = aventureiroService.create(
                aventureiroRecord.nome(),
                aventureiroRecord.classe(),
                aventureiroRecord.nivel(),
                aventureiroRecord.organizacaoId(),
                aventureiroRecord.usuarioId()
        );

        AventureiroPerfilDTO aventureiroPerfilDTO = new AventureiroPerfilDTO(
                aventureiro.getId(),
                aventureiro.getNome() ,
                aventureiro.getClasse(),
                aventureiro.getNivel(),
                aventureiro.getAtivo(),
                null,
                null,
                null

        );
        return ResponseEntity.status(HttpStatus.CREATED).body(aventureiroPerfilDTO);
    }

    @PatchMapping("/{id}/encerrar")
    public ResponseEntity<Void> encerrarVinculo(@PathVariable Long id) {
        Aventureiro aventureiro = aventureiroService.encerrarVinculo(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/recrutar")
    public ResponseEntity<Void> recrutar(@PathVariable Long id) {
        Aventureiro aventureiro = aventureiroService.recrutarNovamente(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarAventureiroRecord dto
    ) {
        Aventureiro aventureiro = aventureiroService.atualizar(
                id,
                dto.nome(),
                dto.classe(),
                dto.nivel()
        );

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/companheiro")
    public ResponseEntity<Void> atualizarCompanheiro(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarCompanheiroRecord dto
    ) {
        Aventureiro aventureiro = aventureiroService.atualizarCompanheiro(
                id,
                dto.nome(),
                dto.especie(),
                dto.lealdade()
        );

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/companheiro")
    public ResponseEntity<Void> deletarCompanheiro(@PathVariable Long id){
       Aventureiro aventureiro = aventureiroService.deletarCompanheiro(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<AventureiroResumoDTO>> buscarPorNome(
            @RequestParam String nome,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {

        Page<AventureiroResumoDTO> page = aventureiroService.buscarPorNome(nome, pageable);

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(page.getTotalElements()))
                .header("X-Total-Pages", String.valueOf(page.getTotalPages()))
                .header("X-Page-Number", String.valueOf(page.getNumber()))
                .header("X-Page-Size", String.valueOf(page.getSize()))
                .body(page.getContent());
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<RankingDTO>> rankearAventureiro(@RequestParam(defaultValue = "totalParticipacoes") String criterio){

        List<RankingDTO> aventureirosRankeados = aventureiroService.rankearAventureiros(criterio);

        return ResponseEntity.ok().body(aventureirosRankeados);
    }

}

