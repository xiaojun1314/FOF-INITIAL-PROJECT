package com.fof.init.controller;

import com.alibaba.fastjson.JSONObject;
import com.fof.common.entity.MenuTree;
import com.fof.common.util.Constants;
import com.fof.init.entity.SysCompanyEntity;
import com.fof.init.entity.SysDepartmentEntity;
import com.fof.init.entity.SysSubCompanyEntity;
import com.fof.init.service.ICompanyInfoService;
import com.fof.init.service.IDepartmentInfoService;
import com.fof.init.service.ISubCompanyInfoService;
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
    public String queryOrgTreeInfo(HttpServletResponse response, HttpServletRequest request)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        Map<String,Object> searchParams=new HashMap<String,Object>();
        searchParams.put("delete_flag", Constants.DELFLG_N);
        List<SysCompanyEntity> companyList=companyInfoService.getAll(searchParams, "order_no=ascend");
        List<MenuTree> mainTreeList=new ArrayList<MenuTree>();
        for(SysCompanyEntity entity:companyList) {
            MenuTree menuTree=new MenuTree();
            menuTree.setKey(entity.getId()+"|"+"0");
            menuTree.setTitle(entity.getName());
            menuTree.setType("0");
            menuTree.setOrderNo(entity.getOrder_no());
            menuTree.setDescription(entity.getDescription());
            searchParams.clear();
            searchParams.put("foreignId", entity.getId());
            searchParams.put("delete_flag", Constants.DELFLG_N);
            List<SysSubCompanyEntity> subCompanyList= subCompanyInfoService.getAll(searchParams, "order_no=ascend");
            List<MenuTree> childrenTreeList=new ArrayList<MenuTree>();
            for(SysSubCompanyEntity subEntity:subCompanyList) {
                MenuTree subMenuTree=new MenuTree();
                subMenuTree.setKey(subEntity.getId()+"|"+"1");
                subMenuTree.setTitle(subEntity.getName());
                subMenuTree.setType("1");
                searchParams.clear();
                searchParams.put("parent_id",subEntity.getId());
                searchParams.put("delete_flag", Constants.DELFLG_N);
                List<MenuTree> list= getChildrenMenu(searchParams);
                if(list.size()>0) {
                    subMenuTree.setChildren(list);
                }
                childrenTreeList.add(subMenuTree);
            }
            menuTree.setChildren(childrenTreeList);
            mainTreeList.add(menuTree);
        }
        json.put("unitTreeData",mainTreeList);
        response.getWriter().write(json.toJSONString());
        return null;
    }

    private List<MenuTree> getChildrenMenu(Map<String, Object> searchParams){
        List<MenuTree> childrenTreeList=new ArrayList<MenuTree>();
        List<SysDepartmentEntity>   childrenList= departmentInfoService.getAll(searchParams, "order_no=ascend");
        for(SysDepartmentEntity entity:childrenList) {
            MenuTree menuTree=new MenuTree();
            menuTree.setKey(entity.getId()+"|"+"2");
            menuTree.setTitle(entity.getName());
            menuTree.setType("2");
            Map<String, Object> childrenParams=new HashMap<String, Object>();
            childrenParams.put("parent_id",entity.getId());
            List<SysDepartmentEntity>  list=departmentInfoService.getAll(childrenParams,"order_no=ascend");
            if(list.size()>0) {
                List<MenuTree> bootList =getChildrenMenu(childrenParams);
                menuTree.setChildren(bootList);
            }
            childrenTreeList.add(menuTree);
        }

        return childrenTreeList;
    }

    @RequestMapping(value="/queryOrgTreeById",method=RequestMethod.POST)
    public String queryOrgTreeById(HttpServletResponse response,HttpServletRequest request,@RequestBody Map<String, Object> searchParams)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        if(null!=searchParams.get("id")&&!"root".equals(searchParams.get("id"))) {
            /**总部*/
            if(searchParams.get("type").equals("0")) {
                SysCompanyEntity entity =companyInfoService.findById(searchParams.get("id").toString());
                json.put("entity",entity);
                json.put("type","0");
            }
            /**分部*/
            if(searchParams.get("type").equals("1")) {
                SysSubCompanyEntity entity =subCompanyInfoService.findById(searchParams.get("id").toString());
                json.put("entity",entity);
                json.put("type","1");
            }
            /**部门*/
            if(searchParams.get("type").equals("2")) {
                SysDepartmentEntity entity =departmentInfoService.findById(searchParams.get("id").toString());
                json.put("entity",entity);
                json.put("type","2");
            }
        }
        response.getWriter().write(json.toJSONString());
        return null;
    }

    @RequestMapping(value="/saveCompanyInfo",method=RequestMethod.POST)
    public String saveCompanyInfo(HttpServletResponse response,HttpServletRequest request,@RequestBody SysCompanyEntity entity){
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        try {

            int flag=companyInfoService.insert(entity);
            if(flag==1) {
                json.put("IsSuccess", true);
                json.put("Message", "保存信息成功");
            }else {
                json.put("IsSuccess", true);
                json.put("Message", "保存信息失败");
            }
        }catch (Exception e) {
            json.put("IsSuccess", false);
            json.put("Message", "保存信息失败");
        }
        try {
            response.getWriter().write(json.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/editCompanyInfo",method=RequestMethod.POST)
    public String editCompanyInfo(HttpServletResponse response,HttpServletRequest request,@RequestBody SysCompanyEntity entity)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        try {
            int flag=companyInfoService.update(entity);
            if(flag==1) {
                json.put("IsSuccess", true);
                json.put("Message", "更新成功");
            }else {
                json.put("IsSuccess", true);
                json.put("Message", "更新失败");
            }
        }catch (Exception e) {
            e.printStackTrace();
            json.put("IsSuccess", false);
            json.put("Message", "更新失败");
        }
        try {
            response.getWriter().write(json.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/checkCompanyCode",method=RequestMethod.POST)
    public String checkCompanyCode(HttpServletResponse response,HttpServletRequest request,@RequestBody SysCompanyEntity entity)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        try {
            if(null==entity.getId()||(null!=entity.getId()&&!entity.getCode().equals(entity.getOldCode()))) {
                boolean checkResult=companyInfoService.checkCode(entity);
                if(checkResult) {
                    json.put("IsSuccess",true);
                    json.put("Message", "检查通过");
                }else {
                    json.put("IsSuccess",false);
                    json.put("Message", "检查不通过");
                }
            }else {
                json.put("IsSuccess",true);
                json.put("Message", "检查通过");
            }
        }catch (Exception e) {
            e.printStackTrace();
            json.put("IsSuccess", false);
            json.put("Message", "检查不通过");
        }
        try {
            response.getWriter().write(json.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
            json.put("IsSuccess", false);
            json.put("Message", "检查不通过");
        }
        return null;
    }

    /**添加分部公司信息*/
    @RequestMapping(value="/saveSubCompanyInfo",method=RequestMethod.POST)
    public String saveSubCompanyInfo(HttpServletResponse response,HttpServletRequest request,@RequestBody SysSubCompanyEntity entity){
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        try {
            int flag=subCompanyInfoService.insert(entity);
            if(flag==1) {
                json.put("IsSuccess", true);
                json.put("Message", "保存信息成功");
            }else {
                json.put("IsSuccess", true);
                json.put("Message", "保存信息失败");
            }
        }catch (Exception e) {
            json.put("IsSuccess", false);
            json.put("Message", "保存信息失败");
        }
        try {
            response.getWriter().write(json.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/editSubCompanyInfo",method=RequestMethod.POST)
    public String editSubCompanyInfo(HttpServletResponse response,HttpServletRequest request,@RequestBody SysSubCompanyEntity entity)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        try {

            int flag=subCompanyInfoService.update(entity);
            if(flag==1) {
                json.put("IsSuccess", true);
                json.put("Message", "更新信息成功");
            }else {
                json.put("IsSuccess", true);
                json.put("Message", "更新信息失败");
            }
        }catch (Exception e) {
            e.printStackTrace();
            json.put("IsSuccess", false);
            json.put("Message", "更新信息失败");
        }
        try {
            response.getWriter().write(json.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/checkSubCompanyCode",method=RequestMethod.POST)
    public String checkSubCompanyCode(HttpServletResponse response,HttpServletRequest request,@RequestBody SysSubCompanyEntity entity)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        try {
            if(null==entity.getId()||(null!=entity.getId()&&!entity.getCode().equals(entity.getOldCode()))) {
                boolean checkResult=subCompanyInfoService.checkCode(entity);
                if(checkResult) {
                    json.put("IsSuccess",true);
                    json.put("Message", "检查通过");
                }else {
                    json.put("IsSuccess",false);
                    json.put("Message", "检查不通过");
                }
            }else {
                json.put("IsSuccess",true);
                json.put("Message", "检查通过");
            }
        }catch (Exception e) {
            e.printStackTrace();
            json.put("IsSuccess", false);
            json.put("Message", "检查不通过");
        }
        try {
            response.getWriter().write(json.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
            json.put("IsSuccess", false);
            json.put("Message", "检查不通过");
        }
        return null;
    }


    /**添加分部公司信息*/
    @RequestMapping(value="/saveDepartmentInfo",method=RequestMethod.POST)
    public String saveDepartmentInfo(HttpServletResponse response,HttpServletRequest request,@RequestBody SysDepartmentEntity entity){
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        try {

            int flag=departmentInfoService.insert(entity);
            if(flag==1) {
                json.put("IsSuccess", true);
                json.put("Message", "保存信息成功");
            }else {
                json.put("IsSuccess", true);
                json.put("Message", "保存信息失败");
            }
        }catch (Exception e) {
            json.put("IsSuccess", false);
            json.put("Message", "保存信息失败");
        }
        try {
            response.getWriter().write(json.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/editDepartmentInfo",method=RequestMethod.POST)
    public String editDepartmentInfo(HttpServletResponse response,HttpServletRequest request,@RequestBody SysDepartmentEntity entity)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        try {
            int flag=departmentInfoService.update(entity);
            if(flag==1) {
                json.put("IsSuccess", true);
                json.put("Message", "更新信息成功");
            }else {
                json.put("IsSuccess", true);
                json.put("Message", "更新信息失败");
            }
        }catch (Exception e) {
            e.printStackTrace();
            json.put("IsSuccess", false);
            json.put("Message", "更新信息失败");
        }
        try {
            response.getWriter().write(json.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/checkDepartmentCode",method=RequestMethod.POST)
    public String checkDepartmentCode(HttpServletResponse response,HttpServletRequest request,@RequestBody SysDepartmentEntity entity)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        try {
            if(null==entity.getId()||(null!=entity.getId()&&!entity.getCode().equals(entity.getOldCode()))) {
                boolean checkResult=departmentInfoService.checkCode(entity);
                if(checkResult) {
                    json.put("IsSuccess",true);
                    json.put("Message", "检查通过");
                }else {
                    json.put("IsSuccess",false);
                    json.put("Message", "检查不通过");
                }
            }else {
                json.put("IsSuccess",true);
                json.put("Message", "检查通过");
            }
        }catch (Exception e) {
            e.printStackTrace();
            json.put("IsSuccess", false);
            json.put("Message", "检查不通过");
        }
        try {
            response.getWriter().write(json.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
            json.put("IsSuccess", false);
            json.put("Message", "检查不通过");
        }
        return null;
    }

}
