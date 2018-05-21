
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
        document.body.appendChild(div);

        tempCompId=   "component_instance_"+componentId+"_"+(Math.floor(Math.random()*100));
        Drag.dragStart({
            target:div,
            copy:false,
            dest:".dropable",
            start:function(){},
            dragging:function(x,y){},
            end:function(x,y){},
            }
        );
    }else{
         $(".dropable").append(div);

    }
    div.id= tempCompId;


    //div.id=   "component_instance_"+componentId+"_"+(Math.floor(Math.random()*100));

    // var  contanerId = "component_instance_container_"+componentId+"_"+(Math.floor(Math.random()*100));
    var  containerId = "component_instance_container_"+componentId+"_"+(Math.floor(Math.random()*100));//"+tempCompId;//componentId+"_"
    div.innerHTML="<div class='float-toolbar'><ul><li><a title='修改'><i class='fa fa-pencil'></i></a></li><li><a class='delBtn' title='删除'><i class='fa fa-trash'></i></a></li><li><a class='moveUp' title='上移'>上移<i class='fa fa-trash'></i></a></li><li><a class='moveDown' title='下移'>下移<i class='fa fa-trash'></i></a></li></ul></div><div class='component-container' id ="+containerId+"></div>";

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
var EditTemplateWrap = React.createClass({displayName: "EditTemplateWrap",
	getInitialState: function() {
	    return {
            titleEdit:false,
            title:"页面1",
             template:[{id:"1",name:"页面",active:true}]
	    };
	  },
      showTitleEdit:function(){
        //React.findDOMNode(this.refs.title).hide();
        //React.findDOMNode(this.refs.titleInput).show();
        ReactDOM.findDOMNode(this.refs.titleInput).style.display="block";
        ReactDOM.findDOMNode(this.refs.title).style.display="none";
        //this.setState({titleEdit:true});
        ReactDOM.findDOMNode(this.refs.titleInput).focus();
      },
    handleTitleChange:function(event){
        this.setState({title: event.target.value});

    },
     componentDidMount:function(){
            Ajax.getJSON(PATH+"/template/list.json",{},function(result){

                if(result.r==AJAX_SUCC){
                    this.setState({template:result.data});
                }
            }.Apply(this));
          },
    hideTitleEdit:function(){
        ReactDOM.findDOMNode(this.refs.titleInput).style.display="none";
        ReactDOM.findDOMNode(this.refs.title).style.display="block";
       // this.setState({titleEdit:false});
    },
    selectTemplate:function(id){
        console.log(id);
        var template =this.state.template;
       for(var i=0;i<template.length;i++){
            if(template[i].id== id){
               template[i].active=true;
            }else{
             template[i].active=false;
            }
       }
       Ajax.getJSON("/editorTempComp/list.json?templateId="+id,null,function(result){
         $(".dropable").empty();//清空其他模板的内容

console.log("后台获取的参数个数"+result.data.length);
            for(var i=0;i<result.data.length;i++){
             console.log("后台获取的参数");
                console.log(result.data[i]);
                var id = result.data[i].id;
                var componentId = result.data[i].componentId;

                addComponent(componentId,id,result.data[i].html,result.data[i].config);
            }
       }.bind(this));
     this.setState({"template":template});
        //$("#"+id).addClass("in");
    },
	  render: function() {
           var title =this.state.title;
          var page_title ;
          var page_title_block;
          var hideStyle ={display:'none'};
          if(this.state.titleEdit){//显示input
              page_title= React.createElement("span", {style: hideStyle, ref: "title"}, this.state.title);
              page_title_block=React.createElement("input", {type: "text", value: this.state.title, ref: "titleInput", onBlur: this.hideTitleEdit, onChange: this.handleTitleChange});
          }else{
              page_title= React.createElement("span", {ref: "title"}, this.state.title);
              page_title_block=React.createElement("input", {style: hideStyle, type: "text", value: this.state.title, ref: "titleInput", onBlur: this.hideTitleEdit, onChange: this.handleTitleChange});
          }
		  return (
            React.createElement("div", null, 
              React.createElement("div", {className: "page-reorder"}, 
                  React.createElement("ul", {className: "sortable"}, 
                      React.createElement("li", {className: "page-menu pageActive"}, 

                        
                          this.state.template.map(function(result,index){
                              return React.createElement("div", {onClick: this.selectTemplate.bind(this,result.id), key: index, id: result.id, className:  result.active ? 'tab-pane fade in  active ' : 'tab-pane fade '}, 
                                 React.createElement(PagePreView, null)
                              );
                          }.bind(this))
                      

                      )
                  )
              ), 
              React.createElement("div", {className: "add-blank-page"}, "+")
         )

        );
        }
    }
);

