package wsir.carrental.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import wsir.carrental.dict.UserStatus;
import wsir.carrental.dict.UserType;
import wsir.carrental.entity.User;
import wsir.carrental.service.UserService;
import wsir.carrental.util.Result;
import wsir.carrental.vo.UserVo;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/userManager")
@PreAuthorize("hasAuthority('userManager')")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getPages")
    public Result<Map<String, Object>> getPages(@RequestParam String email,
                                                @RequestParam String userName,
                                                @RequestParam UserStatus status,
                                                @RequestParam UserType userType,
                                                @RequestParam Long current,
                                                @RequestParam Long size) {
        IPage<User> pages = userService.getPages(email, userName, status, userType, current, size);
        return Result.success(Map.of("total", pages.getTotal(), "page", pages));
    }

    @PostMapping("/insertOne")
    public <T> Result<T> insertWorkerOrAdmin(@RequestBody UserVo userVo) {
        if (userService.insertWorkerOrAdmin(userVo) > 0) {
            return Result.success();
        }
        return Result.error(HttpURLConnection.HTTP_PAYMENT_REQUIRED, "添加员工或管理员失败！");
    }

    @PostMapping("/delMore")
    public <T> Result<T> deleteBatchWorkerOrAdmin(@RequestBody List<String> ids) {
        if (userService.deleteBatchWorkerOrAdmin(ids) > 0) {
            return Result.success();
        }
        return Result.error(HttpURLConnection.HTTP_PAYMENT_REQUIRED, "批量删除员工或管理员失败！");
    }

    @PostMapping("/chgUser")
    public <T> Result<T> chgUser(@RequestBody UserVo userVo) {
        if (userService.chgUser(userVo) > 0) {
            return Result.success();
        }
        return Result.error(HttpURLConnection.HTTP_PAYMENT_REQUIRED, "更改用户状态失败！");
    }
}
