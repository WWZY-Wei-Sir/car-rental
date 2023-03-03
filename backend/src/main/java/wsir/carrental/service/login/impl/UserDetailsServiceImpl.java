package wsir.carrental.service.login.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wsir.carrental.entity.User;
import wsir.carrental.entity.login.LoginUser;
import wsir.carrental.exception.ServiceException;
import wsir.carrental.mapper.UserMapper;
import wsir.carrental.mapper.UserMenuMapper;

import java.net.HttpURLConnection;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserMenuMapper userMenuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName, username);

        User user = userMapper.selectOne(wrapper);
        if (ObjectUtil.isNull(user)) {
            throw new ServiceException(HttpURLConnection.HTTP_CONFLICT, "用户名或密码错误");
        }

//        user.setStatus(null);
//        user.setUserType(null);
        List<String> authentic =  userMenuMapper.getAuthenticByUser(user.getId());
        return new LoginUser(user, authentic, null);
    }
}
