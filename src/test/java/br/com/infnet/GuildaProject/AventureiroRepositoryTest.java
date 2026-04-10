package br.com.infnet.GuildaProject;

import br.com.infnet.GuildaProject.model.*;
import br.com.infnet.GuildaProject.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AventureiroRepositoryTest {

    @Autowired
    private AventureiroRepository aventureiroRepository;

    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MissaoRepository missaoRepository;

    @Autowired
    private ParticipacaoMissaoRepository participacaoMissaoRepository;

    private Organizacao organizacao;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        organizacao = new Organizacao();
        organizacao.setNome("Guilda do Norte");
        organizacao.setAtivo(true);
        organizacao.setCreatedAt(LocalDateTime.now());
        organizacaoRepository.save(organizacao);

        usuario = new Usuario();
        usuario.setNome("Usuário Teste");
        usuario.setEmail("user@test.com");
        usuario.setSenhaHash("hash-fake");
        usuario.setStatus(UsuarioStatus.ATIVO);
        usuario.setOrganizacao(organizacao);
        usuario.setCreatedAt(LocalDateTime.now());
        usuario.setUpdatedAt(LocalDateTime.now());
        usuarioRepository.save(usuario);
    }

    private Aventureiro criarAventureiro(String nome, ClasseAventureiro classe, int nivel, boolean ativo) {
        Aventureiro a = new Aventureiro();
        a.setNome(nome);
        a.setClasse(classe);
        a.setNivel(nivel);
        a.setAtivo(ativo);
        a.setOrganizacao(organizacao);
        a.setUsuario(usuario);
        a.setCreatedAt(LocalDateTime.now());
        a.setUpdatedAt(LocalDateTime.now());
        return aventureiroRepository.save(a);
    }

    private Missao criarMissao(StatusMissao status) {
        Missao m = new Missao();
        m.setTitulo("Missão de Teste");
        m.setNivelPerigo(NivelPerigo.MEDIO);
        m.setStatus(status);
        m.setOrganizacao(organizacao);
        m.setCreatedAt(LocalDateTime.now());
        return missaoRepository.save(m);
    }

    private void criarParticipacao(Aventureiro a, Missao m,
                                   int recompensa, boolean mvp,
                                   LocalDateTime data) {

        ParticipacaoMissao p = new ParticipacaoMissao();
        p.setId(new ParticipacaoMissaoId(m.getId(), a.getId()));
        p.setAventureiro(a);
        p.setMissao(m);
        p.setPapelMissao(PapelMissao.COMBATENTE);
        p.setRecompensaOuro(recompensa);
        p.setMvp(mvp);
        p.setCreatedAt(data);

        participacaoMissaoRepository.save(p);
    }

    // 1 - Listagem com filtros
    @Nested
    class ListagemComFiltros {

        @Test
        void deveFiltrarPorClasse() {
            criarAventureiro("Aragorn", ClasseAventureiro.GUERREIRO, 10, true);
            criarAventureiro("Gandalf", ClasseAventureiro.MAGO, 20, true);

            Pageable pageable = PageRequest.of(0, 10);
            Page<Aventureiro> result =
                    aventureiroRepository.findByFiltros(
                            ClasseAventureiro.MAGO, null, null, pageable);

            assertThat(result.getContent()).hasSize(1);
        }
    }

//    // 2 - Consulta detalhada
//    @Nested
//    class ConsultaDetalhada {
//
//        @Test
//        void deveCarregarParticipacoesDoAventureiro() {
//            Aventureiro a = criarAventureiro("Aragorn", ClasseAventureiro.GUERREIRO, 10, true);
//
//            Missao m1 = criarMissao(StatusMissao.CONCLUIDA);
//            Missao m2 = criarMissao(StatusMissao.CONCLUIDA);
//
//            criarParticipacao(a, m1, 100, false, LocalDateTime.now().minusDays(2));
//            criarParticipacao(a, m2, 200, true, LocalDateTime.now().minusDays(1));
//
//            Aventureiro result =
//                    aventureiroRepository.findById(a.getId()).orElseThrow();
//
//            assertThat(result.getParticipacoes()).hasSize(2);
//        }
//    }
//
//    // 3- Relatórios agregados
//    @Nested
//    class RelatoriosAgregados {
//
//        @Test
//        void deveCalcularTotalDeParticipacoes() {
//            Aventureiro a = criarAventureiro("Frodo", ClasseAventureiro.LADINO, 5, true);
//
//            Missao m = criarMissao(StatusMissao.CONCLUIDA);
//            criarParticipacao(a, m, 50, false, LocalDateTime.now());
//
//            Aventureiro result =
//                    aventureiroRepository.findById(a.getId()).orElseThrow();
//
//            assertThat(result.getParticipacoes().size()).isEqualTo(1);
//        }
//    }

    // 4- Ranking
    @Nested
    class Ranking {

        @Test
        void deveOrdenarPorNivelComoRanking() {
            criarAventureiro("Frodo", ClasseAventureiro.LADINO, 3, true);
            criarAventureiro("Aragorn", ClasseAventureiro.GUERREIRO, 10, true);
            criarAventureiro("Gandalf", ClasseAventureiro.MAGO, 20, true);

            Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "nivel"));
            Page<Aventureiro> result =
                    aventureiroRepository.findByFiltros(null, null, null, pageable);

            List<Integer> niveis =
                    result.getContent().stream().map(Aventureiro::getNivel).toList();

            assertThat(niveis).isSortedAccordingTo(Comparator.reverseOrder());
        }
    }
}
