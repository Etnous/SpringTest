package biz.lala.SpringBoot.controller;

import biz.lala.SpringBoot.dao.UserPojo;
import biz.lala.SpringBoot.entity.ProxyEntity;
import biz.lala.SpringBoot.mapper.UserMapper;
import biz.lala.SpringBoot.util.HttpUtil;
import biz.lala.SpringBoot.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {


//    private UserMapper userMapper;
//
//    @Autowired
//    public void setUserMapper(UserMapper userMapper) {
//        this.userMapper = userMapper;
//    }
//
//    @RequestMapping(value = "/hello", method = RequestMethod.GET)
//    public Result hello() throws Exception {
////        UserPojo userPojo = userMapper.myGetById(1L);
//        ProxyEntity proxyEntity = new ProxyEntity();
//        proxyEntity.setProxyIp("120.24.76.81").setProxyPort(8123).setProxyType("http");
//        Map<String, String> params = new HashMap<>();
//        params.put("regionId", "166");
//        String s = HttpUtil.httpGetRequest("https://yikaoyan-api.51easymaster.com/v1/rank/school/", params, proxyEntity);
//        return Result.success(s);
//    }
}
