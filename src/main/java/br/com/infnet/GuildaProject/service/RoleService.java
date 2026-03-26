package br.com.infnet.GuildaProject.service;
import br.com.infnet.GuildaProject.model.Role;
import br.com.infnet.GuildaProject.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> listarTodos(){
        List<Role> roles = roleRepository.findAll();
        return roles;
    }

    public List<Role> paginar(List<Role> base, int page, int size){
        int total = base.size();
        int from = page * size;
        int to = Math.min(from + size, total);
        return base.subList(from,to);
    }
}
