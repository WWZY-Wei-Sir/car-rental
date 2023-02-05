package wsir.carrental.dict;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum UserStatus {
    Nromal(1, "正常"),
    Block(0, "封禁");

    @EnumValue
    private Integer code;
    private String msg;

    UserStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
