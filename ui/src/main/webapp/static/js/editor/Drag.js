
Drag = {
	dragged: false,
	drawed: false,
	//droptable:null,
	ao: null,
	tdiv: null,
	modifyBtn: document.getElementById("dragmodifybtn"),
	//lastModifyComponent:null,
	currentModifyComponent:null,
	modify:function(it){


	    if(this.currentModifyComponent){
            this.currentModifyComponent.style.borderWidth = "0px";
            this.currentModifyComponent.style.borderTop = "1px solid #3366cc";
            this.currentModifyComponent.style.zIndex = 1;
            this.currentModifyComponent.style.border = "1px solid red";
	    }
	     $("#drag_edit_move").unbind("click");
	     $("#drag_edit_move").click(function(){console.log("move btn click");
                        Drag.dragStart(it);
                        Drag.modifyBtn.style.display="none";
        });
        this.currentModifyComponent=it;
        it.style.filter = "alpha(opacity=70)";
        it.style.cursor = "move";
        it.style.border = "1px dashed red";
        var locationInfo = Drag.getInfo(it);
         this.modifyBtn.style.display="block";
        this.modifyBtn.style.left=locationInfo.right+"px";
        this.modifyBtn.style.top=locationInfo.bottom+"px";
	    //Drag.dragStart(this);
	},
	dragStart: function(option) {

	    var it = option.target;
	    console.log(it);;
        //Drag.droptable =Drag.getInfo( $(".dropable")[0]);
        //console.log(Drag.droptable);
		/*if(it.className.indexOf("dragable")!=-1  ){
		    console.log("dragstart");
		}else{
		    return;
		}*/
		/*Drag.ao = Event.srcElement ? Event.srcElement : arguments.callee.caller.arguments[0].target;
		if((Drag.ao.tagName == "DT") || (Drag.ao.tagName == "DD")) {
			Drag.ao = Drag.ao.offsetParent;
			Drag.ao.style.zIndex = -1;
		} else
			return;*/
        /*if(it.className.indexOf("dragable")==-1  ){
            return;
        }*/
		Drag.ao = it; //.offsetParent

		Drag.dragged = true;
		//Drag.ao.style.zIndex = -1;
		Drag.dragged = true; //替身
        var locationInfo =  Drag.getInfo(Drag.ao);
		if(option.copy  ){
		    Drag.tdiv = document.createElement("div");
		    document.body.appendChild(Drag.tdiv);
		    Drag.tdiv.id="component_instance_"+it.id+"_"+(Math.floor(Math.random()*100));
            Drag.tdiv.style.width = Drag.ao.offsetWidth;
            Drag.tdiv.style.height = Drag.ao.offsetHeight;
            Drag.tdiv.style.top = locationInfo.top;
            Drag.tdiv.style.left =locationInfo.left;
            // Drag.tdiv.className =;
             $(  Drag.tdiv).addClass( "dragOri");
		   /* document.body.appendChild(Drag.tdiv);*/
            console.log("addable");
           /* Drag.ao.style.border = "1px dashed red";
            Drag.tdiv = document.createElement("div");
                    document.body.appendChild(Drag.tdiv);

            Drag.tdiv.style.display = "block";
            Drag.tdiv.style.position = "absolute";
            Drag.tdiv.style.zIndex = 100;
            Drag.tdiv.style.filter = "alpha(opacity=70)";
            Drag.tdiv.style.cursor = "move";
            Drag.tdiv.style.border = "1px solid #000000";

*/
            //增加点击事件
            Drag.tdiv.onclick=function(event){

                 Drag.modify(this);
                //Drag.dragStart(this);
                event.cancelBubble=true;
                event.stopPropagation();
            }
//includeJS(["/tmp/component"+it.id+".js"]);
            if($("#script_comp_"+it.id).length==0){
                $(document).append("<script id='script_comp_"+it.id+"' type='text/javascript' src='"+"/tmp/component"+it.id+".js"+"'  charset='utf-8'> </script>");
            }//setTimeout("var entity=eval(component"+it.id+"(null,Drag.tdiv.id));entity.render()",1000);

            var entity=eval("component"+it.id+"(null,Drag.tdiv.id)");
            entity.render();
                    // var entity = eval("component"+id+"(null,Drag.tdiv.id)");// _Entity_(null,Drag.tdiv.id);
                    //  entity.render();

        }else{
            Drag.tdiv=Drag.ao;
            //Drag.tdiv.className= "dragOri";
             $(  Drag.tdiv).addClass( "dragOri");
           /* Drag.tdiv.style.display = "block";
            Drag.tdiv.style.position = "absolute";
            Drag.tdiv.style.zIndex = 100;
            Drag.tdiv.style.filter = "alpha(opacity=70)";
            Drag.tdiv.style.cursor = "move";
           Drag.ao.style.border = "1px dashed red";*/
            Drag.tdiv.style.width = Drag.ao.offsetWidth;
            Drag.tdiv.style.height = Drag.ao.offsetHeight;
            Drag.tdiv.style.top = locationInfo.top;
            Drag.tdiv.style.left =locationInfo.left;
             document.body.appendChild(Drag.tdiv);
        }

		if(Drag.tdiv.childNodes[0]) {
			Drag.tdiv.childNodes[0].style.position = "";
		};
		Drag.lastX = event.clientX;
		Drag.lastY = event.clientY;
		Drag.lastLeft = locationInfo.left;
		// Drag.getInfo(Drag.ao).left
		Drag.lastTop = locationInfo.top;
	//	alert(Drag.tdiv.style.left);
//console.log("Drag.lastLeft"+Drag.lastLeft);
	},
	/**
	 * 判断事件的源是否正确
	 * 更新标志位
	 * 赋值
	 *
	 */
	drawStart: function(it) {
		//一般来说要取parentNode才是真的
		if(it == null) {
			it = Event.srcElement ? Event.srcElement : arguments.callee.caller.arguments[0].target;
		} else {
		}
		//alert(arguments.callee.caller.arguments[0].target.tagName);
		//alert("drawstart");
		//alert(it.parentNode.parentNode.id );
		if(it.tagName == "DIV" && !Tool.isNull(it.parentNode.parentNode.id)) {

			Drag.ao = it.parentNode.parentNode;
			if(Drag.ao.id.substr(0, 6) == "event_") {
				Drag.ao.style.zIndex = 100;
				Drag.dragged = false;
				Drag.drawed = true;

			} else {
				alert("无法获取拖曳源");
				return;
			}
		} else
			return;
	},

	draging: function() { // 重要:判断MOUSE的位置
		if(Drag.ao == null) return;
		if(!Drag.dragged && !Drag.drawed) return;
		var tX = event.clientX;
		//$$("cc").value=tX;
		var tY = event.clientY;
		var id = Drag.ao.id;
		if(!Drag.dragged || Drag.ao == null)
			return;
        //console.log(Drag.lastLeft);
		Drag.tdiv.style.left = tX+5 +"px";//parseInt(Drag.lastLeft) + tX - Drag.lastX; -Drag.droptable.left
		Drag.tdiv.style.top = tY+5+"px";//parseInt(Drag.lastTop) + tY - Drag.lastY;-Drag.droptable.top

	},
	dragCancel:function(){
		if (!Drag.dragged   ||  Drag.ao ==null )
        			return;

        			console.log("cancel");
	   Drag.tdiv.parentNode.removeChild(Drag.tdiv);
	   //Drag.tdiv.remove();

	   this.clearAll();

	},
	clearAll:function(){
	     Drag.ao=null;
       Drag.tdiv=null;
       Drag.dragged = false;
       Drag.currentModifyComponent=null;
	},
	dragEnd: function() { //通用拖拉和移动
	 //console.log("dragEnd");
	if (!Drag.dragged   ||  Drag.ao ==null ||Drag.tdiv==null )
    			return;
		Drag.drawed = false;
		Drag.dragged = false;
		//console.log("drag end ");
this.currentModifyComponent=null;





				//Drag.mm = Drag.repos(150, 15);
				$(Drag.tdiv).removeClass("dragOri");
				Drag.tdiv.style.left=null;
				Drag.tdiv.style.top=null;
				/*Drag.ao.style.borderWidth = "0px";
				Drag.ao.style.borderTop = "1px solid #3366cc";
				Drag.ao.style.zIndex = 1;
				Drag.ao.style.border = "1px solid red";
				Drag.tdiv.style.borderWidth = "0px";*/
/*Drag.tdiv.className="dragable";*/

/*Drag.tdiv.style.left = "0px";
Drag.tdiv.style.top = "0px";
Drag.tdiv.style.display = "block";
Drag.tdiv.style.position = "relative";*/
/*Drag.tdiv.onmouseup=function(event){
Drag.dragStart(this);
event.cancelBubble=true;
event.stopPropagation();
}*/
	//$(".bg-layer")[0].appendChild(Drag.tdiv);
	console.log(event.srcElement);
	console.log(event.srcElement);
$(event.srcElement).append(Drag.tdiv);
	Drag.ao = null;
	Drag.tdiv = null;
	},
	getInfo: function(o) { // 取得坐标
	    //console.log("getInfo");

		var to = new Object();
		to.left = to.right = to.top = to.bottom = 0;
		var twidth = o.offsetWidth;
		var theight = o.offsetHeight;
        //&& o.offsetParent.className.indexOf("dropable")==-1
		while(o != document.body ) {
// console.log(o);
			to.left += o.offsetLeft;
			to.top += o.offsetTop;

			o = o.offsetParent;
			//console.log(o.className.indexOf("dropable")!=-1);

		}
		to.right = to.left + twidth;
		to.bottom = to.top + theight;
		return to;
	},
	repos: function(aa, ab) { //如果没有移动任何位置上就慢慢的反悔原来的位置
		var f = Drag.tdiv.filters.alpha.opacity; //报错 tdiv为null
		var tl = parseInt(Drag.getInfo(Drag.tdiv).left);
		var tt = parseInt(Drag.getInfo(Drag.tdiv).top);
		var kl = (tl - Drag.getInfo(Drag.ao).left) / ab;
		var kt = (tt - Drag.getInfo(Drag.ao).top) / ab;
		var kf = f / ab;
		return setInterval(function() {
			if(ab < 1) {
				clearInterval(Drag.mm);
				Drag.tdiv.removeNode(true);
				Drag.ao = null;
				return;
			}
			ab--;
			tl -= kl;
			tt -= kt;
			f -= kf;
			Drag.tdiv.style.left = parseInt(tl) + "px";
			Drag.tdiv.style.top = parseInt(tt) + "px";
			Drag.tdiv.filters.alpha.opacity = f;
		}, aa / ab)
	},
	init: function() { // 初始化
			//for ( var i = 1; i < parentTable.cells.length; i++) {
			//var subTables = parentTable.cells[i].getElementsByTagName("dl");
			//for ( var j = 0; j < subTables.length; j++) {
			// if(subTables[j].className!="dragTable")break;
			// subTables[j].rows[0].className="dragTR";
			// subTables[j].rows[0].attachEvent("onmousedown",Drag.dragStart);
			// subTables[j].attachEvent("onmousedown",Drag.dragStart);
			//}
			//}
			// 创建10个事件

			document.onmousemove = Drag.draging;

			  $(".col-sm-12")[0].onmouseup=function(event){
			    Drag.dragCancel();
            event.cancelBubble=true;
            event.stopPropagation();
			  } ;
			//document.onmouseup = Drag.dragCancel;
            $(".bg-layer")[0].onmouseup = function(event){
            Drag.dragEnd();
            event.cancelBubble=true;
            event.stopPropagation();
            }
		}
		//end of Object Drag
}


