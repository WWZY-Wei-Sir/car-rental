package wsir.carrental.entity.authentication;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import wsir.carrental.entity.basic.LogicDelAndVerObject;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("role_menu_ties")
public class RoleMenuTies extends LogicDelAndVerObject {
    private String userRoleId;
    private String userMenuId;

    public static RoleMenuTies of(String id) {
        RoleMenuTies roleMenuTies = new RoleMenuTies();
        roleMenuTies.setId(id);
        return roleMenuTies;
    }
}
