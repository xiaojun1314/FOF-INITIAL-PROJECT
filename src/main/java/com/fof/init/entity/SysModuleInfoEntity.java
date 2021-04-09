package com.fof.init.entity;

import com.fof.common.entity.BaseNoIdEntity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * tree结构
 * 模块权限管理 包括 页面元素,功能操作权限
 */
@Entity
@Table(name = "SYS_MODULE_INFO")
public class SysModuleInfoEntity extends BaseNoIdEntity {

	/**
	 * 模块名称
	 */
	@Column(name = "NAME", columnDefinition = "varchar(225)")
	private String name;

	/**
	 * 模块编码
	 */
	@Column(name = "CODE", columnDefinition = "varchar(225)")
	private String code;

	@Transient
	private String oldCode;

	/**
	 * 描述
	 */
	@Column(name = "DESCRIPTION", columnDefinition = "varchar(225)")
	private String description;

	/**
	 * 排序号
	 */
	@Column(name = "ORDER_NO", columnDefinition = "varchar(225)")
	private String order_no;

	/**
	 * 是否组还是末级
	 */
	@Column(name = "IS_LEAF", columnDefinition = "varchar(225)")
	private String is_leaf;

	/**
	 * 小图标
	 */
	@Column(name = "ICON", columnDefinition = "varchar(225)")
	private String icon;

	/**
	 * 父类ID
	 */
	@Column(name = "PARENT_ID", columnDefinition = "varchar(225)")
	private String parent_id;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "MODULE_ID")
	private List<SysModuleElementEntity> sysModuleElementEntityList = new ArrayList<SysModuleElementEntity>();

	/**
	 * 父类名称
	 */
	@Transient
	private String parentName;

	@Transient
	private String   levelName;

	/**
	 * 节点文本
	 */
	@Transient
	private String isLeafText;

	/**
	 * 按钮是否显示1
	 */
	@Transient
	private boolean card1Visible;

	/**
	 * 按钮是否显示2
	 */
	@Transient
	private boolean card2Visible;
	/**
	 * 按钮是否显示2
	 */
	@Transient
	private boolean card3Visible;

	/**
	 * 按钮是否显示3
	 */
	@Transient
	private boolean btn1Visible;


	/**显示yuansu*/
	@Transient
	private List<Map<String,String>> elementList;

	@Transient
	private List<String> elementIdList;

	/**已选择项*/
	@Transient
	private List<String> checkedList;


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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public boolean isCard3Visible() {
		return card3Visible;
	}

	public void setCard3Visible(boolean card3Visible) {
		this.card3Visible = card3Visible;
	}

	public boolean isBtn1Visible() {
		return btn1Visible;
	}

	public void setBtn1Visible(boolean btn1Visible) {
		this.btn1Visible = btn1Visible;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getIs_leaf() {
		return is_leaf;
	}

	public void setIs_leaf(String is_leaf) {
		this.is_leaf = is_leaf;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public List<SysModuleElementEntity> getSysModuleElementEntityList() {
		return sysModuleElementEntityList;
	}

	public void setSysModuleElementEntityList(List<SysModuleElementEntity> sysModuleElementEntityList) {
		this.sysModuleElementEntityList = sysModuleElementEntityList;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public List<Map<String, String>> getElementList() {
		return elementList;
	}

	public void setElementList(List<Map<String, String>> elementList) {
		this.elementList = elementList;
	}

	public List<String> getElementIdList() {
		return elementIdList;
	}

	public void setElementIdList(List<String> elementIdList) {
		this.elementIdList = elementIdList;
	}

	public List<String> getCheckedList() {
		return checkedList;
	}

	public void setCheckedList(List<String> checkedList) {
		this.checkedList = checkedList;
	}
}
