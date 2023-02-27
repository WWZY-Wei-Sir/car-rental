package wsir.carrental.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import wsir.carrental.dict.OrderStatus;
import wsir.carrental.entity.basic.LoginDelAndVerObject;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("order")
public class Order extends LoginDelAndVerObject{
    private OrderStatus status;
    private Boolean isNew;

    public static Order of(String id) {
        Order order = new Order();
        order.setId(id);
        return order;
    }
}
