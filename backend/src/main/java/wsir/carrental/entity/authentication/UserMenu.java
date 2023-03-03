package wsir.carrental.entity.authentication;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import wsir.carrental.entity.basic.LogicDelAndVerObject;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("user_menu")
public class UserMenu extends LogicDelAndVerObject {
    private String name;
    private String path;
    private String icon;
    private String description;

    public static UserMenu of(String id) {
        UserMenu userMenu = new UserMenu();
        userMenu.setId(id);
        return userMenu;
    }
}
