package org.spring.springboot.controller;

import org.spring.springboot.common.api.CommonResult;
import org.spring.springboot.domain.User;
import org.spring.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public CommonResult<User> findUserById(@RequestParam(value = "id", required = true) String id) {
        return CommonResult.success(userService.findUserById(id));
    }

    public CommonResult<User> register(@RequestBody User user) {
        return CommonResult.success(userService.register(user));
    }

//    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
//    public
}
