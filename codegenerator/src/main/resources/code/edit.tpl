 <div class="ibox float-e-margins">
    <form id="${abc}EditForm" class="form-horizontal" method="post" action="${table.baseUrl}/save.json" enctype="multipart/form-data">
        <!--<div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title">${table.remark}编辑</h4>
        </div>-->
        <div class="ibox-content">
            ${edithtml}
            <#if table.mapper??>
            <div class="form-group">
        <label for="createtime" class="col-sm-2 control-label">${child.remark}:</label>
        <div class="col-sm-10 z-col-table">
            <ul id="treeDemo" class="ztree"></ul>
        </div>
       </div>
       </#if>
       <div class="hr-line-dashed"></div>
        <div class="form-group">
           <div class="col-sm-4 col-sm-offset-2">
               <button type="button"  class="btn btn-primary saveBtn">保存</button>
               <button type="button" class="btn btn-white cancelBtn">取消</button>
           </div>
        </div>
        </div>

    </form>
</div><!-- /.modal-content -->
        
<script type="text/javascript">



var ${abc}Edit={
    form:null,
    modal:true,
    windowIndex:null,
    formId:"#${abc}EditForm",
    <#if ueditor??>
       ue:null,
    </#if>
    validParam:{
        rules: {
           ${jsrules}
        },
        messages:{
           ${jsmsg}
        }
    },
    addEventListener:function(){
        this.form.find(".saveBtn").click(this.saveInfo.Apply(this));
        this.form.find(".cancelBtn").click(this.cancel.Apply(this));
    },
    init:function(){
        this.windowIndex=dialog.windowIndex;
        this.form=$(this.formId);
        this.addEventListener();
        $("#${abc}EditForm").validate(this.validParam);
        <#if editimgjs??>
        var imageUtil=new zImageUtil2({"input":"${editimgjs}"});
        </#if>
        var that =this;

        <#if ueditor??>
        UE.delEditor('editor');
        this.ue=UE.getEditor('editor');
        this.ue.addListener("ready", function () {
            that.loadData();
        });
        <#else>
            that.loadData();
        </#if>

    },
    loadData:function(){
        var that =this;
         <#if reference??>
        ${reference}
        </#if>
        Ajax.getJSON(PATH+"/${abc}/view.json?id="+getParam("id"),null,function(data){
            if(data.r==AJAX_SUCC){
                if(data.data.bean){
                    fillJso2Form("#${abc}EditForm",data.data.bean);
                    <#if ueditor??>
                    that.ue.setContent(data.data.bean.content);
                    </#if>
                    <#if editimgjs??>
                    $("#${editimgjs}").parent().find("img").attr("src",$("#${editimgjs}").val());
                    </#if>
                }
                <#if table.mapper??>
                if(data.data.childs){
                    var treeObj=$.fn.zTree.init($("#treeDemo"), setting, data.data.childs);
                    if(data.data.childMaps!=null){
                        for(var i=0;i<data.data.childMaps.length;i++){
                            var node = treeObj.getNodeByParam("id",data.data.childMaps[i].${table.mapper.childid});
                            treeObj.checkNode(node,true,true);
                        }
                    }
                }
                </#if>
            }else{
                dialog.error("获取信息失败"+data.msg,function(index){
                    goPage("${abc}/list.htm");
                    dialog.close(index);
                });
            }
        });
        <#if reference??>
        });
        </#if>
    },

    saveInfo:function(){
    var that =this;
 <#if ueditor??>
        $("#${ueditor}").val(this.ue.getContent());
 </#if>
        if (!$("#${abc}EditForm").valid())
            return;
        var jso = changeForm2Jso("#${abc}EditForm");
        <#if table.mapper??>
        var childids=[];
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var checkedNodes= treeObj.getCheckedNodes(true);
        for(var i=0;i<checkedNodes.length;i++){
            var obj=checkedNodes[i];
            if(!checkedNodes[i].isParent){
                childids.push(checkedNodes[i].id);
            }
        }
        if(childids.length==0){
         //$("#childs").parent().parent().find(".error").text("请选择相应权限");
          //flag=false;
        }
        jso.childids= childids.join(",");//alert(jso.childids);return;
        </#if>
        var dialogId=dialog.showWait();
        var that=this;
        Ajax.post(PATH+$("#${abc}EditForm").attr("action"),jso,function(data){
            dialog.hideWait(dialogId);
            if(data.r==0){
                dialog.alert(data.msg||"保存成功",function(index){
                    //goPage("${abc}/list.htm");

                    if(that.modal){
                        $("#${Abc}Grid").jqGrid("reloadGrid");
                    }
                    dialog.close(index);
                     that.cancel();
                });
            }else{
                dialog.error(data.msg);
            }
        },'json');
    },
    cancel:function(){
        if(this.modal){
           // $("#mymodal").modal("toggle");
            dialog.closeWindow(this.windowIndex);
        }else{
            goPage("${abc}/list.htm");
        }
    }
}
$(document).ready(function() {
    //获取传入参数
    ${abc}Edit.init();

});
</script>