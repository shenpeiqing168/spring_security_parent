package org.westos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.westos.bean.SysPermission;
import org.westos.bean.SysUser;
import org.westos.service.SysPermissionService;
import org.westos.service.SysUserService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ShenMouMou
 * @Company:西部开源教育科技有限公司
 * @Description:简简单单，只为教育。
 */
@Component("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysPermissionService sysPermissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名，查找用户的信息
        SysUser sysUser = sysUserService.findByUsername(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("该用户没有定义");
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

       /* String password = passwordEncoder.encode("123");
        //查询用户
        User user = new User(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList("sys:user,sys:role,sys:role:add"));
        return user;*/
    }
}
