package wsir.carrental.dict;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum OrderStatusChgEvent {
    PAY_DEPOSIT(1, "待下定金"),
    TAKE_CAR(2, "待拿车"),
    RETURN_CAR(3, "待还车"),
    PAY_REMAINING(4, "待付剩余金额");

    @EnumValue
    private Integer code;
    private String msg;

    OrderStatusChgEvent(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
