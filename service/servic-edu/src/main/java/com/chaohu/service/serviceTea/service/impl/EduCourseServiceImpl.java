package com.chaohu.service.serviceTea.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaohu.myerroe.MyError;
import com.chaohu.service.serviceTea.entity.EduCourse;
import com.chaohu.service.serviceTea.entity.EduCourseDescription;
import com.chaohu.service.serviceTea.entity.EduVideo;
import com.chaohu.service.serviceTea.entity.frontvo.CourseFrontVo;
import com.chaohu.service.serviceTea.entity.frontvo.CourseWebVo;
import com.chaohu.service.serviceTea.entity.vo.CourseInfoVo;
import com.chaohu.service.serviceTea.entity.vo.CoursePublishVo;
import com.chaohu.service.serviceTea.mapper.EduCourseMapper;
import com.chaohu.service.serviceTea.service.EduChapterService;
import com.chaohu.service.serviceTea.service.EduCourseDescriptionService;
import com.chaohu.service.serviceTea.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaohu.service.serviceTea.service.EduVideoService;
import com.chaohu.utlis.ResultCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lrk
 * @since 2020-11-27
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduChapterService eduChapterService;
    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);

        if (insert == 0) {
            //添加失败
            throw new MyError(20001, "添加课程信息失败");
        }

        //获取添加之后课程id
        String cid = eduCourse.getId();

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, eduCourseDescription);
        eduCourseDescription.setId(cid);
        eduCourseDescriptionService.save(eduCourseDescription);

        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        EduCourse eduCourse = baseMapper.selectById(courseId);
        //QueryWrapper<EduCourseDescription> queryWrapper=new QueryWrapper<>();
        EduCourseDescription eduCourseDescriptionServiceOne = eduCourseDescriptionService.getById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        courseInfoVo.setDescription(eduCourseDescriptionServiceOne.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        baseMapper.updateById(eduCourse);
        EduCourseDescription description = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, description);
        eduCourseDescriptionService.updateById(description);
    }

    //根据课程id查询课程确认信息
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        //调用mapper
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    @Override
    public void deleteAllCourse(String courseId) {
        //1 根据课程id删除小节
        eduVideoService.removeVideoByCourseId(courseId);

        //2 根据课程id删除章节
        eduChapterService.removeChapterByCourseId(courseId);

        //3 根据课程id删除描述
        eduCourseDescriptionService.removeById(courseId);

        //4 根据课程id删除课程本身
        int result = baseMapper.deleteById(courseId);
        if(result == 0) { //失败返回
            throw new MyError(ResultCode.ERROR,"删除课程信息失败");
        }

    }

    //1 条件查询带分页查询课程
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo) {
        //2 根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断条件值是否为空，不为空拼接
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) { //一级分类
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())) { //二级分类
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) { //关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) { //最新
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {//价格
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageParam,wrapper);

        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();//下一页
        boolean hasPrevious = pageParam.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;
    }

    //根据课程id，编写sql语句查询课程信息
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
