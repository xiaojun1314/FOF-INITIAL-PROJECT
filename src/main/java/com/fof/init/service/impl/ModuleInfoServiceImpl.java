package com.fof.init.service.impl;

import com.fof.common.entity.TreeDataModel;
import com.fof.common.util.CommonUtil;
import com.fof.common.util.Constants;
import com.fof.init.dao.AuthorityInfoDao;
import com.fof.init.dao.ModuleElementDao;
import com.fof.init.dao.ModuleInfoDao;
import com.fof.init.dao.RoleAndAuthorityDao;
import com.fof.init.entity.SysModuleElementEntity;
import com.fof.init.entity.SysModuleInfoEntity;
import com.fof.init.service.IModuleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ModuleInfoServiceImpl implements IModuleInfoService {

	@Autowired
	private ModuleInfoDao moduleInfoDao;

    @Autowired
    private ModuleElementDao moduleElementDao;

    @Autowired
    private AuthorityInfoDao authorityInfoDao;

	@Autowired
	private RoleAndAuthorityDao roleAndAuthorityDao;


	/**查询菜单信息*/
	public List<TreeDataModel> getAll(Map<String,Object> searchParams) {
		List<SysModuleInfoEntity> sysModuleInfoList=moduleInfoDao.getAll(searchParams);
		List<TreeDataModel>  treeDataModelList=getChildrenMenu("0",sysModuleInfoList);
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

	private List<TreeDataModel> getChildrenMenu(String parentId,List<SysModuleInfoEntity> sysModuleInfoList){
		List<TreeDataModel> childrenTreeList=new ArrayList<TreeDataModel>();
		List<SysModuleInfoEntity> childrenList1 = sysModuleInfoList.stream().filter(entity1 ->entity1.getParent_id().equals(parentId)).collect(Collectors.toList());
		for(SysModuleInfoEntity sysModuleInfoEntity : childrenList1){
			TreeDataModel treeDataModel=new TreeDataModel();
			treeDataModel.setKey(sysModuleInfoEntity.getId());
			treeDataModel.setIsLeaf(sysModuleInfoEntity.getIs_leaf().equals("0")?false:true);
			treeDataModel.setTitle(sysModuleInfoEntity.getName());
			if(sysModuleInfoEntity.getIs_leaf().equals("0")) {
				List<TreeDataModel> childrenList2 =getChildrenMenu(sysModuleInfoEntity.getId(),sysModuleInfoList);
				treeDataModel.setChildren(childrenList2);
			}
			childrenTreeList.add(treeDataModel);
		}
		return childrenTreeList;
	}


	@Transactional(value = "transactionManager")
	public void insert(SysModuleInfoEntity sysModuleInfoEntity) {
		String creater= CommonUtil.getSecurityUserInfo().getId();
		/**添加菜单信息*/
		sysModuleInfoEntity.setCreater(creater);
		moduleInfoDao.insert(sysModuleInfoEntity);
	}

	@Transactional(value = "transactionManager",rollbackFor = Exception.class)
	public void update(SysModuleInfoEntity entity) {
		String updater= CommonUtil.getSecurityUserInfo().getId();
		entity.setUpdater(updater);
		moduleInfoDao.update(entity);
	}

	@Transactional(value = "transactionManager")
	public void delete(String id){

		List<SysModuleInfoEntity> sysModuleInfoList= moduleInfoDao.getAllById(id);

        List<SysModuleInfoEntity> sysLeafModuleInfoList = sysModuleInfoList.stream().filter(entity ->entity.getIs_leaf().equals("1")).collect(Collectors.toList());
        // 全部模块ID
        List<String> moduleIdList = sysModuleInfoList.stream().map(item -> item.getId()).collect(Collectors.toList());
        // 页节点ID
        List<String> LeafModuleIdList = sysLeafModuleInfoList.stream().map(item -> item.getId()).collect(Collectors.toList());
        if(LeafModuleIdList.size()>0){
            Map<String, Object> searchParams =new HashMap<String, Object>();
            searchParams.put("moduleIdList",LeafModuleIdList);
            List<SysModuleElementEntity> sysModuleElementList =moduleElementDao.getAll(searchParams);
            List<String> moduleElementIdList = sysModuleElementList.stream().map(item -> item.getId()).collect(Collectors.toList());
			List<String> authorityIdList = sysModuleElementList.stream().map(item -> item.getAuthority_id()).collect(Collectors.toList());
            if(moduleElementIdList.size()>0){
				/*删除模块与角色关系信息*/
				roleAndAuthorityDao.deleteByAuthorityId(authorityIdList);
				/*删除模块元素信息*/
                moduleElementDao.deleteByIdList(moduleElementIdList);
				/*删除权限表信息*/
				authorityInfoDao.deleteByIdList(authorityIdList);
            }
        }
        moduleInfoDao.deleteByIdList(moduleIdList);
	}

	public SysModuleInfoEntity findById(String id) {
		return moduleInfoDao.findById(id);
	}

	public List<SysModuleInfoEntity>  getLeafModuleById(Map<String, Object> searchParams) {

		return moduleInfoDao.getLeafModuleById(searchParams);
	}

	public List<SysModuleInfoEntity>  getAllById(String id) {

		return moduleInfoDao.getAllById(id);
	}

	public List<SysModuleInfoEntity> getModuleLevelName() {
		return moduleInfoDao.getModuleLevelName();
	}
}
