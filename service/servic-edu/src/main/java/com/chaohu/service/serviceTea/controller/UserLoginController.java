package com.chaohu.service.serviceTea.controller;


import com.chaohu.service.serviceTea.entity.EduTeacher;
import com.chaohu.utlis.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/serviceTea/user")
@CrossOrigin//允许跨域
@Api(description = "用户登录模块")
public class UserLoginController {

    @PostMapping("login")
    @ApiOperation(value = "用户登录")
    public R login() {
        return R.ok().data("token", "admin");
    }

    //info
    @GetMapping("info")
    public R info() {
        return R.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }


}
