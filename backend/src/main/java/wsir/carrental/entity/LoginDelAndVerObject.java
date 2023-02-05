package wsir.carrental.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class LoginDelAndVerObject extends ByUserObject {
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Boolean deleted;

    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;
}
