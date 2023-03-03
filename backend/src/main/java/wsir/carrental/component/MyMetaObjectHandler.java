package wsir.carrental.component;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import wsir.carrental.entity.User;
import wsir.carrental.entity.login.LoginUser;

import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        String currentUserName = getCurrentLoginUser().getUser().getUserName();
        this.strictInsertFill(metaObject, "createBy", String.class, currentUserName);
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateBy", String.class, currentUserName);
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "deleted", Boolean.class, false);
        this.strictInsertFill(metaObject, "version", Integer.class, 1);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String currentUserName = getCurrentLoginUser().getUser().getUserName();
        this.strictInsertFill(metaObject, "updateBy", String.class, currentUserName);
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }

    public LoginUser getCurrentLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (ObjectUtil.isNotNull(authentication)) {
            return (LoginUser) authentication.getPrincipal();
        }

        User user = new User();
        user.setUserName("CarRentalAdmin--01");
        return new LoginUser(user, null, null);
    }
}
