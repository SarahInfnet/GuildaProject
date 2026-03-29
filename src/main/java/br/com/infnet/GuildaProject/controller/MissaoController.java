package br.com.infnet.GuildaProject.controller;
import br.com.infnet.GuildaProject.dto.*;
import br.com.infnet.GuildaProject.model.Aventureiro;
import br.com.infnet.GuildaProject.service.MissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/missao")
@Validated
public class MissaoController {

    @Autowired
    private MissaoService missaoService;

    @GetMapping
    public ResponseEntity<List<MissaoResumoDTO>> listar(
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable,
            FiltroMissaoDTO filtro) {

        Page<MissaoResumoDTO> page = missaoService.listar(filtro, pageable);

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(page.getTotalElements()))
                .header("X-Total-Pages", String.valueOf(page.getTotalPages()))
                .header("X-Page-Number", String.valueOf(page.getNumber()))
                .header("X-Page-Size", String.valueOf(page.getSize()))
                .body(page.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MissaoDetalheDTO> getDetalhe(@PathVariable Long id) {
        return ResponseEntity.ok(missaoService.getDetalhe(id));
    }


}