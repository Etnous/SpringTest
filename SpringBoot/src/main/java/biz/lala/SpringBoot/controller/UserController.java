package biz.lala.SpringBoot.controller;

import biz.lala.SpringBoot.util.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public Result<String> hello() {
        return Result.success();
    }
}
