package org.westos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.westos.bean.SysUser;

import java.util.List;

/**
 * @Author: ShenMouMou
 * @Company: 西部开源教育科技有限公司
 * @Description: 简简单单，只为教育。
 * @Date: 2021/1/6 13:43
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    IPage<SysUser> selectPage(Page<SysUser> page, @Param("u") SysUser sysUser);

    /**
     * 删除角色-用户关联根据用户ID
     */
    boolean deleteUserRoleByUserId(@Param("id") Long userId);

    /**
     * 保存用户与角色关系数据
     *
     * @param userId
     * @param roleIds
     */
    boolean saveUserRole(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);
}
