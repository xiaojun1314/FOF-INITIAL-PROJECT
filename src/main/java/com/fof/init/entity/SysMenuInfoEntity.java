package com.fof.init.entity;

import com.fof.common.entity.BaseNoIdEntity;

import javax.persistence.*;

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
    private String order_no;
    
    /**是否隐藏菜单*/
    @Column(name="is_hide",columnDefinition="varchar(225)")
    private String is_hide;
    
    /**隐藏文本*/
    @Transient
    private String  isHideText;
    
    /**是否组还是末级*/
    @Column(name="is_leaf",columnDefinition="varchar(225)")
    private String is_leaf;
    
    /**小图标*/
    @Column(name="icon",columnDefinition="varchar(225)")
    private String icon;
    
    /**父类ID*/
    @Column(name="parent_id",columnDefinition="varchar(225)")
    private String parent_id;

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

	public String getIsHideText() {
		return isHideText;
	}

	public void setIsHideText(String isHideText) {
		this.isHideText = isHideText;
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

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getIs_hide() {
		return is_hide;
	}

	public void setIs_hide(String is_hide) {
		this.is_hide = is_hide;
	}

	public String getIs_leaf() {
		return is_leaf;
	}

	public void setIs_leaf(String is_leaf) {
		this.is_leaf = is_leaf;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
