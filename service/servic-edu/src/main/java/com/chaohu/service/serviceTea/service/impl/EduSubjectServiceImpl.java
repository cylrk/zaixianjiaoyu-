package com.chaohu.service.serviceTea.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chaohu.myerroe.MyError;
import com.chaohu.service.serviceTea.entity.EduSubject;
import com.chaohu.service.serviceTea.entity.excel.Excel;
import com.chaohu.service.serviceTea.entity.subject.OneSubject;
import com.chaohu.service.serviceTea.entity.subject.TwoSubject;
import com.chaohu.service.serviceTea.listener.SubjectExcelListener;
import com.chaohu.service.serviceTea.mapper.EduSubjectMapper;
import com.chaohu.service.serviceTea.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaohu.utlis.ResultCode;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author lrk
 * @since 2020-11-25
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void addSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            InputStream is = file.getInputStream();
            EasyExcel.read(is, Excel.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
            //throw new MyError(ResultCode.ERROR,"自定义异常");
        }
    }

    @Override
    public ArrayList<OneSubject> getAllOneTwoSubject() {
        //1 查询所有一级分类  parentid = 0
        QueryWrapper<EduSubject> queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("parent_id", "0");
        List<EduSubject> eduSubjects1 = baseMapper.selectList(queryWrapper1);
        //2 查询所有二级分类  parentid != 0
        QueryWrapper<EduSubject> queryWrapper2 = new QueryWrapper();
        queryWrapper1.ne("parent_id", "0");
        List<EduSubject> eduSubjects2 = baseMapper.selectList(queryWrapper2);
        //创建list集合，用于存储最终封装数据
        ArrayList<OneSubject> oneSubjects = new ArrayList<>();
        //3 封装一级分类
        //查询出来所有的一级分类list集合遍历，得到每个一级分类对象，获取每个一级分类对象值，

        for (EduSubject eduSubject : eduSubjects1) {
            OneSubject oneSubject = new OneSubject();
            //oneSubject.setId(eduSubject.getId());
            //oneSubject.setTitle(eduSubject.getTitle());
            BeanUtils.copyProperties(eduSubject, oneSubject);
            //oneSubjects.add(oneSubject);
            oneSubjects.add(oneSubject);

            //在一级分类循环遍历查询所有的二级分类
            //创建list集合封装每个一级分类的二级分类
            ArrayList<TwoSubject> twoSubjects = new ArrayList<>();
            for (EduSubject eduSubject2 : eduSubjects2) {
            /*TwoSubject twoSubject=new TwoSubject();
            BeanUtils.copyProperties(eduSubject,twoSubject);*/
                if (eduSubject2.getParentId().equals(eduSubject.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(eduSubject2, twoSubject);
                    twoSubjects.add(twoSubject);
                }

            }
            oneSubject.setChildren(twoSubjects);
        }
        return oneSubjects;
    }
}
