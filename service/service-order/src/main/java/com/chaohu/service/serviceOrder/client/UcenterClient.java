package com.chaohu.service.serviceOrder.client;


import com.chaohu.ordervo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-educenter")
public interface UcenterClient {


    //根据用户id获取用户信息
    @PostMapping("/serviceEduCenter/ucenter-member/getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id);
}
