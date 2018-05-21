var globalEntity={};
var id =getQueryString("id");
Ajax.getJSON("/editorTempComp/list.json?templateId="+id,null,function(result){
         $(".dropable").empty();//清空其他模板的内容
            for(var i=0;i<result.data.length;i++){
               addComponent(result.data[i].componentId,result.data[i].id,result.data[i].html,result.data[i].config);
}
}.bind(this));



function addComponent(componentId,tempCompId,html,config){

    if($("#script_entity_"+componentId).length==0){

        if(StringUtil.isBlank(html)){
            $(document).append("<script id='script_entity_"+componentId+"' type='text/javascript' src='"+"/static/js/editor/component/source/entity"
            +componentId+".js"+"'  charset='utf-8'> </script>");
            $(document).append("<script id='script_setting_"+componentId+"' type='text/javascript' src='"+"/static/js/editor/component/source/setting"
            +componentId+".js"+"'  charset='utf-8'> </script>");
        }
          // $(document).append("<link rel='stylesheet' type='text/css' href='"+"/tmp/component"+componentId+".css'   id='css_"+componentId+"' />");
            //document.write("<link rel='stylesheet' href='"+data[i]+"' type='text/css' />");
            loadStyles("/tmp/component"+componentId+".css");
    }

   // console.log("div");
    var div = document.createElement("div");
    //var entity =  _Entity_(null,document.getElementById("header"));
    //
    var newFlag = false;
    if(StringUtil.isBlank(tempCompId)){
        newFlag=true;
    }
 div.className="component-wrap";
    if(newFlag){

    }else{
         $("#dropable").append(div);

    }
    div.id= tempCompId;


    //div.id=   "component_instance_"+componentId+"_"+(Math.floor(Math.random()*100));

    // var  contanerId = "component_instance_container_"+componentId+"_"+(Math.floor(Math.random()*100));
    var  containerId = "component_instance_container_"+componentId+"_"+(Math.floor(Math.random()*100));//"+tempCompId;//componentId+"_"
    div.innerHTML="<div class='component-container' id ="+containerId+"></div>";

    // console.log( $(div).find(".delBtn").length);
    $(div).find(".delBtn").click({id:div.id},function(event){
        //console.log("删除"+event.data.id);
        $("#"+event.data.id).remove();
    });
     $(div).find(".moveUp").click({id:div.id},function(event){
        var dom =$("#"+event.data.id);
        if($(dom).prev()){  console.log("up"+event.data.id);
            $(dom).after( $(dom).prev());
        }
    });
     $(div).find(".moveDown").click({id:div.id},function(event){
      //  console.log("down"+event.data.id);
        var dom =$("#"+event.data.id);
        if($(dom).next().length>0){
            $(dom).next().after($(dom));
        }
    })

    //console.log("初始化setting"+result.data[i].config);
    // console.log(eval('('+result.data[i].config+')'));
/*     Ajax.get(PATH+"/static/html/editor/source/entity"+componentId+".html", null, function(data) {


                    $("#"+containerId).html(result);
                    //$('.main').append(ibox.render(data,"新窗口"));
                    //if (typeof fun == 'function') fun();
                });*/
    if(!StringUtil.isBlank(html)){
    // Ajax.getJSON("/static/html/editor/source/entity"+componentId+".html")
        $("#"+containerId).html(html);
           globalEntity[div.id]={componentId:componentId,entity:{}} ;
        return;
    }
    /* if(StringUtil.isBlank(entity)){

    }*/

     var entityClass = eval("Entity"+componentId);
     if(!StringUtil.isBlank(config)){
        config = eval('('+config+')');
     }else{
        config=null;
     }

     console.log("containerId:"+containerId);
     console.log($("#"+containerId).length);
    var entityInstance=   entityClass.call(this,null,containerId);
    //
    // console.log("渲染"+containerId);
    // // var entity = eval("component"+id+"(null,Drag.tdiv.id)");// _Entity_(null,Drag.tdiv.id);

    var entityReact= entityInstance;
    entityInstance.render();
      console.log("渲染结束");
    // console.log(entityReact);;
     // console.log("渲染结束");
     globalEntity[div.id]={componentId:componentId,entity:entityReact} ;
    var config = eval("Setting"+componentId);
    var configInstance=   config.call(this,entityReact,"config-wrap");
    div.onclick=function(){
        $(".component-select").removeClass("component-select");
        $(this).addClass("component-select");
        configInstance.render();
    }
}