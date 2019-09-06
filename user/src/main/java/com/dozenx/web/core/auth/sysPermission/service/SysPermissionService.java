/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.auth.sysPermission.service;

import com.dozenx.web.util.ResultUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.auth.sysPermission.bean.SysPermission;
import com.dozenx.web.core.auth.sysPermission.dao.SysPermissionMapper;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sysPermissionService")
public class SysPermissionService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(SysPermissionService.class);
    @Resource
    private SysPermissionMapper sysPermissionMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<SysPermission> listByParams4Page(Map<String,Object> params) {
        return sysPermissionMapper.listByParams4Page(params);
    }

    /**
     * 查询所有的
     * @param params
     * @return
     */
    public List<SysPermission> listByParams(Map<String ,Object> params) {
        return sysPermissionMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return sysPermissionMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param SysPermission
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(SysPermission sysPermission) {
        // 进行字段验证
      /* ValidateUtil<SysPermission> vu = new ValidateUtil<SysPermission>();
        ResultDTO result = vu.valid(sysPermission);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
               HashMap params =new HashMap();
        params.put("code",sysPermission.getCode());
        int count = sysPermissionMapper.countByOrParams(params);
        if(StringUtil.isNull(sysPermission.getId())&& count>0||count>1 ){
            return ResultUtil.getResult(302,"字段唯一不能重复");
        }

       //判断是更新还是插入
        if (sysPermission.getId()==null ||  this.selectByPrimaryKey(sysPermission.getId())==null) {

            sysPermissionMapper.insert(sysPermission);
        } else {
            sysPermissionMapper.updateByPrimaryKeySelective(sysPermission);
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
        sysPermissionMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public SysPermission selectByPrimaryKey(Long id){
       return sysPermissionMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            sysPermissionMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
}
