package br.com.infnet.GuildaProject.service;

import br.com.infnet.GuildaProject.exception.EntityNotFoundException;
import br.com.infnet.GuildaProject.model.Organizacao;
import br.com.infnet.GuildaProject.repository.OrganizacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizacaoService {
    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    public Organizacao getById(Long id){
        return organizacaoRepository.findById(id)
            .orElseThrow(() ->
            new EntityNotFoundException(
                    "Organização não encontrada com id " + id
            )
        );
    }
}
