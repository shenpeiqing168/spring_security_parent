package org.westos.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.westos.bean.SysPermission;
import org.westos.bean.SysRole;
import org.westos.mapper.SysPermissionMapper;
import org.westos.mapper.SysRoleMapper;

import java.util.Date;
import java.util.List;

/**
 * @Author: ShenMouMou
 * @Company: 西部开源教育科技有限公司
 * @Description: 简简单单，只为教育。
 * @Date: 2021/1/7 9:20
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    SysPermissionMapper sysPermissionMapper;

    @Override
    public IPage<SysRole> selectPage(Page<SysRole> page, SysRole sysRole) {
        //selectPage(page, sysRole) 参数2 是条件 ，比如按照姓名来查询，然后分页，如果没有条件可以传null
        IPage<SysRole> sysRoleIPage = baseMapper.selectPage(page, sysRole);
        return sysRoleIPage;
    }


    //根据角色id来查询，该角色，以及角色对应的权限信息
    @Override
    public SysRole getSysRoleAndPermissionsById(Long id) {
        if (id == null) {
            return new SysRole();
        }
        //1.通过角色id查询该角色
        SysRole role = baseMapper.selectById(id);
        //2.通过角色id查询该角色所对应的权限信息。
        List<SysPermission> sysPermissions = sysPermissionMapper.findByRoleId(id);
        //3.将查询到的权限信息，set到SysRole中
        role.setPerList(sysPermissions);

        return role;
    }

    @Transactional//注意使用事务
    @Override
    public boolean saveOrUpdate(SysRole entity) {
        //设置一下时间
        entity.setUpdateDate(new Date());
        System.out.println("修改：" + entity);
        //调用父类进行更新角色表
        boolean b = super.saveOrUpdate(entity);
        //如果角色更新成功，再更新角色和权限的中间表
        if (b) {
            //先删除该角色在中间表的对应关系
            baseMapper.deleteRolePermissionByRoleId(entity.getId());
            List<Long> perIds = entity.getPerIds();
            //如果用户有勾选权限
            if (perIds.size() > 0) {
                //删完，再加进去
                baseMapper.saveRolePermission(entity.getId(), perIds);
            }
        }
        return b;
    }

    @Transactional//注意使用事务
    @Override
    public boolean deleteRoleById(Long id) {
        //1.根据id删除该角色，
        baseMapper.deleteById(id);
        // 2以及该角色对应的权限信息（也就是删除中间表的对应关系）
        baseMapper.deleteRolePermissionByRoleId(id);
        return true;
    }
}
