package org.westos.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.westos.bean.SysRole;

/**
 * @Author: ShenMouMou
 * @Company: 西部开源教育科技有限公司
 * @Description: 简简单单，只为教育。
 * @Date: 2021/1/7 9:20
 */
public interface SysRoleService extends IService<SysRole> {
    //分页查询所有角色
    IPage<SysRole> selectPage(Page<SysRole> page, SysRole sysRole);

    //根据角色id查询该角色，以及该角色所对应的权限信息
    SysRole getSysRoleAndPermissionsById(Long id);

    //根据id删除该角色，以及该角色对应的权限信息（也就是删除中间表的对应关系）
    boolean deleteRoleById(Long id);
}
