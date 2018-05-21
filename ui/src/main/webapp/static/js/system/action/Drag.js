Drag = {
	dragged: false,
	drawed: false,
	ao: null,
	tdiv: null,
	dragStart: function(it) {
		/*console.log("dragstart");
		Drag.ao = Event.srcElement ? Event.srcElement : arguments.callee.caller.arguments[0].target;
		if((Drag.ao.tagName == "DT") || (Drag.ao.tagName == "DD")) {
			Drag.ao = Drag.ao.offsetParent;
			Drag.ao.style.zIndex = -1;
		} else
			return;*/
		Drag.ao = it; //.offsetParent
		Drag.dragged = true;
		/*Drag.ao.style.zIndex = -1;
		Drag.dragged = true; //替身
		Drag.tdiv = document.createElement("div");
		Drag.tdiv.innerHTML = Drag.ao.outerHTML;
		Drag.ao.style.border = "1px dashed red";
		Drag.tdiv.style.display = "block";
		Drag.tdiv.style.position = "absolute";
		Drag.tdiv.style.zIndex = 100;
		Drag.tdiv.style.filter = "alpha(opacity=70)";
		Drag.tdiv.style.cursor = "move";
		Drag.tdiv.style.border = "1px solid #000000";
		Drag.tdiv.style.width = Drag.ao.offsetWidth;
		Drag.tdiv.style.height = Drag.ao.offsetHeight;
		Drag.tdiv.style.top = Drag.getInfo(Drag.ao).top;
		Drag.tdiv.style.left = Drag.getInfo(Drag.ao).left;
		document.body.appendChild(Drag.tdiv);
		if(Drag.tdiv.childNodes[0]) {

			Drag.tdiv.childNodes[0].style.position = "";
		};
		Drag.lastX = event.clientX;
		Drag.lastY = event.clientY;
		Drag.lastLeft = Drag.tdiv.style.left;
		Drag.lastTop = Drag.tdiv.style.top;*/
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
		if(ca.viewMode == 2) return;
		var tX = event.clientX;
		//$$("cc").value=tX;

		var tY = event.clientY;

		var id = Drag.ao.id;
		var event_id = id.substr(6);

		//var ca = Instance("mz_6");
		var scrollTop = 0;
		if(ca.viewMode != 2) {
			if($$("scrolltimedeventswk").scrollTop) {
				scrollTop = $$("scrolltimedeventswk").scrollTop;
			}
		}
		var ce = ca.getCalendarEvent(event_id);
		//$$("cc").value=tY;
		if(Drag.drawed) {

			if(Drag.ao.tagName == "SPAN") {
				Drag.ao = Drag.ao.parentNode;
			}
			if(Drag.ao.tagName == "DL") {
				Drag.ao = Drag.ao.parentNode;
			}
			console.log("draw");
			ca.drawClanderEventTo(ce, tX, tY, Drag.ao, scrollTop);
			return;
		}

		if(!Drag.dragged || Drag.ao == null)
			return;

		//$$("cc").value=1;
		//判断当前鼠标的位置

		//判断是在哪个div里头 是不是移到另外一个div里了
		//Drag.tdiv.style.left = tX +"px";//parseInt(Drag.lastLeft) + tX - Drag.lastX;

		//Drag.tdiv.style.top = tY+"px";//parseInt(Drag.lastTop) + tY - Drag.lastY;
//console.log(Drag.tdiv);
		ca.moveClanderEventTo (ce, tX,tY,Drag.ao,scrollTop) ;

		//	$$("bb").value=i;
		//$$("cc").value=j;

		/*for(var i = 1; i < parentTable.cells.length; i++) {
			var parentCell = Drag.getInfo(parentTable.cells[i]);
			if(tX >= parentCell.left && tX <= parentCell.right && tY >= parentCell.top && tY <= parentCell.bottom) {
				var subTables = parentTable.cells[i].getElementsByTagName("table");
				if(subTables.length == 0) {
					if(tX >= parentCell.left && tX <= parentCell.right && tY >= parentCell.top && tY <= parentCell.bottom) {
						parentTable.cells[i].appendChild(Drag.ao);
					}
					break;
				}
				for(var j = 0; j < subTables.length; j++) {
					var subTable = Drag.getInfo(subTables[j]);
					if(tX >= subTable.left && tX <= subTable.right && tY >= subTable.top && tY <= subTable.bottom) {
						parentTable.cells[i].insertBefore(Drag.ao, subTables[j]);
						break;
					} else {
						parentTable.cells[i].appendChild(Drag.ao);
					}
				}
			}
		}*/
	},
	dragEnd: function() { //通用拖拉和移动
		Drag.drawed = false;
		Drag.dragged = false;
		console.log("drag end ");
		/*if (!Drag.dragged)
			return;*/
		console.log("drag end save changedDrag.ao");
		if(Drag.ao != null && Drag.ao.id != "event_newEvent")
			ca.saveChangedAction(Drag.ao);
		Drag.ao = null;

		/*
				//Drag.mm = Drag.repos(150, 15);
				Drag.ao.style.borderWidth = "0px";
				Drag.ao.style.borderTop = "1px solid #3366cc";
				Drag.ao.style.zIndex = 1;
				//Drag.ao.style.border = "1px solid red";
				//Drag.tdiv.style.borderWidth = "0px";
				// 修改该控件的外表 时间 修改valuestack 中的event 的date 和 timestart timeend
			// 先获得id
			var id = Drag.ao.id.substr(6);
			// alert("get ID is :"+id);
			var top = Drag.ao.style.top
			top = new Number(top.substr(0, top.length - 2));
			// alert("the top is :"+top);
			// 取整
			var index;

			var tY = Drag.getInfo(Drag.ao).top;
			if(!isNull(parentTable)){
				var aDivTimeSect = parentTable.cells[0].getElementsByTagName("DIV");
			}
			for ( var i = 0; i < aDivTimeSect.length; i++) {
				var parentCell = Drag.getInfo(aDivTimeSect[i]);// 已经知道了是哪个div time sect

				if (tY < parentCell.bottom && tY >= parentCell.top) {
					index = i;
					break;
				}
			}
			//根据top的值直接获取当前时间条所在区间

			// 获得calendar 对象
			var ca = Instance("mz_6");
			// 获得所在位置的时间 timeStart timeEnd
			var timeStart = ca.timeSect[index];
			var timeEnd = (index == (ca.timeSect - 1) ? "24:00"
					: ca.timeSect[index + 1]);
			// 获得所在位置的date
			var td = Drag.ao.parentNode;
			while (td.tagName != "TD") {
				td = td.parentNode;
			}
			var date = td.id.substr(3);
			// 获得event实例
			var ce = ca.getCalendarEvent(id);
			// alert("original timeStart:"+ce.timeStart);
			// 更新数据
			ce.timeStart = timeStart;
			ce.timeEnd = timeEnd;
			ce.day = date;
			// 更新外表
			ca.refreshCalendarEventBar(id);*/
	},
	getInfo: function(o) { // 取得坐标
		var to = new Object();
		to.left = to.right = to.top = to.bottom = 0;
		var twidth = o.offsetWidth;
		var theight = o.offsetHeight;
		while(o != document.body) {
			$$("aa").value = to.left;
			to.left += o.offsetLeft;
			to.top += o.offsetTop;

			o = o.offsetParent;
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
			document.onmouseup = Drag.dragEnd;
		}
		//end of Object Drag
}