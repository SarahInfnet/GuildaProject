package br.com.infnet.GuildaProject.controller;
import br.com.infnet.GuildaProject.dto.*;
import br.com.infnet.GuildaProject.model.PainelTaticoMissao;
import br.com.infnet.GuildaProject.service.MissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @GetMapping("/relatorio")
    public ResponseEntity<List<RelatorioMissaoDTO>> gerarRelatorio(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {

        List<RelatorioMissaoDTO> relatorio = missaoService.gerarRelatorio(dataInicio, dataFim);

        return ResponseEntity.ok().body(relatorio);
    }

    @GetMapping("/top15dias")
    public ResponseEntity<List<PainelTaticoMissao>> geraTop15Dias(){
        List<PainelTaticoMissao> painelTaticoMissoes = missaoService.consultarTop15Dias();
        return ResponseEntity.ok().body(painelTaticoMissoes);
    }

}