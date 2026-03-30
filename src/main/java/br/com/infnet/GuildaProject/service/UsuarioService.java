package br.com.infnet.GuildaProject.service;
import br.com.infnet.GuildaProject.model.Usuario;
import br.com.infnet.GuildaProject.model.UsuarioStatus;
import br.com.infnet.GuildaProject.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;
    private final OrganizacaoService organizacaoService;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            OrganizacaoService organizacaoService
    ) {
        this.usuarioRepository = usuarioRepository;
        this.organizacaoService = organizacaoService;
    }

    public List<Usuario> listarTodos(){
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios;
    }

    public List<Usuario> paginar(List<Usuario> base, int page, int size){
        int total = base.size();
        int from = page * size;
        int to = Math.min(from + size, total);
        return base.subList(from,to);
    }

    public Usuario create(Long organizacaoId, String nome, String email, String senha){
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setOrganizacao(organizacaoService.getById(organizacaoId));
        usuario.setCreatedAt(LocalDateTime.now());
        usuario.setStatus(UsuarioStatus.PENDENTE);
        usuario.setUpdatedAt(LocalDateTime.now());
        usuario.setSenhaHash(senha);

        usuarioRepository.save(usuario);
        return usuario;
    }
}



