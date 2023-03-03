package wsir.carrental.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import wsir.carrental.dict.UserStatus;
import wsir.carrental.dict.UserType;
import wsir.carrental.entity.User;

import java.util.List;

public interface UserService {
    IPage<User> getPages(String email, String userName, UserStatus status, UserType userType, Long current, Long size);

    Integer insertWorkerOrAdmin(User user);

    Integer deleteBatchWorkerOrAdmin(List<String> ids);

    Integer chgUser(User user);
}
