package com.chaohu.service.serviceTea.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chaohu.myerroe.MyError;
import com.chaohu.service.serviceTea.entity.EduChapter;
import com.chaohu.service.serviceTea.entity.EduSubject;
import com.chaohu.service.serviceTea.entity.EduVideo;
import com.chaohu.service.serviceTea.entity.chapter.ChapterVo;
import com.chaohu.service.serviceTea.entity.chapter.VideoVo;
import com.chaohu.service.serviceTea.mapper.EduChapterMapper;
import com.chaohu.service.serviceTea.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaohu.service.serviceTea.service.EduVideoService;
import com.chaohu.utlis.ResultCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lrk
 * @since 2020-11-27
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> selectChapterAndVideo(String courseId) {
        QueryWrapper<EduChapter> eduChapterQueryWrapper = new QueryWrapper<>();
        eduChapterQueryWrapper.eq("course_id", courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(eduChapterQueryWrapper);

        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("course_id", courseId);
        List<EduVideo> eduVideos = eduVideoService.list(eduVideoQueryWrapper);

        List<ChapterVo> chapterVos = new ArrayList<>();
        for (EduChapter eduChapter : eduChapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            chapterVos.add(chapterVo);
            List<VideoVo> videoVos = new ArrayList<>();
            for (EduVideo eduVideo : eduVideos) {
                VideoVo videoVo = new VideoVo();
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoVos.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVos);
        }
        return chapterVos;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", chapterId);
        List<EduVideo> list = eduVideoService.list(queryWrapper);
        if (list == null && list.size() == 0) {
            int i = baseMapper.deleteById(chapterId);
            return i > 0;
        } else {
            throw new MyError(ResultCode.ERROR, "有小节内容，无法删除");
            //return false;
        }
    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        baseMapper.delete(queryWrapper);
    }
}
