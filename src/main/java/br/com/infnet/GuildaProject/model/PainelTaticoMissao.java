package br.com.infnet.GuildaProject.model;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Immutable
@Table(name = "vw_painel_tatico_missao", schema = "operacoes")
@Getter
@NoArgsConstructor
public class PainelTaticoMissao {

    @Id
    @Column(name = "missao_id")
    private Long missaoId;

    @Column(name = "titulo")
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusMissao status;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_perigo")
    private NivelPerigo nivelPerigo;

    @Column(name = "organizacao_id")
    private Long organizacaoId;

    @Column(name = "total_participantes")
    private Integer totalParticipantes;

    @Column(name = "nivel_medio_equipe")
    private BigDecimal nivelMedioEquipe;

    @Column(name = "total_recompensa")
    private Integer totalRecompensa;

    @Column(name = "total_mvps")
    private Integer totalMvps;

    @Column(name = "participantes_com_companheiro")
    private Integer participantesComCompanheiro;

    @Column(name = "ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao;

    @Column(name = "indice_prontidao")
    private BigDecimal indiceProntidao;
}