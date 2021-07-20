package com.chaohu.service.serviceTea.controller;


import com.chaohu.service.serviceTea.entity.EduChapter;
import com.chaohu.service.serviceTea.entity.chapter.ChapterVo;
import com.chaohu.service.serviceTea.service.EduChapterService;
import com.chaohu.utlis.R;
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
@RequestMapping("/serviceTea/edu-chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    @GetMapping("selectChapterAndVideo/{courseId}")
    public R selectChapterAndVideo(@PathVariable("courseId") String courseId) {
        List<ChapterVo> list = eduChapterService.selectChapterAndVideo(courseId);
        return R.ok().data("allChapterAndVideo", list);
    }

    //添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    //修改章节
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    //根据章节id查询
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter", eduChapter);
    }


    //删除的方法
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        boolean flag = eduChapterService.deleteChapter(chapterId);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }

    }
}

