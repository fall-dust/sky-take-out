package com.sky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;

/**
 * @author 高翔宇
 * @since 2023/11/12 周日 9:41
 */
public interface CategoryService extends IService<Category> {
    /**
     * 分页查询
     *
     * @param categoryPageQueryDTO 菜品分类分页查询传递的数据模型
     * @return {@link PageResult}
     */
    PageResult<Category> page(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 新增菜品分类
     *
     * @param categoryDTO 新增菜品传递的数据模型
     */
    void save(CategoryDTO categoryDTO);

    /**
     * 更新菜品类别
     *
     * @param categoryDTO 更新菜品传递的数据模型
     */
    void update(CategoryDTO categoryDTO);

    /**
     * 根据ID删除菜品类别
     *
     * @param id ID
     */
    void removeById(Long id);

    /**
     * 禁用/启用菜品类别
     *
     * @param status 菜品类别状态。0：禁用；1：启用
     * @param id     菜品类别ID
     */
    void changeStatus(Integer status, Long id);
}
