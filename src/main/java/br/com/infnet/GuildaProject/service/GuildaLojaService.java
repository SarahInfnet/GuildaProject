package br.com.infnet.GuildaProject.service;
import br.com.infnet.GuildaProject.dto.CategoriaCountDTO;
import br.com.infnet.GuildaProject.dto.FaixaPrecoDTO;
import br.com.infnet.GuildaProject.dto.PrecoMedioDTO;
import br.com.infnet.GuildaProject.model.GuildaLoja;
import br.com.infnet.GuildaProject.repository.GuildaLojaRepository;
import co.elastic.clients.elasticsearch._types.aggregations.AggregationRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import co.elastic.clients.elasticsearch._types.aggregations.AggregationBuilders;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregation;
import co.elastic.clients.elasticsearch._types.aggregations.RangeBucket;

import java.util.ArrayList;

import java.util.List;

@Service
public class GuildaLojaService {
//    @Autowired
//    private GuildaLojaRepository guildaLojaRepository;
//
//    public List<GuildaLoja> buscarPorNome(String nome) {
//        return guildaLojaRepository.findByNomeContaining(nome);
//    }

    private final GuildaLojaRepository repository;
    private final ElasticsearchOperations elasticsearchOperations;

    public GuildaLojaService(GuildaLojaRepository repository, ElasticsearchOperations elasticsearchOperations) {
        this.repository = repository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public List<GuildaLoja> buscarPorNome(String termo) {
        return repository.buscarPorNome(termo);
    }

    public List<GuildaLoja> buscarPorDescricao(String termo) {
        return repository.buscarPorDescricao(termo);
    }

    public List<GuildaLoja> buscarPorFraseExata(String termo) {
        return repository.buscarPorFraseExata(termo);
    }

    public List<GuildaLoja> buscarFuzzy(String termo) {
        return repository.buscarFuzzy(termo);
    }

    public List<GuildaLoja> buscarPorMultiplosCampos(String termo) {
        return repository.buscarPorMultiplosCampos(termo);
    }

    public List<GuildaLoja> buscarPorDescricaoECategoria(String termo, String categoria) {
        return repository.buscarPorDescricaoECategoria(termo, categoria);
    }

    public List<GuildaLoja> buscarPorFaixaDePreco(Float min, Float max) {
        return repository.buscarPorFaixaDePreco(min, max);
    }

    public List<GuildaLoja> buscaAvancada(
            String categoria,
            String raridade,
            Float min,
            Float max) {

        return repository.buscaAvancada(categoria, raridade, min, max);
    }

    public List<CategoriaCountDTO> contarPorCategoria() {

        Query query = NativeQuery.builder()
                .withAggregation(
                        "por_categoria",
                        AggregationBuilders.terms(t -> t
                                .field("categoria")
                                .size(100)
                        )
                )
                .withMaxResults(0)
                .build();

        SearchHits<GuildaLoja> hits =
                elasticsearchOperations.search(query, GuildaLoja.class);

        ElasticsearchAggregations aggregations =
                (ElasticsearchAggregations) hits.getAggregations();

        ElasticsearchAggregation agregacao =
                aggregations.get("por_categoria");

        List<StringTermsBucket> buckets = agregacao
                .aggregation()
                .getAggregate()
                .sterms()
                .buckets()
                .array();

        List<CategoriaCountDTO> resultado = new ArrayList<>();
        for (StringTermsBucket bucket : buckets) {
            resultado.add(new CategoriaCountDTO(
                    bucket.key().stringValue(),
                    bucket.docCount()
            ));
        }

        return resultado;
    }

    public List<CategoriaCountDTO> contarPorRaridade() {

        Query query = NativeQuery.builder()
                .withAggregation(
                        "por_raridade",
                        AggregationBuilders.terms(t -> t
                                .field("raridade")
                                .size(100)
                        )
                )
                .withMaxResults(0)
                .build();

        SearchHits<GuildaLoja> hits =
                elasticsearchOperations.search(query, GuildaLoja.class);

        ElasticsearchAggregations aggregations =
                (ElasticsearchAggregations) hits.getAggregations();

        ElasticsearchAggregation agregacao =
                aggregations.get("por_raridade");

        List<StringTermsBucket> buckets = agregacao
                .aggregation()
                .getAggregate()
                .sterms()
                .buckets()
                .array();

        List<CategoriaCountDTO> resultado = new ArrayList<>();
        for (StringTermsBucket bucket : buckets) {
            resultado.add(new CategoriaCountDTO(
                    bucket.key().stringValue(),
                    bucket.docCount()
            ));
        }

        return resultado;
    }

    public PrecoMedioDTO calcularPrecoMedio() {

        Query query = NativeQuery.builder()
                .withAggregation(
                        "preco_medio",
                        AggregationBuilders.avg(a -> a
                                .field("preco")
                        )
                )
                .withMaxResults(0)
                .build();

        SearchHits<GuildaLoja> hits =
                elasticsearchOperations.search(query, GuildaLoja.class);

        ElasticsearchAggregations aggregations =
                (ElasticsearchAggregations) hits.getAggregations();

        ElasticsearchAggregation agregacao =
                aggregations.get("preco_medio");

        double media = agregacao
                .aggregation()
                .getAggregate()
                .avg()
                .value();

        return new PrecoMedioDTO(media);
    }

    public List<FaixaPrecoDTO> agruparPorFaixaDePreco() {

        List<AggregationRange> ranges = List.of(
                AggregationRange.of(r -> r.to(100.0)),
                AggregationRange.of(r -> r.from(100.0).to(300.0)),
                AggregationRange.of(r -> r.from(300.0).to(700.0)),
                AggregationRange.of(r -> r.from(700.0))
        );

        Query query = NativeQuery.builder()
                .withAggregation(
                        "faixas_preco",
                        AggregationBuilders.range(r -> r
                                .field("preco")
                                .ranges(ranges)
                        )
                )
                .withMaxResults(0)
                .build();

        SearchHits<GuildaLoja> hits =
                elasticsearchOperations.search(query, GuildaLoja.class);

        ElasticsearchAggregations aggregations =
                (ElasticsearchAggregations) hits.getAggregations();

        ElasticsearchAggregation agregacao =
                aggregations.get("faixas_preco");

        List<RangeBucket> buckets = agregacao
                .aggregation()
                .getAggregate()
                .range()
                .buckets()
                .array();

        List<String> rotulos = List.of(
                "Abaixo de 100",
                "De 100 a 300",
                "De 300 a 700",
                "Acima de 700"
        );

        List<FaixaPrecoDTO> resultado = new ArrayList<>();
        for (int i = 0; i < buckets.size(); i++) {
            resultado.add(new FaixaPrecoDTO(
                    rotulos.get(i),
                    buckets.get(i).docCount()
            ));
        }

        return resultado;
    }
}
