package wsir.carrental.entity.authentication;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import wsir.carrental.entity.basic.LogicDelAndVerObject;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("user_role")
public class UserRole extends LogicDelAndVerObject {
    private String name;
    private String description;

    public static UserRole of(String id) {
        UserRole userRole = new UserRole();
        userRole.setId(id);
        return userRole;
    }
}
