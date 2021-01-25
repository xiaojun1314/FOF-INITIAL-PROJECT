package com.fof.common.entity;

import java.util.List;

/**
 * @className: MenuTree
 * @author: jun
 * @date: 2021-01-16 11:15
 * @Depiction:
 **/
public class MenuTree {

    private String title;

    private String key;

    private boolean isLeaf;

    /**0 集团 1 分部 2 部门*/
    private String type;

    private String orderNo;

    private String description;

    private List<MenuTree> children;

    private List<MenuTree> treeData;

    private String icon;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean getIsLeaf() {
        return isLeaf;
    }
    public void setIsLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MenuTree> getChildren() {
        return children;
    }

    public void setChildren(List<MenuTree> children) {
        this.children = children;
    }

    public List<MenuTree> getTreeData() {
        return treeData;
    }

    public void setTreeData(List<MenuTree> treeData) {
        this.treeData = treeData;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
