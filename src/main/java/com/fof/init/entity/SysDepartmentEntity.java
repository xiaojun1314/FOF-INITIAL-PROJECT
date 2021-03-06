package com.fof.init.entity;

import com.fof.common.entity.BaseNoIdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 总公司信息管理
 */
@Entity
@Table(name = "SYS_DEPARTMENT_INFO")
public class SysDepartmentEntity extends BaseNoIdEntity {
    /**公司名称*/
    @Column(name="name",columnDefinition="varchar(225)") 
    private String name;
    /**公司编码*/
    @Column(name="code",columnDefinition="varchar(225)") 
    private String code;
    /**公司描述*/
    @Column(name="description",columnDefinition="varchar(225)") 
    private String description;
    /**排序号*/
    @Column(name="order_no",columnDefinition="varchar(225)") 
    private String order_no;

	@Column(name="PARENT_ID",columnDefinition="varchar(225)")
	private String parent_id;

    @Transient
    private String          oldCode;

	@Transient
	private String  foreignId;

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

	public String getForeignId() {
		return foreignId;
	}
	public void setForeignId(String foreignId) {
		this.foreignId = foreignId;
	}

	public String getOldCode() {
		return oldCode;
	}

	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
}
