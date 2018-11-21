/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.checkin.faceInfo.service;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.module.checkin.faceInfo.bean.FaceInfo;
import com.dozenx.web.module.checkin.faceInfo.dao.FaceInfoMapper;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.util.UUIDUtil;
import com.dozenx.web.util.ValidateUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;

@Service("faceInfoService")
public class FaceInfoService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(FaceInfoService.class);
    @Resource
    private FaceInfoMapper faceInfoMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<FaceInfo> listByParams4Page(HashMap params) {
        return faceInfoMapper.listByParams4Page(params);
    }
    public List<FaceInfo> listByParams(HashMap params) {
        return faceInfoMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return faceInfoMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param FaceInfo
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(FaceInfo faceInfo) {
        // 进行字段验证
      /* ValidateUtil<FaceInfo> vu = new ValidateUtil<FaceInfo>();
        ResultDTO result = vu.valid(faceInfo);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (faceInfo.getId()==null ||  this.selectByPrimaryKey(faceInfo.getId())==null) {

            faceInfoMapper.insert(faceInfo);
        } else {
            faceInfoMapper.updateByPrimaryKeySelective(faceInfo);
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
        faceInfoMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public FaceInfo selectByPrimaryKey(Long id){
       return faceInfoMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            faceInfoMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }

//    /**
//     * 提供缓存的获取所有的
//     * @return
//     */
//    public List<FaceInfo> getAll(){
//        List<FaceInfo> list = listByParams(new HashMap());
//        for(FaceInfo faceInfo : list){
//            List<BigDecimal> faceFeture = JSON.parseArray(faceInfo.getFace(),BigDecimal.class);//人脸特征数组
//            Double[] ary = new Double[128];
//            for(int i=0;i<faceFeture.size();i++){
//                ary[i] = faceFeture.get(i).doubleValue();
//
//            }
//            faceInfo.setFaceAry(ary);
//
//        }
//        return list;
//    }
}
