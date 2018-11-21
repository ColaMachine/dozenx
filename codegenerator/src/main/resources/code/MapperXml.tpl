<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${table.pkg}.${abc}.dao.${Abc}Mapper">
  <resultMap id="BaseResultMap" type="${table.pkg}.${abc}.bean.${Abc}">
   <#list table.cols as col>
      <<#if col.pk==true>id<#else>result</#if> column="${col.colName}" jdbcType="<@jdbcType>${col.type}</@jdbcType>" property="${col.name}" />
   </#list>
    <#if reference??>
    <#list table.cols as col>
    <#if col.references??>
    <#assign refName = col.references?substring(0,col.references?index_of("."))>
    <#assign refTableName = allTables[refName]['tableName']>
    <#assign firstDo = col.references?index_of(".")>
    <#assign secondeDo = col.references?index_of(".",firstDo+1)>
    <#assign refKey = col.references?substring(firstDo+1,secondeDo)>
    <#assign refKeyName = col.references?substring(secondeDo+1)>
    <#assign refAliasName =refTableName>
    <result column="${refTableName}_${refKeyName}" jdbcType="VARCHAR" property="${refTableName}_${refKeyName}" />
    </#if>
    </#list>
   </#if>
  </resultMap>
  <sql id="Base_Column_List">
    <#list table.cols as col><#if col_index==0>${table.tableName}.`${col.colName}`<#else>,${table.tableName}.`${col.colName}`</#if> </#list>
  </sql>
  <select id="selectByPrimaryKey" parameterType="java.lang.<@javaType>${table.pk.type}</@javaType>" resultMap="BaseResultMap">
    select 
    <include refid="Base_Column_List" />
      <#if reference??>
           <#list table.cols as col>
              <#if col.references??>
                <#assign refName = col.references?substring(0,col.references?index_of("."))>
                <#assign refTableName = allTables[refName]['tableName']>
                <#assign firstDo = col.references?index_of(".")>
                <#assign secondeDo = col.references?index_of(".",firstDo+1)>
                <#assign refKey = col.references?substring(firstDo+1,secondeDo)>
                <#assign refKeyName = col.references?substring(secondeDo+1)>
                <#assign refAliasName =refTableName>
                <#if refAliasName==table.tableName>
                    <#assign refAliasName =refAliasName+'1'>
                </#if>
                ,${refAliasName}.${refKeyName} as ${refName}_${refKeyName}
              </#if>
           </#list>
        </#if>
    from ${table.tableName}
    <#if reference??>
          <#list table.cols as col>
             <#if col.references??>
                <#assign refName = col.references?substring(0,col.references?index_of("."))>
                <#assign refTableName = allTables[refName]['tableName']>
                <#assign firstDo = col.references?index_of(".")>
                <#assign secondeDo = col.references?index_of(".",firstDo+1)>
                <#assign refKey = col.references?substring(firstDo+1,secondeDo)>
                <#assign refKeyName = col.references?substring(secondeDo+1)>
                <#assign refAliasName =refTableName>
                <#if refAliasName==table.tableName>
                    <#assign refAliasName =refAliasName+'1'>
                </#if>
               left join ${refTableName} ${refAliasName}
                 on ${table.tableName}.${col.colName} = ${refAliasName}.${refKey}
             </#if>
          </#list>
       </#if>
    where ${table.tableName}.${table.pk.name} =   ${r'#{'}${table.pk.name},jdbcType=<@jdbcType>${table.pk.type}</@jdbcType>}
  </select>
  <delete id="deleteByPrimaryKey" parameterType="java.lang.<@javaType>${table.pk.type}</@javaType>">
    delete from ${table.tableName}
    where ${table.pk.name} = ${r'#{'}${table.pk.name},jdbcType=<@jdbcType>${table.pk.type}</@jdbcType>}
  </delete>
  <#if table.mapper??>
  <#if table.mapper.mapper==Abc>
   <delete id="deleteExtra" parameterType="map">
          delete from ${table.tableName}
          where ${table.mapper.parentid} in
          <foreach collection="${table.mapper.parentid}s" item="item" index="index"
                   open="(" separator="," close=")"> ${r'#{'}item}</foreach>
         <if test="${table.mapper.childid}s != null and id != '' " >
          and ${table.mapper.childid} not in
          <foreach collection="${table.mapper.childid}s" item="item" index="index"
                   open="(" separator="," close=")"> ${r'#{'}item}</foreach>
       </if>
      </delete>
      </#if>
      </#if>
 <insert id="insert" <#if table.pk.ai==true>useGeneratedKeys="true" keyProperty="id"</#if>   parameterType="${table.pkg}.${abc}.bean.${Abc}" >

    insert into ${table.tableName} (  <include refid="Base_Column_List" />)
    values (
     <#list table.cols as col>
      <#if col_index==0><#else>,</#if>${r'#{'}${col.name},jdbcType=<@jdbcType>${col.type}</@jdbcType>}
    </#list>
    )
  </insert>
   <insert id="insertSelective" parameterType="${table.pkg}.${abc}.bean.${Abc}" >
    insert into ${table.tableName}
    <trim prefix="(" suffix=")" suffixOverrides="," >
      <#list table.cols as col>
        <if test="${col.name} != null" >  
           `${col.colName}`,
        </if>  
    </#list>
    </trim>
    <trim prefix="values (" suffix=")" suffixOverrides="," >
       <#list table.cols as col>
        <if test="${col.name} != null" >  
          ${r'#{'}${col.name},jdbcType=<@jdbcType>${col.type}</@jdbcType>},
        </if>  
    </#list>
    </trim>
  </insert>
  <update id="updateByPrimaryKeySelective" parameterType="${table.pkg}.${abc}.bean.${Abc}" >
    update ${table.tableName}
    <set >
     <#list table.cols as col>
        <if test="${col.name} != null" >  
             `${col.colName}`=${r'#{'}${col.name},jdbcType=<@jdbcType>${col.type}</@jdbcType>},
        </if>  
    </#list>
    </set>
    where ${table.pk.name} = ${r'#{'}${table.pk.name},jdbcType=<@jdbcType>${table.pk.type}</@jdbcType>}
  </update>
  <update id="updateByPrimaryKey" parameterType="${table.pkg}.${abc}.bean.${Abc}" >
    update ${table.tableName}
    set 
    <#list table.cols as col>
        <#if col.pk!=true >
              `${col.colName}`=${r'#{'}${col.name},jdbcType=<@jdbcType>${col.type}</@jdbcType>}<#if col_index<table.cols?size-1>,</#if>
        </#if>
    </#list>
where ${table.pk.name} = ${r'#{'}${table.pk.name},jdbcType=<@jdbcType>${table.pk.type}</@jdbcType>}
  </update>
  <select id="listByParams" parameterType="map" resultMap="BaseResultMap">
    select 
    <include refid="Base_Column_List" />
    from ${table.tableName} where 1=1
    <#list table.cols as col>
        <if test="${col.name} != null and ${col.name} != '' ">  
           and `${col.colName}` = ${r'#{'}${col.name}}
        </if>  
         <#if col.type?length gt 6>
          <#if col.type[0..6]?lower_case=='text'>
         <if test="${col.name}Like != null and ${col.name}Like != '' ">
              and `${col.colName}` like "%"${r'#{'}${col.name}Like}"%"
         </if>
         </#if>
        <#if col.type[0..6]?lower_case=='varchar'>
        <if test="${col.name}Like != null and ${col.name}Like != '' ">
             and `${col.colName}` like "%"${r'#{'}${col.name}Like}"%"
        </if>   
        </#if>
        </#if>
         <#if col.type?length gt 3>
        <#if col.type[0..3]?lower_case=='char'>
        <if test="${col.name}Like != null and ${col.name}Like != '' ">  
             and `${col.colName}` like "%"${r'#{'}${col.name}Like}"%"
        </if>   
        </#if>
        </#if>
          <#if col.type=='timestamp'>
        <if test="${col.name}Begin != null and ${col.name}Begin != '' ">  
             and `${col.colName}` &gt;= ${r'#{'}${col.name}Begin}
        </if>   
         <if test="${col.name}End != null and ${col.name}End != '' ">  
             and `${col.colName}` &lt;= ${r'#{'}${col.name}End}
        </if> 
        </#if>
        <#if col.type=='timestamp'>
        <if test="${col.name}Begin != null and ${col.name}Begin != '' ">  
             and `${col.colName}` &gt;= ${r'#{'}${col.name}Begin}
        </if>   
         <if test="${col.name}End != null and ${col.name}End != '' ">  
             and `${col.colName}` &lt;= ${r'#{'}${col.name}End}
        </if> 
        </#if>
    </#list>
  </select>
   <select id="listByParams4Page" parameterType="map" resultMap="BaseResultMap">
    select 
    <include refid="Base_Column_List" />
    <#if reference??>
       <#list table.cols as col>
         <#if col.references??>
           <#assign refName = col.references?substring(0,col.references?index_of("."))>
           <#assign refTableName = allTables[refName]['tableName']>
           <#assign firstDo = col.references?index_of(".")>
           <#assign secondeDo = col.references?index_of(".",firstDo+1)>
           <#assign refKey = col.references?substring(firstDo+1,secondeDo)>
           <#assign refKeyName = col.references?substring(secondeDo+1)>
           <#assign refAliasName =refTableName>
           <#if refAliasName==table.tableName>
               <#assign refAliasName =refAliasName+'1'>
           </#if>
           ,${refAliasName}.${refKeyName} as ${refName}_${refKeyName}
         </#if>
      </#list>
    </#if>
    from ${table.tableName}

<#if reference??>
    <#list table.cols as col>
      <#if col.references??>
    <#assign refName = col.references?substring(0,col.references?index_of("."))>
    <#assign refTableName = allTables[refName]['tableName']>
    <#assign firstDo = col.references?index_of(".")>
    <#assign secondeDo = col.references?index_of(".",firstDo+1)>
    <#assign refKey = col.references?substring(firstDo+1,secondeDo)>
    <#assign refKeyName = col.references?substring(secondeDo+1)>
    <#assign refAliasName =refTableName>
    <#if refAliasName==table.tableName>
      <#assign refAliasName =refAliasName+'1'>
  </#if>
    left join ${refTableName} ${refAliasName}
      on ${table.tableName}.${col.colName} = ${refAliasName}.${refKey}
  </#if>
</#list>

        </#if>
    where 1=1
    <#list table.cols as col>
        <if test="${col.name} != null and ${col.name} != '' ">  
           and ${table.tableName}.`${col.colName}` = ${r'#{'}${col.name}}
        </if>  
           <#if col.type?length gt 6>
        <#if col.type[0..6]?lower_case=='varchar'>
        <if test="${col.name}Like != null and ${col.name}Like != '' ">  
             and ${table.tableName}.`${col.colName}` like "%"${r'#{'}${col.name}Like}"%"
        </if>   
        </#if>
        </#if>
         <#if col.type?length gt 3>
        <#if col.type[0..3]?lower_case=='char'>
        <if test="${col.name}Like != null and ${col.name}Like != '' ">  
             and ${table.tableName}.`${col.colName}` like "%"${r'#{'}${col.name}Like}"%"
        </if>   
        </#if>
        </#if>
        <#if col.type=='timestamp'>
        <if test="${col.name}Begin != null and ${col.name}Begin != '' ">  
             and ${table.tableName}.`${col.colName}` &gt;= ${r'#{'}${col.name}Begin}
        </if>   
         <if test="${col.name}End != null and ${col.name}End != '' ">  
             and ${table.tableName}.`${col.colName}` &lt;= ${r'#{'}${col.name}End}
        </if> 
        </#if>
    </#list>
  </select>
   <select id="countByParams" parameterType="map" resultType="java.lang.Integer">
    select 
    count(1) 
    from ${table.tableName} where 1=1
    <#list table.cols as col>
        <if test="${col.name} != null and ${col.name} != '' ">  
           and ${table.tableName}.`${col.colName}` = ${r'#{'}${col.name}}
        </if>  
           <#if col.type?length gt 6>
        <#if col.type[0..6]?lower_case=='varchar'>
        <if test="${col.name}Like != null and ${col.name}Like != '' ">  
             and ${table.tableName}.`${col.colName}` like "%"${r'#{'}${col.name}Like}"%"
        </if>   
        </#if>
        </#if>
         <#if col.type?length gt 3>
        <#if col.type[0..3]?lower_case=='char'>
        <if test="${col.name}Like != null and ${col.name}Like != '' ">  
             and ${table.tableName}.`${col.colName}` like "%"${r'#{'}${col.name}Like}"%"
        </if>   
        </#if>
        </#if>
         <#if col.type=='timestamp'>
        <if test="${col.name}Begin != null and ${col.name}Begin != '' ">  
             and ${table.tableName}.`${col.colName}` &gt;= ${r'#{'}${col.name}Begin}
        </if>   
         <if test="${col.name}End != null and ${col.name}End != '' ">  
             and ${table.tableName}.`${col.colName}` &lt;= ${r'#{'}${col.name}End}
        </if> 
        </#if>
    </#list>
  </select>
    <#if distinctCheck??>
 <select id="countByOrParams" parameterType="map" resultType="java.lang.Integer">
    select
    count(1)
    from ${table.tableName} where 1!=1
    <#list table.cols as col>
        <if test="${col.name} != null and ${col.name} != '' ">
           or `${col.colName}` = ${r'#{'}${col.name}}
        </if>
    </#list>
  </select>
        </#if>
</mapper>
