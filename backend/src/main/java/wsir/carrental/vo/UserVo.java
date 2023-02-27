package wsir.carrental.vo;

import lombok.*;
import wsir.carrental.entity.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserVo extends User {
    private String passCode;
}
