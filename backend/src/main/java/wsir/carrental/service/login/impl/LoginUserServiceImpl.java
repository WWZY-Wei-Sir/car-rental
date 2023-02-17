package wsir.carrental.service.login.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import wsir.carrental.entity.User;
import wsir.carrental.entity.login.LoginUser;
import wsir.carrental.mapper.UserMapper;
import wsir.carrental.service.login.LoginUserService;
import wsir.carrental.util.JwtUtil;
import wsir.carrental.util.Result;

import java.util.Map;

@Service
public class LoginUserServiceImpl implements LoginUserService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String, String> login(User user) {
        //  通过email登录用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, user.getUserName());
        User userByEmail = userMapper.selectOne(wrapper);
        if (ObjectUtil.isNotNull(userByEmail)) {
            user.setUserName(userByEmail.getUserName());
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (ObjectUtil.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }

        // 认证通过，使用userId生成jwt
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId();
        String jwt = JwtUtil.createJWT(userId);
        Map<String, String> map = Map.of("token", jwt, "userPrincipal", authenticate.getPrincipal().toString());

        return map;
    }
}
