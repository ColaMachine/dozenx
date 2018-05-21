//Using("System.Data.Calendar");
//div
//	table css: MiniCalendar_table
//			tr 
//					th 年月日
//			tr	
//					th 一二三四五六七
//			tr
//					td 日期
function MiniCalendar() {
	Calendar.call(this);
	this.setHashCode();
	this.index = this.hashCode;
}

t = MiniCalendar.Extends(Calendar, "MiniCalendar");
t.div_id="";
MiniCalendar.prototype.render = function(id) {
	this.div_id=id;
		var str = this.getCalendarStr();

	$$(id).innerHTML=str;
};

MiniCalendar.prototype.getCalendarStr = function() {
	var today_y = this.today.getFullYear()
	var today_m = this.today.getMonth() + 1;
	var today_d = this.today.getDate();
	
	
	var y =  this.dummyDay.getFullYear();
	var m = this.dummyDay.getMonth() + 1;
	var b_thisMonth = false;
	if (y == today_y && m == today_m)
		b_thisMonth = true;

	// var d=this.date.getDate();
	var _weekFirstDay = CaculateDaysWeekNum(y, m, 1);
	var _days = CaculateMonthDays(y, m);
	
	if(m==1){
		var pre_days = CaculateMonthDays(y-1, 12);

	}else{
		var pre_days = CaculateMonthDays(y, m-1);
	}
	var _weekLastDay = CaculateDaysWeekNum(y, m, _days)
	var _index = new Number(0);
	var _date = new Number(1);
	var _rows = new Number(1);
	var str = "<table id=\'minicalendar\'  class=\"table_CalendarView\"  >"
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
	for ( var i = 1; i < _weekFirstDay; i++) {
		

		str += "<td id=\"calendar-mini-"+pre_year+"-"+pre_mon+"-"+(pre_days-_weekFirstDay+i+1)+"\" onclick=\"Instance('" + this.index
				+ "').selectDateTd(this)\" class=\"othermonth\">"+(pre_days-_weekFirstDay+i+1)+"</td>";
		_index++;
	}
	if (_index == 7)
		str += "</tr>";

	for ( var i = 0; i < _days; i++) {
		if (_index % 7 == 0 && _index > 0) {
			str += "<tr>";
			_rows++;
		}
		str += "<td  "
				+ ((b_thisMonth && today_d == _date) ? "class=\"todayTd \" "
						: "") + "  onclick=\"Instance('" + this.index
				+ "').MiniCalendar_select(this)\">"
				+ (_date < 10 ? ("&nbsp;" + _date) : _date) + "</td>";
		_index++;
		_date++;// td中的字符表示日期
		if (_index % 7 == 0)
			str += "</tr>";
	}
	var k=1;
	if(m==12){m=1;y++;}else{m++;}
	for ( var i = _weekLastDay + 1; i <= 7; i++) {
		
		str += "<td id=\"calendar-mini-"+y+"-"+m+"-"+ (k)+"\" class=\"othermonth\" onclick=\"Instance('" + this.index
				+ "').selectDateTd(this)\">"+k+"</td>";k++;
	}

	str += "</tr>";
	
	if (_rows < 6) {
	str += "<tr>";
		for ( var i =0; i < 7; i++) {
		
		str += "<td id=\"calendar-mini-"+y+"-"+m+"-"+ (k++)+"\" class=\"othermonth\" onclick=\"Instance('" + this.index
				+ "').selectDateTd(this) \">"+ (k-1)+"</td>";
	}
	str += "</tr>";
		_rows++;
	}
	str += "</table>";

	return str;
};
MiniCalendar.prototype.selectDateTd = function(it) {
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
MiniCalendar.prototype.MiniCalendar_goPreMonth = function(it) {
	this.goPreMonth();
			this.refreshView();

};

MiniCalendar.prototype.MiniCalendar_goNextMonth = function(it) {
	this.goNextMonth();
			this.refreshView();

};
MiniCalendar.prototype.MiniCalendar_select = function(e) {
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
        $$(this.div_id).innerHTML= this.selectedDay.format("yyyy-MM-dd");
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
	};

// 界面的刷新--------------------------------------------------------------------------------------------------------------------------

MiniCalendar.prototype.refreshView = function() {
	$$(this.div_id).innerHTML = this.getCalendarStr();
	this.clear();

};
MiniCalendar.prototype.selectDate = function(day) {
	// 重新设定dummyday selectedDay 重新
	this.dummyDay = day;
	this.selectedDay = day;
	this.refreshView();

};
// 数据的初始化------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
MiniCalendar.prototype.clear = function() {
	
};