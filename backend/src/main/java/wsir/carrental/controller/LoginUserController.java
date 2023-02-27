package wsir.carrental.controller;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wsir.carrental.service.MailService;
import wsir.carrental.service.login.LoginUserService;
import wsir.carrental.util.RedisUtil;
import wsir.carrental.util.Result;
import wsir.carrental.vo.UserVo;

import java.net.HttpURLConnection;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class LoginUserController {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private LoginUserService loginUserService;

    @Autowired
    private MailService mailService;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody UserVo userVo) {
        return Result.success(loginUserService.login(userVo));
    }

    @PostMapping("/register")
    public <T> Result<T> register(@RequestBody UserVo userVo) {
        // 判断验证码是否合理
        switch (mailService.checkPassCode(userVo.getEmail(), userVo.getPassCode())) {
            case 0:
                return Result.error(HttpURLConnection.HTTP_NOT_ACCEPTABLE, "验证码已过期");
            case 1:
                return Result.error(HttpURLConnection.HTTP_NOT_ACCEPTABLE, "验证码错误");
        }

        //  已存在该用户
        if (ObjectUtil.isNotNull(loginUserService.selectUserByEmailOrName(userVo))) {
            return Result.error(HttpURLConnection.HTTP_CONFLICT, "用户已经被注册");
        }

        // 注册账号
        if (1 == loginUserService.register(userVo)) {
            redisUtil.expire(userVo.getEmail(), -2);
            return Result.success();
        } else {
            return Result.error(HttpURLConnection.HTTP_NOT_ACCEPTABLE, "注册失败");
        }
    }

    @PostMapping("/chgPwd")
    public <T> Result<T> chgPwd(@RequestBody UserVo userVo) {
        // 判断验证码是否合理
        switch (mailService.checkPassCode(userVo.getEmail(), userVo.getPassCode())) {
            case 0:
                return Result.error(HttpURLConnection.HTTP_NOT_ACCEPTABLE, "验证码已过期");
            case 1:
                return Result.error(HttpURLConnection.HTTP_NOT_ACCEPTABLE, "验证码错误");
        }

        // 修改密码
        if (0 < loginUserService.chgPwd(userVo)) {
            redisUtil.expire(userVo.getEmail(), -2);
            return Result.success();
        } else {
            return Result.error(HttpURLConnection.HTTP_NOT_ACCEPTABLE, "修改密码失败");
        }
    }
}
