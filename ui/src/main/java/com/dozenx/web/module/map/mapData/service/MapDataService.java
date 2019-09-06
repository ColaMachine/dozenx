/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.map.mapData.service;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.dozenx.common.util.DateUtil;
import com.dozenx.web.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.module.map.mapData.bean.MapData;
import com.dozenx.web.module.map.mapData.dao.MapDataMapper;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;

@Service("mapDataService")
public class MapDataService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(MapDataService.class);
    @Resource
    private MapDataMapper mapDataMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<MapData> listByParams4Page(HashMap params) {
        return mapDataMapper.listByParams4Page(params);
    }
    public List<MapData> listByParams(HashMap params) {
        return mapDataMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return mapDataMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param MapData
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(MapData mapData) {
        // 进行字段验证
      /* ValidateUtil<MapData> vu = new ValidateUtil<MapData>();
        ResultDTO result = vu.valid(mapData);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (mapData.getId()==null ||  this.selectByPrimaryKey(mapData.getId())==null) {
            mapData.setStatus(1);
            mapData.setUpdatetime(new Timestamp(DateUtil.getNowTimeStampMills()));
            mapDataMapper.insert(mapData);
        } else {
            mapData.setUpdatetime(new Timestamp(new Date().getTime()));
            mapDataMapper.updateByPrimaryKeySelective(mapData);
        }
        return ResultUtil.getSuccResult();
    }

    public ResultDTO addBatch(List<MapData> list){
        for(MapData mapData:list){
            this.save(mapData);
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
        mapDataMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public MapData selectByPrimaryKey(Long id){
       return mapDataMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            mapDataMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }


    public List<MapData> queryNearby(Map<String,Object> params){
        return mapDataMapper.queryNearby(params);
    }
}
