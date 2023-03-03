package wsir.carrental;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import wsir.carrental.dict.UserStatus;
import wsir.carrental.dict.UserType;
import wsir.carrental.entity.User;
import wsir.carrental.entity.authentication.RoleMenuTies;
import wsir.carrental.entity.authentication.UserMenu;
import wsir.carrental.entity.authentication.UserRole;
import wsir.carrental.mapper.RoleMenuTiesMapper;
import wsir.carrental.mapper.UserMenuMapper;
import wsir.carrental.mapper.UserMapper;
import wsir.carrental.mapper.UserRoleMapper;
import wsir.carrental.service.impl.UserServiceImpl;
import wsir.carrental.util.JwtUtil;

import java.util.List;
import java.util.UUID;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserMenuMapper userMenuMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMenuTiesMapper roleMenuTiesMapper;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void contextLoads() {
        User user = new User();
        user.setEmail("qwer@12345678932asd31.com");
        user.setStatus(UserStatus.Normal);
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

    @Test
    void test4() {
//        User user = userMapper.selectById("2a52cb7b8941e12cbe6cd870fc3605c0");
//        user.setUserName("CarRentalAdmin");
//
//        userMapper.updateById(user);
//        System.out.println(userMapper.selectById(user.getId()));

        System.out.println(UserStatus.valueOf("Block"));
        System.out.println(UserStatus.getByMsg("qwer"));
        System.out.println(UserStatus.getByMsg("正常"));


//        System.out.println(userMenuMapper.getAuthenticByUser("2a52cb7b8941e12cbe6cd870fc3605c0"));
//        UserRole userRole = new UserRole();
//        userRole.setName("Admin");
//        System.out.println(userRoleMapper.insert(userRole));
//
//        UserMenu userMenu = new UserMenu();
//        userMenu.setName("userManager");
//        userMenuMapper.insert(userMenu);
//
//        roleMenuTiesMapper.insert(new RoleMenuTies());

//        IPage<User> pages = userServiceImpl.getPages(user);
//        System.out.println(pages);
    }
}
