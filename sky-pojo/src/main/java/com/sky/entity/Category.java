package com.sky.entity;

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
@Schema(description = "菜品类别的数据模型")
public class Category implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "类型。1：菜品分类；2：套餐分类")
    private Integer type;// 类型。1：菜品分类；2：套餐分类

    @Schema(description = "分类名称")
    private String name;// 分类名称

    @Schema(description = "顺序")
    private Integer sort;// 顺序

    @Schema(description = "分类状态。0：标识禁用；1：表示启用")
    private Integer status;// 分类状态。0：标识禁用；1：表示启用

    @Schema(description = "创建时间")
    private LocalDateTime createTime;// 创建时间

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;// 更新时间

    @Schema(description = "创建人")
    private Long createUser;// 创建人

    @Schema(description = "修改人")
    private Long updateUser;// 修改人
}
