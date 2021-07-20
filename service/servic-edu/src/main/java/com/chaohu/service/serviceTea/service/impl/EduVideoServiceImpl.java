package com.chaohu.service.serviceTea.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chaohu.service.serviceTea.client.VodClient;
import com.chaohu.service.serviceTea.entity.EduVideo;
import com.chaohu.service.serviceTea.mapper.EduVideoMapper;
import com.chaohu.service.serviceTea.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author lrk
 * @since 2020-11-27
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {


    @Autowired
    private VodClient vodClient;

    //1 根据课程id删除小节
    @Override
    public void removeVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("course_id",courseId);
        wrapper1.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(wrapper1);
        List<String> list=new ArrayList<>();
        for (EduVideo eduVideo : eduVideos) {
            if (!StringUtils.isEmpty(eduVideo.getVideoSourceId())){
                list.add(eduVideo.getVideoSourceId());
            }
        }

        //根据多个视频id删除多个视频
        if(list.size()>0) {
            vodClient.deleteBatch(list);
        }
        QueryWrapper<EduVideo> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("course_id",courseId);
        baseMapper.delete(wrapper2);
    }
}
