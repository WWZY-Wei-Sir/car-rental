package wsir.carrental.component.exception;

import com.alibaba.fastjson.JSON;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import wsir.carrental.util.Result;
import wsir.carrental.util.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.HttpURLConnection;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        String json = JSON.toJSONString(Result.error(HttpURLConnection.HTTP_PROXY_AUTH, "认证失败请重新登录"));
        WebUtil.renderString(response,json);
    }
}


