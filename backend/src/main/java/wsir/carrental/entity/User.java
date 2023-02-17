package wsir.carrental.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import wsir.carrental.dict.UserStatus;
import wsir.carrental.dict.UserType;
import wsir.carrental.entity.basic.LoginDelAndVerObject;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("user")
public class User extends LoginDelAndVerObject {
    private String email;
    private String userName;
    private String password;
    private String telephone;
    private UserStatus status;
    private UserType userType;

    public static User of(String id) {
        User user = new User();
        user.setId(id);
        return user;
    }
}
