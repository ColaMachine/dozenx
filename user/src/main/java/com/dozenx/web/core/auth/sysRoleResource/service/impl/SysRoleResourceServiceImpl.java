/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.auth.sysRoleResource.service.impl;
import java.sql.Timestamp;
import java.util.*;

import javax.annotation.Resource;

import com.dozenx.common.util.MapUtils;
import com.dozenx.web.core.auth.sysPermission.bean.SysPermission;
import com.dozenx.web.core.auth.sysResource.bean.SimpleSysResource;
import com.dozenx.web.core.auth.sysResource.bean.SysResource;
import com.dozenx.web.core.auth.sysResource.dao.SysResourceMapper;
import com.dozenx.web.core.auth.sysRole.bean.SysRole;
import com.dozenx.web.core.auth.sysRole.dao.SysRoleMapper;
import com.dozenx.web.core.auth.sysRolePermission.bean.SysRolePermission;
import com.dozenx.web.core.rules.Digits;
import com.dozenx.web.core.rules.Required;
import com.dozenx.web.core.rules.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.core.auth.sysRoleResource.bean.SysRoleResource;
import com.dozenx.web.core.auth.sysRoleResource.dao.SysRoleResourceMapper;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.common.util.UUIDUtil;
import com.dozenx.web.util.ValidateUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseService;
import  com.dozenx.web.core.auth.sysRoleResource.bean.SysRoleResource;
import  com.dozenx.web.core.auth.sysRoleResource.dao.SysRoleResourceMapper;

import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.auth.sysRoleResource.service.SysRoleResourceService;

@Service("sysRoleResourceService")
public class SysRoleResourceServiceImpl extends BaseService implements SysRoleResourceService {
    private static final Logger logger = LoggerFactory
            .getLogger(SysRoleResourceService.class);
    @Resource
    private SysRoleResourceMapper sysRoleResourceMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysResourceMapper sysResourceMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<SysRoleResource> listByParams4Page(HashMap params) {
        return sysRoleResourceMapper.listByParams4Page(params);
    }
    public List<SysRoleResource> listByParams(HashMap params) {
        return sysRoleResourceMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return sysRoleResourceMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param SysRoleResource
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(SysRoleResource sysRoleResource) {
        // 进行字段验证
      /* ValidateUtil<SysRoleResource> vu = new ValidateUtil<SysRoleResource>();
        ResultDTO result = vu.valid(sysRoleResource);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (sysRoleResource.getId()==null ||  this.selectByPrimaryKey(sysRoleResource.getId())==null) {

            sysRoleResourceMapper.insert(sysRoleResource);
        } else {
            sysRoleResourceMapper.updateByPrimaryKeySelective(sysRoleResource);
        }
        return ResultUtil.getSuccResult();
    }
    /**
    * 说明:根据主键删除数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public void delete(Integer  id){
        sysRoleResourceMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public SysRoleResource selectByPrimaryKey(Integer id){
       return sysRoleResourceMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Integer[] idAry) {
        for(int i=0;i<idAry.length;i++){
            sysRoleResourceMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
     /**
         * 多项关联保存
         * @param roleIds
         * @param resourceIds
         * @return
         */
        public ResultDTO msave(String roleIds,String resourceIds) {
            if(StringUtil.isBlank(roleIds)){
                return ResultUtil.getResult(101,"参数错误");
            }

            String[] roleIdAry= roleIds.split(",");
            String[] resourceIdAry=resourceIds.split(",");
            Integer[] roleIdAryReal =new  Integer[roleIdAry.length];
            Integer[] resourceIdAryReal =new  Integer[resourceIdAry.length];
            for(int i=0;i<roleIdAry.length;i++){
                if(!StringUtil.checkNumeric(roleIdAry[i])){
                    return ResultUtil.getResult(101,"参数错误");
                }
                roleIdAryReal[i]=Integer.valueOf(roleIdAry[i]);
            }
            if(StringUtil.isBlank(resourceIds)){
                resourceIdAryReal=null;
                 resourceIdAry=null;
            }
            if(resourceIdAry!=null)
            for(int i=0;i<resourceIdAry.length;i++){
                if(!StringUtil.checkNumeric(resourceIdAry[i])){
                    return ResultUtil.getResult(101,"参数错误");
                }
                resourceIdAryReal[i]=Integer.valueOf(resourceIdAry[i]);
            }


            return msave(roleIdAryReal,resourceIdAryReal);


        }

    @Override
    public ResultDTO msave(Integer roleIds, Integer resourceIds) {
        return null;
    }


    /**
         * 多项关联保存
         * @param roleIdAryReal
         * @param resourceIdAryReal
         * @return
         */
        public ResultDTO msave( Integer[] roleIdAryReal, Integer[] resourceIdAryReal) {
            //验证父亲id 正确性 是否存在
             if(roleIdAryReal!=null)
            for(int i=0;i< roleIdAryReal.length;i++){
                //
                SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleIdAryReal[i]);
                if(sysRole==null ){
                    return ResultUtil.getResult(101,"数据不存在");
                }
                //查询的数据不存在
            }
             if(resourceIdAryReal!=null)
            for(int i=0;i<resourceIdAryReal.length;i++){
                 SysResource sysResource = sysResourceMapper.selectByPrimaryKey(resourceIdAryReal[i]);
                //查询的数据不存在
                if(sysResource==null ){
                    return ResultUtil.getResult(101,"数据不存在");
                }
            }
             HashMap params =new HashMap();
            //验证子id 正确性 是否存在
             if(resourceIdAryReal!=null)
            for(int i=0;i<roleIdAryReal.length;i++){
                for(int j=0;j<resourceIdAryReal.length;j++){
                   SysRoleResource sysRoleResource =new  SysRoleResource();
                    Integer roleId =roleIdAryReal[i];
                    Integer resourceId =resourceIdAryReal[j];
                    //查找是否已经有关联数据了

                    params.put("resourceId",resourceId);
                    params.put("roleId",roleId);
                    int count = sysRoleResourceMapper.countByParams(params);
                    if(count>0)continue;
                    sysRoleResource.setResourceId(resourceId);
                    sysRoleResource.setRoleId(roleId);
                    sysRoleResourceMapper.insert(sysRoleResource);
                }
            }
            //删除多余的数据
            params.clear();
            params.put("resourceIds",resourceIdAryReal);
            params.put("roleIds",roleIdAryReal);
            sysRoleResourceMapper.deleteExtra(params);
            //delete from SysUserRole where uid in (1,2,3,4,5) and rid not in(1,2,3)
            return ResultUtil.getSuccResult();
        }



    @Override
    public ResultDTO insertList(List<SysRoleResource> list) {
       sysRoleResourceMapper.insertBatch(list);
        return null;
    }



    /**
     * 根据角色的id得到角色所拥有的权限列表
     * 2020年9月8日 做了一个兼容 返回的格式为
     * @param roleId
     * @return
     */
    public List<SimpleSysResource>  tree(Integer roleId){

        HashMap<String,Object> params =new HashMap<String,Object>();
        params.put("status",1);//删除的不要展示
        List<SimpleSysResource> sysResources = sysResourceMapper.listSimpleByParams(params);

        params.put("roleId",roleId);
        params.put("status",1);//删除的不要展示
        List<SysRoleResource> hasPermissions= listByParams(params);

        List<SimpleSysResource> finalList = new ArrayList<SimpleSysResource>();//最终返回前台的list
        //列表结构 后续组成树
        for(int i=0,length=sysResources.size();i<length;i++){
            SimpleSysResource sysResource = sysResources.get(i);
            sysResource.setChecked(false);
            for(int j=0;j<hasPermissions.size();j++){
                SysRoleResource sysRolePermission  = hasPermissions.get(j);
                if(sysResource.getId() == sysRolePermission.getResourceId()){
                    sysResource.setChecked(true);
                    break;
                }
            }
        }
        params =new HashMap<String,Object>();
        //组装成树状结构
        for(int i=0,length=sysResources.size();i<length;i++){//倒序 方便找到后删除
            SimpleSysResource sysPermission = sysResources.get(i);

            if(sysPermission.getPid()==0){
                finalList.add(sysPermission);
                sysPermission.childs=new ArrayList<>();
                for(int j=0;j<length;j++){//倒序 方便找到后删除
                    SimpleSysResource child = sysResources.get(j);//遍历所有的项目查找所有子项
                    if(child.getPid() == sysPermission.getId()){
                        sysPermission.childs.add(child);//塞入到childs中 并从集合中删除
                        // sysMenuTree.remove(j);
                    }
                }
                // sysMenuTree.remove(i);
            }
        }
        return finalList;
    }


    //根据角色批量更新资源
    public   Map<String ,Object>  roleupdateRolePermissions(Map<String, Object> bodyParam) throws Exception {

        Map<String ,Object> returnMap=new HashMap<>();
        //第一步 获取角色
        Integer roleId = MapUtils.getInteger(bodyParam, "roleId");
        HashMap paramsMap = new HashMap();
        paramsMap.put("id", roleId);
        //第二步 获取自己的
//        List<SysRole> childsysRoles = sysRoleMapper.listByParams(paramsMap);
//        SysRole childSyssRole =childsysRoles.get(0);
//        Integer pid = childSyssRole.getpId();
        //第三步拿到 父id
//        if(pid!=0){
//            //3.1 获取父角色
//            paramsMap.put("id", pid);
//            List<SyssRole> prentsysRoles = syssRoleMapper.getListByPid(pid);
//            SyssRole prentSyssRole = prentsysRoles.get(0);
//            Integer prentRoleId = prentSyssRole.getId();
//            //3.2 获取父角色所有权限
//            HashMap prentRoleMap = new HashMap();
//            prentRoleMap.put("roleId", prentRoleId);
//            prentRoleMap.put("status", 1);
//            List<SysRolePermission> prentPermissions = listByParams(prentRoleMap);
//
//            //3.3 获取所有角色的权限
//            List<Integer> permisList=new ArrayList<>();
//            for (SysRolePermission sysRolePermission:prentPermissions) {
//                permisList.add(sysRolePermission.getPermissionId());
//            }
//
//
//            //3.41 循环两个权限 判断子权限是否在 父权限下面
//            List<Integer> ary = (ArrayList) bodyParam.get("permissionIds");
//            List<Integer> childs=new ArrayList<>();
//            for (Integer i:ary) {
//                childs.add(i);
//            }
//
//
//            ary.removeAll(permisList);
//
//            //3.5 如果不在父权限下面
//            if(ary.size()<0){
//                returnMap.put("right",1);
//                returnMap.put("ids",ary);
//                return returnMap;
//            }
//        }
      //  ValidateUtil.valid(roleId, "userId", new Rule[]{new Required(), new Digits(10, 0)});
        Object obj = bodyParam.get("permissionIds");
        Integer[] permissionIds;
        if (obj == null) {
            permissionIds = new Integer[0];
        } else {
            List<Number> ary = (ArrayList)obj;
            permissionIds = new Integer[ary.size()];

            for (int i = 0; i < ary.size(); ++i) {
                permissionIds[i] = ((Number) ary.get(i)).intValue();
            }
        }

        this.batchUpdate(new Integer[]{roleId}, permissionIds);
        returnMap.put("right",0);
        return returnMap;

    }





    public ResultDTO batchUpdate(Integer[] parentIds, Integer[] childIds) {
        //验证父亲id 正确性 是否存在
        if (parentIds != null){
            for (int i = 0; i < parentIds.length; i++) {
                //
                SysRole sysUser = sysRoleMapper.selectByPrimaryKey(parentIds[i]);
                if (sysUser == null) {
                    return ResultUtil.getResult(101, "数据不存在");
                }
                //查询的数据不存在
            }
        }

        if (childIds != null){
            for (int i = 0; i < childIds.length; i++) {
                SysResource sysPermission = sysResourceMapper.selectByPrimaryKey(childIds[i]);
                //查询的数据不存在
                if (sysPermission == null) {
                    return ResultUtil.getResult(101, "数据不存在");
                }
            }
        }


        //验证子id 正确性 是否存在

        if (childIds != null){
            for (int i = 0; i < parentIds.length; i++) {
                for (int j = 0; j < childIds.length; j++) {
                    SysRoleResource sysUserRole = new SysRoleResource();
                    Integer parentId = parentIds[i];
                    Integer childId = childIds[j];
                    //查找是否已经有关联数据了
                    sysUserRole.setRoleId(parentId);
                    sysUserRole.setResourceId(childId);

                    int count = sysRoleResourceMapper.count(sysUserRole);
                    if (count > 0){
                        continue;//如果有记录了就不保存
                    }
                    sysUserRole.setResourceId(childId);
                    sysUserRole.setRoleId(parentId);
                    sysRoleResourceMapper.insert(sysUserRole);
                }
            }
        }

        //删除多余的数据
        HashMap params = new HashMap(2);

        params.put("resourceIds", childIds);
        params.put("roleIds", parentIds);
        sysRoleResourceMapper.deleteExtra(params);//删除额外的数据
        return ResultUtil.getSuccResult();
    }
}
