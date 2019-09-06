/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.spider.sight.comment.sightComment.service;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.module.spider.sight.comment.sightComment.bean.SightComment;
import com.dozenx.web.module.spider.sight.comment.sightComment.dao.SightCommentMapper;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.common.util.UUIDUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;

@Service("sightCommentService")
public class SightCommentService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(SightCommentService.class);
    @Resource
    private SightCommentMapper sightCommentMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<SightComment> listByParams4Page(HashMap params) {
        return sightCommentMapper.listByParams4Page(params);
    }
    public List<SightComment> listByParams(HashMap params) {
        return sightCommentMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return sightCommentMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param SightComment
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(SightComment sightComment) {
        // 进行字段验证
      /* ValidateUtil<SightComment> vu = new ValidateUtil<SightComment>();
        ResultDTO result = vu.valid(sightComment);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (sightComment.getId()==null ||  this.selectByPrimaryKey(sightComment.getId())==null) {
            sightComment.setCreateTime(new Timestamp(new Date().getTime()));

            sightCommentMapper.insert(sightComment);
        } else {
            sightComment.setUpdatetime(new Timestamp(new Date().getTime()));
            sightCommentMapper.updateByPrimaryKeySelective(sightComment);
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
        sightCommentMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public SightComment selectByPrimaryKey(Integer id){
       return sightCommentMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Integer[] idAry) {
        for(int i=0;i<idAry.length;i++){
            sightCommentMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
}
