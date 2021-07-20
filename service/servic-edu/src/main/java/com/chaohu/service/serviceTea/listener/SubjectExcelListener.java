package com.chaohu.service.serviceTea.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chaohu.service.serviceTea.entity.EduSubject;
import com.chaohu.service.serviceTea.entity.excel.Excel;
import com.chaohu.service.serviceTea.service.EduSubjectService;

public class SubjectExcelListener extends AnalysisEventListener<Excel> {
    public EduSubjectService eduSubjectService;

    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @Override//读取方法，按照一行一行读取
    public void invoke(Excel excel, AnalysisContext analysisContext) {
        EduSubject eduSubject = existOneSubject(eduSubjectService, excel.getOneSubjectName());
        if (eduSubject == null) {
            eduSubject = new EduSubject();
            eduSubject.setTitle(excel.getOneSubjectName());
            eduSubject.setParentId("0");
            eduSubjectService.save(eduSubject);
        }
        String parentId = eduSubject.getId();
        EduSubject eduSubject1 = existTwoSubject(eduSubjectService, excel.getTwoSubjectName(), parentId);
        if (eduSubject1 == null) {
            eduSubject1 = new EduSubject();
            eduSubject1.setTitle(excel.getTwoSubjectName());
            eduSubject1.setParentId(parentId);
            eduSubjectService.save(eduSubject1);
        }
    }


    @Override//读取之后的方法
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }


    //判断一级分类不能重复添加
    private EduSubject  existOneSubject (EduSubjectService subjectService, String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        EduSubject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }

    //判断二级分类不能重复添加
    private EduSubject existTwoSubject(EduSubjectService subjectService, String name, String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        EduSubject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }
}
