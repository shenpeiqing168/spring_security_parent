package org.westos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @Author: ShenMouMou
 * @Company:西部开源教育科技有限公司
 * @Description:简简单单，只为教育。
 */
@Component("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名，查找用户的信息
        if (!"lisi".equals(username)) {
            throw new UsernameNotFoundException("该用户没有定义");
        }
        String password = passwordEncoder.encode("123");
        //查询用户
        User user = new User(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList("sys:user,sys:role,sys:role:add"));
        return user;
    }
}
