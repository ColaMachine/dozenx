
<div id="${Abc}List" class="table-responsive">
   <!-- <div class="main-hd ibox-title">| ${table.remark}</div>-->


    <div class="main-bd ">
        <div class="body_top " >
            <form class="form-inline app-search">
            ${searchhtml}
            <button type="button"  class="btn btn-primary searchBtn">查询</button>
            <button class="btn btn-secondary addBtn" ><i class="fa fa-plus"></i>新增</button>
                        <!--<button class="btn btn-primary deleteBtn"><i class="fa fa-plus"></i>删除</button>-->
                        <!--<button class="btn btn-primary exportBtn"><i class="fa fa-plus"></i>导出</button>-->
            </form>


        </div>
        <table id="${table.name}Grid" class="grid table"></table>
        <div id="${table.name}Grid-Pager" class="pager"></div>
    </div>
</div>
<script>
var ${abc}List={
    modal:true,
    mygrid:null,
    treeObj:null,
    dqData:null,
    root:$("#${table.name}List"),

    init:function(){

        setTitle("${table.remark}");
        this.mygrid =this.root.find(".grid").jqGrid(this.gridParam);
        this.addEventListener();
        this.loadData();
    },
    loadData:function(){
        <#if reference??>
        ${reference}
        });
        </#if>
    },
    addEventListener:function(){
        $(this.root).find(".addBtn").click(this.addInfo.Apply(this));
          $(this.root).find(".editBtn").click(this.editSelectInfo.Apply(this));
        $(this.root).find(".deleteBtn").click(this.multiDelete.Apply(this));
        $(this.root).find(".searchBtn").click(this.searchInfo.Apply(this));
    },
    gridParam:{
        datatype: "json",
        viewrecords: true, sortorder: "desc", caption:"",
        rowNum:10,
        rowList:[10,20,30],
        multiselect : false,
        url : PATH+'${table.baseUrl}/list.json',
        autowidth:true,
        grid:"#${table.name}Grid",
        pager:"#${table.name}Grid-Pager",
        jsonReader:jsonReader,
        colNames : [
        <#list table.cols as col><#if col.list><#if col_index!=0>,</#if>"${col.remark}"</#if></#list> , '操作' ],
        colModel : [
               <#list table.cols as col>
               <#if col.list>
            {   name : '<#if col.references??><#assign refName = col.references?substring(0,col.references?index_of("."))>
           <#assign refTableName = allTables[refName]['tableName']>
           <#assign firstDo = col.references?index_of(".")>
           <#assign secondeDo = col.references?index_of(".",firstDo+1)>
           <#assign refKey = col.references?substring(firstDo+1,secondeDo)>
           <#assign refKeyName = col.references?substring(secondeDo+1)>
           <#assign refAliasName =refTableName>
           <#if refAliasName==table.tableName>
               <#assign refAliasName =refAliasName+'1'>
           </#if>
${(refName+"_"+refKeyName)?uncap_first}<#else>${col.name}</#if>',width : 80,
                <#if col.showValue?exists>
                formatter : function(value, grid, rows) {
                  var map ={<#list col.showValue?keys as key>'${key}':'${col.showValue[key]}',</#list>};
                  return map[value];
                }
                </#if>
                <#if col.type=="timestamp">
                formatter : function(value, grid, rows) {
                    if(value){
                        return new Date(value).format("yyyy-MM-dd");
                    }else{
                        return "";
                    }
                }</#if>
            } ,
            </#if>
               </#list>

            {   name : 'operation',
                width : 150,
                formatter : function(value, grid, rows) {
                    return gridHelper.getViewHtml(rows.${table.pk.name},"${abc}List")+gridHelper.getEditHtml(rows.${table.pk.name},"${abc}List")+gridHelper.getDelHtml(rows.${table.pk.name},"${abc}List");
                }
            }
        ],
        onSelectRow: function(id){ //alert("单击选中"+id);
        },
        loadComplete:function(data){
            dqData=data;
        }
    },

    saveInfo:function(){
    },
    addInfo:function (){
        dialog.window('${table.baseUrl}/edit.htm',this.modal);
    },
    editSelectInfo:function(){
        var rowid =this.mygrid.jqGrid("getGridParam","selrow");
        var rowData = this.mygrid.jqGrid('getRowData',rowid);
        var id = rowData["id"];
        if(StringUtil.isBlank(id)){
            dialog.alert("请选中一行数据");
        }else{
            dialog.window("${table.baseUrl}/edit.htm?id="+id,this.modal);
        }
    },
    editInfo:function (id){
        dialog.window("${table.baseUrl}/edit.htm?id="+id,this.modal);
    },
    searchInfo:function (){
        var jso = changeForm2Jso(".app-search");

        this.mygrid.jqGrid("setGridParam", { search: true ,"postData":jso}).trigger("reloadGrid", [{ page: 1}]);  //重载JQGrid
    },
    viewInfo:function (id){
        dialog.window("${table.baseUrl}/view.htm?id="+id,this.modal);
    },
    exportInfo:function (){
        var jso= changeForm2Jso(".app-search");
        Ajax.getJSON(PATH+"${table.baseUrl}/export.json",jso,function(data){
            if(data.r==AJAX_SUCC){
                window.location=PATH+"/"+data.data;
            }else{
                dialog.error(data.msg,"导出失败",null);
            }
        })
    },
    deleteInfo:function (id){
        var that =this;
         //弹窗
        dialog.confirm("确定删除数据:"+id,function(){
            Ajax.post(PATH+"${table.baseUrl}/del.json",{${table.pk.name}:id},function(result){
                result=ajaxResultHandler(result);
                if(result.r==AJAX_SUCC){
                    var did=dialog.alert("删除成功，数据："+id,function(index){
                    that.mygrid.trigger("reloadGrid");
                    dialog.close(did);
                });
                }else {
                    dialog.error(result.msg,"提醒");
                }
            });
        });
    },
    multiDelete:function (){
        //获取ids字符串
        var ids=this.mygrid.jqGrid("getGridParam","selarrrow");
        if(ids.length==0){
            dialog.alert("请勾选数据");
            return;
        }
        for(var i=0;i<ids.length;i++){
            var data= this.mygrid.jqGrid("getRowData",ids[i]);
            ids[i]=data["${table.pk.name}"];
        }
        //弹窗
        var that=this;
        var dialogid= dialog.confirm("确定删除数据:"+ids.join(","),function(){
            Ajax.post(PATH+"${table.baseUrl}/mdel.json?",{ids:ids.join(",")},function(result){
                result=ajaxResultHandler(result);
                if(result.r==AJAX_SUCC){
                    dialogid=dialog.alert("删除成功，数据："+ids.join(","),function(index){
                    that.mygrid.trigger("reloadGrid");
                    dialog.close(dialogid);
                });
                }else {
                    dialog.error(result.msg);
                }
            });
        });
    }
};
${abc}List.init()
</script>

