/**
 * 版权所有： dozen.zhang
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: ${.now?string("yyyy年MM月dd日")}
 * 文件说明: 
 */
package ${table.pkg}.${abc}.bean;
import java.sql.Timestamp;
import java.util.Date;
<#assign abc="${table.name[0]?lower_case}${table.name[1..]}">
<#assign Abc="${table.name[0]?upper_case}${table.name[1..]}">
/**
 *${table.remark}实体类
 **/
public class ${Abc} {
    ${content}
}
