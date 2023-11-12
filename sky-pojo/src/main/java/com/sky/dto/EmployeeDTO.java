package com.sky.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 添加员工接收的数据格式
 */
@Data
@Schema(description = "添加员工接收的数据格式")
public class EmployeeDTO implements Serializable {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "ID")
    private String name;

    @Schema(description = "ID")
    private String phone;

    @Schema(description = "ID")
    private String sex;

    @Schema(description = "ID")
    private String idNumber;

}
