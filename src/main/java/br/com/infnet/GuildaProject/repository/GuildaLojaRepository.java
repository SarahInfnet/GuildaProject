package br.com.infnet.GuildaProject.repository;
import br.com.infnet.GuildaProject.model.GuildaLoja;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface GuildaLojaRepository extends ElasticsearchRepository<GuildaLoja, String> {
    //List<GuildaLoja> findByNomeContaining(String nome);

       @Query("""
    {
      "match": {
        "nome": {
          "query": "?0",
          "fuzziness": "AUTO",
          "prefix_length": 2,
          "max_expansions": 50
        }
      }
    }
    """)
        List<GuildaLoja> buscarPorNome(String termo);

        @Query("""
    {
      "match": {
        "descricao": {
          "query": "?0",
          "fuzziness": "AUTO",
          "prefix_length": 2,
          "max_expansions": 50
        }
      }
    }
    """)
    List<GuildaLoja> buscarPorDescricao(String termo);

    @Query("""
    {
      "match_phrase": {
        "descricao": {
          "query": "?0"
        }
      }
    }
    """)
    List<GuildaLoja> buscarPorFraseExata(String termo);

    @Query("""
    {
      "multi_match": {
        "query": "?0",
        "fields": ["nome", "descricao"],
        "fuzziness": "AUTO",
        "prefix_length": 2,
        "max_expansions": 50
      }
    }
    """)
    List<GuildaLoja> buscarPorMultiplosCampos(String termo);

    @Query("""
    {
      "bool": {
        "must": [
          {
            "match": {
              "descricao": {
                "query": "?0",
                "fuzziness": "AUTO"
              }
            }
          }
        ],
        "filter": [
          {
            "term": {
              "categoria": "?1"
            }
          }
        ]
      }
    }
    """)
    List<GuildaLoja> buscarPorDescricaoECategoria(String termo, String categoria);

    @Query("""
    {
      "fuzzy": {
        "nome": {
          "value": "?0",
          "fuzziness": "AUTO",
          "prefix_length": 2,
          "max_expansions": 50
        }
      }
    }
    """)
    List<GuildaLoja> buscarFuzzy(String termo);

    @Query("""
    {
      "range": {
        "preco": {
          "gte": ?0,
          "lte": ?1
        }
      }
    }
    """)
    List<GuildaLoja> buscarPorFaixaDePreco(Float min, Float max);

    @Query("""
    {
      "bool": {
        "must": [
          {
            "term": {
              "categoria": "?0"
            }
          },
          {
            "term": {
              "raridade": "?1"
            }
          }
        ],
        "filter": [
          {
            "range": {
              "preco": {
                "gte": ?2,
                "lte": ?3
              }
            }
          }
        ]
      }
    }
    """)
    List<GuildaLoja> buscaAvancada(
            String categoria,
            String raridade,
            Float min,
            Float max
    );
}
