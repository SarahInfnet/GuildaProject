package br.com.infnet.GuildaProject.repository;

import br.com.infnet.GuildaProject.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface MissaoRepository extends JpaRepository<Missao, Long> {

    @Query("""
    SELECT m FROM Missao m
    WHERE (:status IS NULL OR m.status = :status)
    AND (:nivelPerigo IS NULL OR m.nivelPerigo = :nivelPerigo)

""")
    Page<Missao> findByFiltros(
            @Param("status") StatusMissao status,
            @Param("nivelPerigo") NivelPerigo nivelPerigo,
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataTermino") LocalDateTime dataTermino,
            Pageable pageable
    );

    @Query("""
    SELECT m FROM Missao m
    LEFT JOIN FETCH m.participacoes p
    WHERE m.createdAt >= :dataInicio
    AND m.createdAt <= :dataFim
""")
    List<Missao> findByPeriodo(
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim
    );
}
