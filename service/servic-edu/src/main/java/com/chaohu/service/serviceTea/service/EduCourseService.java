package com.chaohu.service.serviceTea.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaohu.service.serviceTea.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chaohu.service.serviceTea.entity.frontvo.CourseFrontVo;
import com.chaohu.service.serviceTea.entity.frontvo.CourseWebVo;
import com.chaohu.service.serviceTea.entity.vo.CourseInfoVo;
import com.chaohu.service.serviceTea.entity.vo.CoursePublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lrk
 * @since 2020-11-27
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourseInfo(String id);

    void deleteAllCourse(String id);

    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
