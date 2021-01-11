package com.fof.init.entity;

import com.fof.common.entity.BaseNoIdEntity;
import com.fof.init.entity.SysDictionaryTypeEntity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SYS_DICTIONARY_INFO")
public class SysDictionaryInfoEntity extends BaseNoIdEntity {

    /**业务字典ID*/
    @Column(name="CODE",columnDefinition="varchar(225)")
    private String code;
    
    /**业务字典名称*/
    @Column(name="NAME",columnDefinition="varchar(225)")
    private String name;

    /**备用字段1*/
    @Column(name="BAK1",columnDefinition="varchar(225)")
    private String bak1;
    /**备用字段2*/
    @Column(name="BAK2",columnDefinition="varchar(225)")
    private String bak2;
    /**备用字段3*/
    @Column(name="BAK3",columnDefinition="varchar(225)")
    private String bak3;
    /**备用字段4*/
    @Column(name="BAK4",columnDefinition="varchar(225)")
    private String bak4;
    /**备用字段5*/
    @Column(name="BAK5",columnDefinition="varchar(225)")
    private String bak5;
    /**备用字段6*/
    @Column(name="BAK6",columnDefinition="varchar(225)")
    private String bak6;
    /**备用字段7*/
    @Column(name="BAK7",columnDefinition="varchar(225)")
    private String bak7;
    /**备用字段8*/
    @Column(name="BAK8",columnDefinition="varchar(225)")
    private String bak8;
    /**备用字段9*/
    @Column(name="BAK9",columnDefinition="varchar(225)")
    private String bak9;
    /**备用字段10*/
    @Column(name="BAK10",columnDefinition="varchar(225)")
    private String bak10; 
    
    @Transient
    private String  oldCode;

	@Transient
	private String  typeCode;


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBak1() {
		return bak1;
	}

	public void setBak1(String bak1) {
		this.bak1 = bak1;
	}

	public String getBak2() {
		return bak2;
	}

	public void setBak2(String bak2) {
		this.bak2 = bak2;
	}

	public String getBak3() {
		return bak3;
	}

	public void setBak3(String bak3) {
		this.bak3 = bak3;
	}

	public String getBak4() {
		return bak4;
	}

	public void setBak4(String bak4) {
		this.bak4 = bak4;
	}

	public String getBak5() {
		return bak5;
	}

	public void setBak5(String bak5) {
		this.bak5 = bak5;
	}

	public String getBak6() {
		return bak6;
	}

	public void setBak6(String bak6) {
		this.bak6 = bak6;
	}

	public String getBak7() {
		return bak7;
	}

	public void setBak7(String bak7) {
		this.bak7 = bak7;
	}

	public String getBak8() {
		return bak8;
	}

	public void setBak8(String bak8) {
		this.bak8 = bak8;
	}

	public String getBak9() {
		return bak9;
	}

	public void setBak9(String bak9) {
		this.bak9 = bak9;
	}

	public String getBak10() {
		return bak10;
	}

	public void setBak10(String bak10) {
		this.bak10 = bak10;
	}

	public String getOldCode() {
		return oldCode;
	}

	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

}
