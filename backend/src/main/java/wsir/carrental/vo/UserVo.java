package wsir.carrental.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import wsir.carrental.dict.UserStatus;
import wsir.carrental.dict.UserType;
import wsir.carrental.entity.User;
import wsir.carrental.entity.basic.LoginDelAndVerObject;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserVo extends User {
    private String passCode;
}
