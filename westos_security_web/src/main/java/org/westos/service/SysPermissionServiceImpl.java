package org.westos.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.westos.bean.SysPermission;
import org.westos.mapper.SysPermissionMapper;

import java.util.List;

/**
 * @Author: ShenMouMou
 * @Company: 西部开源教育科技有限公司
 * @Description: 简简单单，只为教育。
 * @Date: 2021/1/7 9:46
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    @Override
    public List<SysPermission> findByUserId(Long userId) {
        if (userId == null) {
            return null;
        }
        List<SysPermission> sysPermissionList = baseMapper.selectPermissionByUserId(userId);
        //用户无任何权限时，list会有一个 `null` 空的SysPermission 对象，把那个null移除
        sysPermissionList.remove(null);
        return sysPermissionList;
    }

    @Override
    public boolean deleteById(Long id) {
        //根据id删除该菜单
        int i = baseMapper.deleteById(id);
        //删除该菜单下的所有子菜单
        LambdaQueryWrapper<SysPermission> sysPermissionQueryWrapper = new LambdaQueryWrapper<>();
        sysPermissionQueryWrapper.eq(SysPermission::getParentId, id);
        baseMapper.delete(sysPermissionQueryWrapper);
        return true;
    }
}
