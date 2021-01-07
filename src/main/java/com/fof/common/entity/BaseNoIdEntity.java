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
    private String delete_time;
    /**删除标识*/
    @Column(name="DELETE_FLAG",columnDefinition="char")
    private String delete_flag=Constants.DELFLG_N;
    /**创建时间*/
    @Column(name="CREATE_TIME",columnDefinition="varchar(225)")
    private String create_time;
    /**创建人*/
    @Column(name="UPDATE_TIME",columnDefinition="varchar(225)")
    private String update_time;
    /**更新时间*/
    @Column(name="CREATER",columnDefinition="varchar(225)")
    private String creater;
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

    public String getDelete_time() {
        return delete_time;
    }

    public void setDelete_time(String delete_time) {
        this.delete_time = delete_time;
    }

    public String getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(String delete_flag) {
        this.delete_flag = delete_flag;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
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
}
