package org.westos.phonemsg;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Author: ShenMouMou
 * @Company:西部开源教育科技有限公司
 * @Description:简简单单，只为教育。
 **/
public class MobileAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService UserDetailsService;

    public void setUserDetailsService(org.springframework.security.core.userdetails.UserDetailsService userDetailsService) {
        UserDetailsService = userDetailsService;
    }

    /**
     *     * 处理认证:
     *     *  1. 通过 手机号 去数据库查询用户信息(UserDetailsService)
     *     *  2. 再重新构建认证信息
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobileAuthenticationToken mobileAuthenticationToken = (MobileAuthenticationToken) authentication;
        String phoneNumber = (String) mobileAuthenticationToken.getPrincipal();
        UserDetails userDetails = UserDetailsService.loadUserByUsername(phoneNumber);
        //如果通过手机号没有查询到该手机号，那就抛出异常
        if (userDetails == null) {
            throw new AuthenticationServiceException("手机号尚未注册");
        }


        // 查询到了用户信息, 则认证通过,就重新构建 MobileAuthenticationToken 实例
        MobileAuthenticationToken authenticationToken = new MobileAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationToken.setDetails(mobileAuthenticationToken.getDetails());

        return authenticationToken;
    }

    /**
     *   * 通过此方法,来判断 采用哪一个 AuthenticationProvider
     */
    @Override
    public boolean supports(Class<?> authentication) {
        /*class1.isAssignableFrom(class2) 判定此 `Class` 对象所表示的类或接口与指定的 `Class` 参数所表示的类或接口是否相同，或是否是其超类或超接口。
        如果是则返回 `true`；否则返回 `false`。如果该 `Class` 表示一个基本类型，且指定的 `Class` 参数正是该 `Class` 对象，则该方法返回 `true`；否则返回 `false`。 */
        return MobileAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
