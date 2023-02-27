package wsir.carrental.dict;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum OrderStatus {
    WAIT_DEPOSIT(1, "待下定金"),
    WAIT_TAKE_CAR(2, "待拿车"),
    WAIT_RETURN_CAR(3, "待还车"),
    WAIT_REMAINING(4, "待付剩余金额"),
    FINISH(10, "结束");

    @EnumValue
    private Integer code;
    private String msg;

    OrderStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
