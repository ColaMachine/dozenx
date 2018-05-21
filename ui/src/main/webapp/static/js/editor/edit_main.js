var EditorMain = React.createClass({displayName: "EditorMain",
	getInitialState: function() {
	    return {

	    };
	  },
    preView:function(){
        var templateId  =  $(".pageActive>.tab-pane").filter(".in")[0].id;
        window.open("/static/html/editor/index.html?id="+templateId);
    },
    saveCurrentTemplate:function(){
        //console.log("当前选择模板长度:"+$(".pageActive>.tab-pane").filter(".in").length);
        console.log("当前选择模板:"+$(".pageActive>.tab-pane").filter(".in")[0].id);
        var templateId  =  $(".pageActive>.tab-pane").filter(".in")[0].id;

        var components ={templateId:templateId};
var data = [];
var i=0;
       $(".dropable>div").each(function(){
            var divId = $(this).attr("id");
            if(globalEntity[divId]!=null){
                 var componentsInstance={};
                 componentsInstance.index=i;
                 i++;
                 componentsInstance.id=divId;
                // componentsInstance.componentId=globalEntity[divId].templateId;
                 componentsInstance.componentId=globalEntity[divId].componentId;
                 //alert(divId);
                 if(divId.indexOf("component")>=0){
                    componentsInstance.id=null;
                    //alert("新家的");
                 }
                 console.log("_state");
                              console.log(globalEntity[divId].entity);
                 var state =globalEntity[divId].entity.state;
                 console.log("组件id"+componentsInstance.id);
                   // console.log("state:"+json2Str(state));
                    componentsInstance.state= JSON.stringify(state);
                    //components[componentsInstance.id]= json2Str(state);
//                 components.push(componentsInstance);
                data.push(componentsInstance);

            }

        });
        console.log(data);
        components.data=JSON.stringify(data);

        Ajax.post("/editorTempComp/msave1.json",components,function(result){
                            if(result.r==AJAX_SUCC){
                                alert("保存成功");
                            }
                        });

    },
	  render: function() {
          var that =this;
		  return (
              React.createElement("div", {className: "container-fluid"}, 
                  React.createElement("div", {className: "row body-container isplay:none"}, 
                      React.createElement("div", {className: "col-sm-12"}, 
                            React.createElement(EditorHead, null), 
                            React.createElement("div", {className: "left-container"}, 
                                React.createElement(PageNav, null), 
                                React.createElement("input", {type: "button", value: "保存", onClick: this.saveCurrentTemplate}), 
                                 React.createElement("input", {type: "button", value: "预览", onClick: this.preView}), 
                                React.createElement(EditorCanvas, null)
                            ), 
                            React.createElement(RightBar, null)
                      )
                  )

               )

        );
        }
    }
);
