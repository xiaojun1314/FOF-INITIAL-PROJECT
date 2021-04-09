package com.fof.common.entity;

import java.util.List;

public class TreeDataModel {

    private String title;

    private String key;

    private boolean isLeaf;

    private String rowIndex;

    private String description;

    private String icon;

    private List<TreeDataModel> children;

    /**0 集团 1 分部 2 部门*/
    private String type;


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

    public String getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(String rowIndex) {
        this.rowIndex = rowIndex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TreeDataModel> getChildren() {
        return children;
    }

    public void setChildren(List<TreeDataModel> children) {
        this.children = children;
    }

    public boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
