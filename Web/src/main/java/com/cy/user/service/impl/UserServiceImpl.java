package com.cy.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cy.user.entity.User;
import com.cy.user.entity.UserParam;
import com.cy.user.mapper.UserMapper;
import com.cy.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author cy
 * @program: WuYeManagementProgram
 * @description:
 * @date 2021-12-20 18:35:28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public IPage<User> list(UserParam parm) {
        //构建分页对象
        IPage<User> page = new Page<>();
        page.setSize(parm.getPageSize());
        page.setCurrent(parm.getCurrentPage());
        //构造前端查询条件
        QueryWrapper<User> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getPhone())){
            query.lambda().like(User::getPhone,parm.getPhone());
        }
        if(StringUtils.isNotEmpty(parm.getUserName())){
            query.lambda().like(User::getUserName,parm.getUserName());
        }
        return this.page(page,query);
    }
}
