package com.chaohu.service.serviceTea.controller;


import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chaohu.myerroe.MyError;
import com.chaohu.service.serviceTea.client.VodClient;
import com.chaohu.service.serviceTea.entity.EduVideo;
import com.chaohu.service.serviceTea.service.EduVideoService;
import com.chaohu.utlis.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author lrk
 * @since 2020-11-27
 */
@RestController
@RequestMapping("/serviceTea/edu-video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private VodClient vodClient;

    @Autowired
    private EduVideoService videoService;

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }

    //删除小节
    // TODO 后面这个方法需要完善：删除小节时候，同时把里面视频删除
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        //根据小节id获取视频id，调用方法实现视频删除
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        //判断小节里面是否有视频id
        if(!StringUtils.isEmpty(videoSourceId)) {
            //根据视频id，远程调用实现视频删除
            R result = vodClient.deleteAlyVideo(videoSourceId);
            if(result.getCode() == 20001) {
                throw new MyError(20001,"删除视频失败，熔断器...");
            }
        }
        //删除小节
        videoService.removeById(id);
        return R.ok();
    }

    //修改小节 TODO
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        videoService.updateById(eduVideo);
        return R.ok();
    }

    //查询小节
    @GetMapping("byId/{id}")
    public R getVideo(@PathVariable String id) {
        /*QueryWrapper<EduVideo> eduVideoQueryWrapper =new QueryWrapper<>();
        eduVideoQueryWrapper.eq("chapter_id",id);
        List<EduVideo> list = videoService.list(eduVideoQueryWrapper);
        return R.ok().data("videoList",list);*/
        EduVideo one = videoService.getById(id);
        return R.ok().data("video", one);
    }

}

