package com.chaohu.errrorhandle;

import com.chaohu.myerroe.MyError;
import com.chaohu.utlis.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class MyErrorHandle {

    //指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody //为了返回数据
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("请先登录");
    }

    //指定出现什么异常执行这个方法
    @ExceptionHandler(MyError.class)
    @ResponseBody //为了返回数据
    public R error(MyError e) {
        log.info(e.getMgs());
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMgs());
    }

}
