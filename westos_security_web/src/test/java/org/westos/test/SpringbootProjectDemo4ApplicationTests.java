package org.westos.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.westos.bean.SysUser;
import org.westos.service.SysUserService;

import javax.sql.DataSource;
import java.util.List;

@SpringBootTest
class SpringbootProjectDemo4ApplicationTests {
    @Autowired
    DataSource dataSource;
    @Autowired
    SysUserService sysUserService;

    @Test
    void contextLoads() {
        System.out.println("测试");
        // System.out.println(dataSource);
        List<SysUser> list = sysUserService.list();
        for (SysUser sysUser : list) {
            System.out.println(sysUser);
        }
    }
}
