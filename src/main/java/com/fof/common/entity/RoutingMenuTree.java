package com.fof.common.entity;

import java.util.List;

public class RoutingMenuTree {
	
   private String path;
   
   private String name;
   
   private String icon;
   
   private String component;
   
   private boolean hideInMenu;

   private String redirect;
   
   private List<RoutingMenuTree> routes;

public String getPath() {
	return path;
}

public void setPath(String path) {
	this.path = path;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getIcon() {
	return icon;
}

public void setIcon(String icon) {
	this.icon = icon;
}

public String getComponent() {
	return component;
}

public void setComponent(String component) {
	this.component = component;
}

public List<RoutingMenuTree> getRoutes() {
	return routes;
}

public void setRoutes(List<RoutingMenuTree> routes) {
	this.routes = routes;
}

public boolean isHideInMenu() {
	return hideInMenu;
}

public void setHideInMenu(boolean hideInMenu) {
	this.hideInMenu = hideInMenu;
}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}
}


