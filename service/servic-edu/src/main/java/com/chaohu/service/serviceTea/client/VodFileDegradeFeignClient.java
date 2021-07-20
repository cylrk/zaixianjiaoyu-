package com.chaohu.service.serviceTea.client;


import com.chaohu.utlis.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient {
   //出错之后会执行
    @Override
    public R deleteAlyVideo(String id) {
        return R.error().message("删除单个视频出错");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频出错了");
    }
}
