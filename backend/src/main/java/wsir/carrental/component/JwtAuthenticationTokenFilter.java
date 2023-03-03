package wsir.carrental.component;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import wsir.carrental.entity.User;
import wsir.carrental.entity.login.LoginUser;
import wsir.carrental.exception.ServiceException;
import wsir.carrental.mapper.UserMapper;
import wsir.carrental.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserMapper userMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        //获取token
        String token = request.getHeader("user_token");
        LoginUser loginUser = JSON.parseObject(request.getHeader("user_info"), LoginUser.class);
        System.out.println(token);
        if (StrUtil.isBlank(token)) {
            //放行
            try {
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                throw new ServiceException(HttpURLConnection.HTTP_INTERNAL_ERROR, "服务器放行错误");
            }
            return;
        }

        //解析token
        String userId;
        try {
            Claims claims = jwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            throw new ServiceException(HttpURLConnection.HTTP_UNAUTHORIZED, "token非法");
        }
        User user = userMapper.selectById(userId);

        if (!(user.getId().equals(loginUser.getUser().getId())
                && user.getEmail().equals(loginUser.getUser().getEmail())
                && user.getUserName().equals(loginUser.getUser().getUserName()))) {
            throw new ServiceException(HttpURLConnection.HTTP_UNAUTHORIZED, "用户信息被篡改");
        }

        //  存入SecurityContextHolder
        //  获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw new ServiceException(HttpURLConnection.HTTP_INTERNAL_ERROR, "服务器放行错误");
        }
    }
}
