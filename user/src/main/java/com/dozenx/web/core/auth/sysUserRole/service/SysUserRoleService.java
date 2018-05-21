/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.auth.sysUserRole.service;

import com.dozenx.web.util.ResultUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.auth.sysRole.bean.SysRole;
import com.dozenx.web.core.auth.sysRole.dao.SysRoleMapper;
import com.dozenx.web.core.auth.sysUser.bean.SysUser;
import com.dozenx.web.core.auth.sysUser.dao.SysUserMapper;
import com.dozenx.web.core.auth.sysUserRole.bean.SysUserRole;
import com.dozenx.web.core.auth.sysUserRole.dao.SysUserRoleMapper;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("sysUserRoleService")
public class SysUserRoleService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(SysUserRoleService.class);
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;


     /**
         * 多项关联保存
         * @param userIds
         * @param roleIds
         * @return
         */
        public ResultDTO msave(String userIds,String roleIds) {
            if(StringUtil.isBlank(userIds)){
                return ResultUtil.getResult(101,"参数错误");
            }

            String[] uidAry= userIds.split(",");
            String[] roleidAry=roleIds.split(",");
            Long[] uidAryReal =new  Long[uidAry.length];
            Long[] roleidAryReal =new  Long[roleidAry.length];
            for(int i=0;i<uidAry.length;i++){
                if(!StringUtil.checkNumeric(uidAry[i])){
                    return ResultUtil.getResult(101,"参数错误");
                }
                uidAryReal[i]=Long.valueOf(uidAry[i]);
            }
            if(StringUtil.isBlank(roleIds)){
                roleidAryReal=null;
                 roleidAry=null;
            }
            if(roleidAry!=null)
            for(int i=0;i<roleidAry.length;i++){
                if(!StringUtil.checkNumeric(roleidAry[i])){
                    return ResultUtil.getResult(101,"参数错误");
                }
                roleidAryReal[i]=Long.valueOf(roleidAry[i]);
            }
            batchUpdate(uidAryReal,roleidAryReal);
            //delete from SysUserRole where uid in (1,2,3,4,5) and rid not in(1,2,3)
            return ResultUtil.getSuccResult();
        }

    public  ResultDTO batchUpdate(Long[] uidAryReal,Long[] roleidAryReal){
        //验证父亲id 正确性 是否存在
        if(uidAryReal!=null)
            for(int i=0;i< uidAryReal.length;i++){
                //
                SysUser sysUser = sysUserMapper.selectByPrimaryKey(uidAryReal[i]);
                if(sysUser==null ){
                    return ResultUtil.getResult(101,"数据不存在");
                }
                //查询的数据不存在
            }
        if(roleidAryReal!=null)
            for(int i=0;i<roleidAryReal.length;i++){
                SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleidAryReal[i]);
                //查询的数据不存在
                if(sysRole==null ){
                    return ResultUtil.getResult(101,"数据不存在");
                }
            }

        //验证子id 正确性 是否存在
        SysUserRole sysUserRole =new SysUserRole();
        if(roleidAryReal!=null)
            for(int i=0;i<uidAryReal.length;i++){
                for(int j=0;j<roleidAryReal.length;j++){

                    Long uid =uidAryReal[i];
                    Long roleid =roleidAryReal[j];
                    //查找是否已经有关联数据了
                    sysUserRole.setUserId(uid);
                    sysUserRole.setRoleId(roleid);

                    int count = sysUserRoleMapper.count(sysUserRole);
                    if(count>0)continue;//如果有记录了就不保存
                    sysUserRole.setRoleId(roleid);
                    sysUserRole.setUserId(uid);
                    sysUserRoleMapper.insert(sysUserRole);
                }
            }
        //删除多余的数据
        HashMap params =new HashMap(2);

        params.put("roleIds",roleidAryReal);
        params.put("userIds",uidAryReal);
        sysUserRoleMapper.deleteExtra(params);//删除额外的数据
        return ResultUtil.getSuccResult();
    }

}
