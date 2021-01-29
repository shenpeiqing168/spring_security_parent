package org.westos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.westos.bean.SysPermission;

import java.util.List;

/**
 * @Author: ShenMouMou
 * @Company: 西部开源教育科技有限公司
 * @Description: 简简单单，只为教育。
 * @Date: 2021/1/7 9:46
 */
public interface SysPermissionService extends IService<SysPermission> {
    /**
     *     * @param userId 用户id
     *     * @return 用户所拥有的权限
     *    
     */

    List<SysPermission> findByUserId(Long userId);

    /**
     *     * 通过角色id删除角色表和角色权限资源关系表数据
     *     * @param id 角色id
     *     * @return
     *    
     */
    boolean deleteById(Long id);

}
