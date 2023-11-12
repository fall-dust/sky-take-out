package com.sky.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "新增菜品传递的数据模型")
public class CategoryDTO implements Serializable {

    @Schema(description = "主键")
    private Long id;// 主键

    @Schema(description = "类型。1：菜品分类；2：套餐分类")
    private Integer type;// 类型。1：菜品分类；2：套餐分类

    @Schema(description = "分类名称")
    private String name;// 分类名称

    @Schema(description = "排序")
    private Integer sort;// 排序

}
