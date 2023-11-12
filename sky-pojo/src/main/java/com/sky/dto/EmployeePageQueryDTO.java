package com.sky.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "查询员工接收的数据格式")
public class EmployeePageQueryDTO implements Serializable {

    @Schema(description = "员工姓名")
    private String name;// 员工姓名

    @Schema(description = "页码")
    private int page;// 页码

    @Schema(description = "每页显示记录数")
    private int pageSize;// 每页显示记录数

}
