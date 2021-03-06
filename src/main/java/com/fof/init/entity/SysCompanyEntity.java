package com.fof.init.entity;

import com.fof.common.entity.BaseNoIdEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 总公司信息管理
 */
@Entity
@Table(name = "SYS_COMPANY_INFO")
public class SysCompanyEntity extends BaseNoIdEntity {
	
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

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "FOREIGNID")
	private List<SysSubCompanyEntity> sysSubCompanyList = new ArrayList<SysSubCompanyEntity>();

    @Transient
    private String   oldCode;

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

	public List<SysSubCompanyEntity> getSysSubCompanyList() {
		return sysSubCompanyList;
	}

	public void setSysSubCompanyList(List<SysSubCompanyEntity> sysSubCompanyList) {
		this.sysSubCompanyList = sysSubCompanyList;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
}
