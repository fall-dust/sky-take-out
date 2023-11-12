package com.sky.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("employee")
@Schema(description = "员工数据模型")
public class Employee implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "性别")
    private String sex;

    @Schema(description = "身份证号")
    private String idNumber;

    @Schema(description = "账号状态。0：禁用；1：启用")
    private Integer status;

    /*
     * 日期格式化的方式之一：@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     * 但我们更推荐拓展MVC消息转换器。
     * */
    @Schema(description = "账号创建时间")
    private LocalDateTime createTime;

    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "账号更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "创建账号用户的ID")
    private Long createUser;

    @Schema(description = "更新账号用户的ID")
    private Long updateUser;

}
