package com.wt.bl.mapper;

import com.wt.bl.entity.User;

/**
 * @author WangTao
 *         Created at 18/9/3 下午4:43.
 */
public interface UserMapper {

    void addUser(User user);

    User findByUsername(String username);

}
