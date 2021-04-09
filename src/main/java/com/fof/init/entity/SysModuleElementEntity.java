package com.fof.init.entity;

import com.fof.common.entity.BaseNoIdEntity;

import javax.persistence.*;

/**
 * 模块元素信息
 */
@Entity
@Table(name = "SYS_MODULE_ELEMENT")
public class SysModuleElementEntity extends BaseNoIdEntity {
    /**元素名称*/
    @Column(name="NAME",columnDefinition="varchar(225)")
    private String name;
    
    /**元素编码*/
    @Column(name="CODE",columnDefinition="varchar(225)")
    private String code;
    
    /**元素类型*/
    @Column(name="TYPE",columnDefinition="varchar(225)")
    private String type;
    
    @Transient
    private String typeText;
    /**元素状态*/
    @Column(name="STATE",columnDefinition="char")
    private String state;
    @Transient
    private String stateText;
    @Transient
    private String          oldCode;

	/**描述*/
	@Column(name="DESCRIPTION",columnDefinition="varchar(225)")
	private String  description;
    
    /**模块ID*/
	@Transient
	private String  module_id;

	@OneToOne
	@JoinColumn(name="authority_id")
	private SysAuthorityEntity sysAuthorityEntity;

	@Transient
	private String  authority_id;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeText() {
		return typeText;
	}

	public void setTypeText(String typeText) {
		this.typeText = typeText;
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

	public String getOldCode() {
		return oldCode;
	}

	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SysAuthorityEntity getSysAuthorityEntity() {
		return sysAuthorityEntity;
	}

	public void setSysAuthorityEntity(SysAuthorityEntity sysAuthorityEntity) {
		this.sysAuthorityEntity = sysAuthorityEntity;
	}

	public String getModule_id() {
		return module_id;
	}

	public void setModule_id(String module_id) {
		this.module_id = module_id;
	}

	public String getAuthority_id() {
		return authority_id;
	}

	public void setAuthority_id(String authority_id) {
		this.authority_id = authority_id;
	}
}
