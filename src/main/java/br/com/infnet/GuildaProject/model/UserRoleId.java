package br.com.infnet.GuildaProject.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleId implements Serializable {

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "role_id")
    private Long roleId;
} 