package org.westos.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @Author: ShenMouMou
 * @Company: 西部开源教育科技有限公司
 * @Description: 简简单单，只为教育。
 * @Date: 2021/1/4 13:38
 */
@Component("mobileUserDetailsService")
public class MobileUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        // 1. 通过手机号查询用户信息
        // 2. 如果有此用户，则查询用户权限
        // 3. 封装用户信息
        //根据手机号，查询出用户信息，我们把用户名保存进去
        User user = new User("lisi", "", true, true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList("Admin"));
        return user;
    }
}
