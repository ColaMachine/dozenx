
function CalendarView() {
	// Calendar.call(this);
	// this.setHashCode();
	this.index = this.hashCode = "a1234";

	System._instances[this.hashCode] = this;
	//alert(System._instances[this.hashCode].index);
	this.count = 0;
	this.getUniqueId = function() {
		return "ca" + (t.count++).toString(36);
	};
	//this.selectedWeek = new Array(); 2014年2月24日 14:20:25 注释
	// variable
	this.dummyDay = new Date();//一个 用于翻页的日期 指向当前的日历页面中的某个日期
	this.selectedDay = new Date();//用鼠标选中的日期
	this.today = new Date();//今天

	this.sectHeight = 40;//configure 常量 用来设置高度
	this.vampires = [];
	this.preSelectedTd = null;//之前选中的td单位
	this.preSelectedTr = null;//之前选中的tr
	this.preMouseOverTd = null;//之前鼠标移动过的td
	this.eventStack = {};//事件堆栈 暂时没用

	this.preColumn = 0;
	this.currColumn = 0;
	this.valueStack = {};
	this.serverStack={};
	this.calTypes={};
	this.currentCalType=null;
	this.hourHeight=40;//一个小时有多高
	this.timeSect = ["00:00", "01:00", "02:00", "03:00", "04:00", "05:00",
			"06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00",
			"13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00",
			"20:00", "21:00", "22:00", "23:00"];
	this.weekName = [ "Sunday","Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
			"Saturday"];
	this.selectMode = true;// true is week false is day;
	this.viewMode = 1;// 0是天 1 是 周 2 是月;
	this.currentEventId = "";
	this.beforeStack ="";
	this.afterStack="";
}
//t = CalendarView;
//t.count = 0;
//t.getUniqueId = function() {
//	return "ca" + (t.count++).toString(36);
//};
//t.selectedWeek = new Array();
// variable
//t.dummyDay = new Date();//当前选中的时间
//t.selectedDay = new Date();
//t.today = new Date();

// get the view str start
// 界面的初始换--------------------------------------------------------------------------------------------------
CalendarView.prototype.render = function() {
	// var y = this.dummyDay.getFullYear();
	// var m = this.dummyDay.getMonth() + 1;
	var str = "";
	str = "<div id=\"mask\" onclick=\"Instance('"
			+ this.index
			+ "').closeCalendarEventDialog()\" style=\"z-index:100;display:none;position:absolute;height:200%;width:100%;background-color:#000000;filter:alpha  (opacity:20) ;\"></div>"
			+ "<table>"
			+ "<tr>"
			+ "<td  >"
			+ "<button onclick=\"Instance('"
			+ this.index
			+ "').addEvent(null);event.cancelBubble=true;\">写日志</button>"
			+ "</td>"
			+ "<td  >"
            + "<button onclick=\"Instance('"
            + this.index
            + "').datePickerGoTodayAction();event.cancelBubble=true;\">今天</button>"
             //+ "</td>"
			+ "<div style='float:right' class='ca-ty-ch-wrp' align=right>"
			+ "<a  id='ca-ty-day' onclick=\"Instance('"
			+ this.index
			+ "').changeToDayView()\">day</a><a id='ca-ty-week'  onclick=\"Instance('"
			+ this.index + "').changeToWeekView()\">week</a>"
			+ "<a id='ca-ty-month' onclick=\"Instance('" + this.index
			+ "').changeToMonthAction()\">month</a>"

			+ "<a id='ca-ty-month' onclick=\"Instance('" + this.index
            			+ "').changeLongAction()\">long</a>"
            	+ "<a id='ca-ty-month' onclick=\"Instance('" + this.index
                        			+ "').changeListAction()\">list</a>"
			+ "</div>" +"</td>"+ "</tr>"
			+ "<tr>" + "<td  style=\"padding-top:50px\">"
			+ "<div id='dp_canopy_div' class='dp_canopy_div' style='display:none'><span class='h zippy-arrow2' unselectable='on'></span><span class='calHeaderSpace'>迷你日历</span></div>"
			+"<div  id='dp_div' class='dp_div'  >"

			//minicalendar
			 + this.getDatePickerView() + "</div>"
			+"<h2 id='mycal_h2' class='mycal_h2'>"
			+	"<span class='mycal_arrow_sp'></span>"
			+	"<span  class='mycal_title_sp'>我的日历</span>"
			+	"<span class='mycal_menu_sp clstMenu'>...</span>"
			+"</h2 >"
			+"<div id='dp_types_div' class='dp_types_div'>"
				+	"<div class='dp_type_div' id=''><span  class='dp_types_icon_sp' style='background-color:red'></span><span style='width:3px'></span>"
				+"<span class='dp_types_title'>工作</span><span class='dp_types_arrow'></span></div>"
				+	"<div class='dp_type_div' id=''><span  class='dp_types_icon_sp' style='background-color:yellow'></span><span style='width:3px'></span>"
				+"<span class='dp_types_title'>生活</span><span class='dp_types_arrow'></span></div>"
				+	"<div class='dp_type_div' id=''><span  class='dp_types_icon_sp' style='background-color:blue'></span><span style='width:3px'></span>"
				+"<span class='dp_types_title'>账务</span><span class='dp_types_arrow'></span></div>"

			+"</div >"
			+ "</td>"
			+ "<td id=div_CalendarEventView_" + this.index + ">"
			+ this.getCalendarScheduleView() + "</td>" + "</tr>" + "</table>"
			+ this.getCalendarEventDialogView();//
	return str;
};

// 界面的拆分--------------------------------------------------------------------------------------------------
CalendarView.prototype.getRowDrawView = function() {
	var str = "";
	str = "<div  class=\"horizontal-line-container\"><div  class=\"horizontal-line-container2\">";
	for (var i = 0; i < 24; i++) {
		str += "<div style='height:"+(this.hourHeight-1)+"' class=\"horizontal-line\"><div style='height:"+(this.hourHeight/2-1)+"px' ></div></div>";
	}
	str += "</div></div>";
	return str;
};
//deprecated
//显示类别--------------------------------------------------------------------------------------------------
CalendarView.prototype.getMCalTypesView = function() {
	/*testdata start**/
	var type={};
	type.id="321";
	type.color="#FFFFFF";
	type.name="我的日历";
	/*testdata end**/
	var calendar_type_list =$$("calendar_type_list");
	var div =document.createElement("div");
	div.innerHTML="<span id='"+type.id+"' class='dp_types_title_icon' style='background-color:'>"+type.name+"</span><span class='dp_types_title'></span><span class='dp_types_arrow'></span>"
};
/*
 *
 * 右侧日历主体内容布局
 */
CalendarView.prototype.getCalendarScheduleView = function() {console.log("getCalendarScheduleView begin");
	// 周视图
	// style='border:solid 1px red'
	var now = new Date();
	var str = "";
	var weekDays;
	if (this.viewMode == 0) {
		weekDays = [this.dummyDay];
	} else if (this.viewMode == 1) {
		weekDays = getDateOfWeek(this.dummyDay);
	} else if (this.viewMode == 2) {
		//weekDays = getDateOfMonth(this.dummyDay);
		return this.getMonthView();
	}else if (this.viewMode == 3) {
     		//weekDays = getDateOfMonth(this.dummyDay);
     		return this.getLongView();
    }else if (this.viewMode == 4) {
          		//weekDays = getDateOfMonth(this.dummyDay);
          		return this.getListView();//这里只返回了无数据的页面
          		//数据要等异步加载之后才有
         }

	global_weekdays = weekDays;
	// <b class=\"xb1\" style=\"width:1002px; \"></b><b class=\"xb2\"
	// style=\"width:1006px; \"></b><b class=\"xb3\" style=\"width:1008px;
	// \"></b><b class=\"xb4\" style=\"width:1010px; \"></b>
	str = ""
			+ "<table class='firsttable'  >"
			+ "<tr >"
			+ "<td >"
			+ "<div id='topcontainerwk' style=\"\" >"// div 日历右半边最外层的div
			+ "<TABLE class='wk-weektop' >"
			+ "<tr>"
			+ "<td class='wk-tzlabel' style=\"WIdtH: 76px; height:3px\" rowSpan=3>&nbsp;</td>";

	for (var i = 0; i < weekDays.length; i++) {
		str += "<td  id='h_td_"+weekDays[i].format("yyyy-MM-dd")+"' title=\""
				+ (weekDays[i].getMonth() + 1)
				+ "/"
				+ weekDays[i].getDate()
				+ " ("
				+ this.weekName[weekDays[i].getDay()]
				+ ")\"  align=\"center\" >"
				+ "<div class=\"wk-dayname style1\"><SPAN class=\"ca-cdp20537 wk-daylink\" style=\"CURSOR: pointer\">"
				+ (weekDays[i].getMonth() + 1) + "/" + weekDays[i].getDate()
				+ " (" + this.weekName[weekDays[i].getDay()]
				+ ")</SPAN></div></td>";
	}
	str += "<td class=\"wk-dummyth\" style=\"WIdtH: 16px\" rowSpan=3>&nbsp;</td>"
			+ "</tr>"
			+ "<tr>"
			+ "<td class=\"wk-allday\" colSpan=7 height=\"24px\">"
			+ "<div id=\"weekViewAllDaywk\" style=\"height:100%\">"
			+ "<TABLE class=\"innertoptable\" width=\"100%\"  height=\"100%\" >"
			+ "<tr  height=\"100%\">";
	for (var i = 0; i < weekDays.length; i++) {
		str += "<td id='m_td_"+weekDays[i].format("yyyy-MM-dd")+"'  class=\"st-c st-s \"  rowSpan=2></td>";
	}
	str += "</tr>"
			+ "</TABLE>"
			+ "</div>"
			+ "<!--whole day event end -->"
			+ "</td>"
			+ "</tr>"
			+ "</table>"
			+ "</div>"
			+ "</td>"
			+ "</tr>"
			+ "<tr>"
			+ "<td>"
			+ "<div   class=\"wk-scrolltimedevents\" id=\"scrolltimedeventswk\" >"

			+ "<table   id='table2' class='table2 table-no-break ' >"
			+ "<tbody>" + "<tr >" + "<td  class='td-time-pri' >" + "</td>"
			+ "<td colSpan='7'>" + this.getRowDrawView() + "</td>" + "</tr>"
			+ "<tr id='wb-main-tr'>"

			+ "<td    >";

	for (var i = 0; i < this.timeSect.length; i++) {
		str += "<div style='height:"+(this.hourHeight-1)+"'  class='tg-time-pri' >" + this.timeSect[i] + "</div>";
	}
	str += "</td>";

	for (var i = 0; i < weekDays.length; i++) {
		var css = "";
		if (weekDays[i].getFullYear() == now.getFullYear()
				&& weekDays[i].getMonth() == now.getMonth()
				&& weekDays[i].getDate() == now.getDate()) {
			css = " class=\"td-time-pri-today\"";
		} else {
            //css = " class=\"td-time-pri-not-today\"";
		}
		str += "<td  id=\"td_"
				+ weekDays[i].format("yyyy-MM-dd") + "\" " + css+ (this.viewMode == 0?" colspan='7'" :" ")
				+ "   align=\"center\">";
		for (var j = 0; j < this.timeSect.length; j++) {
			// str += "<div class=\"datediv\" style='width:100%' >&nbsp;</div>";
			// str += "&nbsp";
		}
		str += "<div onclick=\"Instance('" + this.index
				+ "').createEventAction(event) \"  class=\"div-tg-time\"></div></td>";

	}
	str += "</tr></tbody></table></div></td></tr></table><b class=\"xb4\" style=\"width:1010px; \"></b><b class=\"xb3\" style=\"width:1007px; \"></b><b class=\"xb2\" style=\"width:1006px; \"></b><b class=\"xb1\" style=\"width:1002px; \"></b>";
	return str;
};
/**
 * 对话框
 * @returns {String}
 */
CalendarView.prototype.getCalendarEventDialogView = function() {
	var str = "<div onclick=\"try{event.stopPropagation();}catch(e){event.cancelBubble=true;}\" id=\"calendarEventDialog\" class=\"calendarEventDialog\" style=\"display:none\"><table>"
			+ "<tr><td></td><td align=right><i onclick=\"Instance('"
			+ this.index
			+ "').closeCalendarEventDialog()\" class=\"bubbleclose\" ></i></td></tr>"
			+ "<tr><td><span>DATE:</span></td><td ><span id=\"calendarEventDialog_date_start\" ondblclick='showCalendar(this)'>123</span><span id=\"calendarEventDialog_date_end\" ondblclick='showCalendar(this)'>123</span></td></tr>"
			+ "<tr><td><span>TITLE:</span></td><td><input type=\"text\" id=\"calendarEventDialog_title\"></input></td></tr>"
			+ "<tr><td><input type=\"button\" value=\"save\" onclick=\"Instance('"
			+ this.index
			+ "').saveCalendarEventAction()\"></input></td><td>"
			+ "<input type=\"button\" value=\"delete\" onclick=\"Instance('"
			+ this.index
			+ "').deleteCalendarEventAction()\"></input>"
            + "<input type=\"button\" value=\"edit\" onclick=\"Instance('"
            + this.index
            + "').editCalendarEventAction()\"></input>"
			+ "</td></tr>"
			+ "</table></div>";
	return str;
};
//minicalendar 左侧mini 日历
CalendarView.prototype.getDatePickerView = function() {

	var today_y = this.today.getFullYear()
	var today_m = this.today.getMonth() + 1;
	var today_d = this.today.getDate();

	var y = this.dummyDay.getFullYear();
	var m = this.dummyDay.getMonth() + 1;
	var b_thisMonth = false;
	if (y == today_y && m == today_m)
		b_thisMonth = true;

	// var d=this.date.getDate();
	var _weekFirstDay = CaculateDaysWeekNum(y, m, 1);//这月的第一天是星期几
	var _days = CaculateMonthDays(y, m);

	if (m == 1) {
		var pre_days = CaculateMonthDays(y - 1, 12);

	} else {
		var pre_days = CaculateMonthDays(y, m - 1);
	}
	var _weekLastDay = CaculateDaysWeekNum(y, m, _days)
	var _index =0;
	var _date = 1;
	var _rows =1;
	var str = "<table  class='dp_table'  >"
			+ "<tr class='dp_title_tr'  >"
			+ "<th id='dp_ym_th' class='dp_ym_th' colspan=5 ><span class='zippy-arrow' unselectable='on'></span><span> "
			+ y
			+ "年"
			+ m
			+ "月</span></th>"
			+ "<th > <i id='dp_prev_month' onClick='Instance(\""+this.index+"\").datePickerGoPrevMonthAction()' class='dp_prev_month'></i> </th>"
			+ "<th  > <i id='dp_next_month' onClick='Instance(\""+this.index+"\").datePickerGoNextMonthAction()' class='dp_prev_month dp_next_month'> </i></th>"
			+ "</tr>"
			+ "<tr class='dp_week_title_tr'><th>一</th><th>二</th><th>三</th><th>四</th><th>五</th><th>六</th><th>七</th></tr>"
			+ "<tr>";
	var pre_mon;
	var pre_year;
	if (m == 1) {
		var pre_mon = 12;
		pre_year = y - 1;
	} else {
		pre_mon = m - 1;
		pre_year = y;
	}
	//计算出包含上月的第一行日期行
	for (var i = 1; i < _weekFirstDay; i++) {
		str += "<td id='dp-mini-" + pre_year + "-" + pre_mon + "-"
				+ (pre_days - _weekFirstDay + i + 1)
				+ "' onclick=\"Instance('" + this.index
				+ "').selectDateTdAction(this)\" class='othermonth'>"
				+ (pre_days - _weekFirstDay + i + 1) + "</td>";
		_index++;
	}
	if (_index == 7)
		str += "</tr>";

	for (var i = 0; i < _days; i++) {
		if (_index % 7 == 0 && _index > 0) {
			str += "<tr>";
			_rows++;
		}
		str += "<td  "
				+ ((b_thisMonth && today_d == _date)
						? "class='todayTd' "
						: "") + "  onclick=\"Instance('" + this.index
				+ "').dpClickAction(event)\">"
				+ (_date < 10 ? ("&nbsp;" + _date) : _date) + "</td>";
		_index++;
		_date++;// td中的字符表示日期
		if (_index % 7 == 0)
			str += "</tr>";
	}
	var k = 1;
	if (m == 12) {
		m = 1;
		y++;
	} else {
		m++;
	}
	for (var i = _weekLastDay + 1; i <= 7; i++) {

		str += "<td id='calendar-mini-" + y + "-" + m + "-" + (k)
				+ "' class='othermonth' onclick=\"Instance('" + this.index
				+ "').selectDateTdAction(this)\">" + k + "</td>";
		k++;
	}

	str += "</tr>";

	if (_rows < 6) {
		str += "<tr>";
		for (var i = 0; i < 7; i++) {

			str += "<td id='calendar-mini-" + y + "-" + m + "-" + (k++)
					+ "' class='othermonth' onclick=\"Instance('"
					+ this.index + "').selectDateTdAction(this) \">" + (k - 1)
					+ "</td>";
		}
		str += "</tr>";
		_rows++;
	}
	str += "</table>";

	return str;
};


//日历主体内容切换到月视图
CalendarView.prototype.getMonthView = function() {

	var today_y = this.today.getFullYear();
	var today_m = this.today.getMonth() + 1;
	var today_d = this.today.getDate();

	var y = this.dummyDay.getFullYear();
	var m = this.dummyDay.getMonth() + 1;
	var b_thisMonth = false;
	if (y == today_y && m == today_m)
		b_thisMonth = true;

	// var d=this.date.getDate();
	var _weekFirstDay = CaculateDaysWeekNum(y, m, 1);//这月的第一天是星期几
	var _days = CaculateMonthDays(y, m);

	if (m == 1) {
		var pre_days = CaculateMonthDays(y - 1, 12);

	} else {
		var pre_days = CaculateMonthDays(y, m - 1);
	}
	var _weekLastDay = CaculateDaysWeekNum(y, m, _days);
	var _index =0;
	var _date = 1;
	var _rows =1;
	var str = "<table  class='wk_mv_table wk-scrolltimedevents'  id='scrolltimedeventswk'>"
			+ "<tr class='dp_title_tr'  >"
			+ "<th id='dp_ym_th' class='dp_ym_th' colspan=5 ><span class='zippy-arrow' unselectable='on'></span><span> "
			+ y
			+ "年"
			+ m
			+ "月</span></th>"
			+ "<th > <i id='dp_prev_month' onClick='Instance(\""+this.index+"\").datePickerGoPrevMonthAction()' class='dp_prev_month'></i> </th>"
			+ "<th  > <i id='dp_next_month' onClick='Instance(\""+this.index+"\").datePickerGoNextMonthAction()' class='dp_prev_month dp_next_month'> </i></th>"
			+ "</tr>"
			+ "<tr class='dp_week_title_tr'><th>一</th><th>二</th><th>三</th><th>四</th><th>五</th><th>六</th><th>七</th></tr>"
			+ "<tr>";
	var pre_mon;
	var pre_year;
	if (m == 1) {
		var pre_mon = 12;
		pre_year = y - 1;
	} else {
		pre_mon = m - 1;
		pre_year = y;
	}
	//计算出包含上月的第一行日期行
	var first_block_date=new Date();
	first_block_date.setFullYear(pre_year);
	first_block_date.setMonth(pre_mon-1);
	first_block_date.setDate(pre_days-_weekFirstDay+2);
	for (var i = 1; i < _weekFirstDay; i++) {
		str += "<td id='td_" +first_block_date.format("yyyy-MM-dd")
				+ "' onclick=\"Instance('" + this.index
				+ "')\" class='othermonth'>"


				+ (pre_days - _weekFirstDay + i + 1) + "</td>";
		_index++;
		DateAddInSelf(first_block_date,1);
	}
	if (_index == 7)
		str += "</tr>";

	for (var i = 0; i < _days; i++) {
		if (_index % 7 == 0 && _index > 0) {
			str += "<tr>";
			_rows++;
		}
		str += "<td   id='td_"  +first_block_date.format("yyyy-MM-dd")

                       				+ "'"
				+ ((b_thisMonth && today_d == _date)
						? "class='todayTd' "
						: "") + "  onclick=\"Instance('" + this.index
				+ "')\">"+ (_date < 10 ? ("&nbsp;" + _date) : _date)

				 + "</td>";
		_index++;DateAddInSelf(first_block_date,1);
		_date++;// td中的字符表示日期
		if (_index % 7 == 0)
			str += "</tr>";
	}
	var k = 1;
	if (m == 12) {
		m = 1;
		y++;
	} else {
		m++;
	}
	for (var i = _weekLastDay + 1; i <= 7; i++) {

		str += "<td id='td_"  +first_block_date.format("yyyy-MM-dd")

                                           				+ "'"
				+ "' class='othermonth' onclick=\"Instance('" + this.index
				+ "')\">" 	+  k+"</td>";
		k++;
		DateAddInSelf(first_block_date,1);
	}

	str += "</tr>";

	if (_rows < 6) {
		str += "<tr>";
		for (var i = 0; i < 7; i++) {
            k++;
			str += "<td  id='td_"  +first_block_date.format("yyyy-MM-dd")
					+ "' class='othermonth' onclick=\"Instance('"
					+ this.index + "')\">" + (k - 1)
					+ "</td>";	DateAddInSelf(first_block_date,1);
		}
		str += "</tr>";
		_rows++;
	}
	str += "</table>";

	return str;
};



//日历主体内容切换到月视图
CalendarView.prototype.getLongView = function() {

  //  Ajax.getJSON("/activity/getActivitys.json",null,function(result){
   /* var finalData ={};
    for(var i=0;i<result.data.length;i++){
        var rowData = result.data[i];
        var title =rowData.title;
        var userName =rowData.userName;
        var ary =title.split("#");
        var mainTitle = ary[0];
        var subTitle = ary[1];
        if(finalData[mainTitle]){
        }else{
        finalData[mainTitle]=new Array();

        }
        var json = {};
        json.mainTitle = mainTitle;
         json.subTitle = subTitle;
          json.userName = userName;
           json.startTime = rowData.startTime;
           json.endTime = rowData.endTime;
        finalData[mainTitle].push(json);

    }
    var data =result.data;*/
	var today_y = this.today.getFullYear();
	var today_m = this.today.getMonth() + 1;
	var today_d = this.today.getDate();

	var y = this.dummyDay.getFullYear();
	var m = this.dummyDay.getMonth() + 1;
	var b_thisMonth = false;
	if (y == today_y && m == today_m)
		b_thisMonth = true;
    var loopDate = new Date(this.dummyDay.getTime());
    loopDate.setDate(1);
	// var d=this.date.getDate();
	var _weekFirstDay = CaculateDaysWeekNum(y, m, 1);//这月的第一天是星期几
	var _days = CaculateMonthDays(y, m);

	if (m == 1) {
		var pre_days = CaculateMonthDays(y - 1, 12);

	} else {
		var pre_days = CaculateMonthDays(y, m - 1);
	}
	var _weekLastDay = CaculateDaysWeekNum(y, m, _days);
	var _index =0;
	var _date = 1;
	var _rows =1;
	var str = "<table  class='wk_mv_table wk-scrolltimedevents'  id='scrolltimedeventswk'>"
			+ "<tr class='dp_title_tr'  >"
			+ "<th id='dp_ym_th' class='dp_ym_th' colspan=5 ><span class='zippy-arrow' unselectable='on'></span><span> "
			+ y
			+ "年"
			+ m
			+ "月</span></th>"
			+ "<th > <i id='dp_prev_month' onClick='Instance(\""+this.index+"\").datePickerGoPrevMonthAction()' class='dp_prev_month'></i> </th>"
			+ "<th  > <i id='dp_next_month' onClick='Instance(\""+this.index+"\").datePickerGoNextMonthAction()' class='dp_prev_month dp_next_month'> </i></th>"
			+ "</tr>"

			+ "<tr class='dp_week_title_tr'><th>主标题</th><th>副标题</th><th>人员</th>";

    for(var i=0;i<30;i++){
        str+="<th>"+loopDate.getDate()+"</th>";
        loopDate.setDate(loopDate.getDate()+1);
    }
    str+= "</tr>";
    str+= "<tr>";

	//计算出包含上月的第一行日期行
/*
 for(var mainTitle in finalData){
    var list = finalData[mainTitle];

      for(var i=0;i<list.length;i++){
       str+="<td>"+list.mainTitle+"</td>";
        str+="<td>"+list.subTitle+"</td>";
            str+="<td>"+list.userName+"</td>";
         for(var j=0;j<30;j++){
            str+="<td>"+j+"</td>";
         }
      }
    }*/
    str+= "</tr>";
	str += "</table>";
	return str;

};



//日历主体内容切换到月视图
CalendarView.prototype.getListView = function() {alert(123);

	var today_y = this.today.getFullYear();
	var today_m = this.today.getMonth() + 1;
	var today_d = this.today.getDate();

	var y = this.dummyDay.getFullYear();
	var m = this.dummyDay.getMonth() + 1;
	var b_thisMonth = false;
	if (y == today_y && m == today_m)
		b_thisMonth = true;
    var loopDate = new Date(this.dummyDay.getTime());
    loopDate.setDate(1);
	// var d=this.date.getDate();
	var _weekFirstDay = CaculateDaysWeekNum(y, m, 1);//这月的第一天是星期几
	var _days = CaculateMonthDays(y, m);

	if (m == 1) {
		var pre_days = CaculateMonthDays(y - 1, 12);

	} else {
		var pre_days = CaculateMonthDays(y, m - 1);
	}
	var _weekLastDay = CaculateDaysWeekNum(y, m, _days);
	var _index =0;
	var _date = 1;
	var _rows =1;
	var str = "<table  class='wk_mv_table wk-scrolltimedevents'  id='scrolltimedeventswk'>"
			+ "<tr class='dp_title_tr'  >"
			+ "<th id='dp_ym_th' class='dp_ym_th' colspan=5 ><span class='zippy-arrow' unselectable='on'></span><span> "
			+ y
			+ "年"
			+ m
			+ "月</span></th>"
			+ "<th > <i id='dp_prev_month' onClick='Instance(\""+this.index+"\").datePickerGoPrevMonthAction()' class='dp_prev_month'></i> </th>"
			+ "<th  > <i id='dp_next_month' onClick='Instance(\""+this.index+"\").datePickerGoNextMonthAction()' class='dp_prev_month dp_next_month'> </i></th>"
			+ "</tr>"

			+ "<tr class='dp_week_title_tr'><th>主标题</th><th>副标题</th><th>人员</th>";

    for(var i=0;i<30;i++){
        str+="<th>"+loopDate.getDate()+"</th>";
        loopDate.setDate(loopDate.getDate()+1);
    }
    str+= "</tr>";
    str+= "<tr>";


    str+= "</tr>";
	str += "</table>";
	return str;

};

// get the view str end
/**
*deprecate
*/
CalendarView.prototype.displaySingleEvent = function(ce) {
	this.valueStack["event_" + ce.id] = ce;
	var title = ce.title;
	var date = ce.startDay;
	var startTimeSV = ce.startTimeSV;
	var endTimeSV = ce.endTimeSV;
	//alert(ce.startTime);
	// 根据日期找到td 根据kssj 结束时间 找到div 根据结束时间决定buttom的值
	var td = document.getElementById("td_" + date);
	if ("undefined" == (typeof td) || td == null)
		return;

	var div_html=document.createElement("div");

	var dl_html = document.createElement("dl");
	div_html.appendChild(dl_html);

	dl_html.innerHTML = "<dt>"
			+ startTimeSV
			+ "~"
			+ endTimeSV
			+ "</dt> "
			+ "<DD  >"
			+ title
			+ "</DD><div  onclick=\"\" /*onmouseup=\"Drag.dragEnd(this);\"*/ onmousedown=\"Drag.drawStart(this)\">_</div>";

	td.childNodes[0].appendChild(div_html);

	// 修改id
	div_html.id = "event_" + ce.id;
	// 修改样式 确定位置
	div_html.className = "calendarEventBar";
	var position = this.timeSectToPosition(startTimeSV, endTimeSV, date);
	div_html.style.position = "absolute";



	/*
	 * div_html.style.position = "absolute";
	div_html.style.top = data_arr[2];
	div.appendChild(div_html);

	// newEvent.style.top=div.style.top;
	// getInfo(div).left;
	div_html.style.left = 0;
	// newEvent.style.top =-38;
	div_html.style.height = this.hourHeight-2;
	 */
	div_html.style.top = position[0]-getInfo($$("table2")).top+"px";
	div_html.style.height = position[1]+"px";
	div_html.style.left =0+"px";
	//div_html.style.width = 10;
	//div_html.style.right = position[2] + position[3];
	div_html.attachEvent("onmousedown", function(event){Drag.dragStart(event.srcElement)},true);
	index = this.index;
	div_html.attachEvent("ondblclick", function() {
				Instance(index).openCalendarEventDialog(div_html);
			});
};

/**
*deprecate
*/
CalendarView.prototype.displayAllEvent = function(ces) {
	for (var ce in ces) {
		this.displaySingleEvent(ce);
	}
};
//actions------------------------------------------
// 大的动作
// 可以拆分为多个连续性的一系列界面类动作---------------------------------------------------------------------------------------------
CalendarView.prototype.editEventAction = function(e) {// 分解此方法

};
// 大的动作
// 可以拆分为多个连续性的一系列界面类动作---------------------------------------------------------------------------------------------
CalendarView.prototype.createEventInMonthViewAction = function(e) {// 分解此方法
	e = e.srcElement || e.target;
    e.cancelBubble=true;
    try{event.stopPropagation();}catch(e){event.cancelBubble=true;}
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


    this.deleteEventDataService("newEvent");
    	// 界面的修改
    this.deleteEventBarAction("newEvent");

    var ce = new calendarEvent();
    	// document.createElement("div");

    ce.id = "newEvent";
    this.currentEventId="newEvent";
    ce.title = "";
    ce.startTime = 0;
    ce.endTime = 60*60;
 ce.startTimeSV = "00:00";
  ce.endTimeSV = "01:00";
  ce.startDay=td.id.substr(3);
   ce.endDay=td.id.substr(3);
    this.valueStack["event_newEvent"] = ce;

    var div_html =this.calendarEventRenderWithOutStyle(ce);
    console.log(div);
    div.appendChild(div_html);
    this.showCalendarEventDialogView("event_newEvent");

}
CalendarView.prototype.createEventAction = function(e) {// 分解此方法
													// 该造成addEvent(calendarEvent)的方法
	// 得到当前鼠标位置

	var mouse_cur_p_x = event.clientX;
	var mouse_cur_p_y = event.clientY;

	var scrollTop = 0;
	if ($$("scrolltimedeventswk").scrollTop) {
		scrollTop = $$("scrolltimedeventswk").scrollTop;
	}

	var data_arr = this.getPositionFromMousePosition(mouse_cur_p_y, scrollTop);
	//创建模型
	//模型转视图
	//渲染


	var top = data_arr[2];// 没用到
	// 先删除原来没有保存的
	this.deleteEventDataService("newEvent");

	// 界面的修改

	this.deleteEventBarAction("newEvent");

	e.cancelBubble = true;
	e = window.event || e;

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
	var startTimeSV = data_arr[1];
//	 console.log("startTimeSV:"+startTimeSV);
	var endTimeSV = data_arr[0];// getElementsByTagName("div")
	/*
	 * var i; for ( i = 0, divs = td.childNodes; i < divs.length; i++) { if (div ==
	 * divs[i]) { startTimeSV = this.timeSect[i]; endTimeSV = (i + 1) ==
	 * this.timeSect.length ? "24:00" : this.timeSect[i + 1]; break; } }
	 */
	this.currentEventId = "newEvent";

	// 创建新的event
	var ce = new calendarEvent();
	// document.createElement("div");

	ce.id = "newEvent";
	ce.title = "";
	ce.startTimeSV = startTimeSV;
	ce.endTimeSV = endTimeSV;

	ce.j = data_arr[3];
	ce.endDay =ce.day=ce.startDay = td.id.substr(3);


	// 入栈
	this.valueStack["event_newEvent"] = ce;
	// 维护calendareventdata
	ce.startIndex = data_arr[3];
	ce.endIndex = data_arr[3] + 2;
	// ce.cellIndex=0;
	//the second tr
	var parentTable = $$("table2").childNodes[0].childNodes[1];

	var mouseCurrentCell;
	/**foreach ever column ervery week day**/
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
	// create the html and bind the drag
	var div_html =this.calendarEventRender(ce);
	div.appendChild(div_html);
	/*var div_html=document.createElement("div");
	div_html.id = "event_newEvent";
	div_html.className = "calendarEventBar";
	var dl_html = document.createElement("dl");
	div_html.appendChild(dl_html);
	//dl_html.id = "event_newEvent";
	//dl_html.className = "calendarEventBar";
	dl_html.innerHTML = "<dt >"
			+ startTimeSV
			+ "~"
			+ endTimeSV
			+ "</dt> "
			+ "<DD >&nbsp;</DD><div   onmouseup=\"Drag.dragEnd();\" onmousedown=\"Drag.drawStart(this)\">_</div>";
	div_html.style.position = "absolute";
	div_html.style.top = data_arr[2]+"px";
	div.appendChild(div_html);
	// newEvent.style.top=div.style.top;
	// getInfo(div).left;
	div_html.style.left = 0+"px";
	// newEvent.style.top =-38;
	div_html.style.height = this.hourHeight-2+"px";
	// td.appendChild(newEvent);
	var index = this.index;// ?
*/	this.showCalendarEventDialogView(this.currentEventId);

/*	dl_html.attachEvent("ondblclick", function() {
		Instance(index).openCalendarEventDialog(div_html)
	});
	// onclick=\"Instance('" + this.index
	// + "').editCalendarEvent(this.id)\"
	div_html.attachEvent("onmousedown", function() {
		Drag.dragStart(div_html);
	});
*/

};
CalendarView.prototype.rejustPositionAndShape = function(calendarEvent) {

};
CalendarView.prototype.datePickerGoPrevMonthAction = function(e) {
	this.dummyDay=getPreMonth(this.dummyDay);
	this.refreshDatePickerView();
	//重新绑定事件
	/*bind($$("dp_prev_month"),'click',new Function("Instance('"
			+ this.index
			+ "').datePickerGoPrevMonthAction()"));
	bind($$("dp_next_month"),'click',new Function("Instance('"
			+ this.index
			+ "').datePickerGoNextMonthAction ()"));*/
}
CalendarView.prototype.datePickerGoNextMonthAction = function(e) {
	this.dummyDay=getNextMonth(this.dummyDay);
	this.refreshDatePickerView();
	//重新绑定事件
	/*bind($$("dp_prev_month"),'click',new Function("Instance('"
			+ this.index
			+ "').datePickerGoPrevMonthAction()"));
	bind($$("dp_next_month"),'click',new Function("Instance('"
			+ this.index
			+ "').datePickerGoNextMonthAction ()"));*/
}
CalendarView.prototype.datePickerGoTodayAction = function(e) {
	this.dummyDay=new Date();
	this.refreshDatePickerView();
	this.refreshCalendarWorkbenchView();
	//重新绑定事件
	/*bind($$("dp_prev_month"),'click',new Function("Instance('"
			+ this.index
			+ "').datePickerGoPrevMonthAction()"));
	bind($$("dp_next_month"),'click',new Function("Instance('"
			+ this.index
			+ "').datePickerGoNextMonthAction ()"));*/
}
CalendarView.prototype.dpClickAction = function(e) {
	// //console.log("进入clickhandle");
	e = window.event || e;
	var B;
	e = e.srcElement || e.target;

	switch (e.tagName) {
		/*case "TH" :// 选择月份
		{
			if (0 <= e.innerHTML.indexOf("<<")
					|| 0 <= e.innerHTML.indexOf("&gt;&gt;")) {
				this.goNextMonth();
				this.refreshView();
			} else if (0 <= e.innerHTML.indexOf(">>")
					|| 0 <= e.innerHTML.indexOf("&lt;&lt;")) {
				this.goPreMonth();
				this.refreshView();
			}
		}
			break;*/
		case "TD" : // 选择日期
		{
			var bToday = (this.selectedDay != null
					&& this.selectedDay.getFullYear() == this.today
							.getFullYear()
					&& this.selectedDay.getMonth() == this.today.getMonth() && this.selectedDay
					.getDate() == this.today.getDate());
			// edit the value of dummyDay and selectedDay
			// edit the css previous selected Day and edit the current selected
			// Day
			// if previous selected day == selected Day selectedMode change
			// if
			this.selectedDay = this.dummyDay;
			this.selectedDay.setDate(new Number(e.innerHTML.replace("&nbsp;",
					"")));
			this.dummyDay = this.selectedDay;

			var curSelectedTd = e;
			var curSeletedTr = (e.parentNode ? e.parentNode : null);

			if (null != this.preSelectedTd
					&& "undefined" != (typeof this.preSelectedTd)) {
				if (curSelectedTd == this.preSelectedTd) {
					this.selectMode = !this.selectMode;
				}
			}
			// cancel css
			if (null != this.preSelectedTd
					&& "undefined" != (typeof this.preSelectedTd)) {

				this.preSelectedTr.className = "";

				this.preSelectedTd.className = (!bToday ? "" : "todayTd");

			}
			// add css

			curSelectedTd.className = "selectedTd";
			curSeletedTr.className = (this.selectMode ? "selectedTr" : "");
			// appointed td tr
			this.preSelectedTr = e.parentNode;
			this.preSelectedTd = e;
			this.refreshCalendarWorkbenchView();
		}
			break;
		default :

			break;
	}
};
CalendarView.prototype.onMouseOverHandle = function(e) {
};
CalendarView.prototype.onDblClickHandle = function(e) {

};

CalendarView.prototype.selectDateTdAction = function(it) {
	var y = it.id.split("-")[2];
	var m = it.id.split("-")[3];
	var d = it.id.split("-")[4];

	var day = new Date();
	day.setYear(y);
	day.setMonth(m - 1);
	day.setDate(d);
	this.dummyDay = day;
	this.selectDate(day);
	this.refreshCalendarWorkbenchView();

};
CalendarView.prototype.selectDate = function(day) {
	// 重新设定dummyday selectedDay 重新
	this.dummyDay = day;
	this.selectedDay = day;
	this.refreshDatePickerView();

};

CalendarView.prototype.saveCalendarEventAction = function(event) {

	// 数据修改
	var ce = this.getCalendarEvent(this.currentEventId);

	ce.title = $$("calendarEventDialog_title").value;
	if (ce.id == "newEvent") {

		this.currentEventId = this.getEventId();
		delete this.valueStack["event_newEvent"];
		ce.id = this.currentEventId;
		$$("event_newEvent").id = "event_" + ce.id;
	}
	if($$("calendarEventDialog_date_start").innerHTML.length==10){
ce.startDay=$$("calendarEventDialog_date_start").innerHTML;
	}
	if($$("calendarEventDialog_date_end").innerHTML.length==10){
ce.endDay=$$("calendarEventDialog_date_end").innerHTML;
    	}

	this.saveCalendarEventDataService(ce);

//	this.valueStack["event_" + this.currentEventId] = ce;
	// alert(ce.title);
	// 界面修改
	this.refreshCalendarEventBarView(this.currentEventId);
	this.closeCalendarEventDialog(this.currentEventId);
	// ajax保存数据

};

CalendarView.prototype.editCalendarEventAction = function(it) {// alert(231);

	// 数据修改
	var ce = this.getCalendarEvent(it.id);
	$$("calendarEventDialog_title").value = ce.title;
	this.showCalendarEventDialog(this.currentEventId);
	// 显示编辑框
	this.refreshCalendarEventDialogView(this.currentEventId);

};

CalendarView.prototype.deleteCalendarEventAction = function(event) {
	// 数据的修改

	this.deleteEventDataService(this.currentEventId);

	// 界面的修改

	this.deleteEventBarAction(this.currentEventId);
	this.closeCalendarEventDialog();
};

CalendarView.prototype.alert = function() {
	var a = this.positionToTimesect(event.clientY, 200);
	// alert("startTimeSV:"+a[0]+"endTimeSV:"+a[1]);
};
CalendarView.prototype.openCalendarEventDialog = function(it) {
	// get the dl element's id
	/*
	 * e = window.event || e; var B; e = e.srcElement || e.target; //
	 * 先设置它的position absolute 获取当前的div // 如果e.tagName="div"的话可能是新建 if (e.tagName ==
	 * "DD" || e.tagName == "dt") { e = e.parentNode; } else if (e.tagName ==
	 * "dl") {
	 *  } else { return; }
	 */
	this.currentEventId = it.id.substr(6);
	var ce = this.getCalendarEvent(this.currentEventId);
	$$("calendarEventDialog_date_start").innerHTML = ce.startDay + " " + ce.startTimeSV ;
	$$("calendarEventDialog_date_end").innerHTML = ce.endDay + " " + ce.endTimeSV;

	$$("calendarEventDialog_title").value = ce.title;
	// get event information from stack
	// select the current event
	// edit the content of calendarEventDialog2
	this.showCalendarEventDialogView(it.id);
};
CalendarView.prototype.changeToDayView = function() {
 if(this.viewMode==0)return;
	this.viewMode = 0;
	this.refreshCalendarWorkbenchView();return;
	/*var eles= $$("wb-main-tr").getElementsByTagName("td");
    var daystr="td_"+this.dummyDay.format("yyyy-MM-dd");
	for(var i=1;i<eles.length;i++){
        if(eles[i].id!=daystr){
            eles[i].style.display="none";
            $$("h_"+eles[i].id).style.display="none";
            $$("m_"+eles[i].id).style.display="none";
        }else{ eles[i].colSpan=7;
             $$("h_"+eles[i].id).colSpan=7;
             $$("m_"+eles[i].id).colSpan=7;
        }
	}*/

};

CalendarView.prototype.changeToWeekView = function() {

	 if(this.viewMode==1)return;
    	this.viewMode = 1;

    	this.refreshCalendarWorkbenchView();
    	console.log(this.viewMode);
/*
    	var eles= $$("wb-main-tr").getElementsByTagName("td");

            var daystr="td_"+this.dummyDay.format("yyyy-MM-dd");

        	for(var i=1;i<eles.length;i++){
                if(eles[i].id!=daystr){
                    eles[i].removeAttribute("style");
                  $$("h_"+eles[i].id).removeAttribute("style");
                    $$("m_"+eles[i].id).removeAttribute("style");
                }else{
                       $$("h_"+eles[i].id).removeAttribute("colspan");
                      $$("m_"+eles[i].id).removeAttribute("colspan");
                        eles[i].removeAttribute("colspan");
                }
        	}*/

};
/**
*转成月视图
*/
CalendarView.prototype.changeToMonthAction = function() {console.log("enter change to monthview")
console.log("viewMode:"+this.viewMode)
 if(this.viewMode==2)return;
    	this.viewMode = 2;
	this.refreshCalendarWorkbenchView();return;

};
/**
*转成以事情为主的视图
*/
CalendarView.prototype.changeLongAction = function() {console.log("enter change to monthview")
console.log("viewMode:"+this.viewMode)
 if(this.viewMode==3)return;
    	this.viewMode = 3;
	this.refreshCalendarWorkbenchView();return;

};


/**
*转成以事情为主的视图
*/
CalendarView.prototype.changeListAction = function() {console.log("enter change to monthview")
console.log("viewMode:"+this.viewMode)
 if(this.viewMode==4)return;
    	this.viewMode = 4;
	this.refreshCalendarWorkbenchView();return;

};
// 界面的刷新--------------------------------------------------------------------------------------------------------------------------
//refreshDatePickerView
CalendarView.prototype.refreshDatePickerView = function() {
	$$("dp_div" ).innerHTML = this.getDatePickerView();
	this.clear();
	//this.init();
};
CalendarView.prototype.refreshCalendarWorkbenchView = function() {console.log("refreshCalendarWorkbenchView begin");

	$$("div_CalendarEventView_" + this.index).innerHTML = this.getCalendarScheduleView();
	  document.getElementsByClassName("ca-ty-ch-wrp")[0].getElementsByClassName("active")[0].removeAttribute("class","active");
    	document.getElementsByClassName("ca-ty-ch-wrp")[0].childNodes[this.viewMode].setAttribute("class","active");
this.loadEventsView();
this.addEventListener();

//添加+ "<div onclick=\"Instance('" + this.index
                    				//+ "').createEventInMonthViewAction(event) \"  class=\"div-tg-time\"></div>"
if(this.viewMode==2){
var that =this;

    var tds=$$("scrolltimedeventswk").getElementsByTagName("td");
   // alert(tds.length)
    for(var i=0;i<tds.length;i++){
        var div=document.createElement("div");
        //div.addEventListener("onclick",)
        bind(div,'click',function(event){that.createEventInMonthViewAction(event);});
        div.className="div-tg-time";
       // console.log(tds[i]);
        tds[i].appendChild(div);
        div.appendChild(tds[i].childNodes[0]);
    }
    }
			if(this.viewMode==2)return;
	document.getElementById("scrolltimedeventswk").style.height = window.innerHeight
			- 120+"px";
			//console.log(document.getElementsByClassName("ca-ty-ch-wrp")[0].childNodes);
  	//document.getElementsByClassName("ca-ty-ch-wrp")[0].childNodes[this.viewMode].addClass("active");

	//重新加载时间

};
// data2view

CalendarView.prototype.refreshCalendarEventBarView = function(id) {

	// 的到event从 堆栈中
	// 如果id对应的HTML对象存在的话 更新内容 并 重新定位位置
	if ($$("event_" + id)) {
		var ce = this.getCalendarEvent(id);
		if (ce == null || (typeof ce) == "undefined")
			return;
		var dts = $$("event_" + id).getElementsByTagName("dt");
		if (dts != null && dts != "undefined" && dts.length && dts.length > 0) {
			dts[0].innerHTML = ce.startTimeSV + "~" + ce.endTimeSV;
		}
		var dds = $$("event_" + id).getElementsByTagName("DD");
		if (dds != null && dds != "undefined" && dds.length && dds.length > 0) {
			dds[0].innerHTML = ce.title;
		}
		// 开始重新定位
		var start_index = 0;
		var end_index = 0;
		// 以下的计算和drag里的代码重复了
		for (var i = 0; i < this.timeSect.length; i++) {
			if (ce.startTimeSV >= this.timeSect[i]
					&& (ce.startTimeSV < this.timeSect[i + 1] || i == (this.timeSect.length - 1))) {
				start_index = i;
			}
			if (ce.endTimeSV > this.timeSect[i]
					&& (ce.startTimeSV <= this.timeSect[i + 1] || i == (this.timeSect.length - 1))) {
				end_index = i;
			}
		}
		// var aDivTimeSect = parentTable.cells[0].getElementsByTagName("div");
		// var aDivTimeSect = parentTable.cells[0].getElementsByTagName("div");
		// $$("event_"+id).style.top= aDivTimeSect[start_index].offesetTop;
		// $$("event_"+id).style.bottom= aDivTimeSect[end_index+1].offsetTop;
	} else {
		// 创建新的dl对象 并定位
	}

};
CalendarView.prototype.refreshCalendarEventDialogView = function(id) {
	// 的到event从 堆栈中
	var ce = this.getCalendarEvent(id);

	if (ce == null)// || (typeof event) == "undefined"
		return;

	$$("calendarEventDialog_date_start").innerHTML = ce.startDay + " " + ce.startTimeSV ;
	$$("calendarEventDialog_date_end").innerHTML = ce.endDay + " " + ce.endTimeSV ;
	$$("calendarEventDialog_title").value = ce.title;
};
// 界面的调整变化 在动作的驱动下 界面类动作
// ------------------------------------------------------------------------------------------
CalendarView.prototype.showCalendarEventDialogView = function(id) {
	// 增加判断使他最低不得超过table2的父亲div的下底线和左右边界还有上边界
	this.refreshCalendarEventDialogView(id);
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

	$$("calendarEventDialog").style.left = eventBarInfo.left - 70-getInfo($$("tt")).left+"px";
	var bottom = eventBarInfo.top - $$("calendarEventDialog").offsetHeight
			- scrollTop;
	var table2ParentInfo = getInfo($$("scrolltimedeventswk"));
	if ((table2ParentInfo.bottom) < (eventBarInfo.top - scrollTop)) {
		$$("calendarEventDialog").style.top =( table2ParentInfo.bottom
				- $$("calendarEventDialog").offsetHeight)+"px";;
	} else {
		var top = eventBarInfo.top
		- $$("calendarEventDialog").offsetHeight - scrollTop;
		if(top<0){
			$$("calendarEventDialog").style.top = (eventBarInfo.bottom-scrollTop)+"px";;
		}else{
			$$("calendarEventDialog").style.top = top+"px";;
		}
	}console.log("over");
};

CalendarView.prototype.closeCalendarEventDialog = function() {console.log("hide");
	if (this.currentEventId == "newEvent") {
		this.deleteEventBarAction("newEvent");
	}
	$$("calendarEventDialog").style.display = "none";
	$$("mask").style.display = "none";
};
CalendarView.prototype.deleteEventBarAction = function(id) {
	if ($$("event_" + id)) {
		$$("event_" + id).innerHTML = "";
		try {
			$$("event_" + id).removeNode();
		} catch (e) {
			$$("event_" + id).parentNode.removeChild($$("event_" + id));
		}
	}
};
// ----------------------------------------------------------------------------------------------------------------------------
// 数据的初始化------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
CalendarView.prototype.clear = function() {
	this.preSelectedTd = null;
	this.preSelectedTr = null;
	this.preMouseOverTd = null;
	this.selectedDay = new Date();
};
// 数据的修改
// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
CalendarView.prototype.saveCalendarEventDataService = function(ce) {
	this.valueStack["event_" + ce.id] = ce;
//	var jso=translateCE2Activity(ce);

	// activityAjax.saveCalendarEvent(activity ,isSuccessFully);
	//alert(jso.STARTDATE);

	save(ce.id);
//	AjaxFun("activity/saveActivity",jso,this.isSuccessFully);
};
// 数据的删除
// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
CalendarView.prototype.deleteEventDataService = function(id) {
	if(id=="newEvent"){
		delete this.valueStack["event_" + id];
	}
	else{
		del(id);
	}
};
// 数据的得到------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
// 根据坐标求时间区间

CalendarView.prototype.getCalendarEvent = function(id) {
    id=id+"";
    if(id.indexOf("event_")==0){
        return this.valueStack[ id];
    }
	return this.valueStack["event_" + id];
};

/**
 * 得到随机id
 * @returns {String}
 */
CalendarView.prototype.getEventId = function() {
	var date = new Date();
	var id = "" + date.getFullYear() + (date.getMonth() + 1) + date.getDate()
			+ parseInt(Math.random() * 1000);
	return id;
};
/**
 * 根据top 和高度 返回开始时间和结束时间.
 * @param top
 * @param height
 * @returns {Array}
 */
CalendarView.prototype.positionToTimesect = function(top, height) {
	var start_index, end_index, startTimeSV, endTimeSV;
	var parentTable = $$("table2").childNodes[0].childNodes[1];
	var DivTimeSects = parentTable.cells[0].getElementsByTagName("div");
	if (DivTimeSects.length != this.timeSect.length) {
		alert("error:aDivTimeSect's length not equal timeSect's!");
		return;
	}
	var _length = DivTimeSects.length;
	for (var i = 0; i < _length; i++) {
		if (getInfo(DivTimeSects[i]).top <= top
				&& getInfo(DivTimeSects[i]).bottom > top) {
			start_index = i;
		}
		if (getInfo(DivTimeSects[i]).top <= (top + height)
				&& getInfo(DivTimeSects[i]).bottom > (top + height)) {
			end_index = i;
		}
	}

	if (end_index <= start_index) {
		alert("error :end_index<=start_index! start_index:" + start_index
				+ " end_index:" + end_index);
	}

	startTimeSV = this.timeSect[start_index];
	endTimeSV = end_index >= _length ? "24:00" : this.timeSect[end_index + 1];
	var a = new Array();
	a[0] = startTimeSV;
	a[1] = endTimeSV;
	return a;
};
/**
 * 根据时间换算出top 高度值
 */
CalendarView.prototype.timeSectToPosition = function(startTimeSV, endTimeSV, date) {

	var start_index, end_index, top, height;
/*	for (var i = 0, _length = this.timeSect.length; i < _length; i++) {
		if (this.timeSect[i] <= startTimeSV
				&& (i >= (_length - 1) ? "24:00" : this.timeSect[i + 1]) > startTimeSV) {
			start_index = i;
		}
		alert( this.timeSect[i >= (_length - 1) ? (_length - 1):(i + 1)  ]);//
		if (this.timeSect[i] < endTimeSV
				&& this.timeSect[i >= (_length - 1) ?  (_length - 1):(i + 1) ]>endTimeSV)// timeSect 00:00 01:00 02:00 23:00
			end_index = i;
	}
	*/


top =parseInt(startTimeSV.substr(0,2))*this.sectHeight;
top+=parseInt(startTimeSV.substr(3,2))/60*this.sectHeight;
heigth =parseInt(endTimeSV.substr(0,2))*this.sectHeight-top+parseInt(endTimeSV.substr(3,2))/60*this.sectHeight;
//	alert(top);
	//var parentTable = $$("table2").childNodes[0].childNodes[1];

//	var divTimeSects = parentTable.cells[0].getElementsByTagName("div");
	//var info =  getInfo(divTimeSects[start_index]);
	//top =
	//height = getInfo(divTimeSects[end_index]).bottom - top;
	//需要判断下当前的日期 td_date 是否存在在calendarview中
if(Tool.isNull($$("td_" + date))){
	return null;
}
	left = getInfo($$("td_" + date)).left;
	width = getInfo($$("td_" + date)).twidth;
	//$$("aa").value = top + "aaa " + height;
	var a = new Array();
	a[0] = top;
	a[1] = heigth;
	a[2] = left;
	a[3] = width;
	return a;
};
/**
 * 拖动活动到指定位置 已经不使用了
 * @param ce
 * @param x
 * @param tY
 * @param ao
 * @param scrollTop
 * @returns {Boolean}
 */
CalendarView.prototype.drawClanderEventTo = function(ce, x, tY, ao, scrollTop) {

	var tr1 = $$("table2").childNodes[0].childNodes[1];
	var aDivTimeSect = tr1.cells[0].childNodes;
	var j = 1;
	// 鼠标当前位置 相对于td的顶端
	var y_distance = tY + scrollTop - Drag.getInfo(aDivTimeSect[0]).top;
	// 移动到了第几段位
	var timesect_index = parseInt(y_distance*2 /this.hourHeight);
	//有疑问 高度应该是小时相减*60再加上分钟的差别
	ao.style.height = (timesect_index + 1) *this.hourHeight/2 - getTop(ce.startTimeSV, this.hourHeight)-2+"px";
	// alert(count_timesect);
	var endTimeSV = this.timeCompute1(timesect_index + 1);
	if(ce.endTimeSV == endTimeSV){
		return
	}

	// ce.j=count_timesect;
	this.refreshCalendarEventBarView(ce.id);
	ce.oriendTimeSV = ce.endTimeSV;
	ce.endTimeSV = endTimeSV;
	// 重置calendarEvent 数据
	ce.endIndex = timesect_index + 1;
	ce.height = ao.style.height+"px";
	ce.length = ce.endIndex - ce.startIndex;
	//this.saveCalendarEventDataService(ce);
	return true;


	/*
	 * for ( j = 0; j < aDivTimeSect.length; j++) {
	 *
	 * var parentCell = Drag.getInfo(aDivTimeSect[j]);// 已经知道了是哪个div time //
	 * sect
	 *
	 * if ((tY+scrollTop ) < parentCell.bottom && (tY+scrollTop) >=
	 * parentCell.top) { //如果没有移除格子外面就不需要change if(ce.j>j)return false;
	 *
	 * ao .style.height= parentCell.bottom- Drag.getInfo( Drag.ao ).top;
	 * ce.endTimeSV = this.timeSect[j+1]; //ce.j=j;
	 * this.refreshCalendarEventBar(ce.id); return true;
	 *  } }
	 */
};

/**
 * 拖动event到某个时间段
 * x:鼠标的位置 ty鼠标的坐标 scrolltop滚动条的位置 ao：event的div dom ce 对应的event模型
 */
CalendarView.prototype.moveClanderEventTo = function(ce, x, tY, ao, scrollTop) {
	// 首先获得鼠标移动到了第几列
	var table2=$$("table2");
	var tbody= table2.childNodes[0];
	var tr1=tbody.childNodes[1];
	var td=null;
	var mouseCurrentCell;
	for (i = 1; i < tr1.cells.length; i++) {
		td = Drag.getInfo(tr1.cells[i]);
		if (x >= td.left && x <= td.right) {

			mouseCurrentCell = i;
			if (ce.i != mouseCurrentCell) {
				var newDate = global_weekdays[i - 1].getFullYear()
						+ "-"
						+ (global_weekdays[i - 1].getMonth() < 9
								? ("0" + (global_weekdays[i - 1].getMonth() + 1))
								: (global_weekdays[i].getMonth() + 1))
						+ "-"
						+ (global_weekdays[i - 1].getDate() < 10
								? ("0" + global_weekdays[i - 1].getDate())
								: global_weekdays[i - 1].getDate());
            ce.endDay=autoChange(ce.startDay,newDate,ce.endDay);

            ce.day=ce.startDay=newDate;
				ce.i = mouseCurrentCell;
			}
			break;
		}
	}

	// 获取时间段div
	var aDivTimeSect = tr1.cells[0].childNodes;
	var mouseCurrentRow;

	if (ce.j != count_timesect) {
		// 获得当前鼠标y轴位置 再根据滚动条的当前位置 即可得出当前在哪个时间段
		var y_distance = tY + scrollTop - Drag.getInfo(aDivTimeSect[0]).top;

		var count_timesect = parseInt(y_distance*2 / this.hourHeight);

		ao.style.top = count_timesect *  this.hourHeight/2+"px";;
		// console.log("ao.style.top"+ao.style.top);
		mouseCurrentRow = count_timesect;

		// 计算偏移量
		var deviation = count_timesect - ce.j;
		// 重新计算startTimeSV 和 endTimeSV
		//计算原来的分割量

		var longs = getTimeLongsBetweenHHMM(ce.startTimeSV,ce.endTimeSV);

		ce.startTimeSV = this.timeCompute1(count_timesect);
		ce.endTimeSV =	add4HHMM(ce.startTimeSV ,longs);// this.timeCompute1(count_timesect+1);
	//	console.log("count_timesect:"+count_timesect);
		ce.j = count_timesect;
	}

	var aDivTimeSect1 = tr1.cells[i].childNodes;

	aDivTimeSect1[0].appendChild(ao);
	// 折算出 转换后的开始时间和结束时间。

	// 更新外表
	this.refreshCalendarEventBarView(ce.id);
	// 重置calendarEvent 数据
	ce.startIndex = mouseCurrentRow;
	ce.endIndex = mouseCurrentRow + ce.length;
	ce.cellIndex = mouseCurrentCell;

	// this.adjust();
	this.adjust(mouseCurrentCell, ce);
	//alert("保存数据");
	//this.saveCalendarEventDataService(ce);
};

	/**
	 * 得到时间区域
	 */
CalendarView.prototype.getPositionFromMousePosition = function(tY, scrollTop) {
	// 获取时间段div
	var table2=$$("table2");
	var tbody= table2.childNodes[0];
	var tr1=tbody.childNodes[1];
	var td=null;
	var aDivTimeSect = tr1.cells[0].childNodes;
	var j = 0;
	var startTimeSV = "00:00";
	var endTimeSV = "00:00";// getElementsByTagName("div")
	var pianyiliang;
	for (j = 0; j < aDivTimeSect.length; j++) {
		var td_info = Drag.getInfo(aDivTimeSect[j]);// 已经知道了是哪个div time
		// sect

		if ((tY + scrollTop) < td_info.bottom
				&& (tY + scrollTop) >= td_info.top) {

			if ((tY + scrollTop) < ((td_info.bottom + td_info.top) / 2)) {
				pianyiliang = td_info.top
						- Drag.getInfo(aDivTimeSect[0]).top;

				endTimeSV = (j + 1) == this.timeSect.length
						? "24:00"
						: this.timeSect[j + 1];
				startTimeSV = this.timeSect[j];
				j = 2 * j;
			} else {
				pianyiliang = ((td_info.bottom + td_info.top) / 2 - Drag
						.getInfo(aDivTimeSect[0]).top);
				endTimeSV = (j + 1) == this.timeSect.length
						? "24:00"
						: (this.timeSect[j + 1].substr(0, 3) + "30");
				startTimeSV = this.timeSect[j].substr(0, 3) + "30";
				j = 2 * j + 1;
			}
			break;
		}
	}
	return [endTimeSV, startTimeSV, pianyiliang, j];// 偏移量 距离td的顶端的距离 j为第几格子 半小时一个格子

};
/**
 * 根据开始时间和间隔时间区间段判断时间
 */
CalendarView.prototype.timeCompute = function(time, timesect) {
	// alert("0:"+time);

	var hour = parseInt(time.substr(0, 2), 10);
	var minute = parseInt(time.substr(3, 2), 10);
	var date = new Date();
	// alert( "1:"+minute);
	date.setHours(hour, minute, 0, 0);
	// alert("3:"+(minute+parseInt(timesect,10)*30));
	date.setMinutes(minute + parseInt(timesect, 10) * 30);

	hour = date.getHours();
	// alert( "2:"+date.getHours());
	minute = date.getMinutes();

	var str = (parseInt(hour, 10) < 10 ? ("0" + hour) : hour) + ":"
			+ (minute < 10 ? ("0" + minute) : minute);

	return str;
};
/**
 * 根据格子数判断时间
 */
CalendarView.prototype.timeCompute1 = function(timesect) {

	var date = new Date();

	date.setHours(0, 0, 0, 0);
	date.setMinutes(parseInt(timesect, 10) * 30);
	hour = date.getHours();

	minute = date.getMinutes();

	var str = (parseInt(hour, 10) < 10 ? ("0" + hour) : hour) + ":"
			+ (minute < 10 ? ("0" + minute) : minute);

	return str;
};

CalendarView.prototype.adjust = function(nowColumn, ce) {

	// bubbleSort

	var nowColumn = nowColumn;// 当前列
	var preColumn = nowColumn;// 之前列
	var currentEvent = ce;// 当前拖曳或新增的块 内涵当前的日期 starttime endtime
	var eventArr = new Array(ce);
	// event=ce;
	if (nowColumn == preColumn) {
		// nowColumn
		{// 取出所有在此列的event
			for (pop in this.valueStack) {
				if (this.valueStack[pop].cellIndex == nowColumn) {
					// this.valueStack[pop].index=-1;
					if (this.valueStack[pop].id != event.id)
						eventArr.push(this.valueStack[pop]);
				}
			}
			/*
			 * var globalNewEvent=new Array(currentEvent); var
			 * leftEvents=eventArr; var relatedEvents=new Array(currentEvent);
			 * ////console.log("currentEvent"+currentEvent+"relatedEvents.length"+relatedEvents.length);
			 *
			 * globalNewEvent=chongxinfenbu(globalNewEvent,relatedEvents,leftEvents);
			 * //console.log("关联的event个数为"+globalNewEvent);
			 */
			// 如果联系的块只有单独一个就没必要进行算法
			if (eventArr.length == 1)
				return;

			this.VampireAlgorithm(eventArr);
		}
		// preColumn
		{

		}
	} else {
		// nowColumn
		{

		}
	}

};
/*
 * 吸血鬼算法，假设有N个未被感染的人类 ，而生态圈个数可以无限增长，我们先选取一个人类作为吸血鬼，然后遍历剩下的N-1个人类，
 * 任何和第一个吸血鬼接触的人类都会变成吸血鬼，而没有和之前定型的接触的都会成为新种群的吸血鬼的开拓者拥有独立的阶级标志0，
 * 后面的累计+1；如果新成员和第一代接触就成为
 * 该总群吸血鬼的二代份子，如果该种群已经发展到了3代，但是新接触的成员和1带接触了，那他还是2代成员，该种群的代数不增长如果，
 *
 * 一个新成员同时被多个种群接触，那么他的主种群是代数最多的那个，他的形状和代数也由那个种群所决定， 其他与他接触的种群接触法则就显得复杂了：
 * 如果一个代数是2的种群 的第二代和这个新成员接触了，那么代数要增加，他也是这个种群的第三代份子
 *
 *
 * 但是不影响这个新个体。当所有的人类全变成了吸血鬼，那么事情就结束了。这个时候我们可以计算出共有多少个种群的吸血鬼（无用），
 * 还有每个成员所属于的种群的总代数（三世同堂，孤家寡人，四世同堂），以及他们所属的种群中的辈分,而这个就是我们所要的最终数据
 *
 * 补充： 如果一个
 *
 * 堆叠算法 把所有的砖块先平铺摆开 这样的话呈现的是关于序号和 y轴高度的坐标图 然后我们把1号砖列为基准 放置第二块砖 如果第二块砖和第一块相加则
 */
CalendarView.prototype.VampireAlgorithm = function(vampires) {
	// 按照top的高度重新排序vampires
	// 设置top
	// console.log("进入VampireAlgorithm的vampires个数为"+vampires.length);
	// 冒泡

	vampires = bubbleSort(vampires);
	console.log("排序后"+vampires[0].title);
	this.vampires = vampires;
	// 开始层叠
	var sects = new Array();
	var curr_generation = 0;
	var generation_arr = new Array();
	var left_vampires = new Array();
	vampires[0].generation = 0;
	// console.log("title:"+vampires[0].title);
	generation_arr[0] = new Array(vampires[0]);
	 //console.log("before slice vampireslength:"+vampires.length);
	left_vampires = vampires.slice(1);
	// console.log("after slice vampireslength:"+left_vampires.length);
	// 给每个块赋值代数
	while (true) {// 代数遍历
		console.log("curr_generation:"+curr_generation);
		if(curr_generation==1){
			console.log("curr_generation==1");
		}
		if (generation_arr[curr_generation] == null) {
			// 初始化
			generation_arr[curr_generation] = new Array();
		}
		// var temp = left_vampires.slice(0);
		var the_index_of_choose_arr = new Array();
		for (var i = 0; i < left_vampires.length; i++) { // 保证对所有块进行遍历

			if (left_vampires[i] == null)
				continue;
			var flag = true;// 是否需要单独创建基地
			for (var j = 0, _length = generation_arr[curr_generation].length; j < _length; j++) {// 和这一层的所有
																									// 除自己以外的基块做比较
				// console.log("进入isTouchSide"+left_vampires[i]);
				if (generation_arr[curr_generation][j] == null)
					alert("bug");
				console.log("left_vampires"+left_vampires.length);
				if (isTouchSide(left_vampires[i],
						generation_arr[curr_generation][j])) {
					flag = false;
					console.log("flag1111111111111"+flag);
					break;
				}
			}// console.log("flag"+flag);
			if (flag) {

				left_vampires[i].generation = curr_generation;// console.log(curr_generation);
				generation_arr[curr_generation].push(left_vampires[i]);
				// left_vampires[i].generation=curr_generation;
				// 判断所属那根柱子
				// left_vampires[i]=null;
				the_index_of_choose_arr.push(i);
			}

		}
		// 对 left_vampires重新整理
		//console.log("the_index_of_choose_arr"+the_index_of_choose_arr);
		// console.log("left_vampires.length"+left_vampires.length);
		// console.log("left_vampires.length"+left_vampires.length+"choose arr"
		// +the_index_of_choose_arr);
		// console.log(left_vampires);
		// console.log(left_vampires[1]);
		// console.log(left_vampires[0]);alert(1);
		// console.log(left_vampires);
		 //console.log("||||||||||||||||||||||||||||||||||||||||||||||||||");
		for (var i = the_index_of_choose_arr.length - 1; i >= 0; i--) {
			// console.log("111111111111111111111111111111111111111111111111111");
			// console.log(i+"splice number:"+(the_index_of_choose_arr[i]));
			// console.log(left_vampires);
			// console.log("the_index_of_choose_arr"+the_index_of_choose_arr+"curr_generation"+curr_generation+"left_vampires.length"+left_vampires.length);
			left_vampires.splice(the_index_of_choose_arr[i], 1);
			// console.log("the_index_of_choose_arr"+the_index_of_choose_arr+"curr_generation"+curr_generation+"left_vampires.length"+left_vampires.length);

			// console.log(left_vampires);
			// console.log("2222222222222222222222222222222222222222222222222");
			// if(i==5) break;
		}
		// console.log("-------------------------------------------");
		// console.log("hahaleft_vampires.length"+left_vampires.length);
		// console.log("left_vampires.length"+left_vampires.length);
		if (left_vampires.length == 0 || left_vampires == null)
			break;
		// console.log("curr_generation:"+curr_generation);
		// if(curr_generation==5)break;
		curr_generation++;
		// console.log("curr_generation++"+curr_generation);
	}
	// console.log("generation_arr.length"+generation_arr.length);//console.log(generation_arr.length);
	// 对generation 逆序遍历
	// var temp_arr=generation_arr[curr_generation].slice(0);
	// if(curr_generation==0)return; //console.log(generation_arr[1]);
	// console.log("curr_generation"+curr_generation);
	// console.log("generation_arr[0].length"+generation_arr[0].length);
	// console.log("curr_generation"+curr_generation);

	// console.log(vampires);
	for (var i = curr_generation; i > 0; i--) {
		// 每次染色都要从vampires中删除它
		var currentVampires = generation_arr[i];
		var parentVampires = generation_arr[i - 1];
		for (var j = 0; j < currentVampires.length; j++) {// console.log("currentVampires[j].MaxRepeatedCount"+currentVampires[j].MaxRepeatedCount);
			if (i == curr_generation) {// currentVampires[j].MaxRepeatedCount==0||currentVampires[j].MaxRepeatedCount==null
			// console.log("currentVampires.generation"+currentVampires[j].generation);
				currentVampires[j].MaxRepeatedCount = i;
				// console.log("i:"+i);
				// console.log("currentVampires[j].MaxRepeatedCount"+currentVampires[j].MaxRepeatedCount);
			}
			// console.log("parentVampires.length"+parentVampires.length);
			for (var k = 0; k < parentVampires.length; k++) {// console.log("parentVampires[k]"+k+parentVampires[k]);
				if (isTouchSide(currentVampires[j], parentVampires[k])) {
					if (currentVampires[j].MaxRepeatedCount > parentVampires[k].MaxRepeatedCount)
						parentVampires[k].MaxRepeatedCount = currentVampires[j].MaxRepeatedCount;
				}
			}
		}
	}
	// console.log(vampires);
	// 纠正每个的宽度和左边距；
	// console.log("vampires.lengt"+vampires.length);

	for (var i = 0; i < vampires.length; i++) {
		if (vampires[i].MaxRepeatedCount != 0){
			$$("event_" + vampires[i].id).style.width = parseInt(100
							/ (vampires[i].MaxRepeatedCount + 1) + 20, 10)
					+ "%";

		}
		else{

			$$("event_" + vampires[i].id).style.width = this.EventWidth;
		}

		if(vampires[i].generation!=0){
			$$("event_" + vampires[i].id).className="calendarEventBar calendarEventBar_side";

		}else{
			$$("event_" + vampires[i].id).className="calendarEventBar";
		}
		// console.log(i);
		// console.log("hjasdf"+vampires[i].id+"hhh:"+(vampires[i].MaxRepeatedCount!=0)+":"+vampires[i].MaxRepeatedCount);

		// 计算left
		$$("event_" + vampires[i].id).style.zIndex = vampires[i].generation;

		if (vampires[i].MaxRepeatedCount != 0) {
			$$("event_" + vampires[i].id).style.left = (100 - 120
					/ (vampires[i].MaxRepeatedCount + 1))
					/ (vampires[i].MaxRepeatedCount) * vampires[i].generation+"px";
			// console.log((100-120/(vampires[i].MaxRepeatedCount+1))/(vampires[i].MaxRepeatedCount)*vampires[i].generation);
		}
		// alert(calendarEvent.index);
		// $$("event_"+calendarEvent.id).style.left=calendarEvent.index*10;
		else {
			$$("event_" + vampires[i].id).style.left = 0+"px";

		}
		$$("event_" + vampires[i].id).setAttribute("z-index",
				vampires[i].generation);

	}

};
/*
CalendarView.prototype.adjustNew = function() {
	// 现在的算法太复杂了，其实首先知道他现在移动到第几列了 那么最多他会影响两列 再分析这两列中有哪些被占用的块 对这些块进行分析效率要高的多

	for (var currentCell = 1; currentCell <= 7; currentCell++) {
		// var tr=$$("table2").childNodes[0].childNodes[1];
		// var td=tr.cells[1];
		// var div_container= td.childNodes[0];

		// var div_childs= div_container.childNodes;
		var array = new Array(48);
		// var array2 = new Array(48);
		for (var i = 0; i < 48; i++) {
			array[i] = 0;
			// array2[i]=0;
		}

		// 不应该按照div_childs的顺序来排 应该按照 时间段来排 花的时间长
		// 计算width

		 * var max_number_array=new Array(); for(var i =0;i<div_childs.length;i++){
		 * var max_number=0; var begin_index=Math.round(parseInt(
		 * div_childs[i].style.top,10)/20.5+1); var
		 * end_index=begin_index+Math.round(parseInt(div_childs[i].style.
		 * height,10)/20.5); for(var j=begin_index;j<end_index;j++){
		 * if(array[j]>max_number){max_number=array[j];
		 * max_number_array[j]=max_number;}
		 * }//$$("cc").value=parseInt(100/max_number,10)+"%";
		 *
		 * div_childs[i].style.width=parseInt(100/max_number+20,10)+"%";
		 *
		 * if(max_number==1){alet(1); div_childs[i].style.width=100%; }
		 *  }


		// 要重新计算left距离和index
		// 根据当前的列数得出当前的calendarevent 数组；

		// 通过原型遍历属性
		// 取出当列的所有日历事件
		var calendarEventArray = new Array();

		for (pop in this.valueStack) {// alert(this.valueStack[pop].cellIndex);
			// console.log("this.valueStack[pop].cellIndex"+this.valueStack[pop].cellIndex);
			if (this.valueStack[pop].cellIndex == currentCell) {
				this.valueStack[pop].index = -1;
				calendarEventArray[calendarEventArray.length] = this.valueStack[pop];
			}

			// alert(calendarEventArray[calendarEventArray.length-1].length);
			// alert(calendarEventArray[calendarEventArray.length-1]);
		}
		if (calendarEventArray.length == 0)
			continue;
		// alert(calendarEventArray.length);
		// 重写开始
		// 对24小时48个阶梯进行日历事件统计；
		for (var i = 0; i < calendarEventArray.length; i++) {// 计算每一格的重叠个数
			// alert("start:"+calendarEventArray[i].startIndex+"end:"+calendarEventArray[i].endIndex);
			var begin_index = calendarEventArray[i].startIndex;
			var end_index = calendarEventArray[i].endIndex;
			for (var j = begin_index; j < end_index; j++) {
				array[j]++;
			}
		}
		// alert(array[1]);
		var max_number_array = new Array();
		for (var i = 0; i < calendarEventArray.length; i++) {
			var max_number = 0;
			var begin_index = calendarEventArray[i].startIndex;
			var end_index = calendarEventArray[i].endIndex;
			for (var j = begin_index; j < end_index; j++) {
				if (array[j] > max_number) {
					max_number = array[j];
					max_number_array[j] = max_number;
				}
			}// $$("cc").value=parseInt(100/max_number,10)+"%";

			if (max_number != 1)
				$$("event_" + calendarEventArray[i].id).style.width = parseInt(
						100 / max_number + 20, 10)
						+ "%";
			else
				$$("event_" + calendarEventArray[i].id).style.width = "90%";

		}
		// alert("相交程序判断");
		// alert("valueStack长度为:"+calendarEventArray.length);
		// 遍历所有的calendar event
		// alert(calendarEventArray.length);
		for (var i = 0, length = calendarEventArray.length; i < length; i++) {
			var calendarEvent = calendarEventArray[i];
			// alert("开始："+calendarEvent.startIndex+"结束："+calendarEvent.endIndex);
			calendarEvent.index = 0;

			var max_number = -1;
			var zuiduoxiangjiaojirownumber = 0;
			for (var k = calendarEvent.startIndex; k < calendarEvent.endIndex; k++) {
				if (max_number_array[k] > max_number) {
					max_number = max_number_array[k];
					zuiduoxiangjiaojirownumber = k;
				}
			}
			var paiweiArray = new Array(10);
			for (var j = 0; j < i; j++) {
				// 如果在最多相交集的哪条线上相逢
				if (zuiduoxiangjiaojirownumber >= calendarEventArray[j].startIndex
						&& zuiduoxiangjiaojirownumber < calendarEventArray[j].endIndex) {
					paiweiArray[calendarEventArray[j].index] = 1;
				}

			}

			for (var k = 0; k < paiweiArray.length; k++) {
				if (paiweiArray[k] != 1) {
					calendarEvent.index = k;
					break;
				}
			}
			$$("event_" + calendarEvent.id).style.zIndex = calendarEvent.index;
			if (max_number_array[zuiduoxiangjiaojirownumber] != 1)
				$$("event_" + calendarEvent.id).style.left = (100 - 120
						/ max_number_array[zuiduoxiangjiaojirownumber])
						/ (max_number_array[zuiduoxiangjiaojirownumber] - 1)
						* calendarEvent.index;
			// alert(calendarEvent.index);
			// $$("event_"+calendarEvent.id).style.left=calendarEvent.index*10;
			else
				$$("event_" + calendarEvent.id).style.left = 0;

			$$("event_" + calendarEvent.id).setAttribute("z-index",
					calendarEvent.index);
			// //console.log(calendarEvent.title+"index:"+calendarEvent.index+"inline"+$$("event_"+calendarEvent.id).style.zIndex+"css"+$$("event_"+calendarEvent.id).getAttribute("z-index"));

		}
	}

}*/

CalendarView.prototype.selectDate = function(day) {
	// 重新设定dummyday selectedDay 重新
	this.dummyDay = day;
	this.selectedDay = day;
	this.refreshDatePickerView();

};
/**点击页面生成一个空的日历对话框**/
CalendarView.prototype.addEventView = function(ce) {
	// 生成dom节点
	// dialogue附着

	// 得到当前鼠标位置
	if (ce == null) {// console.log("ce is null");
		ce = new calendarEvent();
		var date = new Date();
		ce.startTimeSV = getTimeStrFromDate(date);
		ce.endTimeSV = getTimeStrFromDate(date);
		ce.id = "newEvent";

		ce.day = getdayStrFromDate(date);
		ce.endDay=ce.startDay= ce.day;
		ce.title = "";
		this.currentEventId = "newEvent";
	}
	startTimeSV = ce.startTimeSV;
	// console.log("startTimeSV:"+startTimeSV);
	endTimeSV = ce.endTimeSV;// getElementsByTagName("div")




	/*
	 */
	/**数据加工**/
	ce.j = getTimeEndIndex(ce.endTimeSV, 0);
	// 入栈
	this.valueStack["event_" + ce.id] = ce;
	var date = ce.day;


	if (!$$("td_" + ce.day))
		return;
	ce.startIndex = ce.j;// console.log("startIndex"+ce.startIndex);
	ce.endIndex = ce.j + 2;

	if(this.viewMode!=2){

	var tr1 = $$("table2").childNodes[0].childNodes[1];
	// //console.log("table2childNodeschildNodes");
	// //console.log($$("table2").childNodes[0].childNodes[1].childNodes[1]);
	var mouseCurrentColum;
	for (i = 1; i < tr1.cells.length; i++) {// //console.log(ce.day);
		if (ce.day == tr1.cells[i].id.substr(3)) {
			mouseCurrentColum = i;
			break;
		}
	}
	//alert(mouseCurrentCell);
	ce.cellIndex = mouseCurrentColum;
	}
	//ce.endIndex=0;
	//ce.top=data_arr[2]-18;
	ce.left = 0;
	ce.length = 2;////console.log("end");
	//alert("开始："+ce.startIndex+"结束"+ce.endIndex);
	/**模板渲染**/
	var div_html = this.calendarEventRender(ce);
if(this.viewMode==2){
    $$("td_" + ce.day).childNodes[0].appendChild(div_html);
}else
	$$("td_" + ce.day).childNodes[0].appendChild(div_html);
//	alert($$("event_" + ce.id));
	/*var div_html=document.createElement("div");
	var dl_html = document.createElement("dl");
	div_html.appendChild(dl_html);
	div_html.id = "event_" + ce.id;
	div_html.className = "calendarEventBar";
	dl_html.innerHTML = "<dt >"
			+ startTimeSV
			+ "~"
			+ endTimeSV
			+ "</dt> "
			+ "<DD >"+(StringUtil.isNull(ce.title)?"&nbsp;":ce.title)+"</DD><div  onmouseup=\"Drag.dragEnd();\" onmousedown=\"Drag.drawStart(this);event.cancelBubble=true;\">_</div>";
	$$("td_" + ce.day).childNodes[0].appendChild(div_html);
	//dl_html.style.position = "absolute";
	div_html.style.position = "absolute";
	div_html.style.top = position[0]+"px";//-getInfo($$("table2")).top;
//alert("postion[0]:"+ position[0]+"top:"+div_html.style.top );
	div_html.style.height = position[1]+"px";
	div_html.style.left =0+"px";
	// console.log(dl_html.style.top);
	dl_html.style.left = 0+"px";
	dl_html.style.height = "100%";
	var index = this.index;// ?
*/	/**渲染结束**/
	//dl_html.style.top = getTop(ce.startTimeSV,this.hourHeight);
	// console.log($$("td_"+ce.day));
	/**后续事件**/
	if(ce.id=="newEvent")
	this.showCalendarEventDialogView(ce.id);//this.currentEventId

/*	div_html.attachEvent("onmousedown", function(event){Drag.dragStart(div_html)},true);
	index = this.index;
	div_html.attachEvent("ondblclick", function() {
				Instance(index).openCalendarEventDialog(div_html);
			});
*/
	/*dl_html.attachEvent("ondblclick", function() {
				Instance(index).openCalendarEventDialog(dl_html)
			,true});

	dl_html.attachEvent("onmousedown", function() {
				Drag.dragStart(dl_html);
			},true);*/
	// 维护calendareventdata
	if(this.viewMode!=2)
	this.adjust(mouseCurrentColum, ce);
};

CalendarView.prototype.calendarEventRenderWithOutStyle=function(ce){

	var div_html=document.createElement("div");
	div_html.id = "event_"+ce.id;//alert(ce.id);
	div_html.className = "calendarEventBar";
	var dl_html = document.createElement("dl");
	div_html.appendChild(dl_html);
	//dl_html.id = "event_newEvent";
	//dl_html.className = "calendarEventBar";
	dl_html.innerHTML = "<dt >"
		+ ce.startTimeSV
		+ "~"
		+ ce.endTimeSV
		+ "</dt> "
		+ "<DD >"+(StringUtil.isNull(ce.title)?"&nbsp;":ce.title)+"</DD><div  onmouseup=\"Drag.dragEnd();\" onmousedown=\"Drag.drawStart(this);event.cancelBubble=true;\">_</div>";


	if(this.viewMode!=2){
	div_html.style.position = "absolute";
	div_html.style.top = position[0]+"px";//-getInfo($$("table2")).top;
//alert("postion[0]:"+ position[0]+"top:"+div_html.style.top );
	div_html.style.height = position[1]+"px";
	div_html.style.left =0+"px";
	// console.log(dl_html.style.top);
	dl_html.style.left = 0+"px";
	dl_html.style.height = "100%";

	}
	// td.appendChild(newEvent);
	var index = this.index;// ?
	//this.showCalendarEventDialog(this.currentEventId);

	dl_html.attachEvent("ondblclick", function() {
		Instance(index).openCalendarEventDialog(div_html)
	});
	// onclick=\"Instance('" + this.index
	// + "').editCalendarEvent(this.id)\"
	div_html.attachEvent("onmousedown", function() {
		Drag.dragStart(div_html);
	});
	return div_html;
	};
CalendarView.prototype. calendarEventRender=function(ce){
	var position = this.timeSectToPosition(ce.startTimeSV,ce.endTimeSV, ce.day);
	if(position==null)return;//说明该日期不在当前日历表中
	var div_html=document.createElement("div");
	div_html.id = "event_"+ce.id;//alert(ce.id);
	div_html.className = "calendarEventBar";
	var dl_html = document.createElement("dl");
	div_html.appendChild(dl_html);
	//dl_html.id = "event_newEvent";
	//dl_html.className = "calendarEventBar";
	dl_html.innerHTML = "<dt >"
		+ ce.startTimeSV
		+ "~"
		+ ce.endTimeSV
		+ "</dt> "
		+ "<DD >"+(StringUtil.isNull(ce.title)?"&nbsp;":ce.title)+"</DD><div  onmouseup=\"Drag.dragEnd();\" onmousedown=\"Drag.drawStart(this);event.cancelBubble=true;\">_</div>";


	if(this.viewMode!=2){
	div_html.style.position = "absolute";
	div_html.style.top = position[0]+"px";//-getInfo($$("table2")).top;
//alert("postion[0]:"+ position[0]+"top:"+div_html.style.top );
	div_html.style.height = position[1]+"px";
	div_html.style.left =0+"px";
	// console.log(dl_html.style.top);
	dl_html.style.left = 0+"px";
	dl_html.style.height = "100%";

	}
	// td.appendChild(newEvent);
	var index = this.index;// ?
	//this.showCalendarEventDialog(this.currentEventId);

	dl_html.attachEvent("ondblclick", function() {
		Instance(index).openCalendarEventDialog(div_html)
	});
	// onclick=\"Instance('" + this.index
	// + "').editCalendarEvent(this.id)\"
	div_html.attachEvent("onmousedown", function() {
		Drag.dragStart(div_html);
	});
	return div_html;
	};
CalendarView.prototype._eval = function(str) {
	eval(str);
};
CalendarView.prototype.hideMCalTitle = function() {
	$$("dp_div").style.display="none";
	$$("dp_canopy_div").style.display="";
};
CalendarView.prototype.showMCalTitle = function() {
	$$("dp_div").style.display="";
	$$("dp_canopy_div").style.display="none";
};
CalendarView.prototype.showOrHideMCalTypes = function() {
	if($$("dp_types_div").style.display==""){
		$$("dp_types_div").style.display="none";
	}else{
		$$("dp_types_div").style.display="";
	}

};
/**
 * 改变颜色
 * @param it
 */
CalendarView.prototype.changeColor = function(it){
	//当前的click
	//当前的type对象的color属性改值
	this.calTypes[this.currentCalType].color=it.style.backgroundColor;
	$$("_caltype_color_div_"+this.calTypes[this.currentCalType].id).style.backgroundColor=it.style.backgroundColor;
	//对应的id的div的背景颜色修改了
	//将菜单关掉

};
/**
 *
 *初始化
 **/
CalendarView.prototype.init= function() {
	this.addEventListener();

	//alert("开始时间"+global_weekdays[0].getMonth());//给他毫秒数 + 格林威治时间差
	//alert("测试UTC"+global_weekdays[0].UTC());
	//alert("测试getTime"+global_weekdays[0].getTime());
	//alert("结束时间");

	//var startDate = global_weekdays[0].getTime();
	var startSzShift= global_weekdays[0].getTimezoneOffset();
	//var endDate = global_weekdays[6].getTime();
	//var endSzShift= global_weekdays[6].getTimezoneOffset();

this.loadEventsView();
};
CalendarView.prototype.loadEventsView=function(){//alert("before loadevents");
	var jso={};
	//alert(global_weekdays[0]);
	//alert(global_weekdays[0]);
	//alert(global_weekdays[6]);

	//print offsettime;
	var datename =new Date();
	//console.log(datename.getTimezoneOffset());
	//alert(global_weekdays[0].format("yyyy-MM-ddT00:00:00"));

	if(this.viewMode==0){
		jso.STARTDATE=parseInt(DateUtils.retainDay(DateUtils.copyDate(global_weekdays[0])).getTime()/60000);
		//jso.STARTSZSHIFT=startSzShift;
		jso.ENDDATE=parseInt((DateUtils.retainDay(DateUtils.copyDate(global_weekdays[0])).getTime()+24*60*60*1000)/60000);
	}else if(this.viewMode==1){
		jso.STARTDATE=parseInt(DateUtils.retainDay(DateUtils.copyDate(global_weekdays[0])).getTime()/60000);
		//jso.STARTSZSHIFT=startSzShift;
		jso.ENDDATE=parseInt((DateUtils.retainDay(DateUtils.copyDate(global_weekdays[6])).getTime()+7*24*60*60*1000)/60000);
	}else if(this.viewMode==2){
		jso.STARTDATE=parseInt(getFirstMonthDay(this.dummyDay).getTime()/60000);
		//jso.STARTSZSHIFT=startSzShift;

		jso.ENDDATE=parseInt((getLastMonthDay(this.dummyDay).getTime()+24*60*60*1000)/60000);
		//console.log("startdate:"+jso.STARTDATE+" ENDDATE："+jso.ENDDATE);
	}else if(this.viewMode==3){
     		jso.STARTDATE=parseInt(getFirstMonthDay(this.dummyDay).getTime()/60000);
     		//jso.STARTSZSHIFT=startSzShift;

     		jso.ENDDATE=parseInt((getLastMonthDay(this.dummyDay).getTime()+24*60*60*1000)/60000);


     		//console.log("startdate:"+jso.STARTDATE+" ENDDATE："+jso.ENDDATE);
     	}else if(this.viewMode==4){
              		jso.STARTDATE=parseInt(getFirstMonthDay(this.dummyDay).getTime()/60000);
              		//jso.STARTSZSHIFT=startSzShift;

              		jso.ENDDATE=parseInt((getLastMonthDay(this.dummyDay).getTime()+24*60*60*1000)/60000);


              		//console.log("startdate:"+jso.STARTDATE+" ENDDATE："+jso.ENDDATE);
              	}

	//jso.endSZSHIFT=startSzShift;alert(1)
	/*$.post("http://127.0.0.1:8080/calendar/activity/getActivities",jso,function (data){
		alert(data[AJAX_RESULT]);
	});*/
	if(this.viewMode==3||this.viewMode==4 ){
	Ajax.post(PATH+"/activity/getAllActivities.json",jso,this.loadEventsViewCallBack.Apply(this));
	}else{
	Ajax.post(PATH+"/activity/getActivities.json",jso,this.loadEventsViewCallBack.Apply(this));
	}
};


/**
 * 暂时事件
 * @param data
 */
CalendarView.prototype.loadEventsViewCallBack=function(data){//alert(this);//alert("before showActivities");

	this.valueStack={};
	var firstRow;
    if(this.viewMode==3){
          var finalData ={};
            for(var i=0;i<data.data.length;i++){
                var rowData = data.data[i];
                var title =rowData.title;
                var userName =rowData.userName;
                var ary =title.split("#");
                var mainTitle = ary[0];
                var subTitle = ary[1];
                if(finalData[mainTitle]){

                }else{
                finalData[mainTitle]=new Array();

                }
                var json = {};
                json.mainTitle = mainTitle;
                 json.subTitle = subTitle;
                  json.userName = userName;
                   json.startTime = rowData.startTime;
                   json.endTime = rowData.endTime;
                finalData[mainTitle].push(json);
                 finalData[mainTitle][0].count=1+  (finalData[mainTitle][0].count||0);

            }
            var data =data.data;
        	var today_y = this.today.getFullYear();
        	var today_m = this.today.getMonth() + 1;
        	var today_d = this.today.getDate();

        	var y = this.dummyDay.getFullYear();
        	var m = this.dummyDay.getMonth() + 1;
        	var b_thisMonth = false;
        	if (y == today_y && m == today_m)
        		b_thisMonth = true;
            var loopDate = new Date(this.dummyDay.getTime());
            loopDate.setDate(1);

        	var _index =0;
        	var _date = 1;
        	var _rows =1;
        	var str =
        	 "<table  class='wk_mv_table wk-scrolltimedevents'  id='scrolltimedeventswk'>"
        			+ "<tr class='dp_title_tr'  >"
        			+ "<th id='dp_ym_th' class='dp_ym_th' colspan=5 ><span class='zippy-arrow' unselectable='on'></span><span> "
        			+ y
        			+ "年"
        			+ m
        			+ "月</span></th>"
        			+ "<th > <i id='dp_prev_month' onClick='Instance(\""+this.index+"\").datePickerGoPrevMonthAction()' class='dp_prev_month'></i> </th>"
        			+ "<th  > <i id='dp_next_month' onClick='Instance(\""+this.index+"\").datePickerGoNextMonthAction()' class='dp_prev_month dp_next_month'> </i></th>"
        			+ "</tr>"

        			+ "<tr class='dp_week_title_tr'><th>主标题</th><th>副标题</th><th>人员</th>";

            for(var i=0;i<60;i++){
                str+="<th>"+DateAdd(loopDate,i).format("MM-dd")+"</th>";
                //loopDate.setDate(loopDate.getDate()+1);
            }
            str+= "</tr>";


        	//计算出包含上月的第一行日期行

         for(var mainTitle in finalData){
            var list = finalData[mainTitle];

              for(var i=0;i<list.length;i++){
              var nowDate = new Date(this.dummyDay.getTime());
              nowDate.setDate(1);
               str+= "<tr style='height:25px;'>";
               if(list[i].count && list[i].count>=1){
               str+="<td style='height:25px;' rowSpan="+list[i].count +">"+list[i].mainTitle+"</td>";
               }else{
               }
              // str+="<td style='height:25px;' rowSpan="++"">"+list[i].mainTitle+"</td>";
                str+="<td style='height:25px;'>"+list[i].subTitle+"</td>";
                    str+="<td style='height:25px;'>"+list[i].userName+"</td>";
                 for(var j=0;j<60;j++){
                 console.log(getDateStrByFen(list[i].startTime));

                 console.log(DateAdd(nowDate,j).format("yyyy-MM-dd HH:mm"));
                   // str+="<td>"+nowDate.getMonth()+"/"+(nowDate.getDate()+j)+"</td>";
                    if(( DateAdd(nowDate,j).getTime()<=list[i].startTime*60000 && DateAdd(nowDate,j+1).getTime()>=list[i].startTime*60000)||
( DateAdd(nowDate,j).getTime()<=list[i].endTime*60000 && DateAdd(nowDate,j+1).getTime()>=list[i].endTime*60000)||

( DateAdd(nowDate,j).getTime()>=list[i].startTime*60000 && DateAdd(nowDate,j).getTime()<=list[i].endTime*60000)

                    ){
                         str+="<td style='height:25px;background-color:red'></td>";
                    }else{
                      str+="<td style='height:25px;'></td>";
                      }
                 }
                   str+= "</tr>";
              }
            }

          str+= "</table>";
            $("#div_CalendarEventView_" + this.index).html(str);


    }else if(this.viewMode==4){
                   var finalData ={};
                     for(var i=0;i<data.data.length;i++){
                         var rowData = data.data[i];
                         var title =rowData.title;
                         var userName =rowData.userName;
                         var ary =title.split("#");
                         var mainTitle = ary[0];
                         var subTitle = ary[1];
                         if(finalData[mainTitle]){

                         }else{
                         finalData[mainTitle]=new Array();

                         }
                         var json = {};
                         json.mainTitle = mainTitle;
                          json.subTitle = subTitle;
                           json.userName = userName;
                            json.startTime = rowData.startTime;
                            json.endTime = rowData.endTime;
                         finalData[mainTitle].push(json);
                          finalData[mainTitle][0].count=1+  (finalData[mainTitle][0].count||0);

                     }
                     var data =data.data;
                 	var today_y = this.today.getFullYear();
                 	var today_m = this.today.getMonth() + 1;
                 	var today_d = this.today.getDate();

                 	var y = this.dummyDay.getFullYear();
                 	var m = this.dummyDay.getMonth() + 1;
                 	var b_thisMonth = false;
                 	if (y == today_y && m == today_m)
                 		b_thisMonth = true;
                     var loopDate = new Date(this.dummyDay.getTime());
                     loopDate.setDate(1);

                 	var _index =0;
                 	var _date = 1;
                 	var _rows =1;
                 	var str =
                 	 "<table  class='wk_mv_table wk-scrolltimedevents'  id='scrolltimedeventswk'>"
                 			+ "<tr class='dp_title_tr'  >"
                 			+ "<th id='dp_ym_th' class='dp_ym_th' colspan=5 ><span class='zippy-arrow' unselectable='on'></span><span> "
                 			+ y
                 			+ "年"
                 			+ m
                 			+ "月</span></th>"
                 			+ "<th > <i id='dp_prev_month' onClick='Instance(\""+this.index+"\").datePickerGoPrevMonthAction()' class='dp_prev_month'></i> </th>"
                 			+ "<th  > <i id='dp_next_month' onClick='Instance(\""+this.index+"\").datePickerGoNextMonthAction()' class='dp_prev_month dp_next_month'> </i></th>"
                 			+ "</tr>"

                 			+ "<tr class='dp_week_title_tr'><th>主标题</th><th>副标题</th><th>人员</th>";


                         str+="<th>开始时间</th><th>结束时间</th>";
                         //loopDate.setDate(loopDate.getDate()+1);

                     str+= "</tr>";


                 	//计算出包含上月的第一行日期行

                  for(var mainTitle in finalData){
                     var list = finalData[mainTitle];

                       for(var i=0;i<list.length;i++){
                       var nowDate = new Date(this.dummyDay.getTime());
                       nowDate.setDate(1);
                        str+= "<tr style='height:25px;'>";
                       if(list[i].count && list[i].count>=1){
                        str+="<td style='height:25px;'rowSpan="+list[i].count +" >"+list[i].mainTitle+"</td>";//rowSpan="+list[i].count +"
                       }else{
                       }
                       // str+="<td style='height:25px;' rowSpan="++"">"+list[i].mainTitle+"</td>";
                         str+="<td style='height:25px;'>"+list[i].subTitle+"</td>";
                             str+="<td style='height:25px;'>"+list[i].userName+"</td>";




                          str+="<td style='height:25px;background-color:red'>"+(new Date(list[i].startTime*60*1000)).format("yyyy-MM-dd")+"</td>"+
                          "<td style='height:25px;background-color:red'>"+(new Date(list[i].endTime*60*1000)).format("yyyy-MM-dd")+"</td>";


                            str+= "</tr>";

                     }
 }
                   str+= "</table>";
                     $("#div_CalendarEventView_" + this.index).html(str);


             }else {
	if(data!=null && data.data!=null && data.data.length>0){
		for(var i=0,length=data.data.length;i<length;i++){
			if(data.data[i]){
				var ce = changeJson2CE(data.data[i]);
				//this.displaySingleEvent(ce);
				this.addEventView(ce);
			}
		}
	}
	}

};
/**
 *
 *初始化 事件初始化
 **/
CalendarView.prototype.addEventListener= function() {
	bind($$("dp_canopy_div"),'click',new Function("Instance('"
			+ this.index
			+ "').showMCalTitle()"));
	bind($$("dp_ym_th"),'click',new Function("Instance('"
			+ this.index
			+ "').hideMCalTitle()"));
	/*bind($$("dp_prev_month"),'click',new Function("Instance('"
			+ this.index
			+ "').datePickerGoPrevMonthAction()"));
	bind($$("dp_next_month"),'click',new Function("Instance('"
			+ this.index
			+ "').datePickerGoNextMonthAction ()"));*/
	bind($$("mycal_h2"),'click',new Function("Instance('"
			+ this.index
			+ "').showOrHideMCalTypes()"));
	var divs=document.getElementById("calpalette").getElementsByTagName("div");
	for(var i=0;i<divs.length;i++){
		divs[i].style.backgroundColor=color_list[i];
		bind(divs[i],'click',
		new Function("Instance('"
				+ this.index
				+ "').changeColor(this)")
		);
		//divs[i].style.borderColor=color_list[i];
	}
    var container= $$("div_CalendarEventView_"+this.index);
	var that =this;
	//console.log("begin add mousemove on td");
	if(container&&this.viewMode==2){
	    var tds = container.getElementsByTagName("td");
        console.log("find "+tds.length+" td");
	    for(var i=0;i<tds.length;i++){
            //console.log(" adding mousemove on td");
	       // bind(tds[i],"mouseover",function(){console.log("hello");});
	        tds[i].addEventListener("mouseover",function(){that.monthViewMouseMoveInAction(this)})
	        // tds[i].addEventListener("mouseover",function(){console.log("hello");})
	    }
	}
	//console.log("end add mousemove on td");

	//$$("minical_canopy_"+this.index).add
	//加载活动

};
CalendarView.prototype.monthViewMouseMoveInAction= function(td) {
 if(Drag.ao==null)return ;
 		if(!Drag.dragged&&!Drag.drawed    )return;
 		if(td!=Drag.ao.parentNode.parentNode){
 		td.childNodes[0].appendChild(Drag.ao);

	    console.log("mousemoveover");
	}
};
CalendarView.prototype.getDateFromMixStr=function(id){
    var index=0;
    var position=0;
    while(index>=0){
        position=index;
        console.log(id.indexOf("_",index+1));
           console.log(id.indexOf("_",index+1));
        index=id.indexOf("_",index+1);
    }
    var date = parseDate(id.substr(position+1),"yyyy-MM-dd");
    return date;
};
/**
*according to parentnode td 's id changed the ce property and save in db
* 当拖动了日历的时候发生变化要自动保存到数据库
*/
CalendarView.prototype.saveChangedAction= function(ao) {

//比对是否有更改
    //if(this.viewMode!=2)return;
    if(!ao)return;
    var ce = this.getCalendarEvent(ao.id);
    var tdNode = ao.parentNode.parentNode;

    var date =this.getDateFromMixStr(ao.parentNode.parentNode.id);
    if(!ce.data){
    console.log(ce);
    }
    var ceDate =new Date(ce.data.startTime*60000);
    //如果天数月数发生了变化
    if(date.getMonth()!= ceDate.getMonth() || date.getDate()!= ceDate.getDate()){
        //ceDate.setDate(date.getDate());
        //ceDate.setMonth(date.getMonth());
        ce.startDay=ce.day= date.format("yyyy-MM-dd");//endTime=ceDate.getTime()-ce.startTime+ce.endTime;
        //ce.startTime= ceDate.getTime();
        if(ce.id=="event_newEvent"){
            alert("new Event should not enther saveChangeAction method ");
            return;
        }
        this.saveCalendarEventDataService(ce);
        console.log("save");
    }
    //判断是否更新了 开始时间和结束时间 judge whether drag to another time or draw to another time
    if(ce.data && (ce.data.startTime !=ce.startTime || ce.data.endTime != ce.endTime)){
            //ceDate.setDate(date.getDate());
            //ceDate.setMonth(date.getMonth());

            ce.endDay = autoChange(ce.startDay,date.format("yyyy-MM-dd"),ce.endDay);
            ce.startDay= ce.day= date.format("yyyy-MM-dd");//endTime=ceDate.getTime()-ce.startTime+ce.endTime;
            //ce.startTime= ceDate.getTime();
            this.saveCalendarEventDataService(ce);
            console.log("save");
    }
    /*
     if(Drag.ao==null)return ;
    if(!Drag.dragged&&!Drag.drawed    )return;
    if(td!=Drag.ao.parentNode){
        td.appendChild(Drag.ao);
        console.log("mousemoveover");
    }*/
};

/**
 * 把calendarview 添加到指定的id容器中
 * @param id
 */
CalendarView.prototype.attachTo= function(id) {

	$$(id).innerHTML=this.render();
	// document.getElementsByClassName("ca-ty-ch-wrp")[0].getElementsByClassName("active")[0].removeAttribute("class","active");
    	document.getElementsByClassName("ca-ty-ch-wrp")[0].childNodes[this.viewMode].setAttribute("class","active");

	document.getElementById("scrolltimedeventswk").style.height=document.body.clientHeight -100+"px";
	//	document.getElementById("scrolltimedeventswk").scrollTo(0,300);
};
CalendarView.prototype.judgeIfSaveInDb= function(id) {
    var ce = ca.getCalendarEvent(ceid);
    if(ce.isdel==ce.data.isdel
     &&ce.title!=ce.data.title
     &&getTimes(ce.startDay,ce.startTimeSV)==ce.data.startTime
      &&getTimes(ce.endDay,ce.endTimeSV)==ce.data.endTime
     ){

        return false;
    }
      console.log("need save in server");
    return true;



};

function judgeIfNeed2Save(ceid) {
    var ce = ca.getCalendarEvent(ceid);
    if(ce.data && ce.isdel==ce.data.isdel
     &&ce.title==ce.data.title
     &&getTimes(ce.startDay,ce.startTimeSV)==ce.data.startTime
      &&getTimes(ce.endDay,ce.endTimeSV)==ce.data.endTime
     ){
        console.log(ce.isdel +":");
        return false;
    }
      console.log("need save in server");
    return true;



};
//action of event

/*
 CalendarView.prototype.closeEventDialog = function(){
 //如果有id则数据库删除
 //如果没有id则关闭窗口 同时删除event
 //$$("CalendarEventDialog").innerHTML="";
 //$$("CalendarEventDialog").removeNode();
 $$("CalendarEventDialog").style.display="none";
 //$$("CalendarEventDialog2").style.display="none";
 $$("mask").style.display="none";
 if(this.currentEventId==""){
 this.newEvent.src.innerHTML="";
 this.newEvent.src.removeNode();
 }

 };
 */;
window['CalendarView'] = CalendarView;

/**
 * 将activity转换成calendarevent
 *
 */
function changeJson2CE(data){
	var ce=new calendarEvent();
	ce.id=data.id;

	ce.title=data.title;
	ce.startDay=ce.day=new Date(data.startTime*60000).format("yyyy-MM-dd");

	ce.startTimeSV=new Date(data.startTime*60000).format("HH:mm");

	ce.endTimeSV=new Date(data.endTime*60000).format("HH:mm");
    ce.endDay=new Date(data.endTime*60000).format("yyyy-MM-dd");
	console.log((data.startTime%(24*60)/60)+":"+(data.startTime%(60))  );

	ce.startTime=data.startTime%(24*60);
	ce.endTime=data.endTime%(24*60);
	ce.isdel=data.isdel;
	ce.data=data;
	//{"r":0,"data":[{"isdel":false,"title":"123","id":201641653,"startTime":24347040,"endTime":24347100,"address":null,"userId":7,"description":null,"type":0,"privacy":0,"busyLevel":null}],"msg":null,"page":null,"right":true}
	return ce;
}
/**
 * 把calendarevent  转成activity
 * @param ce
 * @returns activity bean json
 */
function translateCE2Activity(ce){
	var activity =new Object();
	activity.ID=ce.id;
	activity.TITLE=ce.title;

	activity. STARTTIME = getTimes(ce.startDay,ce.startTimeSV);
	if(ce.startDay){
    	activity. STARTTIME = getTimes(ce.startDay,ce.startTimeSV);
    	}
	activity.ENDTIME = getTimes(ce.endDay,ce.endTimeSV);
	if(ce.endDay){
        	activity. ENDTIME = getTimes(ce.endDay,ce.startTimeSV);
        	}
	activity.ISDEL=ce.isdel;
	return activity;
}

//var synStack =new Array();//这样的数据结构容易导致一个数据被保存多次
var synStack={};

//setTimeout("loopCheckAndSave()",1000);

function save(ceid){
	var ce = ca.getCalendarEvent(ceid);
	if(!judgeIfNeed2Save(ceid)){
	    return;
	}

	//判断是否需要保存到数据库
	synStack[ceid]=ce;
	//ce.changeFlag=true;
	ce.lastChangeTime=(new Date()).getTime();
}
function del(ceid){
	var ce = ca.getCalendarEvent(ceid);
	synStack[ceid]=ce;
	//ce.changeFlag=true;
	ce.isdel=true;
	ce.lastChangeTime=(new Date()).getTime();

}

/**
 * 轮循保存数据
 */
function loopCheckAndSave(){
	var arr=new Array();
	for(var key in synStack){
		var ceid=key;

		var ce = synStack[key];
		if((new Date()).getTime()- ce.lastChangeTime<500){//alert(ce.lastChangeTime);
			setTimeout("loopCheckAndSave()",1000);
			return;
		}
		//TODO 校验数据正确行
		arr.push(translateCE2Activity(synStack[key]));
		ce.data=translateCE2Activity(synStack[key]);
	}
	if(arr.length>0){console.log(arr);
	    console.log("begin ajax save");
		Ajax.post(PATH+"/activity/saveActivitys.json",{'jsonstr':	JSON.stringify(arr) },saveHandler);
	}else{
		setTimeout("loopCheckAndSave()",1000);
	}
}
function saveHandler(data){
    //TODO 增加失败调用
      console.log(" ajax save complete result:"+data.r);
	synStack={};
	setTimeout("loopCheckAndSave()",5000);
}
/*	//过一个时间间隔再来检查 数据是否
	function	makeSureSave(ceid,changedTime){
		var ce = ca.getCalendarEvent(ceid);
//		var now = (new Date()).getMilliseconds();
		if(changedTime== ce.lastChangeTime){
			//此时就应该删除掉缓存中的数据
			sysStack[ceid]=undefined;
			delete sysStack[ceid];
			ca.saveCalendarEventDataService(ce);
		}else{//再等一秒
			//让他到下轮循环中再进行修改好了
		}
	}*/
setTimeout("loopCheckAndSave()",5000);
function isSuccessFully(data){
	if(data.RESULT){
		alert("保存成功");
	}else{
		alert("保存失败");
	}
}