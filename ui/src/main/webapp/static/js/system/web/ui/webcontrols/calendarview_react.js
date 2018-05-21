var CalendarView = React.createClass({
	getInitialState: function() {
	    return {
	    	index :"a1234",
	    	dummyDay: new Date(),//一个 用于翻页的日期 指向当前的日历页面中的某个日期
	    	selectedDay:new Date(),//用鼠标选中的日期
	    	today : new Date(),//今天
	    	hourHeight:40,
	    	vampires : [],
	    	preSelectedTd:null,
	    	valueStack :{},
	    	preSelectedTr:null,
	    	preMouseOverTd:null,
	    	preColumn:null,
	    	currColumn:null,
	    	timeSect :["00:00", "01:00", "02:00", "03:00", "04:00", "05:00",
	    				"06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00",
	    				"13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00",
	    				"20:00", "21:00", "22:00", "23:00"],
			weekName: [ "Sunday","Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
	    				 			"Saturday"],
 			selectMode : true,// true is week false is day
 			viewMode : 1,
 			currentEventId: "",
	    };
	  },
	  closeCalendarEventDialog:function(){
		  
	  },
	  render: function() {
		  return  <div><div id="mask" onClick={this.closeCalendarEventDialog}  ></div>
					<table >
					<tr>
						<td  >
							<button onClick={this.addEvent} >写日志</button>
						</td>
						<td  >
							<span  onClick={this.changeToDayView}>day</span>
							&nbsp;<span onClick={this.changeToWeekView}>week</span>
							&nbsp;<span onClick={this.changeToMonthView}>month</span>
						</td> 
					 </tr>
					 <tr> 
						<td >
							<div id='dp_canopy_div' className='dp_canopy_div' ><span className='h zippy-arrow2' unselectable='on'></span><span className='calHeaderSpace'>迷你日历</span></div>
							<div  	 id='dp_div' className='dp_div' >{ this.getDatePickerStr()} </div> 
						<h2 id='mycal_h2' className='mycal_h2'>
						<span className='mycal_arrow_sp'></span>
						<span  className='mycal_title_sp'>我的日历</span>
						<span className='mycal_menu_sp clstMenu'>...</span>
						</h2 >
						<div id='dp_types_div' className='dp_types_div'>
						<div className='dp_type_div' id=''><span  className='dp_types_icon_sp' ></span><span ></span>
						<span className='dp_types_title'>工作</span><span className='dp_types_arrow'></span></div>
						<div className='dp_type_div' id=''><span  className='dp_types_icon_sp' ></span><span ></span>
						<span className='dp_types_title'>生活</span><span className='dp_types_arrow'></span></div>
						<div className='dp_type_div' id=''><span  className='dp_types_icon_sp' ></span><span ></span>
						<span className='dp_types_title'>账务</span><span className='dp_types_arrow'></span></div>
					</div >
					</td>
					<td id="div_CalendarEventView" >
					 {this.getCalendarScheduleStr()} </td> 
					 </tr> 
				 </table> {this.getCalendarEventDialogStr()} </div>;
				
	  },
	// 界面的拆分--------------------------------------------------------------------------------------------------
	  getRowDraw : function() {
		  return <div  className="horizontal-line-container"><div  className="horizontal-line-container2">
		  {this.get24RowHourSperateBox()}
		  </div></div>
	  },
	  get24RowHourSperateBox:function(){
		  var arr=[];
		  	for (var i = 0; i < 24; i++) {
		  	arr.push(<div style={{height:this.state.hourHeight-1}} className="horizontal-line"><div style={{height:this.state.hourHeight/2-1}} ></div></div>);
		  	}
		  	return arr;
	  },
	  
	//显示类别--------------------------------------------------------------------------------------------------
	  displayMCalTypes : function() {
	  	/*testdata start**/
	  	var type={};
	  	type.id="321";
	  	type.color="#FFFFFF";
	  	type.name="我的日历";
	  	/*testdata end**/
	  return <div><span id={type.id} className='dp_types_title_icon' >{type.name}</span><span className='dp_types_title'></span><span className='dp_types_arrow'></span></div>
	  },

	  /*
	   * 
	   * 右侧布局
	   */
	 getCalendarScheduleStr :function() {
	  	// 周视图
	  	// style='border:solid 1px red'
	  	var now = new Date();
	  	var weekDays;
	  	if (this.state.viewMode == 0) {
	  		weekDays = [this.state.dummyDay];
	  	} else if (this.state.viewMode == 1) {
	  		weekDays = getDateOfWeek(this.state.dummyDay);
	  	} else if (this.state.viewMode == 2) {
	  		weekDays = getDateOfMonth(this.state.dummyDay);
	  	}
	  	global_weekdays = weekDays;
	  	// <b className="xb1" style="width:1002px; "></b><b className="xb2"
	  	// style="width:1006px; "></b><b className="xb3" style="width:1008px;
	  	// "></b><b className="xb4" style="width:1010px; "></b>
	  	return <div>
	  			<table className="firsttable" >
	  				<tr >
	  					<td >
	  						<div id="topcontainerwk" >
	  							<table className="wk-weektop"  >
	  								<tr>
	  									<td className="wk-tzlabel"  rowSpan="3">&nbsp;</td>
	  									{this.getWeekTitle(weekDays)}
	  	
	  									<td className="wk-dummyth" rowSpan="3">&nbsp;</td>
	  								</tr>
	  								<tr>
	  									<td className="wk-allday" colSpan="7" height="24px">
	  										<div id="weekViewAllDaywk" >
	  											<table className="innertoptable"  width="100%"  height="100%" >
	  												<tr  height="100%">
	  													{this.getWeekTitileBelow(weekDays)}
	  												</tr>
	  											</table>
	  										</div>
	  			
	  									</td>
	  								</tr>
	  							</table>
	  						</div>
	  					</td>
	  				</tr>
	  				<tr>
	  					<td>
	  						<div   className="wk-scrolltimedevents" id="scrolltimedeventswk" >

	  							<table   id="table2" className="table2" >
	  								<tbody>
	  									<tr ><td  className="td-time-pri"></td><td colSpan="7">{ this.getRowDraw() }</td></tr>
	  									<tr>
	  										<td  className="td-time-pri">
	  											{this.getTimeSec()}
	  										</td>
  											{this.getDayCol(weekDays)}
	  	
	  									</tr>
	  								</tbody>
	  							</table>
	  						</div>
	  					</td>
	  				</tr>
	  			</table>
	  			<b className="xb4" ></b>
	  			<b className="xb3" ></b>
	  			<b className="xb2" ></b>
	  			<b className="xb1"  ></b>
	  			</div>
	  },
	  
	  getWeekTitle:function(weekDays){
		  var arr=[];
		  for (var i = 0; i < weekDays.length; i++) {
		  		arr.push( <td  title="
		  				{ (weekDays[i].getMonth() + 1)}
		  				/
		  				{ weekDays[i].getDate()}
		  				 (
		  						 { this.state.weekName[weekDays[i].getDay()]}
		  				)"    >
		  				<div className="wk-dayname "><span className="ca-cdp20537 wk-daylink" >
		  				{ weekDays[i].getMonth() + 1 }/{ weekDays[i].getDate()}
		  				 ({ this.state.weekName[weekDays[i].getDay()]}
		  				)</span></div></td>);
		  	}
		  return arr;
	  },
	  getWeekTitileBelow:function(weekDays){
		  var arr=[];
			for (var i = 0; i < weekDays.length; i++) {
				arr.push(<td className="st-c st-s "  rowSpan="2"></td>);
		  	}
			return arr;
	  },
	  getTimeSec:function(){
		  var arr =[];
		  for (var i = 0; i < this.state.timeSect.length; i++) {
			  arr.push(<div style={{height:this.state.hourHeight-1}}  className="tg-time-pri" >{this.state.timeSect[i]}</div>);
		  	}
		  return arr;
	  },
	  //工作区的列
	  getDayCol:function(weekDays){
		  var arr=[];
		  for (var i = 0; i < weekDays.length; i++) {
		  		var css = "";
		  		if (eqToday(weekDays[i])) {
		  			css = " td-time-pri-today";
		  		} else {
		  			
		  		}
		  		var id= "td_"+weekDays[i].format('yyyy-MM-dd');
		  		arr .push(<td  id={id}
		  				className="{css}">
		  		<div onClick={this.createEvent}    className="div-tg-time"></div></td>);
		  	}
		  return arr;
	  
	  },
	  createEvent:function(e){
			// 该造成addEvent(calendarEvent)的方法

			// 得到当前鼠标位置

			var mouse_cur_p_x = e.clientX;
			var mouse_cur_p_y = e.clientY;

			var scrollTop = 0;
			if ($$("scrolltimedeventswk").scrollTop) {
				scrollTop = $$("scrolltimedeventswk").scrollTop;
			}
			var data_arr = this.getPositionFromMousePosition(mouse_cur_p_y, scrollTop);

			var top = data_arr[2];// 没用到
			// 先删除原来没有保存的
			this.deleteEventData("newEvent");

			// 界面的修改

			this.deleteEventBar("newEvent");

			e.cancelBubble = true;
			e = window.event || e;
			var B;
			e = e.srcElement || e.target;

			// 先设置它的position absolute 获取当前的div
			var div;
			var td;
			if (e.tagName == "DIV") {
				div = e;
				td = e.parentNode;
			} else if (e.tagName == "TD") {
				div = e.childNodes[0];
				td = e;
			} else

			{
				return;
			}
			var timeStart = data_arr[1];
			// console.log("timeStart:"+timeStart);
			var timeEnd = data_arr[0];// getElementsByTagName("div")
			/*
			 * var i; for ( i = 0, divs = td.childNodes; i < divs.length; i++) { if (div ==
			 * divs[i]) { timeStart = this.state.timeSect[i]; timeEnd = (i + 1) ==
			 * this.state.timeSect.length ? "24:00" : this.state.timeSect[i + 1]; break; } }
			 */
			this.currentEventId = "newEvent";
			var div_html=document.createElement("DIV");
			div_html.id = "event_newEvent";
			div_html.className = "calendarEventBar";
			var dl_html = document.createElement("DL");
			div_html.appendChild(dl_html);
			//dl_html.id = "event_newEvent";
			//dl_html.className = "calendarEventBar";
			dl_html.innerHTML = "<DT >"
					+ timeStart
					+ "~"
					+ timeEnd
					+ "</dt> "
					+ "<DD >&nbsp;</DD><div   onmouseup=\"Drag.dragEnd();\" onmousedown=\"Drag.drawStart(this)\">_</div>";

			// 创建新的event
			var ce = new calendarEvent();
			// document.createElement("div");

			ce.id = "newEvent";
			ce.title = "";
			ce.timeStart = timeStart;
			ce.timeEnd = timeEnd;

			ce.j = data_arr[3];
			ce.day = td.id.substr(3);
			// 入栈
			this.state.valueStack["event_newEvent"] = ce;

			div_html.style.position = "absolute";
			div_html.style.top = data_arr[2];
			div.appendChild(div_html);

			// newEvent.style.top=div.style.top;
			// getInfo(div).left;
			div_html.style.left = 0;
			// newEvent.style.top =-38;
			div_html.style.height = this.hourHeight-2;
			// td.appendChild(newEvent);
			var index = this.index;// ?
			this.showCalendarEventDialog(this.currentEventId);
			dl_html.attachEvent("ondblclick", function() {

						Instance(index).openCalendarEventDialog(div_html)
					});
			// onclick=\"Instance('" + this.index
			// + "').editCalendarEvent(this.id)\"
			div_html.attachEvent("onmousedown", function() {
						Drag.dragStart(div_html);
					});

			// 维护calendareventdata
			ce.startIndex = data_arr[3];
			ce.endIndex = data_arr[3] + 2;
			// ce.cellIndex=0;
			var parentTable = $$("table2").childNodes[0].childNodes[1];

			var mouseCurrentCell;
			for (i = 1; i < parentTable.cells.length; i++) {

				if (div == parentTable.cells[i].childNodes[0]) {

					mouseCurrentCell = i;

					break;
				}
			}
			// alert(mouseCurrentCell);
			ce.cellIndex = mouseCurrentCell;
			// ce.endIndex=0;
			ce.top = data_arr[2] - 18;
			ce.left = 0;
			ce.length = 2;
			// alert("开始："+ce.startIndex+"结束"+ce.endIndex);
			// this.adjust();
	  },
	  

	 getPositionFromMousePosition : function(tY, scrollTop) {
	  	// 获取时间段div
	  	var table2=$$("table2");
	  	var tbody= table2.childNodes[0];
	  	var tr1=tbody.childNodes[1];
	  	var td=null;
	  	var aDivTimeSect = tr1.cells[0].childNodes;
	  	var j = 0;
	  	var timeStart = "00:00";
	  	var timeEnd = "00:00";// getElementsByTagName("div")
	  	var pianyiliang;
	  	for (j = 0; j < aDivTimeSect.length; j++) {
	  		var td_info = Drag.getInfo(aDivTimeSect[j]);// 已经知道了是哪个div time
	  		// sect

	  		if ((tY + scrollTop) < td_info.bottom
	  				&& (tY + scrollTop) >= td_info.top) {

	  			if ((tY + scrollTop) < ((td_info.bottom + td_info.top) / 2)) {
	  				pianyiliang = td_info.top
	  						- Drag.getInfo(aDivTimeSect[0]).top;

	  				timeEnd = (j + 1) == this.state.timeSect.length
	  						? "24:00"
	  						: this.state.timeSect[j + 1];
	  				timeStart = this.state.timeSect[j];
	  				j = 2 * j;
	  			} else {
	  				pianyiliang = ((td_info.bottom + td_info.top) / 2 - Drag
	  						.getInfo(aDivTimeSect[0]).top);
	  				timeEnd = (j + 1) == this.state.timeSect.length
	  						? "24:00"
	  						: (this.state.timeSect[j + 1].substr(0, 3) + "30");
	  				timeStart = this.state.timeSect[j].substr(0, 3) + "30";
	  				j = 2 * j + 1;
	  			}
	  			break;
	  		}
	  	}
	  	return [timeEnd, timeStart, pianyiliang, j];// 偏移量 距离td的顶端的距离 j为第几格子 半小时一个格子

	  },
	  /**
	   * 对话框
	   * @returns {String}
	   */
	  getCalendarEventDialogStr :function() {
	  return <div onClick={this.stopPropa} id="calendarEventDialog" className="calendarEventDialog" ><table>
	  			<tr><td></td><td ><i onClick={this.closeCalendarEventDialog} className="bubbleclose" ></i></td></tr>
	  			<tr><td><span>DATE:</span></td><td ><span id="calendarEventDialog_date" >&nbsp;</span></td></tr>
	  			<tr><td><span>TITLE:</span></td><td><input type="text" id="calendarEventDialog_title"></input></td></tr>
	  			<tr><td><input type="button" value="save" onClick={this.saveCalendarEvent}></input></td><td>
	  			<input type="button" value="delete" onClick={this.deleteCalendarEvent}></input>
	  			</td></tr>
	  			</table></div>;
	  	
	  },
	  stopPropa:function(e){
		  try{e.stopPropagation();}catch(e1){e.cancelBubble=true;}
	  },
	  
	//minicalendar 左侧mini 日历
	  getDatePickerStr: function() {

	  	var today_y = this.state.today.getFullYear()
	  	var today_m = this.state.today.getMonth() + 1;
	  	var today_d = this.state.today.getDate();

	  	var y = this.state.dummyDay.getFullYear();
	  	var m = this.state.dummyDay.getMonth() + 1;
	  	var b_thisMonth = false;
	  	if (y == today_y && m == today_m)
	  		b_thisMonth = true;

	  	// var d=this.date.getDate();
	  	var _weekFirstDay = CaculateDaysWeekNum(y, m, 1);
	  	var _days = CaculateMonthDays(y, m);
	  	var pre_days=31;//上个月有几天
	  	var this_month_days=31;//这个月有几天
	  	this_month_days=CaculateMonthDays(y , m);
	  	if (m == 1) {
	  		 pre_days = CaculateMonthDays(y - 1, 12);

	  	} else {
	  		 pre_days = CaculateMonthDays(y, m - 1);
	  	}
	  	var _weekLastDay = CaculateDaysWeekNum(y, m, _days)
	  	var _index = new Number(0);
	  	var _date = new Number(1);
	  	var _rows = new Number(1);
	 	var pre_mon;
	  	var pre_year;
	  	if (m == 1) {
	  		var pre_mon = 12;
	  		pre_year = y - 1;
	  	} else {
	  		pre_mon = m - 1;
	  		pre_year = y;
	  	}
	  return <table   className="dp_table"  >
	  			<tr className='dp_title_tr'  >
	  			<th id='dp_title_fir_th' className='dp_title_fir_th' colSpan='5' ><span className="zippy-arrow" unselectable="on"></span><span> 
	  			 {y}
	  			年
	  			 {m}
	  			月</span></th>
	  			<th > <span id='dp_prev_month' onClick={this.dpPreMonth} className='dp_prev_month'></span> </th>
	  			<th  > <span id='dp_next_month' onClick={this.dpNextMonth}   className='dp_prev_month dp_next_month'> </span></th>
	  			</tr>
	  			<tr className="dp_week_title_tr"><th>一</th><th>二</th><th>三</th><th>四</th><th>五</th><th>六</th><th>七</th></tr>
	  	
	  			
{this.getDpDateRow(_weekFirstDay,pre_year,pre_mon,pre_days,this_month_days)}
	  	
	  	</table>;
	  	
	  	
	  },
	  getDpDateRow:function(_weekFirstDay,pre_year,pre_mon,pre_days,this_month_days){
		  var arr=[];
		  for(var i=0;i<6;i++){
			  arr.push(<tr>{this.getDpDateCol(_weekFirstDay,pre_year,pre_mon,pre_days,this_month_days,i)}</tr>);
		  }
		  return arr;
	  },
	  getDpDateCol:function(_weekFirstDay,pre_year,pre_mon,pre_days,this_month_days,row_index){
		  var dummy_mon =this.state.dummyDay.getMonth()+1;
		  var dummy_year =this.state.dummyDay.getFullYear();
		  var dummy_next_mon_year= dummy_mon==12? (dummy_year+1):dummy_year;
		  var dummy_next_mon=(dummy_mon+1)%12;
		  var _index=0;
		  var arr=[];
		  var todayStr= this.state.today.format("yyyy-MM-dd");
		  if(row_index==0){
		  	for (var i = 1; i < _weekFirstDay; i++) {
		  		var id = "dp-td-"+pre_year+"-"+pre_mon+"-"+(pre_days - _weekFirstDay + i + 1);
		  			arr.push(
		  					<td id={id} onClick={this.selectDateTd} className="othermonth">
		  							{pre_days - _weekFirstDay + i + 1} </td>
		  							
		  						);
		  					  		_index++;
		  					  	}  
		  					  	
		  						for (var i = _weekFirstDay; i <= 7; i++) {
		  							var id = "dp-td-"+dummy_year+"-"+dummy_mon+"-"+( i- _weekFirstDay+1);
		  							arr.push(
		  									<td id={id} >
		  											{  i- _weekFirstDay+1 } 
		  											</td>
		  										);
		  					  	} 
		  		}else {
		  			for(var i = 0; i <7; i++){
		  		  	
		  				if((7*row_index+1 - _weekFirstDay +i + 1)<=this_month_days){
		  					var id = "dp-td-"+dummy_year+"-"+dummy_mon+"-"+(7*row_index+1 - _weekFirstDay +i + 1);
		  					if((7*row_index+1 - _weekFirstDay +i + 1)== this.state.today.getDate() &&this.state.today.getMonth() ==(dummy_mon-1) && this.state.today.getFullYear() == pre_year ){
		  						arr.push(
					  					<td id={id} className="todayTd" 
					  							 onClick={this.selectDateTd} >
					  							{7*row_index+1 - _weekFirstDay +i + 1} </td>	);
		  					}else{
		  					arr.push(
				  					<td id={id}
				  							 onClick={this.selectDateTd} >
				  							{7*row_index+1 - _weekFirstDay +i + 1} </td>
				  							
				  						);
		  				}
		  				}else{
		  					
		  					var id = "dp-td-"+dummy_next_mon_year+"-"+dummy_next_mon+"-"+(7*row_index+1 - _weekFirstDay +i + 1-this_month_days);
		  					arr.push(
				  					<td id={id}
				  							 onClick={this.selectDateTd} className="othermonth">
				  							{7*row_index+1 - _weekFirstDay +i + 1-this_month_days}
				  							</td>
				  							
				  						);
		  				}
		  			}
		  		}

		  	return arr;
	  },
	// 数据的删除
	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	deleteEventData : function(id) {
		delete this.state.valueStack["event_" + id];
	},
	// 数据的得到------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// 根据坐标求时间区间

	getCalendarEvent : function(id) {
		return this.state.valueStack["event_" + id];
	},
getEventId : function() {
		var date = new Date();
		var id = "" + date.getFullYear() + (date.getMonth() + 1) + date.getDate()
				+ parseInt(Math.random() * 1000);
		return id;
	},
	  selectDateTd : function(e) {
		  var it= e.target;
		  if(e.target.tagName=="SPAN"){
			  it=it.parentNode;
		  }
		  
			var y = it.id.split("-")[2];
			var m = it.id.split("-")[3];
			var d = it.id.split("-")[4];

			var day = new Date();
			day.setYear(y);
			day.setMonth(m - 1);
			day.setDate(d);
			this.setState({dummyDay : day,selectedDay:day});
		},
		dpPreMonth:function(){
			this.setState({dummyDay:getPreMonth(this.state.dummyDay)});
		},
		dpNextMonth:function(){
			this.setState({dummyDay:getNextMonth(this.state.dummyDay)});
		},

		closeCalendarEventDialog : function() {
			if (this.currentEventId == "newEvent") {
				this.deleteEventBar("newEvent");
			}
			$$("calendarEventDialog").style.display = "none";
			$$("mask").style.display = "none";
		},
		deleteEventBar :function(id) {
			if ($$("event_" + id)) {
				$$("event_" + id).innerHTML = "";
				try {
					$$("event_" + id).removeNode();
				} catch (e) {
					$$("event_" + id).parentNode.removeChild($$("event_" + id));
				}
			}
		},
		showCalendarEventDialog : function(id) {
			// 增加判断使他最低不得超过table2的父亲div的下底线和左右边界还有上边界
			this.refreshCalendarEventDialog(id);
			$$("calendarEventDialog").style.display = "block";

			// $$("bb").value= getInfo($$("event_"+id)).top;
			// $$("cc").value= getInfo($$("event_"+id)).left;
			// $$("ee").value= $$("calendarEventDialog").style.height;
			// alert($$("calendarEventDialog").offsetHeight);
			// 获取 scroll的高度
			var scrollTop = 0;
			if ($$("scrolltimedeventswk").scrollTop) {
				scrollTop = $$("scrolltimedeventswk").scrollTop;
			}
			//if (id == "newEvent")
			//	id = "event_newEvent";
			var eventBarInfo=null;
			var event_id="";
			if(!isNull(id)&&id.substr(0,6)!="event_"){
				event_id = "event_"+id;
			}else{
				event_id = id;
			}
			eventBarInfo=getInfo($$(event_id));
			// $$("calendarEventDialog").style.top =
			// eventBarInfo.top-$$("calendarEventDialog").offsetHeight-scrollTop;
			$$("calendarEventDialog").style.left = eventBarInfo.left - 70;
			
			var bottom = eventBarInfo.top - $$("calendarEventDialog").offsetHeight
					- scrollTop;
			var table2ParentInfo = getInfo($$("scrolltimedeventswk"));
			if ((table2ParentInfo.bottom) < (eventBarInfo.top - scrollTop)) {
				$$("calendarEventDialog").style.top = table2ParentInfo.bottom
						- $$("calendarEventDialog").offsetHeight;
			} else {
				var top = eventBarInfo.top
				- $$("calendarEventDialog").offsetHeight - scrollTop;
				
				if(top<0){
					$$("calendarEventDialog").style.top = eventBarInfo.bottom-scrollTop;
				}else{
					$$("calendarEventDialog").style.top = top;
				}
			}

		},
		refreshCalendarEventDialog : function(id) {
			// 的到event从 堆栈中
			var ce = this.getCalendarEvent(id);
			
			if (ce == null)// || (typeof event) == "undefined"
				return;
			
			$$("calendarEventDialog_date").innerHTML = ce.day + " " + ce.timeStart + "~"
					+ ce.timeEnd;
			$$("calendarEventDialog_title").value = ce.title;
		},
		

		saveCalendarEvent : function(event) {
			
			// 数据修改
			var ce = this.getCalendarEvent(this.currentEventId);
			ce.title = $$("calendarEventDialog_title").value;
			if (ce.id == "newEvent") {

				this.currentEventId = this.getEventId();
				delete this.state.valueStack["event_newEvent"];
				ce.id = this.currentEventId;
				$$("event_newEvent").id = "event_" + ce.id;
			}
			
			this.saveCalendarEventData(ce);

//			this.valueStack["event_" + this.currentEventId] = ce;
			// alert(ce.title);
			// 界面修改
			this.refreshCalendarEventBar(this.currentEventId);
			this.closeCalendarEventDialog(this.currentEventId);
			// ajax保存数据
			
		}
});
React.render(
        <CalendarView name="John" />,
        document.getElementById('tt')
      );