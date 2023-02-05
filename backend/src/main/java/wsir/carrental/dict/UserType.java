package wsir.carrental.dict;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum UserType {
    Admin(1, "管理员"),
    Worker(2, "员工"),
    Customer(3, "顾客");

    @EnumValue
    private Integer code;
    private String msg;

    UserType(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
