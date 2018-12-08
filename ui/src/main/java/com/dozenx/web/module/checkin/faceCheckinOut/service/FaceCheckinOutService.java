/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.checkin.faceCheckinOut.service;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.module.checkin.faceCheckinOut.bean.FaceCheckinOut;
import com.dozenx.web.module.checkin.faceCheckinOut.dao.FaceCheckinOutMapper;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.util.UUIDUtil;
import com.dozenx.web.util.ValidateUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;

@Service("faceCheckinOutService")
public class FaceCheckinOutService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(FaceCheckinOutService.class);
    @Resource
    private FaceCheckinOutMapper faceCheckinOutMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<FaceCheckinOut> listByParams4Page(HashMap params) {
        return faceCheckinOutMapper.listByParams4Page(params);
    }
    public List<FaceCheckinOut> listByParams(HashMap params) {
        return faceCheckinOutMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return faceCheckinOutMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param FaceCheckinOut
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(FaceCheckinOut faceCheckinOut) {
        // 进行字段验证
      /* ValidateUtil<FaceCheckinOut> vu = new ValidateUtil<FaceCheckinOut>();
        ResultDTO result = vu.valid(faceCheckinOut);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (faceCheckinOut.getId()==null ||  this.selectByPrimaryKey(faceCheckinOut.getId())==null) {

            faceCheckinOutMapper.insert(faceCheckinOut);
        } else {
            faceCheckinOutMapper.updateByPrimaryKeySelective(faceCheckinOut);
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
        faceCheckinOutMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public FaceCheckinOut selectByPrimaryKey(Long id){
       return faceCheckinOutMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            faceCheckinOutMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
}
