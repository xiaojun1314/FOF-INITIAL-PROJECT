package com.fof.init.entity;

import com.fof.common.entity.BaseNoIdEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SYS_DICTIONARY_TYPE")
public class SysDictionaryTypeEntity extends BaseNoIdEntity {

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "FOREIGNID")
	private List<SysDictionaryInfoEntity> sysDictionaryInfoList = new ArrayList<>();

    /**字典分类名称*/
    @Column(name="NAME",columnDefinition="varchar(225)")
    private String name;

    /**字典分类id*/
    @Column(name="CODE",columnDefinition="varchar(225)")
    private String code;

    @Transient
    private String  oldCode;

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

	public String getOldCode() {
		return oldCode;
	}

	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
	}

	public List<SysDictionaryInfoEntity> getSysDictionaryInfoList() {
		return sysDictionaryInfoList;
	}

	public void setSysDictionaryInfoList(List<SysDictionaryInfoEntity> sysDictionaryInfoList) {
		this.sysDictionaryInfoList = sysDictionaryInfoList;
	}
}
