package wsir.carrental.service.login.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wsir.carrental.entity.User;
import wsir.carrental.entity.login.LoginUser;
import wsir.carrental.mapper.UserMapper;
import wsir.carrental.service.login.LoginUserService;
import wsir.carrental.util.JwtUtil;

import java.util.Map;

@Service
public class LoginUserServiceImpl implements LoginUserService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Map<String, Object> login(User user) {
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
        String jwt = jwtUtil.createJWT(userId);
        return Map.of("user_token", jwt, "userPrincipal", loginUser);
    }

    @Override
    public Integer register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.insert(user);
    }

    @Override
    public Integer chgPwd(User user) {
        User oldUser = this.selectUserByEmailOrName(user);
        if (ObjectUtil.isNull(oldUser)) {
            throw new RuntimeException("该用户不存在");
        }
        oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.updateById(oldUser);
    }

    @Override
    public User selectUserByEmailOrName(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, user.getEmail()).or().eq(User::getUserName, user.getUserName());
        return userMapper.selectOne(wrapper);
    }
}
