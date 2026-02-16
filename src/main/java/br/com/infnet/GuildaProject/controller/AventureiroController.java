package br.com.infnet.GuildaProject.controller;

import br.com.infnet.GuildaProject.dto.*;
import br.com.infnet.GuildaProject.model.Aventureiro;
import br.com.infnet.GuildaProject.service.AventureiroService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aventureiro")
@Validated
public class AventureiroController {

    private final AventureiroService aventureiroService;

    public AventureiroController(AventureiroService aventureiroService) {
        this.aventureiroService = aventureiroService;
    }

    @GetMapping
    public ResponseEntity<List<AventureiroResumoDTO>> getAll(@PositiveOrZero @RequestHeader(value = "X-Page", defaultValue = "0") int page,
                                                    @Min(1) @Max(50) @RequestHeader(value = "X-Size" , defaultValue = "10") int size){

        System.out.println(page + "----" + size);
        List<Aventureiro> aventureiros = aventureiroService.listarTodos();

        return ResponseEntity.status(HttpStatus.OK)
                .header("X-Page", String.valueOf(page))
                .header("X-Size", String.valueOf(size))
                .header("X-Total-Pages", String.valueOf(aventureiros.size() / size))
                .header("X-Total-Count", String.valueOf(aventureiros.size()))
                .body( aventureiroService.paginar(aventureiros,page,size).stream()
                .map(a -> new AventureiroResumoDTO(
                        a.getId(), a.getNome(), a.getClasse(), a.getNivel(), a.getAtivo()
                )).toList());
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<AventureiroResumoDTO>> filtrar(
            @PositiveOrZero @RequestHeader(value = "X-Page", defaultValue = "0") int page,
            @Min(1) @Max(50) @RequestHeader(value = "X-Size" , defaultValue = "10") int size,
            @RequestBody FiltroDTO filtro
    ){

        System.out.println(page + "----" + size);
        List<Aventureiro> aventureiros = aventureiroService.filtrar(filtro.classe(), filtro.ativo(), filtro.nivel());
        return ResponseEntity.status(HttpStatus.OK)
                .header("X-Page", String.valueOf(page))
                .header("X-Size", String.valueOf(size))
                .header("X-Total-Pages", String.valueOf(aventureiros.size() / size))
                .header("X-Total-Count", String.valueOf(aventureiros.size()))
                .body( aventureiroService.paginar(aventureiros,page,size).stream()
                    .map(a -> new AventureiroResumoDTO(
                            a.getId(), a.getNome(), a.getClasse(), a.getNivel(), a.getAtivo()
                    )).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        Aventureiro a = aventureiroService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(a);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody AventureiroRecord aventureiroRecord){
        Aventureiro aventureiro = aventureiroService.create(aventureiroRecord.nome(), aventureiroRecord.classe(), aventureiroRecord.nivel());
        return ResponseEntity.status(HttpStatus.CREATED).body(aventureiro);
    }

    @PatchMapping("/{id}/encerrar")
    public ResponseEntity<Aventureiro> encerrarVinculo(@PathVariable Integer id) {
        Aventureiro aventureiro = aventureiroService.encerrarVinculo(id);
        return ResponseEntity.status(HttpStatus.OK).body(aventureiro);
    }

    @PatchMapping("/{id}/recrutar")
    public ResponseEntity<Aventureiro> recrutar(@PathVariable Integer id) {
        Aventureiro aventureiro = aventureiroService.recrutarNovamente(id);
        return ResponseEntity.status(HttpStatus.OK).body(aventureiro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aventureiro> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody AtualizarAventureiroRecord dto
    ) {
        Aventureiro aventureiro = aventureiroService.atualizar(
                id,
                dto.nome(),
                dto.classe(),
                dto.nivel()
        );

        return ResponseEntity.status(HttpStatus.OK).body(aventureiro);
    }

    @PutMapping("/{id}/companheiro")
    public ResponseEntity<Aventureiro> atualizarCompanheiro(
            @PathVariable Integer id,
            @Valid @RequestBody AtualizarCompanheiroRecord dto
    ) {
        Aventureiro aventureiro = aventureiroService.atualizarCompanheiro(
                id,
                dto.nome(),
                dto.especie(),
                dto.lealdade()
        );

        return ResponseEntity.status(HttpStatus.OK).body(aventureiro);
    }

    @DeleteMapping("/{id}/companheiro")
    public ResponseEntity<Aventureiro> deletarCompanheiro(@PathVariable Integer id){
       Aventureiro aventureiro = aventureiroService.deletarCompanheiro(id);
       return ResponseEntity.status(HttpStatus.OK).body(aventureiro);
    }

}

