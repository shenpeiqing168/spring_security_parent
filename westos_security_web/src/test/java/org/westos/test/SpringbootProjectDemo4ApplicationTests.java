package org.westos.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.westos.bean.SysPermission;
import org.westos.bean.SysRole;
import org.westos.bean.SysUser;
import org.westos.service.SysPermissionService;
import org.westos.service.SysRoleService;
import org.westos.service.SysUserService;

import javax.sql.DataSource;
import java.util.List;

@SpringBootTest
class SpringbootProjectDemo4ApplicationTests {
    @Autowired
    DataSource dataSource;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysRoleService sysRoleService;
    @Autowired
    SysPermissionService sysPermissionService;

    @Test
    void contextLoads0() {
        System.out.println("测试");
        System.out.println(dataSource);
      
    }

    @Test
    void contextLoads() {
        System.out.println("测试");
        // System.out.println(dataSource);
        List<SysUser> list = sysUserService.list();
        for (SysUser sysUser : list) {
            System.out.println(sysUser);
        }
    }

    @Test
    void test() {
        System.out.println("测试");
        SysRole role = sysRoleService.getById(9);
        System.out.println(role);
    }

    @Test
    void test2() {
        System.out.println("测试");
        SysPermission sysPermission = sysPermissionService.getById(17);
        System.out.println(sysPermission);
    }
}
