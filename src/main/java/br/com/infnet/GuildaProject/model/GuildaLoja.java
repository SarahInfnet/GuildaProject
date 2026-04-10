package br.com.infnet.GuildaProject.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "guilda_loja")
public class GuildaLoja {

    @Id
    private String id;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "portuguese_custom"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private String nome;

    @Field(type = FieldType.Text, analyzer = "portuguese_custom")
    private String descricao;

    @Field(type = FieldType.Keyword)
    private String categoria;

    @Field(type = FieldType.Keyword)
    private String raridade;

    @Field(type = FieldType.Float )
    private Float  preco;
}