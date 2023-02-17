package wsir.carrental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wsir.carrental.entity.User;
import wsir.carrental.mapper.UserMapper;
import wsir.carrental.service.login.LoginUserService;
import wsir.carrental.util.Result;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginUserService loginUserService;

    @GetMapping("/find")
    public List<User> find() {
       return userMapper.selectList(null);
    }

    @PostMapping("/login")
    public <T> Result<Map<String, String>> login(@RequestBody User user) {
        return Result.success(loginUserService.login(user));
    }
}
