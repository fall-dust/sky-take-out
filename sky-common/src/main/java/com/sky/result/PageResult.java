package com.sky.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 封装分页查询结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "分页查询结果")
public class PageResult<O> implements Serializable {

    @Schema(description = "总记录数")
    private long total; // 总记录数

    @Schema(description = "当前页数据集合")
    private List<O> records; // 当前页数据集合

}
