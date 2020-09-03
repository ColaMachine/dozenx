/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明:
 */

package com.dozenx.web.core.auth.sysRolePermission.service;

import com.dozenx.common.util.MapUtils;
import com.dozenx.common.util.StringUtil;
import com.dozenx.swagger.annotation.APIResponse;
import com.dozenx.web.core.auth.sysPermission.bean.SysPermission;
import com.dozenx.web.core.auth.sysPermission.dao.SysPermissionMapper;
import com.dozenx.web.core.auth.sysPermission.service.SysPermissionService;
import com.dozenx.web.core.auth.sysRole.bean.SysRole;
import com.dozenx.web.core.auth.sysRole.dao.SysRoleMapper;
import com.dozenx.web.core.auth.sysRolePermission.bean.SysRolePermission;
import com.dozenx.web.core.auth.sysRolePermission.dao.SysRolePermissionMapper;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.rules.Digits;
import com.dozenx.web.core.rules.Required;
import com.dozenx.web.core.rules.Rule;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sysRolePermissionService")
public class SysRolePermissionService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(SysRolePermissionService.class);
    @Resource
    private SysRolePermissionMapper sysRolePermissionMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysPermissionMapper sysPermissionMapper;

    /**
     * 说明:list by page and params根据参数返回列表
     *
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<SysRolePermission> listByParams4Page(HashMap params) {
        return sysRolePermissionMapper.listByParams4Page(params);
    }

    public List<SysRolePermission> listByParams(HashMap params) {
        return sysRolePermissionMapper.listByParams(params);
    }

    /**
     * 说明:countByParams 根据参数提取个数
     *
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
        return sysRolePermissionMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param SysRolePermission
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(SysRolePermission sysRolePermission) {

        sysRolePermissionMapper.insert(sysRolePermission);

        return ResultUtil.getSuccResult();
    }

    /**
     * 多项关联保存
     *
     * @return
     */
    public ResultDTO msave(String rids, String pids) {
        if (StringUtil.isBlank(rids)) {
            return ResultUtil.getResult(101, "参数错误");
        }

        String[] ridAry = rids.split(",");
        String[] pidAry = pids.split(",");
        Integer[] ridAryReal = new Integer[ridAry.length];
        Integer[] pidAryReal = new Integer[pidAry.length];
        for (int i = 0; i < ridAry.length; i++) {
            if (!StringUtil.checkNumeric(ridAry[i])) {
                return ResultUtil.getResult(101, "参数错误");
            }
            ridAryReal[i] = Integer.valueOf(ridAry[i]);
        }
        if (StringUtil.isBlank(pids)) {
            pidAryReal = null;
            pidAry = null;
        }
        if (pidAry != null)
            for (int i = 0; i < pidAry.length; i++) {
                if (!StringUtil.checkNumeric(pidAry[i])) {
                    return ResultUtil.getResult(101, "参数错误");
                }
                pidAryReal[i] = Integer.valueOf(pidAry[i]);
            }
        //验证父亲id 正确性 是否存在
        if (ridAryReal != null)
            for (int i = 0; i < ridAryReal.length; i++) {
                //
                SysRole sysRole = sysRoleMapper.selectByPrimaryKey(ridAryReal[i]);
                if (sysRole == null) {
                    return ResultUtil.getResult(101, "数据不存在");
                }
                //查询的数据不存在
            }
        if (pidAryReal != null)
            for (int i = 0; i < pidAryReal.length; i++) {
                SysPermission sysPermission = sysPermissionMapper.selectByPrimaryKey(pidAryReal[i]);
                //查询的数据不存在
                if (sysPermission == null) {
                    return ResultUtil.getResult(101, "数据不存在");
                }
            }
        HashMap params = new HashMap();
        //验证子id 正确性 是否存在
        if (pidAryReal != null)
            for (int i = 0; i < ridAryReal.length; i++) {
                for (int j = 0; j < pidAryReal.length; j++) {
                    SysRolePermission sysRolePermission = new SysRolePermission();
                    Integer rid = ridAryReal[i];
                    Integer pid = pidAryReal[j];
                    //查找是否已经有关联数据了

                    params.put("pid", pid);
                    params.put("rid", rid);
                    int count = sysRolePermissionMapper.countByParams(params);
                    if (count > 0) continue;
                    sysRolePermission.setPermissionId(pid);
                    sysRolePermission.setRoleId(rid);
                    sysRolePermissionMapper.insert(sysRolePermission);
                }
            }
        //删除多余的数据
        params.clear();
        params.put("permissionIds", pidAryReal);
        params.put("roleIds", ridAryReal);
        sysRolePermissionMapper.deleteExtra(params);
        //delete from SysUserRole where uid in (1,2,3,4,5) and rid not in(1,2,3)
        return ResultUtil.getSuccResult();
    }


    public ResultDTO batchUpdate(Integer[] parentIds, Integer[] childIds) {
        //验证父亲id 正确性 是否存在
        if (parentIds != null)
            for (int i = 0; i < parentIds.length; i++) {
                //
                SysRole sysUser = sysRoleMapper.selectByPrimaryKey(parentIds[i]);
                if (sysUser == null) {
                    return ResultUtil.getResult(101, "数据不存在");
                }
                //查询的数据不存在
            }
        if (childIds != null)
            for (int i = 0; i < childIds.length; i++) {
                SysPermission sysPermission = sysPermissionMapper.selectByPrimaryKey(childIds[i]);
                //查询的数据不存在
                if (sysPermission == null) {
                    return ResultUtil.getResult(101, "数据不存在");
                }
            }

        //验证子id 正确性 是否存在
        SysRolePermission sysUserRole = new SysRolePermission();
        if (childIds != null)
            for (int i = 0; i < parentIds.length; i++) {
                for (int j = 0; j < childIds.length; j++) {

                    Integer parentId = parentIds[i];
                    Integer childId = childIds[j];
                    //查找是否已经有关联数据了
                    sysUserRole.setRoleId(parentId);
                    sysUserRole.setPermissionId(childId);

                    int count = sysRolePermissionMapper.count(sysUserRole);
                    if (count > 0) continue;//如果有记录了就不保存
                    sysUserRole.setPermissionId(childId);
                    sysUserRole.setRoleId(parentId);
                    sysRolePermissionMapper.insert(sysUserRole);
                }
            }
        //删除多余的数据
        HashMap params = new HashMap(2);

        params.put("permissionIds", childIds);
        params.put("roleIds", parentIds);
        sysRolePermissionMapper.deleteExtra(params);//删除额外的数据
        return ResultUtil.getSuccResult();
    }


    /**
     * 批量更新角色所属权限
     * @param bodyParam
     * @return
     * @throws Exception
     */
    public ResultDTO updateRolePermissions( @RequestBody(required=true) Map<String,Object> bodyParam) throws Exception {
        Integer roleId = MapUtils.getInteger(bodyParam,"roleId");
        ValidateUtil.valid(roleId,"userId",new Rule[]{new Required(),new Digits(10,0)});

        Object obj = bodyParam.get("permissionIds");
        Integer[] permissionIds;
        if(obj==null){
            permissionIds=new Integer[]{};
        }else{
            List<Number> ary = (ArrayList<Number>)bodyParam.get("permissionIds");//bodyoaran 只不过的参数是 arryList<Double>格式的
            permissionIds = new Integer[ary.size()];
            for(int i=0;i<ary.size();i++){
                permissionIds[i] = ary.get(i).intValue();
            }

        }
        return batchUpdate(new Integer[]{ roleId}, permissionIds);
    }

    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * 根据角色的id得到角色所拥有的权限列表
     * @param roleId
     * @return
     */
    public List<SysPermission>  tree(Integer roleId){

        HashMap<String,Object> params =new HashMap<String,Object>();
        params.put("status",1);//删除的不要展示
        List<SysPermission> sysPermissions = sysPermissionService.listByParams(params);


        params.put("roleId",roleId);
        params.put("status",1);//删除的不要展示
        List<SysRolePermission> hasPermissions= listByParams(params);


        List<SysPermission> finalList = new ArrayList<SysPermission>();//最终返回前台的list
        //列表结构 后续组成树
        for(int i=0,length=sysPermissions.size();i<length;i++){
            SysPermission sysPermission = sysPermissions.get(i);
            sysPermission.setChecked(false);
            for(int j=0;j<hasPermissions.size();j++){
                SysRolePermission sysRolePermission  = hasPermissions.get(j);
                if(sysPermission.getId() == sysRolePermission.getPermissionId()){
                    sysPermission.setChecked(true);
                    break;
                }
            }

        }
        params =new HashMap<String,Object>();
        //组装成树状结构
        for(int i=0,length=sysPermissions.size();i<length;i++){//倒序 方便找到后删除
            SysPermission sysPermission = sysPermissions.get(i);

            if(sysPermission.getPid()==0){
                finalList.add(sysPermission);
                sysPermission.childs=new ArrayList<>();
                for(int j=0;j<length;j++){//倒序 方便找到后删除
                    SysPermission child = sysPermissions.get(j);//遍历所有的项目查找所有子项
                    if(child.getPid() == sysPermission.getId()){
                        sysPermission.childs.add(child);//塞入到childs中 并从集合中删除
                        // sysMenuTree.remove(j);
                    }
                }
                // sysMenuTree.remove(i);
            }
        }
        return sysPermissions;
    }
}
