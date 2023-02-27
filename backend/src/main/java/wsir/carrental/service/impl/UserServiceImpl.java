package wsir.carrental.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wsir.carrental.dict.UserStatus;
import wsir.carrental.dict.UserType;
import wsir.carrental.entity.User;
import wsir.carrental.mapper.UserMapper;
import wsir.carrental.service.UserService;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    public IPage<User> getPages(String email,
                                String userName,
                                String telephone,
                                UserStatus status,
                                UserType userType,
                                String createTimeFirst,
                                String createTimeLast,
                                Long current,
                                Long size) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (!StrUtil.isBlank(email)) {
            wrapper.like(User::getEmail, email);
        }
        if (!StrUtil.isBlank(userName)) {
            wrapper.like(User::getUserName, userName);
        }
        if (!StrUtil.isBlank(telephone)) {
            wrapper.like(User::getUserName, telephone);
        }
        if (!ObjectUtil.isNull(status)) {
            wrapper.eq(User::getStatus, status);
        }
        if (!ObjectUtil.isNull(userType)) {
            wrapper.eq(User::getUserType, userType);
        }
        if (!StrUtil.isBlank(createTimeFirst)) {
            wrapper.ge(User::getCreateTime, LocalDate.parse(createTimeFirst).atStartOfDay());
        }
        if (!StrUtil.isBlank(createTimeLast)) {
            wrapper.le(User::getCreateTime, LocalDate.parse(createTimeLast).atStartOfDay());
        }

        Page<User> page = new Page<>(current, size);
        return userMapper.selectPage(page, wrapper);
    }

    @Override
    public Integer insertWorkerOrAdmin(User user) {
        return userMapper.insert(user);
    }

    @Override
    public Integer deleteBatchWorkerOrAdmin(List<String> ids) {
        return userMapper.deleteBatchIds(ids);
    }

    @Override
    public Integer chgStatus(User user) {
        return userMapper.updateById(user);
    }
}
