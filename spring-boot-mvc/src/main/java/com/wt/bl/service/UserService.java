package com.wt.bl.service;

import com.wt.bl.entity.User;

/**
 * @author WangTao
 *         Created at 18/9/3 下午4:43.
 */
public interface UserService {

    void addUser(User user);

    User findByUsername(String username);

}
