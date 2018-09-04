package com.wt.bl.service.impl;

import com.wt.bl.dto.UserDTO;
import com.wt.bl.mapper.UserMapper;
import com.wt.bl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author WangTao
 *         Created at 18/9/3 下午4:44.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void addUser(UserDTO user) {
        String id = UUID.randomUUID().toString().replace("-", "");
        LocalDateTime now = LocalDateTime.now();
        userMapper.addUser(user.toBuilder().id(id).gmtCreate(now).gmtModified(now).build());
    }
}
