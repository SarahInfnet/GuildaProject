package br.com.infnet.GuildaProject.controller;
import br.com.infnet.GuildaProject.dto.PermissionRecord;
import br.com.infnet.GuildaProject.dto.RoleRecord;
import br.com.infnet.GuildaProject.dto.UsuarioRecord;
import br.com.infnet.GuildaProject.model.Role;
import br.com.infnet.GuildaProject.service.RoleService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/roles")
@Validated
public class RolesController {

    private final RoleService roleService;

    public RolesController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<RoleRecord>> getAll(@PositiveOrZero @RequestHeader(value = "X-Page", defaultValue = "0") int page,
                                                   @Min(1) @Max(50) @RequestHeader(value = "X-Size" , defaultValue = "10") int size){
        System.out.println(page + "----" + size);
        List<Role> roles = roleService.listarTodos();

        return ResponseEntity.status(HttpStatus.OK)
                .header("X-Page", String.valueOf(page))
                .header("X-Size", String.valueOf(size))
                .header("X-Total-Pages", String.valueOf(roles.size() / size))
                .header("X-Total-Count", String.valueOf(roles.size()))
                .body( roleService.paginar(roles,page,size).stream()
                        .map(u -> new RoleRecord(
                            u.getId(), u.getNome(), u.getPermissions().stream().map(
                            p ->  new PermissionRecord(
                                    p.getCode(), p.getDescricao()
                                )
                            ).toList()
                        )).toList());
    }

}