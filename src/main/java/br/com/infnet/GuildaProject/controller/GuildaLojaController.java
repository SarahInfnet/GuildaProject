package br.com.infnet.GuildaProject.controller;
import br.com.infnet.GuildaProject.dto.CategoriaCountDTO;
import br.com.infnet.GuildaProject.dto.FaixaPrecoDTO;
import br.com.infnet.GuildaProject.dto.PrecoMedioDTO;
import br.com.infnet.GuildaProject.model.GuildaLoja;
import br.com.infnet.GuildaProject.service.GuildaLojaService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@Validated
public class GuildaLojaController {

    private final GuildaLojaService guildaLojaService;

    public GuildaLojaController(GuildaLojaService guildaLojaService) {
        this.guildaLojaService = guildaLojaService;
    }


    @GetMapping("/busca/nome")
    public ResponseEntity<List<GuildaLoja>> buscarPorNome(@RequestParam String termo) {
        List<GuildaLoja> lista = guildaLojaService.buscarPorNome(termo);

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/busca/descricao")
    public ResponseEntity<List<GuildaLoja>> buscarPorDescricao(@RequestParam String termo) {
        List<GuildaLoja> lista = guildaLojaService.buscarPorDescricao(termo);

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/busca/frase")
    public ResponseEntity<List<GuildaLoja>> buscarPorFrase(@RequestParam String termo) {
        List<GuildaLoja> lista = guildaLojaService.buscarPorFraseExata(termo);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/busca/fuzzy")
    public ResponseEntity<List<GuildaLoja>> buscarFuzzy(@RequestParam String termo) {
        List<GuildaLoja> lista = guildaLojaService.buscarFuzzy(termo);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/busca/multicampos")
    public ResponseEntity<List<GuildaLoja>> buscarPorMultiplosCampos(@RequestParam String termo) {
        List<GuildaLoja> lista = guildaLojaService.buscarPorMultiplosCampos(termo);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/busca/com-filtro")
    public ResponseEntity<List<GuildaLoja>> buscarComFiltro(
            @RequestParam String termo,
            @RequestParam String categoria) {

        List<GuildaLoja> lista = guildaLojaService.buscarPorDescricaoECategoria(termo, categoria);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/busca/faixa-preco")
    public ResponseEntity<List<GuildaLoja>> buscarPorFaixaDePreco(
            @RequestParam Float min,
            @RequestParam Float max) {

        List<GuildaLoja> lista = guildaLojaService.buscarPorFaixaDePreco(min, max);

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/busca/avancada")
    public ResponseEntity<List<GuildaLoja>> buscaAvancada(
            @RequestParam String categoria,
            @RequestParam String raridade,
            @RequestParam Float min,
            @RequestParam Float max) {

        List<GuildaLoja> lista =
                guildaLojaService.buscaAvancada(categoria, raridade, min, max);

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/agregacoes/por-categoria")
    public ResponseEntity<List<CategoriaCountDTO>> contarPorCategoria() {

        List<CategoriaCountDTO> resultado = guildaLojaService.contarPorCategoria();

        if (resultado.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/agregacoes/por-raridade")
    public ResponseEntity<List<CategoriaCountDTO>> contarPorRaridade() {

        List<CategoriaCountDTO> resultado = guildaLojaService.contarPorRaridade();

        if (resultado.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/agregacoes/preco-medio")
    public ResponseEntity<PrecoMedioDTO> calcularPrecoMedio() {

        PrecoMedioDTO resultado = guildaLojaService.calcularPrecoMedio();

        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/agregacoes/faixas-preco")
    public ResponseEntity<List<FaixaPrecoDTO>> agruparPorFaixaDePreco() {

        List<FaixaPrecoDTO> resultado = guildaLojaService.agruparPorFaixaDePreco();

        if (resultado.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(resultado);
    }
}
