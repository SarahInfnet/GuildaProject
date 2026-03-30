package br.com.infnet.GuildaProject.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "roles",
    schema = "audit",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_roles_nome_por_org",
            columnNames = {"organizacao_id", "nome"}
        )
    })

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_permissions",
            schema = "audit",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permissions> permissions;

    @Column(nullable = false, length = 60)
    private String nome;

    @Column(length = 255)
    private String descricao;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "role",
    cascade = CascadeType.REMOVE,
    orphanRemoval = true
    )
    private List<UserRole> userRoles;
}
