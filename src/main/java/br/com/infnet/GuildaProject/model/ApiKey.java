package br.com.infnet.GuildaProject.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "api_keys",
    schema = "audit",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_api_keys_nome_por_org",
            columnNames = {"organizacao_id", "nome"}
        )
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, length = 255)
    private String keyHash;

    @Column(nullable = false)
    private Boolean ativo;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "last_used_at")
    private LocalDateTime lastUsedAt;

    @OneToMany(mappedBy = "actorApiKeyId")
    private List<AuditEntries> auditEntries;
}
