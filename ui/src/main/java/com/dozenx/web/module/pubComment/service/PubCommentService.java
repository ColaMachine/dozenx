/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.pubComment.service;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.dozenx.web.module.article.dao.ArticleMapper;
import com.dozenx.web.module.msgInfo.dao.MsgInfoMapper;
import com.dozenx.web.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.module.pubComment.bean.PubComment;
import com.dozenx.web.module.pubComment.dao.PubCommentMapper;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;

@Service("pubCommentService")
public class PubCommentService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(PubCommentService.class);
    @Resource
    private PubCommentMapper pubCommentMapper;

    @Resource
    private ArticleMapper articalMapper;
    @Resource
    private MsgInfoMapper msgInfoMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<PubComment> listByParams4Page(HashMap params) {
        return pubCommentMapper.listByParams4Page(params);
    }
    public List<PubComment> listByParams(HashMap params) {
        return pubCommentMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return pubCommentMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param PubComment
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(PubComment pubComment) {
        // 进行字段验证
      /* ValidateUtil<PubComment> vu = new ValidateUtil<PubComment>();
        ResultDTO result = vu.valid(pubComment);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (pubComment.getId()==null ||  this.selectByPrimaryKey(pubComment.getId())==null) {

            pubCommentMapper.insert(pubComment);
        } else {
            pubComment.setUpdatetime(new Timestamp(new Date().getTime()));
            pubCommentMapper.updateByPrimaryKeySelective(pubComment);
        }

        msgInfoMapper.updateCommentCountById(pubComment.getPid());
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
        pubCommentMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public PubComment selectByPrimaryKey(Long id){
       return pubCommentMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            pubCommentMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
}
