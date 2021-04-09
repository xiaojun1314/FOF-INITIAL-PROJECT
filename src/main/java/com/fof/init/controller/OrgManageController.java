package com.fof.init.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fof.common.bean.JsonResult;
import com.fof.common.entity.MenuTree;
import com.fof.common.entity.TreeBreadcrumb;
import com.fof.common.entity.TreeDataModel;
import com.fof.common.util.Constants;
import com.fof.common.util.ResultTool;
import com.fof.common.util.StringHelper;
import com.fof.init.entity.SysCompanyEntity;
import com.fof.init.entity.SysDepartmentEntity;
import com.fof.init.entity.SysSubCompanyEntity;
import com.fof.init.service.ICompanyInfoService;
import com.fof.init.service.IDepartmentInfoService;
import com.fof.init.service.ISubCompanyInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: OrgManageController
 * @author: jun
 * @date: 2021-01-16 11:24
 * @Depiction:
 **/

@Controller
@RequestMapping("/orgManage")
public class OrgManageController {
    @Autowired
    private ICompanyInfoService companyInfoService;

    @Autowired
    private ISubCompanyInfoService subCompanyInfoService;

    @Autowired
    private IDepartmentInfoService departmentInfoService;

    @RequestMapping(value="/queryOrgTreeInfo",method= RequestMethod.POST)
    public void queryOrgTreeInfo(HttpServletResponse response, HttpServletRequest request,@RequestBody Map<String, Object> searchParams) throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        String findType= searchParams.get("findType").toString();
        searchParams.clear();
        List<SysCompanyEntity> companyList=companyInfoService.getAll(searchParams, "order_no=ascend");
        List<TreeDataModel> treeDataModelList=new ArrayList<TreeDataModel>();
        for(SysCompanyEntity entity:companyList) {
            TreeDataModel treeDataModel=new TreeDataModel();
            treeDataModel.setKey(entity.getId());
            treeDataModel.setTitle(entity.getName());
            treeDataModel.setType("0");
            treeDataModel.setRowIndex(entity.getOrder_no());
            treeDataModel.setDescription(entity.getDescription());
            searchParams.clear();
            searchParams.put("foreignId", entity.getId());
            List<SysSubCompanyEntity> subCompanyList= subCompanyInfoService.getAll(searchParams, "order_no=ascend");
            List<TreeDataModel> childrenTreeDataModelList=new ArrayList<TreeDataModel>();
            for(SysSubCompanyEntity subEntity:subCompanyList) {
                TreeDataModel subTreeDataModel=new TreeDataModel();
                subTreeDataModel.setKey(subEntity.getId());
                subTreeDataModel.setTitle(subEntity.getName());
                subTreeDataModel.setType("1");
                searchParams.clear();
                searchParams.put("parent_id",subEntity.getId());
                if(findType.equals("ALL")){
                    List<TreeDataModel> list= getChildrenMenu(searchParams);
                    if(list.size()>0) {
                        subTreeDataModel.setChildren(list);
                    }
                }
                childrenTreeDataModelList.add(subTreeDataModel);
            }
            treeDataModel.setChildren(childrenTreeDataModelList);
            treeDataModelList.add(treeDataModel);
        }
        TreeDataModel treeDataModel=new TreeDataModel();
        treeDataModel.setKey("root");
        treeDataModel.setTitle("组织架构");
        treeDataModel.setIsLeaf(false);
        treeDataModel.setIcon("ClusterOutlined");
        treeDataModel.setChildren(treeDataModelList);
        List<TreeDataModel> childrenTreeList=new ArrayList<TreeDataModel>();
        childrenTreeList.add(treeDataModel);
        json.put("unitTreeData",childrenTreeList);
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }

    private List<TreeDataModel> getChildrenMenu(Map<String, Object> searchParams){
        List<TreeDataModel> childrenTreeDataModelList=new ArrayList<TreeDataModel>();
        List<SysDepartmentEntity>   childrenList= departmentInfoService.getAll(searchParams, "order_no=ascend");
        for(SysDepartmentEntity entity:childrenList) {
            TreeDataModel treeDataModel=new TreeDataModel();
            treeDataModel.setKey(entity.getId());
            treeDataModel.setTitle(entity.getName());
            treeDataModel.setType("2");
            Map<String, Object> childrenParams=new HashMap<String, Object>();
            childrenParams.put("parent_id",entity.getId());
            List<SysDepartmentEntity>  list=departmentInfoService.getAll(childrenParams,"order_no=ascend");
            if(list.size()>0) {
                List<TreeDataModel> subTreeDataModelList =getChildrenMenu(childrenParams);
                treeDataModel.setChildren(subTreeDataModelList);
            }
            childrenTreeDataModelList.add(treeDataModel);
        }
        return childrenTreeDataModelList;
    }

    @RequestMapping(value="/queryOrgTreeById",method=RequestMethod.POST)
    public void queryOrgTreeById(HttpServletResponse response,HttpServletRequest request,@RequestBody Map<String, Object> searchParams) throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        TreeBreadcrumb treeBreadcrumb=new TreeBreadcrumb();
        treeBreadcrumb.setOneLevel("组织架构");
        /**总部*/
        if(searchParams.get("type").equals("0")) {
            SysCompanyEntity entity =companyInfoService.findById(searchParams.get("id").toString());
            treeBreadcrumb.setTwoLevel(entity.getName());
            json.put("entity",entity);
            json.put("type","0");
        }
        /**分部*/
        if(searchParams.get("type").equals("1")) {
            SysSubCompanyEntity entity =subCompanyInfoService.findById(searchParams.get("id").toString());
            treeBreadcrumb.setThreeLevel(entity.getName());
            SysCompanyEntity sysCompanyEntity=companyInfoService.findById(entity.getForeignId());
            treeBreadcrumb.setTwoLevel(sysCompanyEntity.getName());
            json.put("entity",entity);
            json.put("type","1");
        }
        /**部门*/
        if(searchParams.get("type").equals("2")) {
            SysDepartmentEntity entity =departmentInfoService.findById(searchParams.get("id").toString());
            treeBreadcrumb.setFourLevel(entity.getName());
            SysSubCompanyEntity sysSubCompanyEntity=subCompanyInfoService.findById(entity.getForeignId());
            treeBreadcrumb.setThreeLevel(sysSubCompanyEntity.getName());
            SysCompanyEntity sysCompanyEntity=companyInfoService.findById(sysSubCompanyEntity.getForeignId());
            treeBreadcrumb.setTwoLevel(sysCompanyEntity.getName());
            json.put("entity",entity);
            json.put("type","2");
        }
        json.put("treeBreadcrumb",treeBreadcrumb);
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }

    public  int[] initPage(String currentPage,String pageSize1) {
        int pageNumber = Integer.parseInt(StringUtils.defaultIfBlank(currentPage,"1"));
        int pageSize = Integer.parseInt(StringUtils.defaultIfBlank(pageSize1,"10"));
        return new int[] { pageNumber, pageSize };
    }

}
