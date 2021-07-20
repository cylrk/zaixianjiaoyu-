package com.chaohu.service.serviceTea.mapper;

import com.chaohu.service.serviceTea.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chaohu.service.serviceTea.entity.frontvo.CourseWebVo;
import com.chaohu.service.serviceTea.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author lrk
 * @since 2020-11-27
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getPublishCourseInfo(String id);

    CourseWebVo getBaseCourseInfo(String courseId);
}
