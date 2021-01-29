package org.westos.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.westos.bean.SysRole;
import org.westos.service.SysRoleService;
import org.westos.utils.ResponseResult;

/**
 * 角色管理
 */
@Controller
@RequestMapping("/role")
public class SysRoleController {

    private static final String HTML_PREFIX = "system/role/";

    @GetMapping(value = {"/", ""}) // /role/  /role
    public String role() {
        return HTML_PREFIX + "role-list";
    }

    @Autowired
    private SysRoleService sysRoleService;

    /**
     *     * 分页：角色列表数据
     *     * @param page 分页
     *     * @return
     *    
     */
    @PreAuthorize("hasAuthority('sys:role:list')")
    @PostMapping("/page") // 不要少了 /
    @ResponseBody // 不要少了
    public ResponseResult page(Page<SysRole> page, SysRole role) {
        // Page<SysRole> sysRolePage = new Page<>(1,5);
        return ResponseResult.ok(sysRoleService.selectPage(page, role));
    }

    /**
     *     * 跳转新增 或 修改页面
     *     * @return
     *    
     */
    @PreAuthorize("hasAnyAuthority('sys:role:add','sys:role:edit')")
    @GetMapping(value = {"/form", "/form/{id}"})
    public String form(@PathVariable(required = false) Long id, Model model) {
        // 查询角色信息与绑定的权限资源
        SysRole role = sysRoleService.getSysRoleAndPermissionsById(id);
        model.addAttribute("role", role);
        return HTML_PREFIX + "role-form";
    }

    /**
     *     * 提交新增（POST）或更新（PUT）数据
     *     * @param sysRole
     *     * @return
     *    
     */
    @PreAuthorize("hasAnyAuthority('sys:role:add','sys:role:edit')")
    @RequestMapping(value = "", method = {RequestMethod.POST, RequestMethod.PUT})
    public String saveOrUpdate(SysRole sysRole) {
        System.out.println(sysRole);
        sysRoleService.saveOrUpdate(sysRole);
        System.out.println(sysRole.getId());
        System.out.println(sysRole.getPerIds());

        return "redirect:/role";
    }

    @PreAuthorize("hasAuthority('sys:role:delete')")
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseResult deleteById(@PathVariable("id") Long id) {
        sysRoleService.deleteRoleById(id);
        return ResponseResult.ok();
    }
}
