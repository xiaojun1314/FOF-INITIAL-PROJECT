package com.fof.init.entity;

import com.fof.common.entity.BaseNoIdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Table(name = "SYS_ROLE_INFO")
public class SysRoleInfoEntity extends BaseNoIdEntity {

    /**角色名称*/
    @Column(name="NAME",columnDefinition="varchar(225)")
    private String name;
    /**角色编码*/
    @Column(name="CODE",columnDefinition="varchar(225)")
    private String code;
    @Transient
    private String  oldCode;
    /**角色描述*/
    @Column(name="DESCRIPTION",columnDefinition="varchar(225)")
    private String description;
    /**角色状态*/
    @Column(name="STATE",columnDefinition="varchar(225)")
    private String state;
    @Transient
    private String stateText;
    @Transient
    private List<SysUserInfoEntity> sysUserInfoList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOldCode() {
        return oldCode;
    }

    public void setOldCode(String oldCode) {
        this.oldCode = oldCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateText() {
        return stateText;
    }

    public void setStateText(String stateText) {
        this.stateText = stateText;
    }

    public List<SysUserInfoEntity> getSysUserInfoList() {
        return sysUserInfoList;
    }

    public void setSysUserInfoList(List<SysUserInfoEntity> sysUserInfoList) {
        this.sysUserInfoList = sysUserInfoList;
    }
}
