package br.com.infnet.GuildaProject.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "organizacoes", schema = "audit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Organizacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true ,nullable = false, length = 120)
    private String nome;

    @Column(nullable = false)
    private Boolean ativo;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "organizacao")
    private List<Role> roles;

    @OneToMany(mappedBy = "organizacao")
    private List<ApiKey> apiKeys;

    @OneToMany(mappedBy = "organizacao")
    private List<AuditEntries> auditEntries;

    @OneToMany(mappedBy = "organizacao")
    private List<Usuario> usuarios;
}
