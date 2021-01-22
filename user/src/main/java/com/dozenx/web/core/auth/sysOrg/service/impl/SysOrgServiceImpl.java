/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明:
 */

package com.dozenx.web.core.auth.sysOrg.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.core.auth.sysOrg.bean.SysOrg;
import com.dozenx.web.core.auth.sysOrg.dao.SysOrgMapper;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.common.util.UUIDUtil;
import com.dozenx.web.util.ValidateUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.auth.sysOrg.service.SysOrgService;

@Service("sysOrgService")
public class SysOrgServiceImpl extends BaseService implements SysOrgService {
    private static final Logger logger = LoggerFactory
            .getLogger(SysOrgService.class);
    @Resource
    private SysOrgMapper sysOrgMapper;

    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<SysOrg> listByParams4Page(HashMap params) {
        return sysOrgMapper.listByParams4Page(params);
    }

    public List<SysOrg> listByParams(HashMap params) {
        return sysOrgMapper.listByParams(params);
    }

    /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
        return sysOrgMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param SysOrg
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(SysOrg sysOrg) {
        // 进行字段验证
      /* ValidateUtil<SysOrg> vu = new ValidateUtil<SysOrg>();
        ResultDTO result = vu.valid(sysOrg);
        if (result.getR() != 1) {
            return result;
        }*/
        //逻辑业务判断判断
        //判断是否有uq字段

        //判断是更新还是插入
        if (sysOrg.getId() == null || this.selectByPrimaryKey(sysOrg.getId()) == null) {
            sysOrg.setCreateTime(new Timestamp(new Date().getTime()));

            sysOrgMapper.insert(sysOrg);
        } else {
            sysOrgMapper.updateByPrimaryKeySelective(sysOrg);
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
    public void delete(Long id) {
        sysOrgMapper.deleteByPrimaryKey(id);
    }

    /**
     * 说明:根据主键获取数据
     * description:delete by key
     * @param id
     * @return void
     * @author dozen.zhang
     * @date 2015年12月27日下午10:56:38
     */
    public SysOrg selectByPrimaryKey(Long id) {
        return sysOrgMapper.selectByPrimaryKey(id);
    }

    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for (int i = 0; i < idAry.length; i++) {
            sysOrgMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }


    @Override
    public ResultDTO insertList(List<SysOrg> list) {
        sysOrgMapper.insertBatch(list);
        return null;
    }


    public String getOrgNameByCamera(Long camera){
        return sysOrgMapper.getOrgNameByCamera(camera);
    }
}
