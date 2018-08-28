/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.artical.service;

import com.dozenx.util.DateUtil;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.artical.bean.Artical;
import com.dozenx.web.module.artical.dao.ArticalMapper;
import com.dozenx.web.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service("articalService")
public class ArticalService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(ArticalService.class);
    @Resource
    private ArticalMapper articalMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<Artical> listByParams4Page(HashMap params) {
        return articalMapper.listByParams4Page(params);
    }

    public List<HashMap<String,Object>> listWithUserInfoByParams4Page(HashMap params) {
        return articalMapper.listWithUserInfoByParams4Page(params);
    }
    public List<Artical> listByParams(HashMap params) {
        return articalMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return articalMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param Artical
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(Artical artical) {
        // 进行字段验证
      /* ValidateUtil<Artical> vu = new ValidateUtil<Artical>();
        ResultDTO result = vu.valid(artical);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (artical.getId()==null ||  this.selectByPrimaryKey(artical.getId())==null) {
            artical.setCreatetime(DateUtil.getNowTimeStamp());
            articalMapper.insert(artical);
        } else {
            artical.setUpdatetime(new Timestamp(new Date().getTime()));
            articalMapper.updateByPrimaryKeySelective(artical);
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
        articalMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public Artical selectByPrimaryKey(Long id){
       return articalMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            articalMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }

    public void updatePinglunCount(Long id){
        articalMapper.updateCommentCountById(id);
    }
}
