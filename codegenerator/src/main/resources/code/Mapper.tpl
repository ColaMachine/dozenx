package ${table.pkg}.${abc}.dao;
 <#assign abc="${table.name[0]?lower_case}${table.name[1..]}">
<#assign Abc="${table.name[0]?upper_case}${table.name[1..]}">
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import ${table.pkg}.${abc}.bean.${Abc};

public interface ${Abc}Mapper {
    <#if !table.mapper??>
    int deleteByPrimaryKey(<@javaType>${table.pk.type}</@javaType> ${table.pk.name});
   </#if>
    
    int insert(${Abc} record);

   
    int insertSelective(${Abc}  record);

     <#if !table.mapper??>
    ${Abc}  selectByPrimaryKey(<@javaType>${table.pk.type}</@javaType> id);
  </#if>
    /**
     * 说明:根据主键修改所存在属性内容
     * @param ${abc}
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(${Abc} ${abc});

    /**
     * 说明:根据主键修改record完整内容
     * @param ${abc}
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(${Abc} ${abc});

    /**
     * 说明:根据map查找bean结果集
     * @param ${abc}
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<${Abc}> listByParams(Map ${abc});
    
    /**
     * 说明:根据bean查找bean结果集
     * @param ${abc}
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<${Abc}> listByParams4Page(Map ${abc});
    
    /**
     * 说明:根据map查找map结果集
     * @param ${abc}
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(${Abc} ${abc});*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param ${abc}
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<${Abc}> selectBeanByMap4Page(HashMap map);
    
    int countByBean(${Abc} record);*/
    
    int countByParams(HashMap map);

    <#if distinctCheck??>
    int countByOrParams(HashMap map);
    </#if>

      
     <#if table.mapper??>
     <#if table.mapper.mapper==Abc>
    int deleteExtra(HashMap map);
     </#if>
     </#if>
}