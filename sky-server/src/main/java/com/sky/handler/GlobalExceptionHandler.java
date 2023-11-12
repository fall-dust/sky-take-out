package com.sky.handler;

import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BaseException.class)
    public Result<String> exceptionHandler(BaseException ex) {
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * 处理SQL异常
     */
    @ExceptionHandler(value = {SQLIntegrityConstraintViolationException.class})
    public Result<String> SQLIntegrityConstraintViolationExceptionHandler(SQLIntegrityConstraintViolationException e) {
        /*
         * 错误示例：
         * java.sql.SQLIntegrityConstraintViolationException: Duplicate entry 'admin' for key 'employee.idx_username'
         * ...
         **/
        String message = e.getMessage();
        if (message.contains("Duplicate entry")) {
            String entryName = message.split(" ")[2];
            return Result.error("重复的条目：%s".formatted(entryName));
        } else {
            return Result.error("未知错误！");
        }
    }
}
