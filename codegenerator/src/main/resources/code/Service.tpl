/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package ${table.pkg}.${abc}.service;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ${table.pkg}.${abc}.bean.${Abc};
import ${table.pkg}.${abc}.dao.${Abc}Mapper;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.util.UUIDUtil;
import com.dozenx.web.util.ValidateUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseService;
<#if table.mapper??>
import java.util.StringTokenizer;
import  ${table.pkg}.${abc}.bean.${table.mapper.mapper};
import  ${table.pkg}.${abc}.dao.${table.mapper.mapper}Mapper;

</#if>
 <#if table.mapper??>
           <#if table.mapper.mapper==Abc>
import ${table.pkg}.${abc}.bean.${table.mapper.parent};
import ${table.pkg}.${abc}.bean.${table.mapper.child};
import ${table.pkg}.${abc}.dao.${table.mapper.parent}Mapper;
import ${table.pkg}.${abc}.dao.${table.mapper.child}Mapper;
    </#if>
        </#if>
import com.dozenx.web.core.log.ResultDTO;

public interface ${Abc}Service  {
    private static final Logger logger = LoggerFactory
            .getLogger(${Abc}Service.class);

    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<${Abc}> listByParams4Page(HashMap params) ;
    public List<${Abc}> listByParams(HashMap params);

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) ;

    /*
     * 说明:
     * @param ${Abc}
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(${Abc} ${abc}) ;
    <#if table.mapper??>
     <#if table.mapper.parent== table.name>
     /*
     * 说明:和关联关系一起保存
     * @param ${Abc}
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO saveWithChilds(${Abc} ${abc},String childids) ;
     </#if>
    </#if>
    /**
    * 说明:根据主键删除数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public void delete(<@javaType>${table.pk.type}</@javaType>  ${table.pk.name});
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public ${Abc} selectByPrimaryKey(<@javaType>${table.pk.type}</@javaType> id);
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(<@javaType>${table.pk.type}</@javaType>[] idAry) ;
      <#if table.mapper??>
         <#if table.mapper.mapper==Abc>
     /**
         * 多项关联保存
         * @param uids
         * @param rids
         * @return
         */
        public ResultDTO msave(String ${table.mapper.parentid}s,String ${table.mapper.childid}s) ;

       public ResultDTO msave(  ${parentType} ${table.mapper.parentid}s,  ${childType} ${table.mapper.childid}s) ;
      </#if>
      </#if>


       public ResultDTO insertList(List<${Abc}> ${abc}) ;
}
