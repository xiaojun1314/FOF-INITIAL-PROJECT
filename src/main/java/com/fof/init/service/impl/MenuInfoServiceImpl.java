package com.fof.init.service.impl;

import com.fof.common.entity.RoutingMenuTree;
import com.fof.common.entity.TreeDataModel;
import com.fof.common.util.CommonUtil;
import com.fof.common.util.Constants;
import com.fof.init.dao.AuthorityInfoDao;
import com.fof.init.dao.MenuInfoDao;
import com.fof.init.dao.RoleAndAuthorityDao;
import com.fof.init.entity.SysAuthorityEntity;
import com.fof.init.entity.SysMenuInfoEntity;
import com.fof.init.service.IMenuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MenuInfoServiceImpl implements IMenuInfoService {

	@Autowired
	private MenuInfoDao menuInfoDao;

	@Autowired
	private AuthorityInfoDao authorityInfoDao;

	@Autowired
	private RoleAndAuthorityDao roleAndAuthorityDao;

	/**查询菜单信息*/
	public List<TreeDataModel> getAll(Map<String,Object> searchParams) {
		List<SysMenuInfoEntity> sysMenuInfoList=menuInfoDao.getAll(searchParams);
		List<TreeDataModel>  treeDataModelList=getChildrenMenu("0",sysMenuInfoList);
		TreeDataModel treeDataModel=new TreeDataModel();
		treeDataModel.setKey("root");
		treeDataModel.setTitle("ROOT");
		treeDataModel.setIsLeaf(false);
		treeDataModel.setIcon("ClusterOutlined");
		treeDataModel.setChildren(treeDataModelList);
		List<TreeDataModel> childrenTreeList=new ArrayList<TreeDataModel>();
		childrenTreeList.add(treeDataModel);
		return childrenTreeList;
	}

	private List<TreeDataModel> getChildrenMenu(String parentId,List<SysMenuInfoEntity> sysMenuInfoList){
		List<TreeDataModel> childrenTreeList=new ArrayList<TreeDataModel>();
		List<SysMenuInfoEntity> childrenList1 = sysMenuInfoList.stream().filter(entity1 ->entity1.getParent_id().equals(parentId)).collect(Collectors.toList());
		for(SysMenuInfoEntity sysMenuInfoEntity1 : childrenList1){
			TreeDataModel treeDataModel=new TreeDataModel();
			treeDataModel.setKey(sysMenuInfoEntity1.getId());
			treeDataModel.setIsLeaf(sysMenuInfoEntity1.getIs_leaf().equals("0")?false:true);
			treeDataModel.setTitle(sysMenuInfoEntity1.getName());
			if(sysMenuInfoEntity1.getIs_leaf().equals("0")) {
				List<TreeDataModel> childrenList2 =getChildrenMenu(sysMenuInfoEntity1.getId(),sysMenuInfoList);
				treeDataModel.setChildren(childrenList2);
			}
			childrenTreeList.add(treeDataModel);
		}
		return childrenTreeList;
	}

	/**通过id获取菜单信息*/
	public SysMenuInfoEntity findById(String id) {
		return menuInfoDao.findById(id);
	}

	/**更新菜单以及权限信息*/
	@Transactional(value = "transactionManager",rollbackFor = Exception.class)
	public void update(SysMenuInfoEntity entity) {
		String updater= CommonUtil.getSecurityUserInfo().getId();
		SysAuthorityEntity sysAuthorityEntity=new SysAuthorityEntity();
		sysAuthorityEntity.setId(entity.getAuthority_id());
		sysAuthorityEntity.setName(entity.getName());
		sysAuthorityEntity.setDescription(entity.getName());
		sysAuthorityEntity.setUpdater(updater);
		/**更新权限信息*/

		authorityInfoDao.update(sysAuthorityEntity);
		entity.setUpdater(updater);
		/**更新菜单信息*/
		menuInfoDao.update(entity);
	}

	/**新增菜单以及权限信息*/
	@Transactional(value = "transactionManager")
	public void insert(SysMenuInfoEntity sysMenuInfoEntity) {
		String creater= CommonUtil.getSecurityUserInfo().getId();
		SysAuthorityEntity sysAuthorityEntity=new SysAuthorityEntity();
		sysAuthorityEntity.setName(sysMenuInfoEntity.getName());
		/**权限类型*/
		sysAuthorityEntity.setType(Constants.AuthorityType.MENU.getValue());
		sysAuthorityEntity.setDescription(sysMenuInfoEntity.getDescription());
		sysAuthorityEntity.setCreater(creater);
		/**添加权限信息*/
		authorityInfoDao.insert(sysAuthorityEntity);
		/**添加菜单信息*/
		sysMenuInfoEntity.setCreater(creater);
		sysMenuInfoEntity.setAuthority_id(sysAuthorityEntity.getId());
		menuInfoDao.insert(sysMenuInfoEntity);
	}
	/**删除菜单以及权限信息*/
	@Transactional(value = "transactionManager")
	public void deleteById(String id){

		List<SysMenuInfoEntity> sysMenuInfoList=menuInfoDao.getAllById(id);
		List<String> menuIdsList = sysMenuInfoList.stream().map(item -> item.getId()).collect(Collectors.toList());
		List<String> authorityIdsList = sysMenuInfoList.stream().map(item -> item.getAuthority_id()).collect(Collectors.toList());
		/**删除权限与角色对应关系*/
		roleAndAuthorityDao.deleteByAuthorityId(authorityIdsList);
		/**删除菜单信息*/
		menuInfoDao.deleteByIdList(menuIdsList);
		/**删除权限表*/
		authorityInfoDao.deleteByIdList(authorityIdsList);

	}

	public List<SysMenuInfoEntity> getMenuToAuthorityByRoleId(String role_id) {
		return menuInfoDao.getMenuToAuthorityByRoleId(role_id);
	}

	public List<RoutingMenuTree> getMenuByUserId() {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userId",CommonUtil.getSecurityUserInfo().getId());
		List<SysMenuInfoEntity> sysMenuInfoList = menuInfoDao.getMenuByUserId(params);
		List<RoutingMenuTree> mainRoutingMenuTreelist=new ArrayList<RoutingMenuTree>();
		RoutingMenuTree routingMenuTree1=new RoutingMenuTree();
		routingMenuTree1.setPath("/welcome");
		routingMenuTree1.setName("welcome");
		routingMenuTree1.setIcon("DropboxOutlined");
		routingMenuTree1.setComponent("./welcome");
		mainRoutingMenuTreelist.add(routingMenuTree1);

		List<SysMenuInfoEntity> childrenList = sysMenuInfoList.stream().filter(entity1 ->entity1.getParent_id().equals("0")).collect(Collectors.toList());
		for(SysMenuInfoEntity sysMenuInfoEntity: childrenList){
			RoutingMenuTree routingMenuTree=new RoutingMenuTree();
			routingMenuTree.setName(sysMenuInfoEntity.getTitle());
			routingMenuTree.setPath(sysMenuInfoEntity.getUrl());
			routingMenuTree.setIcon(sysMenuInfoEntity.getIcon());
			List<RoutingMenuTree> routingMenuTreeList=getChildrenMenuByUserId(sysMenuInfoEntity.getId(),sysMenuInfoList);
			routingMenuTree.setRoutes(routingMenuTreeList);
			mainRoutingMenuTreelist.add(routingMenuTree);
		}
		return mainRoutingMenuTreelist;
	}

	private List<RoutingMenuTree> getChildrenMenuByUserId(String parent_id,List<SysMenuInfoEntity> sysMenuInfoList){
		List<SysMenuInfoEntity> childrenList = sysMenuInfoList.stream().filter(entity1 ->entity1.getParent_id().equals(parent_id)).collect(Collectors.toList());
		List<RoutingMenuTree> routingMenuTreeList=new ArrayList<RoutingMenuTree>();
		for(SysMenuInfoEntity sysMenuInfoEntity : childrenList){
			RoutingMenuTree routingMenuTree=new RoutingMenuTree();
			routingMenuTree.setPath(sysMenuInfoEntity.getUrl());
			routingMenuTree.setName(sysMenuInfoEntity.getTitle());
			routingMenuTree.setIcon(sysMenuInfoEntity.getIcon());
			if(sysMenuInfoEntity.getIs_leaf().equals("1")){
				routingMenuTree.setHideInMenu(sysMenuInfoEntity.getIs_hide().equals("1")?true:false);
				routingMenuTree.setComponent("."+sysMenuInfoEntity.getUrl());
			}
			if(sysMenuInfoEntity.getIs_leaf().equals("0")){
				List<RoutingMenuTree> childrenList2 =getChildrenMenuByUserId(sysMenuInfoEntity.getId(),sysMenuInfoList);
				routingMenuTree.setRoutes(childrenList2);
			}
			routingMenuTreeList.add(routingMenuTree);
		}
        return routingMenuTreeList;
	}
	public List<RoutingMenuTree> getLeafMenuByUserId() {
		List<RoutingMenuTree> routingMenuTreeList=new ArrayList<RoutingMenuTree>();
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userId",CommonUtil.getSecurityUserInfo().getId());
		List<SysMenuInfoEntity> sysMenuInfoList = menuInfoDao.getLeafMenuByUserId(params);
		for(SysMenuInfoEntity sysMenuInfoEntity : sysMenuInfoList){
			RoutingMenuTree routingMenuTree=new RoutingMenuTree();
			routingMenuTree.setPath(sysMenuInfoEntity.getUrl());
			routingMenuTree.setName(sysMenuInfoEntity.getTitle());
			routingMenuTreeList.add(routingMenuTree);
		}
		return routingMenuTreeList;
	}


}
