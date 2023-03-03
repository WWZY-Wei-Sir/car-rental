package wsir.carrental.entity.car;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import wsir.carrental.entity.basic.LogicDelAndVerObject;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("car")
public class Car extends LogicDelAndVerObject {
    private String carName;
    private String carType;
    private String carTag;
    private String licensePlate;
    private BigDecimal dailyRent;
    private Boolean rented;
    private Integer status;
}
