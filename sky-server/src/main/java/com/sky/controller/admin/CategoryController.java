package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author 高翔宇
 * @since 2023/11/12 周日 9:26
 */
@Slf4j
@RestController
@Tag(name = "分类管理", description = "CategoryController")
@RequestMapping("admin/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    /**
     * 分页查询
     *
     * @param categoryPageQueryDTO 菜品分类分页查询传递的数据模型
     * @return {@link Result}
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询", description = "分页查询", security = {@SecurityRequirement(name = "token")})
    public Result<PageResult<Category>> page(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分页查询菜品类别：page：{}，pageSize：{}", categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        return Result.success(categoryService.page(categoryPageQueryDTO));
    }

    /**
     * 新增菜品分类
     *
     * @param categoryDTO 新增菜品传递的数据模型
     * @return {@link Result}
     */
    @Operation(summary = "新增类别", description = "新增类别", security = {@SecurityRequirement(name = "token")})
    @PostMapping
    public Result<Object> save(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增菜品类别...");
        categoryService.save(categoryDTO);
        return Result.success();
    }

    /**
     * 更新菜品
     *
     * @param categoryDTO 更新菜品传递的数据模型
     * @return {@link Result}
     */
    @Operation(summary = "更新菜品类别", description = "更新菜品类别", security = {@SecurityRequirement(name = "token")})
    @PutMapping
    public Result<Object> update(@RequestBody CategoryDTO categoryDTO) {
        log.info("更新ID为{}的菜品", categoryDTO.getId());
        categoryService.update(categoryDTO);
        return Result.success();
    }

    /**
     * 禁用/启用菜品类别
     *
     * @param status 菜品类别状态。0：禁用；1：启用
     * @param id     菜品类别ID
     * @return {@link Result}
     */
    @PostMapping("/status/{status}")
    @Operation(summary = "更新菜品类别状态", description = "更新菜品类别状态", security = {@SecurityRequirement(name = "token")})
    public Result<Object> changeStatus(@PathVariable Integer status, Long id) {
        log.info((status == 1 ? "启用" : "禁用") + "ID为{}的菜品类别", id);
        categoryService.changeStatus(status, id);
        return Result.success();
    }

    /**
     * 根据ID删除菜品类别
     *
     * @param id ID
     * @return {@link Result}
     */
    @Operation(summary = "删除菜品类别", description = "删除菜品类别", security = {@SecurityRequirement(name = "token")})
    @DeleteMapping
    public Result<Objects> delete(@RequestParam Long id) {
        log.info("删除ID为{}的菜品类别...", id);
        categoryService.removeById(id);
        return Result.success();
    }
}
