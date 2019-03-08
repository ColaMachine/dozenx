/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.goods.service;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.module.goods.bean.Goods;
import com.dozenx.web.module.goods.dao.GoodsMapper;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.util.UUIDUtil;
import com.dozenx.web.util.ValidateUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;

@Service("goodsService")
public class GoodsService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(GoodsService.class);
    @Resource
    private GoodsMapper goodsMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<Goods> listByParams4Page(HashMap params) {
        return goodsMapper.listByParams4Page(params);
    }
    public List<Goods> listByParams(HashMap params) {
        return goodsMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return goodsMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param Goods
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(Goods goods) {
        // 进行字段验证
      /* ValidateUtil<Goods> vu = new ValidateUtil<Goods>();
        ResultDTO result = vu.valid(goods);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
//               HashMap params =new HashMap();
//        params.put("telno",goods.getTelno());
//        int count = goodsMapper.countByOrParams(params);
//        if(StringUtil.isNull(goods.getId())&& count>0||count>1 ){
//            return ResultUtil.getResult(302,"字段唯一不能重复");
//        }

       //判断是更新还是插入
        if (goods.getId()==null ||  this.selectByPrimaryKey(goods.getId())==null) {
            goods.setCreateTime(new Timestamp(new Date().getTime()));

            goodsMapper.insert(goods);
        } else {
            goodsMapper.updateByPrimaryKeySelective(goods);
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
        goodsMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public Goods selectByPrimaryKey(Long id){
       return goodsMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            goodsMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }

    public void updateZan(Long id) {
        goodsMapper.updateZan(id);

    }

    public ResultDTO updateComments(Long pid) {
        goodsMapper.updateComments(pid);
        return ResultUtil.getSuccResult();
    }
}
