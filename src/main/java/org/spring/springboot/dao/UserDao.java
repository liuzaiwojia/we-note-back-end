package org.spring.springboot.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.spring.springboot.domain.User;

@Mapper
public interface UserDao {

    /**
     * 根据用户id查用户
     * @param id 用户id
     * @return User 用户
     */
    User findUserById(@Param("id") String id);
}
