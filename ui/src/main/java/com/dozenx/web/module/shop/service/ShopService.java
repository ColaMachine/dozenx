/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.shop.service;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.module.shop.bean.Shop;
import com.dozenx.web.module.shop.dao.ShopMapper;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.common.util.UUIDUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;

@Service("shopService")
public class ShopService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(ShopService.class);
    @Resource
    private ShopMapper shopMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<Shop> listByParams4Page(HashMap params) {
        return shopMapper.listByParams4Page(params);
    }
    public List<Shop> listByParams(HashMap params) {
        return shopMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return shopMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param Shop
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(Shop shop) {
        // 进行字段验证
      /* ValidateUtil<Shop> vu = new ValidateUtil<Shop>();
        ResultDTO result = vu.valid(shop);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
               HashMap params =new HashMap();
        params.put("telno",shop.getTelno());
        int count = shopMapper.countByOrParams(params);
        if(StringUtil.isNull(shop.getId())&& count>0||count>1 ){
            return ResultUtil.getResult(302,"字段唯一不能重复");
        }

       //判断是更新还是插入
        if (shop.getId()==null ||  this.selectByPrimaryKey(shop.getId())==null) {
            shop.setCreateTime(new Timestamp(new Date().getTime()));

            shopMapper.insert(shop);
        } else {
            shopMapper.updateByPrimaryKeySelective(shop);
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
        shopMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public Shop selectByPrimaryKey(Integer id){
       return shopMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Integer[] idAry) {
        for(int i=0;i<idAry.length;i++){
            shopMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
}
