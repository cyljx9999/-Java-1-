package com.cy.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cy.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author cy
 * @program: WuYeManagementProgram
 * @description: 员工管理数据访问层
 * @date 2021-12-20 18:27:30
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
