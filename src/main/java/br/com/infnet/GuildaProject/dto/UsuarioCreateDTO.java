package br.com.infnet.GuildaProject.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioCreateDTO {
    private Long organizacaoId;
    private String nome;
    private String email;
    private String senha;
}
