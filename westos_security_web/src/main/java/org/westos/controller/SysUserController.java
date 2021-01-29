package org.westos.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.westos.bean.SysRole;
import org.westos.bean.SysUser;
import org.westos.service.SysRoleService;
import org.westos.service.SysUserService;
import org.westos.utils.ResponseResult;

import java.util.List;

@Controller
@RequestMapping("/user")
public class SysUserController {

    private static final String HTML_PREFIX = "system/user/";

    @Autowired
    private SysUserService sysUserService;

    @PreAuthorize("hasAuthority('sys:user')")
    @GetMapping(value = {"/", ""}) // /user/  /user
    public String user() {
        return HTML_PREFIX + "user-list";
    }

    //查询所有的用户，并分页
    @PreAuthorize("hasAuthority('sys:user:list')")
    @PostMapping("/page")
    @ResponseBody
    public ResponseResult page(Page<SysUser> page, SysUser sysUser) {
        IPage<SysUser> sysUserIPage = sysUserService.selectPage(page, sysUser);
        return ResponseResult.ok(sysUserIPage);
    }

    @Autowired
    SysRoleService sysRoleService;

    /**
     * 跳转到新增或者修改页面
     *
     * @return
     */
    // 有 'sys:user:add' 或 'sys:user:edit'权限 的用户可以访问
    @PreAuthorize("hasAnyAuthority('sys:user:add', 'sys:user:edit')")
    @GetMapping(value = {"/form", "/form/{id}"}) // /user/form
    public String form(@PathVariable(required = false) Long id, Model model) {
        //查询用户的旧信息，包括该用户所用的角色信息，
        System.out.println(id);
        SysUser sysUser = sysUserService.findById(id);
        System.out.println("用户的旧信息：" + sysUser);
        model.addAttribute("user", sysUser);
        //2.查询角色表中所有的角色
        List<SysRole> roleList = sysRoleService.list();
        model.addAttribute("roleList", roleList);
        return HTML_PREFIX + "user-form";
    }

    /**
     *     * 提交新增（POST）或更新（PUT）数据
     *     * @param sysUser
     *     * @return
     *    
     */
    @PreAuthorize("hasAnyAuthority('sys:user:add', 'sys:user:edit')")
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, value = "")
    public String saveOrUpdate(SysUser sysUser) {
        System.out.println("更新" + sysUser);
        sysUserService.saveOrUpdate(sysUser);
        return "redirect:/user";
    }

  /*  // 返回值的code等于200，则调用成功有权限 ，否则把403
    @PostAuthorize("returnObject.code == 200")
    @RequestMapping("/{id}")  // /user/{id}
    @ResponseBody
    public ResponseResult deleteByIdtest(@PathVariable Long id) {

        if (id < 0) {
            return ResponseResult.build(500, "id不能小于0", id);
        }

        return ResponseResult.ok();
    }*/

    // 过滤请求参数：filterTarget 指定哪个参数，filterObject是集合中的每个元素，
    // 如果value表达式为true的数据则不会被过滤，否则 就过滤掉
    @PreFilter(filterTarget = "ids", value = "filterObject > 0")
    @RequestMapping("/batch/{ids}") // /user/batch/-1,0,1,2
    @ResponseBody
    public ResponseResult deleteByIds(@PathVariable List<Long> ids) {
        return ResponseResult.ok(ids);
    }

    // 过滤返回值：filterObject是返回值集合中的每一个元素，当表达式为true则对应元素会返回
    @PostFilter("filterObject != authentication.principal.username")
    @RequestMapping("/list")
    @ResponseBody
    public List<String> page2() {
        List<String> userList = Lists.newArrayList("meng", "xue", "gu");
        return userList;
    }


    /**
     *     * 删除用户
     *     * @param id 用户id
     *    
     */

    @PostAuthorize("hasAuthority('sys:user:delete')")
    @RequestMapping("/{id}")
    @ResponseBody
    public ResponseResult deleteById(@PathVariable Long id) {
        sysUserService.deleteById(id);
        return ResponseResult.ok();
    }
}
