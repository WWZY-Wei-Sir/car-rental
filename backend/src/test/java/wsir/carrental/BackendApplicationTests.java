package wsir.carrental;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import wsir.carrental.dict.UserStatus;
import wsir.carrental.dict.UserType;
import wsir.carrental.entity.User;
import wsir.carrental.mapper.UserMapper;
import wsir.carrental.util.JwtUtil;

import java.util.List;
import java.util.UUID;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

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

    @Test
    void test2() {
        System.out.println(UUID.randomUUID().toString().replace("-", "="));
    }

    @Test
    void test3() throws Exception {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUserName, "hellozxc");
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        System.out.println(user);
        System.out.println("=====================");

        String pwd = passwordEncoder.encode("123456");
        System.out.println("passwordEncoder.encode(\"123456\") = " + pwd);
        user.setPassword(pwd);
        System.out.println(user);
        System.out.println("----------------------");
        Claims claims = jwtUtil.parseJWT(jwtUtil.createJWT(user.toString()));

        System.out.println("```````````````````````````````````````");
        System.out.println(claims.getSubject());
        System.out.println("```````````````````````````````````````");

        userMapper.updateById(user);
        System.out.println(passwordEncoder.matches("123456", user.getPassword()));
    }

}
