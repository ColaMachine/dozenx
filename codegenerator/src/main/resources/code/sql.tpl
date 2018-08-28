


CREATE TABLE `${table.tableName}` (
 <#list table.cols as col>
 `${col.colName}` ${col.type} <#if col.nn >NOT NULL</#if> <#if col.ai > AUTO_INCREMENT</#if> <#if col.def?? >DEFAULT ${col.def} </#if> COMMENT '${col.remark}',
</#list>
  PRIMARY KEY (`${table.pk.colName}`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='${table.remark}';












