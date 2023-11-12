package com.sky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 高翔宇
 * @since 2023/11/12 周日 9:42
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    DishMapper dishMapper;

    /**
     * 分页查询
     *
     * @param categoryPageQueryDTO 菜品分类分页查询传递的数据模型
     * @return {@link PageResult}
     */
    @Override
    public PageResult<Category> page(CategoryPageQueryDTO categoryPageQueryDTO) {
        int current = categoryPageQueryDTO.getPage();// 查询的页码
        int pageSize = categoryPageQueryDTO.getPageSize();// 单页展示记录数
        String name = categoryPageQueryDTO.getName();// 分类名称
        Integer type = categoryPageQueryDTO.getType();// 类型。1：菜品分类；2：套餐分类

        Page<Category> page = new Page<>(current, pageSize);
        page = categoryMapper.selectPage(page,
                new LambdaQueryWrapper<Category>()
                        .like(name != null, Category::getName, name)
                        .eq(type != null, Category::getType, type));

        return new PageResult<>(page.getTotal(), page.getRecords());
    }

    /**
     * 新增菜品分类
     *
     * @param categoryDTO 新增菜品传递的数据模型
     */
    @Override
    public void save(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);// 属性拷贝
        Long currentUserId = BaseContext.getCurrentId();// 当前登录的用户ID
        category.setStatus(1);
        category.setCreateUser(currentUserId);
        category.setUpdateUser(currentUserId);
        categoryMapper.insert(category);
    }

    /**
     * 更新菜品类别
     *
     * @param categoryDTO 更新菜品传递的数据模型
     */
    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);// 属性拷贝
        Long currentUserId = BaseContext.getCurrentId();// 当前登录的用户ID
        category.setUpdateUser(currentUserId);
        categoryMapper.updateById(category);
    }

    /**
     * 禁用/启用菜品类别
     *
     * @param status 菜品类别状态。0：禁用；1：启用
     * @param id     菜品类别ID
     */
    @Override
    public void changeStatus(Integer status, Long id) {
        categoryMapper.updateById(Category
                .builder()
                .id(id)
                .status(status)
                .build());
    }

    /**
     * 根据ID删除菜品类别
     *
     * @param id ID
     */
    @Override
    public void removeById(Long id) {
        if (dishMapper.selectCount(new LambdaQueryWrapper<Dish>().eq(Dish::getCategoryId, id)) > 0) {
            throw new DeletionNotAllowedException("存在菜品关联，无法删除！");
        }
        categoryMapper.deleteById(id);
    }
}
