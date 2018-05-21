var globalEntity={};
var RightBar = React.createClass({displayName: "RightBar",
	getInitialState: function() {
	    return {
            components:[],
            pageNo:1
	    };
    },
    selectBlcok:function(component){
addComponent(component.id,"",component.html,null);return;
        var dom =document.getElementById(component.id);
       // document.appendChild(document.createElement("div"));
//   alert(document.getElementById("header"));
//      var entity =  _Entity_(null,document.getElementById("header"));
//                         entity.render();

    if($("#script_entity_"+component.id).length==0){
        $(document).append("<script id='script_entity_"+component.id+"' type='text/javascript' src='"+"/static/js/editor/component/source/entity"
        +component.id+".js"+"'  charset='utf-8'> </script>");
        $(document).append("<script id='script_setting_"+component.id+"' type='text/javascript' src='"+"/static/js/editor/component/source/setting"
        +component.id+".js"+"'  charset='utf-8'> </script>");
loadStyles("/tmp/component"+component.id+".css");
       // $(document).append("<link rel='stylesheet' type='text/css' href='"+"/tmp/css/component"+component.id+".css'   id='css_"+component.id+"' />");
    }


    var div = document.createElement("div");
    //var entity =  _Entity_(null,document.getElementById("header"));
    document.body.appendChild(div);
    div.className="component-wrap";
    div.id=   "component_instance_"+component.id+"_"+(Math.floor(Math.random()*100));

    var  containerId = "component_instance_container_"+component.id+"_"+(Math.floor(Math.random()*100));
      //div.innerHTML="<div class='float-toolbar'><ul><li><a title='修改'><i class='fa fa-pencil'></i></a></li><li><a class='delBtn' title='删除'><i class='fa fa-trash'></i></a></li></ul></div><div class='component-container' id ="+contanerId+"></div>";

    div.innerHTML="<div class='float-toolbar'><ul><li><a title='修改'><i class='fa fa-pencil'></i></a></li><li><a class='delBtn' title='删除'><i class='fa fa-trash'></i></a></li><li><a class='moveUp' title='上移'>上移<i class='fa fa-trash'></i></a></li><li><a class='moveDown' title='下移'>下移<i class='fa fa-trash'></i></a></li></ul></div><div class='component-container' id ="+containerId+"></div>";

    var entity = eval("Entity"+component.id);
    $(div).find(".delBtn").click(function(){

        $(div).remove();
         globalEntity[div.id]=null;

    })
    var entityInstance=   entity.call(this,null,containerId);

  // // var entity = eval("component"+id+"(null,Drag.tdiv.id)");// _Entity_(null,Drag.tdiv.id);
    var entityReact= entityInstance;entityInstance.render();
     globalEntity[div.id]={id:div.id,componentId:component.id,entity:entityReact} ;
        var config = eval("Setting"+component.id);
        var configInstance=   config.call(this,entityReact,"config-wrap");
        div.onclick=function(){
        $(".component-select").removeClass("component-select");
        $(this).addClass("component-select");
        configInstance.render();

    /*  Drag.dragStart({
                 target:this,
                 copy:false,
                 dest:".dropable",
                 start:function(){},
                 dragging:function(x,y){},
                 end:function(x,y){},
                 }
             );*/
     }


        console.log(div);
        Drag.dragStart({
            target:div,
            copy:false,
            dest:".dropable",
            start:function(){},
            dragging:function(x,y){},
            end:function(x,y){},
            }
        );
    },
    componentDidMount:function(){
    /* for(var i in EntityStatck){
                               var id = "addable-block_1"+i;
                               $(".right-bar").append(  <div id={id} className="addable-block dragable addable" onClick={this.selectBlcok}></div>

                                                                                )
                             <div id={id} className="addable-block dragable addable" onClick={this.selectBlcok}>
                                EntityStatck[i]
                            </div>
                        }*/

        this.request();
        Drag.init();

    /* for(var key in EntityStatck.data){
        alert(EntityStatck.data[key]+"(null,document.getElementById('"+key+"'))");
                var entity =eval(EntityStatck.data[key]+"(null,document.getElementById('"+key+"'))");

           entity.render();
                }*/
    },
    request:function(){
        Ajax.getJSON("/component/list.json",{curPage:this.state.pageNo},function(result){
        for( var i=0;i<result.data.length;i++){
            result.data[i].face="/"+ result.data[i].face;
        }
               // alert(result.data.length);
            this.setState({components:result.data});
        }.bind(this))
    },
    goPrev:function(){

        if(this.state>=0){
            this.state.pageNo--;
        }
          this.request();

    },
    goNext:function(){
         this.state.pageNo++;
         this.request();

    },
    render: function() {

          // var results  ={"1":"1","2":"2"};
        var val = [];
/*            for(var key in this.state.EntityStatck.data){
            //console.log("key"+key);
                val.push({key:key,value:EntityStatck.get[key]});
            }

     return  <div key={index} className="addable-block dragable addable" onClick={this.selectBlcok.bind(this,{key})}>{key} </div>;
                              }.bind(this))}
            */
		  return (


            React.createElement("div", {className: "right-bar"}, 
            React.createElement("div", null, " ", React.createElement("button", {onClick: this.goPrev}, "prev"), React.createElement("button", {onClick: this.goNext}, "next")), 
            React.createElement("div", {id: "config-wrap", className: "config-wrap"}
                       		    ), 
                this.state.components.map(function(component,index){
                     return  React.createElement("div", {key: component.id, id: component.id, className: "addable-block dragable addable", onClick: this.selectBlcok.bind(this,component)}, 
                        React.createElement("img", {src: component.face}), component.name, 
                        React.createElement("div", null, component.name), " ", React.createElement("div", {className: "bottom-title-bar"}, component.remark)
                     );
                  }.bind(this))
            )


        );
        }
    }
);
