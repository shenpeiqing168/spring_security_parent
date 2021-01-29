package org.westos.utils;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: ShenMouMou
 * @Company: 西部开源教育科技有限公司
 * @Description: 简简单单，只为教育。
 * @Date: 2021/1/8 14:17
 */
@Data
public class TreeNode {
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 父资源id,给它初始值 0
     * 新增和修改页面上默认的父资源id
     */
    private Long parentId = 0L;
    /**
     * 用于新增和修改页面上默认的根菜单名称
     */
    @TableField(exist = false)
    private String parentName = "根菜单";

    private String name;
    private String code;
    private String url;
    /**
     * 菜单：1，按钮：2
     */
    private Integer type;
    private String icon;
    private String remark;
    private Date createDate;
    private Date updateDate;


    /**
     * 所有子权限对象集合
     * 左侧菜单渲染时要用
     * 这个集合就是用来装，该菜单的所有子菜单
     */
    @TableField(exist = false)
    private List<TreeNode> children;

    /**
     * 所有子权限 URL 集合
     * 左侧菜单渲染时要用
     * 这个集合，就是用来装，该菜单所有子菜单的url
     */
    @TableField(exist = false)
    private List<String> childrenUrl;
}
