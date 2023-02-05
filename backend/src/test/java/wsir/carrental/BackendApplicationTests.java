package wsir.carrental;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wsir.carrental.dict.UserStatus;
import wsir.carrental.dict.UserType;
import wsir.carrental.entity.User;
import wsir.carrental.mapper.UserMapper;

import java.util.List;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        User user = new User();
        user.setEmail("qwer@12345678932asd31.com");
        user.setStatus(UserStatus.Nromal);
        user.setUserType(UserType.Admin);
        user.setUserName("hello123");
        System.out.println(user.toString());
        System.out.println(user.isNew());

        userMapper.insert(user);

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserName, "hello123");
        List<User> users = userMapper.selectList(lambdaQueryWrapper);
        System.out.println("users.size() = " + users.size());
        for (User user1 : users) {
            System.out.println(user1);
            System.out.println(user1.isNew());
        }
        IPage<User> userPage = new Page<>(3, 4);
        IPage<User> iPage = userMapper.selectPage(userPage, null);

        System.out.println("=============");
        for (User record : iPage.getRecords()) {
            System.out.println(record);
        }
    }

}
