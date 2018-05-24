<div id="${Abc}View" class="ibox float-e-margins">
    <form id="${Abc}ViewForm" class="form-horizontal" >
      <!--  <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title">${table.remark}查看</h4>
        </div>-->
        <div class="ibox-content">
            ${viewhtml}
            <div class="hr-line-dashed"></div>
            <div class="form-group">
                <div class="col-sm-4 col-sm-offset-2">
                  <button type="button" class="btn btn-white cancelBtn">取消</button>
                </div>
            </div>
        </div>

    </form>
</div><!-- /.modal-content -->

<!--<div class="body_title">| ${table.remark}编辑</div>
<form id="editForm" class="form-horizontal" method="post" action="/${abc}/save.json" enctype="multipart/form-data">
 ${viewhtml}
   <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
       <button type="button"   class="btn btn-default cancelBtn">返回</button>
    </div>
  </div>
</form>
</div>
</div>-->
<script type="text/javascript">
var ${abc}View={
    modal:true,
    windowIndex:null,
    root:$("#${table.name}View"),
    init:function(){
        this.windowIndex=dialog.windowIndex;
        var that = this;
        this.addEventListener();
        //获取传入参数
        if(!StringUtil.isBlank(getParam("id"))){
            Ajax.getJSON("${abc}/view.json?id="+getParam("id"),null,function(data){
                if(data.r==AJAX_SUCC){
                    fillJso2FormSpan("#${Abc}View",data.data.bean);
                    <#if editimgjs??>
                    $("#${editimgjs}").attr("src",data.data.bean.${editimgjs});
                    </#if>
                }else{
                    dialog.error("获取信息失败"+data.msg,function(index){
                        that.cancel();
                        dialog.close(index);
                    });
                }

            });

        }
    },
    loadData:function(){
        <#if reference??>
        ${reference}
        });
        </#if>
    },
    cancel:function(){
        if(${abc}View.modal){
            dialog.closeWindow("${abc}View");
        }else{
            goPage("${abc}/list.htm");
        }
    },
    addEventListener:function(){
        $(this.root).find(".cancelBtn").click(${abc}View.cancel);
    }
}
$(document).ready(function() {
    ${abc}View.init();
});
   

</script>