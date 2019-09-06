/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.pubImageBelong.service.impl;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.module.pubImageBelong.bean.PubImageBelong;
import com.dozenx.web.module.pubImageBelong.dao.PubImageBelongMapper;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.common.util.UUIDUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.pubImageBelong.service.PubImageBelongService;

@Service("pubImageBelongService")
public class PubImageBelongServiceImpl extends BaseService implements PubImageBelongService {
    private static final Logger logger = LoggerFactory
            .getLogger(PubImageBelongService.class);
    @Resource
    private PubImageBelongMapper pubImageBelongMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<PubImageBelong> listByParams4Page(HashMap params) {
        return pubImageBelongMapper.listByParams4Page(params);
    }
    public List<PubImageBelong> listByParams(HashMap params) {
        return pubImageBelongMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return pubImageBelongMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param PubImageBelong
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(PubImageBelong pubImageBelong) {
        // 进行字段验证
      /* ValidateUtil<PubImageBelong> vu = new ValidateUtil<PubImageBelong>();
        ResultDTO result = vu.valid(pubImageBelong);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (pubImageBelong.getId()==null ||  this.selectByPrimaryKey(pubImageBelong.getId())==null) {

            pubImageBelongMapper.insert(pubImageBelong);
        } else {
            pubImageBelongMapper.updateByPrimaryKeySelective(pubImageBelong);
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
        pubImageBelongMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public PubImageBelong selectByPrimaryKey(Integer id){
       return pubImageBelongMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Integer[] idAry) {
        for(int i=0;i<idAry.length;i++){
            pubImageBelongMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }



    @Override
    public ResultDTO insertList(List<PubImageBelong> list) {
       pubImageBelongMapper.insertBatch(list);
        return null;
    }


    public ResultDTO save(Long pid ,String[] urls){
        HashMap params =new HashMap();
        params.put("pid",pid);
        List<PubImageBelong> list = pubImageBelongMapper.listByParams(params);
        loop1:
        for(String s : urls){
            if(StringUtil.isBlank(s) || s.equals("null")){
                continue;
            }
            for(PubImageBelong pubImageBelong: list){
                if(pubImageBelong.getUrl().equals(s)){
                    pubImageBelong.find=true;
                    continue loop1;
                }

            }


           //说明数据库没有这一条记录我现在就把他插入到数据库
            PubImageBelong pubImageBelong=new PubImageBelong();
            pubImageBelong.setPid(pid.intValue());
            pubImageBelong.setUrl(s);
            pubImageBelong.setType("1");
            pubImageBelongMapper.insert(pubImageBelong);
        }
        for(PubImageBelong pubImageBelong: list){
            if(!pubImageBelong.find){//没有
                pubImageBelongMapper.deleteByPrimaryKey(pubImageBelong.getId());
            }

        }
        return ResultUtil.getSuccResult();
    }
}
