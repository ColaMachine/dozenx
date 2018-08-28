/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.spider.art.comment.artComment.service;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.module.spider.art.comment.artComment.bean.ArtComment;
import com.dozenx.web.module.spider.art.comment.artComment.dao.ArtCommentMapper;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.util.UUIDUtil;
import com.dozenx.web.util.ValidateUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;

@Service("artCommentService")
public class ArtCommentService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(ArtCommentService.class);
    @Resource
    private ArtCommentMapper artCommentMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<ArtComment> listByParams4Page(HashMap params) {
        return artCommentMapper.listByParams4Page(params);
    }
    public List<ArtComment> listByParams(HashMap params) {
        return artCommentMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return artCommentMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param ArtComment
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(ArtComment artComment) {
        // 进行字段验证
      /* ValidateUtil<ArtComment> vu = new ValidateUtil<ArtComment>();
        ResultDTO result = vu.valid(artComment);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (artComment.getId()==null ||  this.selectByPrimaryKey(artComment.getId())==null) {
            artComment.setCreateTime(new Timestamp(new Date().getTime()));

            artCommentMapper.insert(artComment);
        } else {
            artComment.setUpdatetime(new Timestamp(new Date().getTime()));
            artCommentMapper.updateByPrimaryKeySelective(artComment);
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
        artCommentMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public ArtComment selectByPrimaryKey(Integer id){
       return artCommentMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Integer[] idAry) {
        for(int i=0;i<idAry.length;i++){
            artCommentMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
}
