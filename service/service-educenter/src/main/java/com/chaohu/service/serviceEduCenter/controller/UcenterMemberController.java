package com.chaohu.service.serviceEduCenter.controller;


import com.chaohu.ordervo.UcenterMemberOrder;
import com.chaohu.service.serviceEduCenter.entity.UcenterMember;
import com.chaohu.service.serviceEduCenter.entity.vo.LoginVo;
import com.chaohu.service.serviceEduCenter.entity.vo.RegisterVo;
import com.chaohu.service.serviceEduCenter.service.UcenterMemberService;
import com.chaohu.utlis.JwtUtils;
import com.chaohu.utlis.R;
import com.google.gson.Gson;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author lrk
 * @since 2020-12-19
 */
@RestController
@RequestMapping("/serviceEduCenter/ucenter-member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;


    //登录
    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo){
        String token=ucenterMemberService.login(loginVo);
        return R.ok().data("token",token);

    }
    //注册
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        ucenterMemberService.regiser(registerVo);
        return R.ok();
    }
    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        //调用jwt工具类的方法。根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据用户id获取用户信息
        UcenterMember member = ucenterMemberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }


    //根据用户id获取用户信息
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id) {
        UcenterMember member = ucenterMemberService.getById(id);
        //把member对象里面值复制给UcenterMemberOrder对象
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    //查询某一天注册人数
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day) {
        Integer count = ucenterMemberService.countRegisterDay(day);
        return R.ok().data("countRegister",count);
    }
}

