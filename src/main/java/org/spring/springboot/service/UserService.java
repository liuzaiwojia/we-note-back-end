package org.spring.springboot.service;

import org.spring.springboot.domain.User;
import org.spring.springboot.dto.UserRegisterParam;

public interface UserService {

    /**
     * 根据id查用户
     * @param id
     * @return
     */
    User findUserById(String id);

    User getUserByUserName(String userName);

    User register (User user);
}
