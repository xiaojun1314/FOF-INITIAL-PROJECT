package com.fof.init.entity;

import com.fof.common.entity.BaseNoIdEntity;

import javax.persistence.*;
import java.util.List;

/**
 * 系统菜单管理
 */
@Entity
@Table(name = "SYS_MENU_INFO")
public class SysMenuInfoEntity extends BaseNoIdEntity {
    
    /**菜单名称*/
    @Column(name="name",columnDefinition="varchar(225)")
    private String name;
    
    /**菜单Title*/
    @Column(name="title",columnDefinition="varchar(225)")
    private String title;
    
    /**菜单URL*/
    @Column(name="url",columnDefinition="varchar(225)")
    private String url;
    
    /**菜单描述*/
    @Column(name="description",columnDefinition="varchar(225)")
    private String  description;
    
    /**排序号*/
    @Column(name="order_no",columnDefinition="varchar(225)")
    private String orderNo;
    
    /**是否隐藏菜单*/
    @Column(name="is_hide",columnDefinition="varchar(225)")
    private String isHide;
    
    /**隐藏文本*/
    @Transient
    private String  isHideText;
    
    /**是否组还是末级*/
    @Column(name="is_leaf",columnDefinition="varchar(225)")
    private String isLeaf;
    
    /**小图标*/
    @Column(name="ico",columnDefinition="varchar(225)")
    private String ico;
    
    /**父类ID*/
    @Column(name="parent_id",columnDefinition="varchar(225)")
    private String parentId;

	/**层级*/
	@Transient
	private String  level;
    
    /**父类名称*/
    @Transient
    private String  parentName;
    
    /**节点文本*/
    @Transient
    private String  isLeafText;
    
    /**按钮是否显示1*/
    @Transient
    private boolean  card1Visible;
    
    /**按钮是否显示2*/
    @Transient
    private boolean  card2Visible;
    
    /**按钮是否显示3*/
    @Transient
    private boolean  btn1Visible;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getIsHide() {
		return isHide;
	}

	public void setIsHide(String isHide) {
		this.isHide = isHide;
	}

	public String getIsHideText() {
		return isHideText;
	}

	public void setIsHideText(String isHideText) {
		this.isHideText = isHideText;
	}

	public String getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getIco() {
		return ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getIsLeafText() {
		return isLeafText;
	}

	public void setIsLeafText(String isLeafText) {
		this.isLeafText = isLeafText;
	}

	public boolean isCard1Visible() {
		return card1Visible;
	}

	public void setCard1Visible(boolean card1Visible) {
		this.card1Visible = card1Visible;
	}

	public boolean isCard2Visible() {
		return card2Visible;
	}

	public void setCard2Visible(boolean card2Visible) {
		this.card2Visible = card2Visible;
	}

	public boolean isBtn1Visible() {
		return btn1Visible;
	}

	public void setBtn1Visible(boolean btn1Visible) {
		this.btn1Visible = btn1Visible;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}


}
