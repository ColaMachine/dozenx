/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.zan.service.impl;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.dozenx.common.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.module.zan.bean.Zan;
import com.dozenx.web.module.zan.dao.ZanMapper;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.zan.service.ZanService;

@Service("zanService")
public class ZanServiceImpl extends BaseService implements ZanService {
    private static final Logger logger = LoggerFactory
            .getLogger(ZanService.class);
    @Resource
    private ZanMapper zanMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<Zan> listByParams4Page(HashMap params) {
        return zanMapper.listByParams4Page(params);
    }
    public List<Zan> listByParams(HashMap params) {
        return zanMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return zanMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param Zan
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(Zan zan) {
        // 进行字段验证
      /* ValidateUtil<Zan> vu = new ValidateUtil<Zan>();
        ResultDTO result = vu.valid(zan);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (zan.getId()==null ||  this.selectByPrimaryKey(zan.getId())==null) {
            zan.setCreateTime(new Timestamp(new Date().getTime()));

            zanMapper.insert(zan);
        } else {
            zanMapper.updateByPrimaryKeySelective(zan);
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
        zanMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public Zan selectByPrimaryKey(Long id){
       return zanMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            zanMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }



    @Override
    public ResultDTO insertList(List<Zan> list) {
       zanMapper.insertBatch(list);
        return null;
    }

    @Override
    public void up(Long userId, Long pid, int category) {
        {
            if(userId ==null){
                throw new BizException(30102001,"未登录");
            }
            HashMap params = new HashMap();
            params.put("userId",userId);
            params.put("pid",pid);
            params.put("category",category);
            List<Zan> zanList = listByParams(params);

            if(zanList!=null && zanList.size()>0){
                Zan zan = zanList.get(0);
                if(zan.getType()==1) {
                    throw new BizException(30405001, "你已经点过了");
                }else{
                    //更新
                    byte type =1;
                    zan.setType(type);
                    zan.setCategory(category);
                    this.save(zan);//更新为顶
                }
            }else {

                Zan zan = new Zan();
                //查看是否有重复点赞
                byte type =1;
                zan.setType(type);
                zan.setPid(pid);
                zan.setCategory(category);
                zan.setUserId(userId);
                this.save(zan);
            }

        }
    }


    @Override
    public void down(Long userId, Long pid, int category) {
        {
            if(userId ==null){
                throw new BizException(30102176,"未登录");
            }
            if(pid ==null){
                throw new BizException(30102177,"参数错误");
            }
            if(category !=1 && category !=2){
                throw new BizException(30102178,"参数错误");
            }
            HashMap params = new HashMap();
            params.put("userId",userId);
            params.put("pid",pid);
            params.put("category",category);
            List<Zan> zanList = listByParams(params);

            if(zanList!=null && zanList.size()>0){
                Zan zan = zanList.get(0);
                if(zan.getType()==2) {
                    throw new BizException(30405001, "你已经点过了");
                }else{
                    //更新
                    byte type =2;
                    zan.setType(type);
                    zan.setCategory(category);
                    this.save(zan);//更新为顶
                }
            }else {

                Zan zan = new Zan();
                //查看是否有重复点赞
                byte type =2;
                zan.setType(type);
                zan.setPid(pid);
                zan.setCategory(category);
                zan.setUserId(userId);
                this.save(zan);
            }

        }
    }
}
