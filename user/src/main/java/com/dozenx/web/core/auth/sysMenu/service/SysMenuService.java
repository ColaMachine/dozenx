/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.auth.sysMenu.service;

import com.dozenx.web.util.ResultUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.auth.sysMenu.bean.SysMenu;
import com.dozenx.web.core.auth.sysMenu.dao.SysMenuMapper;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sysMenuService")
public class SysMenuService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(SysMenuService.class);
    @Resource
    private SysMenuMapper sysMenuMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<SysMenu> listByParams4Page(Map<String,Object> params) {
        return sysMenuMapper.listByParams4Page(params);
    }
    public List<SysMenu> listByParams(HashMap params) {
        return sysMenuMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return sysMenuMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param SysMenu
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(SysMenu sysMenu) {
        // 进行字段验证
      /* ValidateUtil<SysMenu> vu = new ValidateUtil<SysMenu>();
        ResultDTO result = vu.valid(sysMenu);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
               HashMap params =new HashMap();
        params.put("code",sysMenu.getCode());
        params.put("url",sysMenu.getUrl());
        int count = sysMenuMapper.countByOrParams(params);
        if(StringUtil.isNull(sysMenu.getId())&& count>0||count>1 ){
            return ResultUtil.getResult(302,"字段唯一不能重复");
        }

       //判断是更新还是插入
        if (sysMenu.getId()==null ||  this.selectByPrimaryKey(sysMenu.getId())==null) {

            sysMenuMapper.insert(sysMenu);
        } else {
            sysMenuMapper.updateByPrimaryKeySelective(sysMenu);
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
        sysMenuMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public SysMenu selectByPrimaryKey(Long id){
       return sysMenuMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            sysMenuMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
    /**
     * @Author: dozen.zhang
     * @Description:根据角色查找菜单
     * @Date: 2018/2/8
     */
    public List<SysMenu> selectMenuByRoleId(Long roleId){
        return sysMenuMapper.selectMenuByRoleId(roleId);
    }

    /**
     * @Author: dozen.zhang
     * @Description:根据角色查找菜单
     * @Date: 2018/2/8
     */
    public List<SysMenu> selectMenuByUserId(Long userId){
        return sysMenuMapper.selectMenuByUserId(userId);
    }


    /**
     * @Author: dozen.zhang
     * @Description:根据角色查找菜单
     * @Date: 2018/2/8
     */
    public List<SysMenu> selectAllMenu(){
        return sysMenuMapper.selectAllMenu();
    }



}
