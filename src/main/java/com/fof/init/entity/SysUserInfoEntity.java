package com.fof.init.entity;


import com.fof.common.entity.BaseNoIdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Table(name = "SYS_USER_INFO")
public class SysUserInfoEntity extends BaseNoIdEntity {
    /**名称*/
    @Column(name="simplename",columnDefinition="varchar(225)")
    private String simpleName;
    /**全名*/
    @Column(name="fullname",columnDefinition="varchar(225)") 
    private String fullName;
    /**用户名*/
    @Column(name="username",columnDefinition="varchar(225)") 
    private String userName;
    /**密码*/
    @Column(name="password",columnDefinition="varchar(225)") 
    private String passWord;
    /**密码盐*/
    @Column(name="salt",columnDefinition="varchar(225)") 
    private String salt;
    /**用户状态*/
    @Column(name="state",columnDefinition="varchar(225)") 
    private String state;
    
    /**用户类型*/
    @Column(name="usertype",columnDefinition="varchar(225)") 
    private String userType;

    @Transient
    private String stateText;
    
    @Transient
    private String userTypeText;

    @Transient
    private String token;
    
    @Transient
    private String oldUserName;

    public String getCredentialsSalt(){
        return this.userName+this.salt;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getStateText() {
        return stateText;
    }

    public void setStateText(String stateText) {
        this.stateText = stateText;
    }

    public String getUserTypeText() {
        return userTypeText;
    }

    public void setUserTypeText(String userTypeText) {
        this.userTypeText = userTypeText;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOldUserName() {
        return oldUserName;
    }

    public void setOldUserName(String oldUserName) {
        this.oldUserName = oldUserName;
    }

}
