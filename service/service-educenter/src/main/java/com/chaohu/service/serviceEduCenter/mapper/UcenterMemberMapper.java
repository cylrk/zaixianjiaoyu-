package com.chaohu.service.serviceEduCenter.mapper;

import com.chaohu.service.serviceEduCenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author lrk
 * @since 2020-12-19
 */

public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer countRegisterDay(String day);
}
