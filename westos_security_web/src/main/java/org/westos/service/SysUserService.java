package org.westos.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.westos.bean.SysUser;

/**
 * @Author: ShenMouMou
 * @Company: 西部开源教育科技有限公司
 * @Description: 简简单单，只为教育。
 * @Date: 2021/1/6 13:48
 */
public interface SysUserService extends IService<SysUser> {
    //提供根据用户名查找一个用户的方法
    SysUser findByUsername(String username);

    //提供根据手机号查找一个用户的方法
    SysUser findByPhoneNumber(String phoneNumber);

    //查询所有用户并分页
    IPage<SysUser> selectPage(Page<SysUser> page, SysUser sysUser);

    //点击修改用户时，所要查询的信息
    //1.根据用户id查出用户的旧信息
    //2.根据用户id查询用户原来所拥有的角色信息。
    //3.查询所有的角色信息
    SysUser findById(Long id);

    /**
     *     * 通过id假删除用户数据，把 is_enabled = 0
     *     * @param id
     *     * @return
     *    
     */
    boolean deleteById(Long id);
}
