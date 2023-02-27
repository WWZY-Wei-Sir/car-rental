package wsir.carrental.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import wsir.carrental.dict.UserStatus;
import wsir.carrental.dict.UserType;
import wsir.carrental.entity.User;

import java.util.List;

public interface UserService {
    IPage<User> getPages(String email, String userName, String telephone, UserStatus status, UserType userType,
                         String createTimeFirst, String createTimeLast, Long current, Long size);

    Integer insertWorkerOrAdmin(User user);

    Integer deleteBatchWorkerOrAdmin(List<String> ids);

    Integer chgStatus(User user);
}
