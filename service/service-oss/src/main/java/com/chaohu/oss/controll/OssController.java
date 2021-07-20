package com.chaohu.oss.controll;

import com.chaohu.oss.service.OssService;
import com.chaohu.utlis.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("teaoss/fileoss")
public class OssController {
    @Autowired
    OssService ossService;

    @PostMapping("upload")
    public R fileUpload(MultipartFile file) {
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url", url);
    }

}
