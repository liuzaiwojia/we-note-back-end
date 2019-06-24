package org.spring.springboot.service;

import org.spring.springboot.domain.User;

public interface UserService {

    /**
     * 根据id查用户
     * @param id
     * @return
     */
    User findUserById(String id);
}
