/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.auth.sysUserDepart.service;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.dozenx.web.core.auth.sysDepart.bean.SysDepart;
import com.dozenx.web.core.auth.sysDepart.dao.SysDepartMapper;
import com.dozenx.web.core.auth.sysUser.bean.SysUser;
import com.dozenx.web.core.auth.sysUser.dao.SysUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.core.auth.sysUserDepart.bean.SysUserDepart;
import com.dozenx.web.core.auth.sysUserDepart.dao.SysUserDepartMapper;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.util.UUIDUtil;
import com.dozenx.web.util.ValidateUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseService;
import java.util.StringTokenizer;
import  com.dozenx.web.core.auth.sysUserDepart.bean.SysUserDepart;
import  com.dozenx.web.core.auth.sysUserDepart.dao.SysUserDepartMapper;

import com.dozenx.web.core.log.ResultDTO;

@Service("sysUserDepartService")
public class SysUserDepartService extends BaseService {
	private static final Logger logger = LoggerFactory
			.getLogger(SysUserDepartService.class);
	@Resource
	private SysUserDepartMapper sysUserDepartMapper;
	@Resource
	private SysDepartMapper sysDepartMapper;
	@Resource
	private SysUserMapper sysUserMapper;
	/*
     * 说明:
     * @param SysUserDepart
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
	public ResultDTO save(SysUserDepart sysUserDepart) {
		// 进行字段验证
      /* ValidateUtil<SysUserDepart> vu = new ValidateUtil<SysUserDepart>();
        ResultDTO result = vu.valid(sysUserDepart);
        if (result.getR() != 1) {
            return result;
        }*/
		//逻辑业务判断判断
		//判断是否有uq字段

		//判断是更新还是插入

			sysUserDepartMapper.insert(sysUserDepart);
		return ResultUtil.getSuccResult();
	}
	/**
	 * 多项关联保存
	 * @param uids
	 * @param rids
	 * @return
	 */
	public ResultDTO msave(String departIds,String userIds) {
		if(StringUtil.isBlank(departIds)){
			return ResultUtil.getResult(101,"参数错误");
		}

		String[] departIdAry= departIds.split(",");
		String[] userIdAry=userIds.split(",");
		Long[] departIdAryReal =new  Long[departIdAry.length];
		Long[] userIdAryReal =new  Long[userIdAry.length];
		for(int i=0;i<departIdAry.length;i++){
			if(!StringUtil.checkNumeric(departIdAry[i])){
				return ResultUtil.getResult(101,"参数错误");
			}
			departIdAryReal[i]=Long.valueOf(departIdAry[i]);
		}
		if(StringUtil.isBlank(userIds)){
			userIdAryReal=null;
			userIdAry=null;
		}
		if(userIdAry!=null)
			for(int i=0;i<userIdAry.length;i++){
				if(!StringUtil.checkNumeric(userIdAry[i])){
					return ResultUtil.getResult(101,"参数错误");
				}
				userIdAryReal[i]=Long.valueOf(userIdAry[i]);
			}
		//验证父亲id 正确性 是否存在
		if(departIdAryReal!=null)
			for(int i=0;i< departIdAryReal.length;i++){
				//
				SysDepart sysDepart = sysDepartMapper.selectByPrimaryKey(departIdAryReal[i]);
				if(sysDepart==null ){
					return ResultUtil.getResult(101,"数据不存在");
				}
				//查询的数据不存在
			}
		if(userIdAryReal!=null)
			for(int i=0;i<userIdAryReal.length;i++){
				SysUser sysUser = sysUserMapper.selectByPrimaryKey(userIdAryReal[i]);
				//查询的数据不存在
				if(sysUser==null ){
					return ResultUtil.getResult(101,"数据不存在");
				}
			}
		HashMap params =new HashMap();
		//验证子id 正确性 是否存在
		if(userIdAryReal!=null)
			for(int i=0;i<departIdAryReal.length;i++){
				for(int j=0;j<userIdAryReal.length;j++){
					SysUserDepart sysUserDepart =new  SysUserDepart();
					Long departId =departIdAryReal[i];
					Long userId =userIdAryReal[j];
					//查找是否已经有关联数据了

					params.put("userId",userId);
					params.put("departId",departId);
					int count = sysUserDepartMapper.countByParams(params);
					if(count>0)continue;
					sysUserDepart.setUserId(userId);
					sysUserDepart.setDepartId(departId);
					sysUserDepartMapper.insert(sysUserDepart);
				}
			}
		//删除多余的数据
		params.clear();
		params.put("userIds",userIdAryReal);
		params.put("departIds",departIdAryReal);
		sysUserDepartMapper.deleteExtra(params);
		//delete from SysUserRole where uid in (1,2,3,4,5) and rid not in(1,2,3)
		return ResultUtil.getSuccResult();
	}
}
