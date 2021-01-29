package org.westos.utils;


import org.westos.bean.SysPermission;

import java.util.ArrayList;
import java.util.List;

public class TreeUtil {

    private List<SysPermission> treeNodeList;

    public TreeUtil(List<SysPermission> treeNodeList) {
        this.treeNodeList = treeNodeList;
    }


    /**
     * 根据节点id获取节点对象信息
     *
     * @param id
     * @return
     */
    private SysPermission getById(long id) {
        //遍历所有的节点
        for (SysPermission treeNode : treeNodeList) {
            if (treeNode.getId() == id) {
                return treeNode;
            }
        }
        return null;
    }


    /**
     * 根据节点id查询儿子节点的集合
     *
     * @param id
     * @return
     */
    private List<SysPermission> getChildrenById(long id) {
        List<SysPermission> children = new ArrayList<>();
        for (SysPermission treeNode : treeNodeList) {
            if (treeNode.getParentId() == id) {
                children.add(treeNode);
            }
        }
        return children;
    }


    /**
     * 生成树形结构数据
     *
     * @param id
     * @return
     */
    public SysPermission generateTree(long id) {
        //1.根据节点id查询该节点信息
        SysPermission treeNode = getById(id);

        //2.根据节点id查询该节点下所有的子节点
        List<SysPermission> children = getChildrenById(id);
        //3.遍历子节点的 儿子节点
        for (SysPermission node : children) {
            SysPermission child = generateTree(node.getId());
            treeNode.getChildren().add(child);
        }
        return treeNode;
    }
}
