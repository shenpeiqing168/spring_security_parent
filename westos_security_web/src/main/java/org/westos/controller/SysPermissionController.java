package org.westos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.westos.bean.SysPermission;
import org.westos.service.SysPermissionService;
import org.westos.utils.ResponseResult;

import java.util.List;

/**
 * 角色管理
 */
@Controller
@RequestMapping("/permission")
public class SysPermissionController {
    @Autowired
    private SysPermissionService sysPermissionService;
    private static final String HTML_PREFIX = "system/permission/";

    //进入权限管理界面
    @PreAuthorize("hasAuthority('sys:permission')")
    @GetMapping(value = {"/", ""}) // /permission/  /permission
    public String permission() {
        return HTML_PREFIX + "permission-list";
    }

    //查询所有权限资源列表
    @PostAuthorize("hasAuthority('sys:permission:list')")
    @PostMapping("/list")
    @ResponseBody
    public ResponseResult list() {
        //查询所有权限资源
        List<SysPermission> list = sysPermissionService.list();
       /*
       返回树形结构的JSON
       TreeUtil treeUtil = new TreeUtil(list);
        SysPermission sysPermission = treeUtil.generateTree(17L);
        ArrayList<SysPermission> arr = new ArrayList<>();
        arr.add(sysPermission);
        ResponseResult result = ResponseResult.build(200, "成功", arr);
        System.out.println(result.toJsonString());*/
        return ResponseResult.ok(list);
    }

    @PreAuthorize("hasAnyAuthority('sys:permission:add','sys:permission:edit')")
    @GetMapping(value = {"/form", "/form/{id}"})
    public String form(@PathVariable(required = false) Long id, Model model) {

        // 通过权限id查询权限资源
        SysPermission permission = sysPermissionService.getById(id);
        //如果id没有值，说明是要跳转到新增页面，id没有值，查询出的permission就是null,那就新创建一个新的permission对象。
        model.addAttribute("permission", permission == null ? new SysPermission() : permission);

        return HTML_PREFIX + "permission-form";
    }

    //修改权限或新增权限的方法
    @PreAuthorize("hasAnyAuthority('sys:permission:edit','sys:permission:add')")
    @RequestMapping(value = "", method = {RequestMethod.PUT, RequestMethod.POST})
    public String saveOrUpdate(SysPermission permission) {
        sysPermissionService.saveOrUpdate(permission);
        return "redirect:/permission";
    }

    @PreAuthorize("hasAuthority('sys:permission:delete')")
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseResult deleteById(@PathVariable("id") Long id) {
        sysPermissionService.deleteById(id);
        return ResponseResult.ok();
    }
}
