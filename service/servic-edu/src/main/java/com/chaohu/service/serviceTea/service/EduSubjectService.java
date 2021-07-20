package com.chaohu.service.serviceTea.service;

import com.chaohu.service.serviceTea.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chaohu.service.serviceTea.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author lrk
 * @since 2020-11-25
 */
public interface EduSubjectService extends IService<EduSubject> {

    void addSubject(MultipartFile file, EduSubjectService eduSubjectService);

    ArrayList<OneSubject> getAllOneTwoSubject();
}
