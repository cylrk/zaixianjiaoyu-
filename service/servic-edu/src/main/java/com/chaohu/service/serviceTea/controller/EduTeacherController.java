package com.chaohu.service.serviceTea.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaohu.myerroe.MyError;
import com.chaohu.service.serviceTea.entity.EduTeacher;
import com.chaohu.service.serviceTea.entity.vo.TeacherQuery;
import com.chaohu.service.serviceTea.service.EduTeacherService;
import com.chaohu.utlis.R;
import com.chaohu.utlis.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author lrk
 * @since 2020-11-03
 */
@RestController
@RequestMapping("/serviceTea/edu-teacher")
@Api(description = "讲师管理模块")
@CrossOrigin//允许跨域
public class EduTeacherController {


    @Autowired
    private EduTeacherService eduTeacherService;

    @GetMapping("findALL")
    @ApiOperation(value = "查询所有用户")
    public R findAll() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("all", list);
    }

    @DeleteMapping("{id}")
    public R delete(@PathVariable("id") String id) {
        boolean b = eduTeacherService.removeById(id);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }

    }

    //分页查询
    @PostMapping("pageTeacher/{current}/{row}")
    public R page(@PathVariable("current") Long current, @PathVariable("row") Long row) {
        Page<EduTeacher> pageTeacher = new Page<>(current, row);
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            throw new MyError(ResultCode.ERROR, "自定义异常");
        }
        eduTeacherService.page(pageTeacher, null);
        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords(); //数据list集合
        return R.ok().data("total", total).data("records", records);
    }

    //分页带条件查询
    @PostMapping("pageTeacherCondition/{current}/{row}")
    public R pageCondition(@PathVariable("current") Long current, @PathVariable("row") Long row,
                           @RequestBody(required = false) TeacherQuery teacherQuery) {
        //System.out.println(teacherQuery);
        Page<EduTeacher> pageTeacher = new Page<>(current, row);
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        if (teacherQuery != null && !"".equals(teacherQuery)) {
            String name = teacherQuery.getName();
            String begin = teacherQuery.getBegin();
            Integer level = teacherQuery.getLevel();
            String end = teacherQuery.getEnd();
            if (!StringUtils.isEmpty(name)) {
                queryWrapper.like("name", name);
            }
            if (!StringUtils.isEmpty(begin)) {
                queryWrapper.ge("gmt_create", begin);
            }
            if (!StringUtils.isEmpty(level)) {
                queryWrapper.eq("level", level);
            }
            if (!StringUtils.isEmpty(end)) {
                queryWrapper.le("gmt_create", end);
            }
        }
        //根据时间进行降序排序
        queryWrapper.orderByDesc("gmt_create");
        eduTeacherService.page(pageTeacher, queryWrapper);
        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords(); //数据list集合
        return R.ok().data("total", total).data("records", records);
    }

    //添加接口方法
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = eduTeacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }


    //根据id单个查询
    @PostMapping("findById/{id}")
    public R addTeacher(@PathVariable("id") Long id) {
        EduTeacher byId = eduTeacherService.getById(id);
        return R.ok().data("byid", byId);
    }


    //修改讲师id
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean b = eduTeacherService.updateById(eduTeacher);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }

    }


}

