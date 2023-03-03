package wsir.carrental.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import wsir.carrental.entity.authentication.UserMenu;

import java.util.List;

@Repository
public interface UserMenuMapper extends BaseMapper<UserMenu> {

    @Select("SELECT DISTINCT m.name FROM user u " +
            "LEFT JOIN user_role r ON u.user_role_id = r.id " +
            "LEFT JOIN role_menu_ties rm ON u.user_role_id = rm.user_role_id " +
            "LEFT JOIN user_menu m ON rm.user_menu_id = m.id " +
            "WHERE u.id = #{id} AND u.deleted = 0 AND r.deleted = 0 AND rm.deleted = 0 AND m.deleted = 0")
    List<String> getAuthenticByUser(String id);
}
