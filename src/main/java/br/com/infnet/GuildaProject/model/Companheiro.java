package br.com.infnet.GuildaProject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(name = "companheiros", schema = "aventura")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Companheiro {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "aventureiro_id", nullable = false)
    private Aventureiro aventureiro;

    @Column(nullable = false, length = 120)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EspecieCompanheiro especie;

    @Min(0)
    @Max(100)
    @Column(name = "indice_lealdade", nullable = false)
    private Integer lealdade;
}


