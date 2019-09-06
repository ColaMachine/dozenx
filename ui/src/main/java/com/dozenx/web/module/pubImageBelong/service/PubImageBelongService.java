/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.pubImageBelong.service;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dozenx.web.module.pubImageBelong.bean.PubImageBelong;
import com.dozenx.common.util.UUIDUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.log.ResultDTO;

public interface PubImageBelongService  {
    static final Logger logger = LoggerFactory
            .getLogger(PubImageBelongService.class);

    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<PubImageBelong> listByParams4Page(HashMap params) ;
    public List<PubImageBelong> listByParams(HashMap params);

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) ;

    /*
     * 说明:
     * @param PubImageBelong
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(PubImageBelong pubImageBelong) ;
    /**
    * 说明:根据主键删除数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public void delete(Integer id);
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public PubImageBelong selectByPrimaryKey(Integer id);
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Integer[] idAry) ;


       public ResultDTO insertList(List<PubImageBelong> pubImageBelong) ;

    public ResultDTO save(Long pid ,String[] urls);
}
