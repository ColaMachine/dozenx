/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.auth.sysRole.service;

import com.dozenx.util.DateUtil;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.core.auth.sysRole.bean.SysRole;
import com.dozenx.web.core.auth.sysRole.dao.SysRoleMapper;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ErrorMessage;
import com.dozenx.web.core.log.ResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sysRoleService")
public class SysRoleService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(SysRoleService.class);
    @Resource
    private SysRoleMapper sysRoleMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<SysRole> listByParams4Page(Map<String,Object> params) {
        return sysRoleMapper.listByParams4Page(params);
    }

    /**
     * 根据条件查询 对于角色来说没什么用处 角色大多是全部提取就可以了
     * @param params
     * @return
     */
    public List<SysRole> listByParams(Map<String,Object> params) {
        return sysRoleMapper.listByParams(params);
    }

    /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
        return sysRoleMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param SysRole
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(SysRole sysRole) {
        //判断是更新还是插入
        if (sysRole.getId()!=null &&  this.selectByPrimaryKey(sysRole.getId())==null) {
            return ResultUtil.getResult(10203002, ErrorMessage.getErrorMsg("err.db.not.find.msg"));
        }
        if (sysRole.getId()==null ||  this.selectByPrimaryKey(sysRole.getId())==null) {
            sysRole.setStatus(1);
            sysRole.setCreatetime(DateUtil.getNowTimeStamp());
            sysRoleMapper.insert(sysRole);
        } else {
            sysRoleMapper.updateByPrimaryKeySelective(sysRole);
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
        sysRoleMapper.deleteByPrimaryKey(id);
    }
    /**
     * 说明:根据主键获取数据
     * description:delete by key
     * @param id
     * @return void
     * @author dozen.zhang
     * @date 2015年12月27日下午10:56:38
     */
    public SysRole selectByPrimaryKey(Long id){
        return sysRoleMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            sysRoleMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }

    public List<SysRole> listByUserId(Long userId) {
        return sysRoleMapper.listByUserId(userId);
    }
}
