package com.chaohu.service.serviceTea.service;

import com.chaohu.service.serviceTea.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chaohu.service.serviceTea.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lrk
 * @since 2020-11-27
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> selectChapterAndVideo(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);

}
