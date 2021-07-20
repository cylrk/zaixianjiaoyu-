package com.chaohu.msmservice.controller;



import com.chaohu.msmservice.service.MsmService;
import com.chaohu.msmservice.utils.RandomUtil;
import com.chaohu.utlis.R;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;


@RestController
@CrossOrigin
@RequestMapping("service-msm/msm")
public class MsmController {

    @Autowired
    public MsmService msmService;
    @Autowired
    public RedisTemplate<String,String> redisTemplate;

    @RequestMapping("send/{phone}")
    public R sendCode(@PathVariable("phone") String phone){
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)){
            return R.ok();
        }
        String fourBitRandom = RandomUtil.getFourBitRandom();
        HashMap<String,String> map=new HashMap<>();
        map.put("code",code);
        //需要使用map，因为后面需要转成json格式
        boolean isSend = msmService.sendCode(phone,map);

        if(isSend) {
            //发送成功，把发送成功验证码放到redis里面
            //设置有效时间
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("短信发送失败");
        }

    }

}
