package com.fof.init.entity;

/**菜单与权限对应关系表*/
public class SysMenuToAuthorityEntity {

	private String menu_id;

	private String authority_id;

	public String getAuthority_id() {
		return authority_id;
	}

	public void setAuthority_id(String authority_id) {
		this.authority_id = authority_id;
	}

	public String getMenu_id() {
		return menu_id;
	}

	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}
}
