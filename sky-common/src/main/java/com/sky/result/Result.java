package com.sky.result;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 后端统一返回结果
 *
 * @param <T>
 */
@Data
@Schema(description = "统一响应格式")
public class Result<T> implements Serializable {

    @Schema(name = "code", description = "编码：1成功，0和其它数字为失败")
    private Integer code; // 编码：1成功，0和其它数字为失败
    @Schema(name = "msg", description = "错误信息")
    private String msg; // 错误信息
    @Schema(description = "数据")
    private T data; // 数据

    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.code = 1;
        return result;
    }

    @Parameters({@Parameter(description = "返回的数据")})
    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = 1;
        return result;
    }

    @Parameters({@Parameter(description = "错误消息")})
    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.msg = msg;
        result.code = 0;
        return result;
    }
}
