package wsir.carrental.service.login;

import wsir.carrental.entity.User;

import java.util.Map;

public interface LoginUserService {
    Map<String, Object> login(User user);

    Integer register(User user);

    Integer chgPwd(User user);

    User selectUserByEmailOrName(User user);
}
