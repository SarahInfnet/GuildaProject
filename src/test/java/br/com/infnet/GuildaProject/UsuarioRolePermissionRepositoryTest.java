package br.com.infnet.GuildaProject;

import br.com.infnet.GuildaProject.model.*;
import br.com.infnet.GuildaProject.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Set;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsuarioRolePermissionRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionsRepository permissionsRepository;

    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Test
    void deveCarregarUsuarioComRolesOrganizacaoEPermissoes() {

        Organizacao org = new Organizacao();
        org.setNome("Guilda Central");
        org.setAtivo(true);
        org.setCreatedAt(LocalDateTime.now());
        organizacaoRepository.save(org);

        Permissions permission = new Permissions();
        permission.setCode("AVENTUREIRO_CRIAR");
        permission.setDescricao("Criar aventureiro");
        permissionsRepository.save(permission);

        Role role = new Role();
        role.setNome("ADMIN");
        role.setDescricao("Administrador");
        role.setOrganizacao(org);
        role.setPermissions(Set.of(permission));
        role.setCreatedAt(LocalDateTime.now());
        roleRepository.save(role);

        Usuario usuario = new Usuario();
        usuario.setNome("Sarah");
        usuario.setEmail("sarah@guilda.com");
        usuario.setSenhaHash("hash");
        usuario.setStatus(UsuarioStatus.ATIVO);
        usuario.setOrganizacao(org);
        usuario.setCreatedAt(LocalDateTime.now());
        usuario.setUpdatedAt(LocalDateTime.now());
        usuarioRepository.save(usuario);

        UserRole userRole = new UserRole();
        userRole.setId(new UserRoleId(usuario.getId(), role.getId()));
        userRole.setUsuario(usuario);
        userRole.setRole(role);
        userRole.setGrantedAt(LocalDateTime.now());
        userRoleRepository.save(userRole);

        usuario.getUserRoles().add(userRole);

        Usuario usuarioPersistido =
                usuarioRepository.findById(usuario.getId()).orElseThrow();

        assertThat(usuarioPersistido.getUserRoles()).hasSize(1);

        //Organização
        assertThat(usuarioPersistido.getOrganizacao()).isNotNull();
        assertThat(usuarioPersistido.getOrganizacao().getNome())
                .isEqualTo("Guilda Central");

        //Roles
        assertThat(usuarioPersistido.getUserRoles()).hasSize(1);

        Role rolePersistida =
                usuarioPersistido.getUserRoles()
                        .get(0)
                        .getRole();

        assertThat(rolePersistida.getNome()).isEqualTo("ADMIN");

        //Permissões via Role
        assertThat(rolePersistida.getPermissions()).hasSize(1);

        Permissions permissionPersistida =
                rolePersistida.getPermissions().iterator().next();

        assertThat(permissionPersistida.getCode())
                .isEqualTo("AVENTUREIRO_CRIAR");
    }
}