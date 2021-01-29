package org.westos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.westos.bean.SysPermission;
import org.westos.bean.SysUser;
import org.westos.service.SysPermissionService;
import org.westos.service.SysUserService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ShenMouMou
 * @Company: 西部开源教育科技有限公司
 * @Description: 简简单单，只为教育。
 * @Date: 2021/1/4 13:38
 */
@Component("mobileUserDetailsService")
public class MobileUserDetailsService implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysPermissionService sysPermissionService;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        // 1. 通过手机号查询用户信息
        // 2. 如果有此用户，则查询用户权限
        // 3. 封装用户信息
        //根据手机号，查询出用户信息，我们把用户名保存进去

        //根据用户名，查找用户的信息
        SysUser sysUser = sysUserService.findByPhoneNumber(phoneNumber);
        if (sysUser == null) {
            throw new UsernameNotFoundException("该手机号没有注册");
        }
        //根据用户id查询该用户所有的权限标识
        List<SysPermission> sysPermissionList = sysPermissionService.findByUserId(sysUser.getId());
        //如果该用户没有权限，就返回
        if (sysPermissionList == null || sysPermissionList.isEmpty()) {
            return sysUser;
        }
        //把用户所拥有的权限标识，封装进来
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (SysPermission sysPermission : sysPermissionList) {
            grantedAuthorities.add(new SimpleGrantedAuthority(sysPermission.getCode()));
        }
        //设置用户所拥有的权限标识
        sysUser.setAuthorities(grantedAuthorities);

        //设置用户所有的资源对象，等会有用
        sysUser.setPermissions(sysPermissionList);

        return sysUser;
        /*User user = new User("lisi", "", true, true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList("Admin"));
        return user;*/
    }
}
