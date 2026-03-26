package br.com.infnet.GuildaProject;
import br.com.infnet.GuildaProject.model.Organizacao;
import br.com.infnet.GuildaProject.model.Role;
import br.com.infnet.GuildaProject.model.Usuario;
import br.com.infnet.GuildaProject.repository.OrganizacaoRepository;
import br.com.infnet.GuildaProject.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTest {
    @Autowired
    private OrganizacaoRepository organizacaoRepository;
    private UsuarioRepository usuarioRepository;

    @Test
    void deveSalvarEConsultar() {
        Organizacao organizacao = new Organizacao();
        organizacao.setNome("nomeOrganizacao");
        organizacao.setAtivo(true);
        organizacao.setCreatedAt(LocalDateTime.now());

        organizacaoRepository.save(organizacao);

        List<Organizacao> lista = organizacaoRepository.findAll();

        assertFalse(lista.isEmpty());
    }

//    @Test
//    void userAndRoles(){
//        Usuario usuario = new Usuario();
//        Role role = new Role();
//
//        usuarioRepository.findAll();
//
//    }
}