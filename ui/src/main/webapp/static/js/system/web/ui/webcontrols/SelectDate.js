Using("System.Data.Calendar");
//div
//	table css: MiniCalendar_table
//			tr 
//					th 年月日
//			tr	
//					th 一二三四五六七
//			tr
//					td 日期
function SelectDate() {
	Calendar.call(this);
	this.setHashCode();
	this.index = this.hashCode;
}

t = SelectDate.Extends(Calendar, "SelectDate");
t.div_id="";
t.node=null;
t.parentDivNode;
t.parentInput_id="";
t.tableNode="";
SelectDate.prototype.render = function(id) {
	this.parentInput_id=id;
	var div=document.createElement("div");
	div.style.position="absolute";
	div.style.top=getInfo($(id)).bottom;
	div.style.left=getInfo($(id)).left;
	$(id).parentNode.appendChild(div);
	t.parentDivNode=div;
	this.getCalendarStr();
	
};

SelectDate.prototype.getCalendarStr = function() {
	var today_y = this.today.getFullYear()
	var today_m = this.today.getMonth() + 1;
	var today_d = this.today.getDate();
	
	
	var y =  this.dummyDay.getFullYear();
	var m = this.dummyDay.getMonth() + 1;
	var b_thisMonth = false;
	if (y == today_y && m == today_m)
		b_thisMonth = true;

	// var d=this.date.getDate();
	var _weekFirstDay = CaculateWeekDay(y, m, 1);
	var _days = CaculateMonthDays(y, m);
	
	if(m==1){
		var pre_days = CaculateMonthDays(y-1, 12);

	}else{
		var pre_days = CaculateMonthDays(y, m-1);
	}
	var _weekLastDay = CaculateWeekDay(y, m, _days)
	var _index = new Number(0);
	var _date = new Number(1);
	var _rows = new Number(1);

	var _table =document.createElement("table");	this.parentDivNode.appendChild(_table);
	this.tableNode=_table;
	var _tr =document.createElement("tr");
	_table.appendChild(_tr);
	_table.className="table_CalendarView";
	var _th=document.createElement("th");
	_tr.appendChild(_th);
	_th.setAttribute("colspan","5");
	_th.id="date_"+ this.index;
	_th.innerHTML=+ y
			+ "年"
			+ m
			+ "月";
	var _th_leftarrow=document.createElement("th");
	var index=this.index;
	_th_leftarrow.attachEvent('onclick',function(){Instance(index).MiniCalendar_goPreMonth();} );
	_th_leftarrow.innerHTML="<<"; 
	_tr.appendChild(_th_leftarrow);
	
	var _th_rightarrow=document.createElement("th");
	_th_rightarrow.attachEvent('onclick',function(){Instance(index).MiniCalendar_goNextMonth();} );
	_th_rightarrow.innerHTML=">>"; 
	_tr.appendChild(_th_rightarrow);
	_tr.appendChild(_th_rightarrow);
	_table.appendChild(_tr);
	var _tr_title =document.createElement("tr");
	_tr_title.className="calendar-table-title";
	_table.appendChild(_tr_title);
	var weekname_title=["一","二","三","四","五","六","七"];
	
	for(var i =0;i<7;i++){
		var _th_weekname=document.createElement("th");
		_tr_title.appendChild(_th_weekname);
		_th_weekname.innerHTML=weekname_title[i];
		
	}
	var str = "<table   class=\"table_CalendarView\"  >"
			+ "<tr align=center>"
			+ "<th colspan=5 id=\"date_"
			+ this.index
			+ "\"> "
			+ y
			+ "年"
			+ m
			+ "月</th>"
			+"<th  onclick=\"Instance('"
			+ this.index
			+ "').MiniCalendar_goPreMonth(event)\"> << </th>"
			+ "<th  onclick=\"Instance('"
			+ this.index
			+ "').MiniCalendar_goNextMonth()\"> >></th>"
			+ "</tr>"
			+ "<tr class=\"calendar-table-title\"><th>一</th><th>二</th><th>三</th><th>四</th><th>五</th><th>六</th><th>七</th></tr>"
			+ "<tr>";
	var pre_mon;
	var pre_year;
	if(m==1){
	var pre_mon=12;
	pre_year=y-1;
	}else{
		pre_mon=m-1;
		pre_year=y;
	}
	
	var _tr_3=document.createElement("tr");
	_table.appendChild(_tr_3);
	for ( var i = 1; i < _weekFirstDay; i++) {
	
		var _td_date=document.createElement("td");
		_tr_3.appendChild(_td_date);
		var evalstr_0= "Instance(\'"+index+"\').selectAnotherMonDateTd(this)";
		_td_date.attachEvent('onclick',function(){eval(evalstr_0);});
		_td_date.innerHTML=pre_days-_weekFirstDay+i+1;
		_td_date.className ="othermonth";
		_td_date.id="calendar-mini-"+pre_year+"-"+pre_mon+"-"+(pre_days-_weekFirstDay+i+1)+"-"+this.index;
		str += "<td id=\"calendar-mini-"+pre_year+"-"+pre_mon+"-"+(pre_days-_weekFirstDay+i+1)+"-"+this.index+"\" onclick=\"Instance('" + this.index
				+ "').selectAnotherMonDateTd(this)\" class=\"othermonth\">"+(pre_days-_weekFirstDay+i+1)+"</td>";
				
		
		_index++;
	}
	if (_index == 7){
		str += "</tr>";
	}
	var _tr_date=_tr_3;
	for ( var i = 0; i < _days; i++) {

		if (_index % 7 == 0 && _index > 0) {
			str += "<tr>";
			_rows++;
			_tr_date=document.createElement("tr");
			_table.appendChild(_tr_date);
		}
		var _td_date=document.createElement("td");
		_tr_date.appendChild(_td_date);
		if(b_thisMonth && today_d == _date){
			_td_date.className="todayTd";
		}
		var evalstr_1= "Instance(\'"+index+"\').MiniCalendar_select(this)";
		//console.log(evalstr_1);
		_td_date.attachEvent('onclick',function(){eval(evalstr_1);});
		_td_date.innerHTML=(_date < 10 ? ("&nbsp;" + _date) : _date);
		str += "<td  "
				+ ((b_thisMonth && today_d == _date) ? "class=\"todayTd \" "
						: "") + "  onclick=\"Instance('" + this.index
				+ "').MiniCalendar_select(this)\">"
				+ (_date < 10 ? ("&nbsp;" + _date) : _date) + "</td>";
		_index++;
		_date++;// td中的字符表示日期
		if (_index % 7 == 0){
			//_tr_date=document.createElement("tr");
			//_table.appendChild(_tr_date);
			str += "</tr>";
		}
	}
		
	var k=1;
	if(m==12){m=1;y++;}else{m++;}
	for ( var i = _weekLastDay + 1; i <= 7; i++) {
		var _td_date=document.createElement("td");
		_tr_date.appendChild(_td_date);
		_td_date.id="calendar-mini-"+y+"-"+m+"-"+ (k)+"-"+this.index;
		_td_date.className='othermonth';
		
		var evalstr_2= "Instance(\'"+index+"\').selectAnotherMonDateTd(this)";
		_td_date.attachEvent('onclick',function(){eval(evalstr_2)});
		_td_date.innerHTML=k;
		str += "<td id=\"calendar-mini-"+y+"-"+m+"-"+ (k)+"-"+this.index+"\" class=\"othermonth\" onclick=\"Instance('" + index
				+ "').selectAnotherMonDateTd(this)\">"+k+"</td>";
				k++;
	}

	str += "</tr>";
	
	if (_rows < 6) {
	_tr_date=document.createElement("tr");
			_table.appendChild(_tr_date);
	str += "<tr>";
		for ( var i =0; i < 7; i++) {
		var _td_date=document.createElement("td");
		_tr_date.appendChild(_td_date);
		_td_date.id="calendar-mini-"+y+"-"+m+"-"+ (k++)+"-"+this.index;
		_td_date.className='othermonth';
		var evalstr_3= "Instance(\'"+index+"\').selectAnotherMonDateTd(this)";
		_td_date.attachEvent('onclick',function(){eval(evalstr_3)});
		_td_date.innerHTML=k-1;
		str += "<td id=\"calendar-mini-"+y+"-"+m+"-"+ (k++)+"-"+this.index+"\" class=\"othermonth\" onclick=\"Instance('" + this.index
				+ "').selectAnotherMonDateTd(this) \">"+ (k-1)+"</td>";
	}
	str += "</tr>";
		_rows++;
	}
	str += "</table>";
	return "";
};
SelectDate.prototype.selectAnotherMonDateTd = function(it) {//console.log("selectAnotherMonDateTd"+it.innerHTML);
	var y =it.id.split("-")[2];
	var m =it.id.split("-")[3];
	var d =it.id.split("-")[4];
	
	
	var day =new Date();
	day.setYear(y);
	day.setMonth(m-1);
	day.setDate(d);
	this.dummyDay=day;
		this.selectDate(day);
	//对外响应this.refreshCalendarEventView();

};
SelectDate.prototype.MiniCalendar_goPreMonth = function(it) {
	this.goPreMonth();
			this.refreshView();

};

SelectDate.prototype.MiniCalendar_goNextMonth = function(it) {
	this.goNextMonth();
			this.refreshView();

};
SelectDate.prototype.MiniCalendar_select = function(e) {//console.log("MiniCalendar_select"+e.innerHTML);
var bToday = (this.selectedDay != null
				&& this.selectedDay.getFullYear() == this.today.getFullYear()
				&& this.selectedDay.getMonth() == this.today.getMonth() && this.selectedDay
				.getDate() == this.today.getDate());
		// edit the value of dummyDay and selectedDay
		// edit the css previous selected Day and edit the current selected Day
		// if previous selected day == selected Day selectedMode change
		// if
		this.selectedDay = this.dummyDay;
		
		this.selectedDay.setDate(new Number(e.innerHTML.replace("&nbsp;","")));
		this.dummyDay = this.selectedDay;

		var curSelectedTd = e;
		var curSeletedTr = (e.parentNode ? e.parentNode : null);

		/*if (null != this.preSelectedTd
				&& "undefined" != (typeof this.preSelectedTd)) {
			if (curSelectedTd == this.preSelectedTd) {
				//this.selectMode = !this.selectMode;
			}
		}*/
		// cancel css
		if (null != this.preSelectedTd
				&& "undefined" != (typeof this.preSelectedTd)) {

			//this.preSelectedTr.className = "";

			this.preSelectedTd.className = (!bToday ? "" : "todayTd");

		}
		// add css

		curSelectedTd.className = "selectedTd";
		//curSeletedTr.className = (this.selectMode ? "selectedTr" : "");
		// appointed td tr
		//this.preSelectedTr = e.parentNode;
		this.preSelectedTd = e;
	
		$(this.parentInput_id).value=this.selectedDay.format("yyyy-MM-dd");
		$(this.parentInput_id).parentNode.removeChild(this.parentDivNode);
		
	};

// 界面的刷新--------------------------------------------------------------------------------------------------------------------------

SelectDate.prototype.refreshView = function() {

 	this.parentDivNode.removeChild(this.tableNode);
 
	this.getCalendarStr();
	this.clear();

};
SelectDate.prototype.selectDate = function(day) {
	// 重新设定dummyday selectedDay 重新
	this.dummyDay = day;
	this.selectedDay = day;
	this.refreshView();

};
// 数据的初始化------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
SelectDate.prototype.clear = function() {
	
};