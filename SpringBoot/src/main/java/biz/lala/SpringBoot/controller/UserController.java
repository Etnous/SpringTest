package biz.lala.SpringBoot.controller;

import biz.lala.SpringBoot.dao.UserPojo;
import biz.lala.SpringBoot.mapper.UserMapper;
import biz.lala.SpringBoot.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {


    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public Result hello() {
        UserPojo userPojo = userMapper.myGetById(1L);
        return Result.success(userPojo);
    }
}
