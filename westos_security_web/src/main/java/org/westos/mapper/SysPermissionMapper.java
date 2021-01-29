package org.westos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.westos.bean.SysPermission;

import java.util.List;

/**
 * @Author: ShenMouMou
 * @Company: 西部开源教育科技有限公司
 * @Description: 简简单单，只为教育。
 * @Date: 2021/1/7 9:45
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {
    /**
     *     * 根据用户ID获取拥有资源权限
     *     * @param userId 用户id
     *     * @return
     *    
     */
    List<SysPermission> selectPermissionByUserId(@Param("userId") Long userId);


    /**
     *     * 根据角色ID获取拥有资源权限
     *     * @param roleId 角色ID
     *     * @return 资源权限集合
     *    
     */
    List<SysPermission> findByRoleId(@Param("roleId") Long roleId);
}
