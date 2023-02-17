package wsir.carrental.service.login;

import wsir.carrental.entity.User;
import wsir.carrental.util.Result;

import java.util.Map;

public interface LoginUserService {
    Map<String, String> login(User user);
}
