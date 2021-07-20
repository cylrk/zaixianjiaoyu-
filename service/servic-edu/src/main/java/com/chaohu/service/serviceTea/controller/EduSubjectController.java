package com.chaohu.service.serviceTea.controller;


import com.alibaba.excel.EasyExcel;
import com.chaohu.service.serviceTea.entity.EduSubject;
import com.chaohu.service.serviceTea.entity.subject.OneSubject;
import com.chaohu.service.serviceTea.service.EduSubjectService;
import com.chaohu.utlis.R;
import io.swagger.annotations.Api;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author lrk
 * @since 2020-11-25
 */
@RestController
@RequestMapping("/serviceTea/edu-subject")
@Api(description = "分类管理模块")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        eduSubjectService.addSubject(file, eduSubjectService);
        return R.ok();
    }

    @PostMapping("getAllOneTwoSubject")
    public R getAllOneTwoSubject() {
        ArrayList<OneSubject> allOneTwoSubject = eduSubjectService.getAllOneTwoSubject();
        return R.ok().data("AllOneTwoSubject", allOneTwoSubject);
    }


}

