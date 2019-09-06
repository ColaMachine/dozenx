/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.auth.sysUserPermission.service;

import com.dozenx.web.util.ResultUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.auth.sysPermission.bean.SysPermission;
import com.dozenx.web.core.auth.sysPermission.dao.SysPermissionMapper;
import com.dozenx.web.core.auth.sysUser.bean.SysUser;
import com.dozenx.web.core.auth.sysUser.dao.SysUserMapper;
import com.dozenx.web.core.auth.sysUserPermission.bean.SysUserPermission;
import com.dozenx.web.core.auth.sysUserPermission.dao.SysUserPermissionMapper;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("sysUserPermissionService")
public class SysUserPermissionService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(SysUserPermissionService.class);
    @Resource
    private SysUserPermissionMapper sysUserPermissionMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysPermissionMapper sysPermissionMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<SysUserPermission> listByParams4Page(HashMap params) {
        return sysUserPermissionMapper.listByParams4Page(params);
    }
    public List<SysUserPermission> listByParams(HashMap params) {
        return sysUserPermissionMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return sysUserPermissionMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param SysUserPermission
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(SysUserPermission sysUserPermission) {
        // 进行字段验证
      /* ValidateUtil<SysUserPermission> vu = new ValidateUtil<SysUserPermission>();
        ResultDTO result = vu.valid(sysUserPermission);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (sysUserPermission.getId()==null ||  this.selectByPrimaryKey(sysUserPermission.getId())==null) {

            sysUserPermissionMapper.insert(sysUserPermission);
        } else {
            sysUserPermissionMapper.updateByPrimaryKeySelective(sysUserPermission);
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
    public void delete(Long  id){
        sysUserPermissionMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public SysUserPermission selectByPrimaryKey(Long id){
       return sysUserPermissionMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            sysUserPermissionMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
     /**
         * 多项关联保存
         * @param uids
         * @param rids
         * @return
         */
        public ResultDTO msave(String uids,String pids) {
            if(StringUtil.isBlank(uids)){
                return ResultUtil.getResult(101,"参数错误");
            }

            String[] uidAry= uids.split(",");
            String[] pidAry=pids.split(",");
            Long[] uidAryReal =new  Long[uidAry.length];
            Long[] pidAryReal =new  Long[pidAry.length];
            for(int i=0;i<uidAry.length;i++){
                if(!StringUtil.checkNumeric(uidAry[i])){
                    return ResultUtil.getResult(101,"参数错误");
                }
                uidAryReal[i]=Long.valueOf(uidAry[i]);
            }
            if(StringUtil.isBlank(pids)){
                pidAryReal=null;
                 pidAry=null;
            }
            if(pidAry!=null)
            for(int i=0;i<pidAry.length;i++){
                if(!StringUtil.checkNumeric(pidAry[i])){
                    return ResultUtil.getResult(101,"参数错误");
                }
                pidAryReal[i]=Long.valueOf(pidAry[i]);
            }
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
             if(pidAryReal!=null)
            for(int i=0;i<pidAryReal.length;i++){
                 SysPermission sysPermission = sysPermissionMapper.selectByPrimaryKey(pidAryReal[i]);
                //查询的数据不存在
                if(sysPermission==null ){
                    return ResultUtil.getResult(101,"数据不存在");
                }
            }
             HashMap params =new HashMap();
            //验证子id 正确性 是否存在
             if(pidAryReal!=null)
            for(int i=0;i<uidAryReal.length;i++){
                for(int j=0;j<pidAryReal.length;j++){
                   SysUserPermission sysUserPermission =new  SysUserPermission();
                    Long uid =uidAryReal[i];
                    Long pid =pidAryReal[j];
                    //查找是否已经有关联数据了

                    params.put("pid",pid);
                    params.put("uid",uid);
                    int count = sysUserPermissionMapper.countByParams(params);
                    if(count>0)continue;
                    sysUserPermission.setPid(pid);
                    sysUserPermission.setUid(uid);
                    sysUserPermissionMapper.insert(sysUserPermission);
                }
            }
            //删除多余的数据
            params.clear();
            params.put("pids",pidAryReal);
            params.put("uids",uidAryReal);
            sysUserPermissionMapper.deleteExtra(params);
            //delete from SysUserRole where uid in (1,2,3,4,5) and rid not in(1,2,3)
            return ResultUtil.getSuccResult();
        }
}
