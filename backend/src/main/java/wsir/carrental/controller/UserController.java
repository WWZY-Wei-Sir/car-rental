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
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getPages")
    @PreAuthorize("hasAuthority('test')")
    public Result<Map<String, Object>> getPages(@RequestParam String email) {
//        IPage<User> pages = userService.getPages(email, userName, telephone, status, userType, createTimeFirst, createTimeLast, current, size);
//        return Result.success(Map.of("total", pages.getTotal(), "data", pages));
        return Result.success();
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

    @PostMapping("/chgStatus")
    public <T> Result<T> chgStatus(@RequestBody UserVo userVo) {
        if (userService.chgStatus(userVo) > 0) {
            return Result.success();
        }
        return Result.error(HttpURLConnection.HTTP_PAYMENT_REQUIRED, "更改用户状态失败！");
    }
}
