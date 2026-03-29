package br.com.infnet.GuildaProject.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "participacao_missao",
    schema = "aventura",
    uniqueConstraints = {
            @UniqueConstraint(columnNames = {"missao_id", "aventureiro_id"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipacaoMissao {
    @EmbeddedId
    private ParticipacaoMissaoId id;

    @MapsId("missaoId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "missao_id", nullable = false)
    private Missao missao;

    @MapsId("aventureiroId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "aventureiro_id", nullable = false)
    private Aventureiro aventureiro;

    @Enumerated(EnumType.STRING)
    @Column(name = "papel_missao", nullable = false)
    private PapelMissao papelMissao;

    @Min(0)
    @Column(name = "recompensa_ouro")
    private Integer recompensaOuro;

    @Column(nullable = false)
    private Boolean mvp;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


}
