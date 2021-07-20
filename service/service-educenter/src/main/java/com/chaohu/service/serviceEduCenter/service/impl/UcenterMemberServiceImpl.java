package com.chaohu.service.serviceEduCenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chaohu.myerroe.MyError;
import com.chaohu.service.serviceEduCenter.entity.UcenterMember;
import com.chaohu.service.serviceEduCenter.entity.vo.LoginVo;
import com.chaohu.service.serviceEduCenter.entity.vo.RegisterVo;
import com.chaohu.service.serviceEduCenter.mapper.UcenterMemberMapper;
import com.chaohu.service.serviceEduCenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaohu.service.serviceEduCenter.utils.MD5;

import com.chaohu.utlis.JwtUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author lrk
 * @since 2020-12-19
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String login(LoginVo loginVo) {
        String password = loginVo.getPassword();
        String phone = loginVo.getMobile();

        //对手机号与密码进行非空判断
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(phone)){
            throw new MyError(20001,"账号密码为空");
        }

        QueryWrapper<UcenterMember> memberQueryWrapper =new QueryWrapper<>();
        memberQueryWrapper.eq("mobile",phone);
        UcenterMember ucenterMember = baseMapper.selectOne(memberQueryWrapper);
        if (ucenterMember!=null){
            //判断密码
            //因为存储到数据库密码肯定加密的
            //把输入的密码进行加密，再和数据库密码进行比较
            //加密方式 MD5
            if(!MD5.encrypt(password).equals(ucenterMember.getPassword())) {
                throw new MyError(20001,"登录失败");
            }
            if (ucenterMember.getIsDisabled()){
                throw new MyError(20001,"登录失败");
            }
            return JwtUtils.getJwtToken(ucenterMember.getId(),ucenterMember.getNickname());
        }else {
            throw new MyError(20001,"账号未注册");
        }

    }

    @Override
    public void regiser(RegisterVo registerVo) {
        //获取注册的数据
        String code = registerVo.getCode(); //验证码
        String mobile = registerVo.getMobile(); //手机号
        String nickname = registerVo.getNickname(); //昵称
        String password = registerVo.getPassword(); //密码

        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile) ||
            StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)){
            throw new MyError(20001,"注册失败");
        }
        QueryWrapper<UcenterMember> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);
        UcenterMember ucenterMember = baseMapper.selectOne(queryWrapper);
        if (ucenterMember!=null){
            throw new MyError(20001,"注册失败");
        }
       /* String s = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(s)){
            throw new MyError(20001,"注册失败");
        }*/
        //数据添加数据库中
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));//密码需要加密的
        member.setIsDisabled(false);//用户不禁用
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);

    }

    //根据openid判断
    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    //查询某一天注册人数
    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
