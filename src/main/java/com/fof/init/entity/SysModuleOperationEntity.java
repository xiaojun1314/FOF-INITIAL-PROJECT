package com.fof.init.entity;

import com.fof.common.entity.BaseNoIdEntity;

import javax.persistence.*;

/**
 * 模块元素信息
 */
@Entity
@Table(name = "SYS_MODULE_OPERATION")
public class SysModuleOperationEntity extends BaseNoIdEntity {
    /**操作名称*/
    @Column(name="NAME",columnDefinition="varchar(225)")
    private String name;
    
    /**操作编码*/
    @Column(name="CODE",columnDefinition="varchar(225)")
    private String code;
    
    /**拦截URL*/
    @Column(name="URL",columnDefinition="varchar(225)")
    private String url;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getModule_id() {
		return module_id;
	}

	public void setModule_id(String module_id) {
		this.module_id = module_id;
	}

	public SysAuthorityEntity getSysAuthorityEntity() {
		return sysAuthorityEntity;
	}

	public void setSysAuthorityEntity(SysAuthorityEntity sysAuthorityEntity) {
		this.sysAuthorityEntity = sysAuthorityEntity;
	}

	public String getAuthority_id() {
		return authority_id;
	}

	public void setAuthority_id(String authority_id) {
		this.authority_id = authority_id;
	}
}
