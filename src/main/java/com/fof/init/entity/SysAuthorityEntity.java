package com.fof.init.entity;

import com.fof.common.entity.BaseNoIdEntity;
import com.fof.common.util.Constants;
import com.fof.common.util.StringUtil;

import javax.persistence.*;
import java.util.List;

/**
 * @className: SysAuthorityEntity
 * @author: jun
 * @date: 2021-03-22 10:07
 * @Depiction:系统权限表
 **/
@Entity
@Table(name = "SYS_AUTHORITY_INFO")
public class SysAuthorityEntity extends BaseNoIdEntity {
    /**权限名称*/
    @Column(name="NAME",columnDefinition="varchar(225)")
    private String name;
    /**权限类型*/
    @Column(name="TYPE",columnDefinition="varchar(225)")
    private String type;
    /**描述*/
    @Column(name="DESCRIPTION",columnDefinition="varchar(225)")
    private String description;

    @ManyToMany(mappedBy = "sysAuthorityInfoList")
    private List<SysRoleInfoEntity> sysRoleInfoList;

    /**权限类型名称*/
    @Transient
    private String          typeText;
    /**菜单的访问权限 统计*/
    @Transient
    private String          menuCount;
    /**页面元素的可见性控制 统计*/
    @Transient
    private String          elementCount;
    /**功能模块的操作权限 统计*/
    @Transient
    private String          operationCount;
    /**文件的修改权限 统计*/
    @Transient
    private String          fileCount;

    public SysAuthorityEntity() {
        super();
        this.menuCount = "0";
        this.elementCount = "0";
        this.operationCount = "0";
        this.fileCount = "0";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeText() {
        if (StringUtil.isNotBlank(typeText)) {
            return typeText;
        } else if(StringUtil.isBlank(this.type)){
            return "";
        }else{
            return Constants.AuthorityTypeName.valueOf("_" + this.type).getValue();
        }
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }

    public String getMenuCount() {
        return menuCount;
    }

    public void setMenuCount(String menuCount) {
        this.menuCount = menuCount;
    }

    public String getElementCount() {
        return elementCount;
    }

    public void setElementCount(String elementCount) {
        this.elementCount = elementCount;
    }

    public String getOperationCount() {
        return operationCount;
    }

    public void setOperationCount(String operationCount) {
        this.operationCount = operationCount;
    }

    public String getFileCount() {
        return fileCount;
    }

    public void setFileCount(String fileCount) {
        this.fileCount = fileCount;
    }

    public List<SysRoleInfoEntity> getSysRoleInfoList() {
        return sysRoleInfoList;
    }

    public void setSysRoleInfoList(List<SysRoleInfoEntity> sysRoleInfoList) {
        this.sysRoleInfoList = sysRoleInfoList;
    }
}
