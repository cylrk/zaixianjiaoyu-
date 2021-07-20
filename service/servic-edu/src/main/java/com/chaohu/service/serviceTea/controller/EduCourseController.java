package com.chaohu.service.serviceTea.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chaohu.service.serviceTea.entity.EduCourse;
import com.chaohu.service.serviceTea.entity.EduTeacher;
import com.chaohu.service.serviceTea.entity.vo.CourseInfoVo;
import com.chaohu.service.serviceTea.entity.vo.CoursePublishVo;
import com.chaohu.service.serviceTea.service.EduCourseService;
import com.chaohu.utlis.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lrk
 * @since 2020-11-27
 */
@RestController
@CrossOrigin
@RequestMapping("/serviceTea/edu-course")
@Api(description = "课程管理模块")
public class EduCourseController {


    @Autowired
    private EduCourseService courseService;

    //添加课程基本信息的方法
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        //返回添加之后课程id，为了后面添加大纲使用
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }

    //删除所有课程信息
    @GetMapping("deleteById/{id}")
    public R deleteById(@PathVariable("id") String id) {
        courseService.deleteAllCourse(id);
        return R.ok();
    }

    //根据课程id查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    //查询所有课程信息
    @GetMapping("getAllCourse")
    public R getAllCourse() {
        List<EduCourse> list = courseService.list(new QueryWrapper<>());
        return R.ok().data("allCourse", list);
    }

    //修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse", coursePublishVo);
    }

    //发布课程信息
    @PostMapping("publihCourse/{id}")
    public R publihCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return R.ok();
    }


}

