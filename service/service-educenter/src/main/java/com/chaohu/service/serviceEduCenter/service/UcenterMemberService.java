package com.chaohu.service.serviceEduCenter.service;

import com.chaohu.service.serviceEduCenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chaohu.service.serviceEduCenter.entity.vo.LoginVo;
import com.chaohu.service.serviceEduCenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author lrk
 * @since 2020-12-19
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(LoginVo loginVo);

    void regiser(RegisterVo registerVo);

    UcenterMember getOpenIdMember(String openid);

    Integer countRegisterDay(String day);
}
