package br.com.infnet.GuildaProject.repository;

import br.com.infnet.GuildaProject.model.Aventureiro;
import br.com.infnet.GuildaProject.model.ClasseAventureiro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AventureiroRepository extends JpaRepository<Aventureiro, Long> {
    @Query("""
        SELECT a FROM Aventureiro a
        WHERE (:classe IS NULL OR a.classe = :classe)
        AND (:ativo IS NULL OR a.ativo = :ativo)
        AND (:nivelMinimo IS NULL OR a.nivel >= :nivelMinimo)
    """)
    Page<Aventureiro> findByFiltros(
            @Param("classe") ClasseAventureiro classe,
            @Param("ativo") Boolean ativo,
            @Param("nivelMinimo") Integer nivelMinimo,
            Pageable pageable
    );

    @Query("""
    SELECT a FROM Aventureiro a
    WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%', :nome, '%'))
    """)
    Page<Aventureiro> findByNomeContaining(
            @Param("nome") String nome,
            Pageable pageable
    );

    @Query("""
    SELECT a FROM Aventureiro a
    LEFT JOIN a.participacoes p
    GROUP BY a
    ORDER BY COUNT(p) DESC
""")
    Page<Aventureiro> findByTotalParticipacoes(Pageable pageable);

}
