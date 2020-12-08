package com.fof.common.entity;

import com.fof.common.util.Constants;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@MappedSuperclass
public class BaseNoIdEntity {
    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    /**主键,自动生成*/
    @Column(name="ID",columnDefinition="varchar(32)") 
	private String id;
	/**删除人*/
    @Column(name="DELETER",columnDefinition="varchar(225)")
    private String deleter;
	/**删除时间*/
    @Column(name="DELETE_TIME",columnDefinition="varchar(225)")
    private String deleteTime;
	/**删除标识*/
    @Column(name="DELETE_FLAG",columnDefinition="char")
    private String deleteFlag=Constants.DELFLG_N;
    /**创建时间*/
    @Column(name="CREATE_TIME",columnDefinition="varchar(225)")
    private String createTime;
    /**创建人*/
    @Column(name="UPDATE_TIME",columnDefinition="varchar(225)")
    private String updateTime;
    /**更新时间*/
    @Column(name="CREATER",columnDefinition="varchar(225)")
    private String creater;
    @Transient
    private String createrText;
    /**更新人*/
    @Column(name="UPDATER",columnDefinition="varchar(225)")
    private String updater;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeleter() {
        return deleter;
    }

    public void setDeleter(String deleter) {
        this.deleter = deleter;
    }

    public String getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(String deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getCreaterText() {
        return createrText;
    }

    public void setCreaterText(String createrText) {
        this.createrText = createrText;
    }
}
