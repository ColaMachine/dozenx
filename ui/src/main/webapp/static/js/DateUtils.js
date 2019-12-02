/*---------------------------------------------------------------------------*\
|  Subject: JavaScript Framework
|  Author:  cola.machine
|  Created: 2014-4-25 17:20:30
|  Version: v1.0
|-----------------------------------
|  QQ:371452875
|  Copyright (c) cola.machine   MIT-style license
|  The above copyright notice and this permission notice shall be
|  included in all copies or substantial portions of the Software
\*---------------------------------------------------------------------------*/
/**
 * @author colamachine
 * 日期格式化
 */

 'use strict';
   function DateUtils(){
 }

Date.prototype.format = function(format)
   {
    var o = {
	    "M+" : this.getMonth()+1, // month
	    "d+" : this.getDate(),    // day
	    "H+" : this.getHours(),   // hour
	    "m+" : this.getMinutes(), // minute
	    "s+" : this.getSeconds(), // second
	    "q+" : Math.floor((this.getMonth()+3)/3),  // quarter
	    "S" : this.getMilliseconds() // millisecond
    }
    if(/(y+)/.test(format)) //用正则表达式去解析格式字符串 如果
    	format=format.replace(RegExp.$1,    (this.getFullYear()+"").substr(4 - RegExp.$1.length));//向后截取 如果fromt 是yy 就截取 2015 的15部分
    for(var k in o)
    	if(new RegExp("("+ k +")").test(format))
    		format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] :("00"+ o[k]).substr((""+ o[k]).length));// 很妙的设计
    return format;
   }

/*function parseDate(dateStr,format){
	var date =new Date();
	if(/(y+)/.test(format)){
		var year =  dateStr.substr(format.indexOf(RegExp.$1),RegExp.$1.length);
	}
}*/
/**
 * @author colamachine
 * 根据指定日期字符串 和 日期格式转化成 date 对象返回
 */
DateUtils.prototype.parseDate= function (dateStr,format){
	var year ,month,day,hour=0,minute=0,seconds=0;

	var date =new Date();
	if(/(y+)/.test(format)){
		 year=parseInt(dateStr.substr(format.indexOf(RegExp.$1),RegExp.$1.length));
		 date.setFullYear(year);
	}
	if(/(M+)/.test(format)){
		 month=parseInt(dateStr.substr(format.indexOf(RegExp.$1),RegExp.$1.length));
		 date.setMonth(month-1);
	}
	if(/(d+)/.test(format)){
		 day=parseInt(dateStr.substr(format.indexOf(RegExp.$1),RegExp.$1.length));
		 date.setDate(day);
	}
	if(/(h+)/.test(format)){
		 hour=parseInt(dateStr.substr(format.indexOf(RegExp.$1),RegExp.$1.length));
		//date+=hour*60*60000;

	}date.setHours(hour);
	if(/(H+)/.test(format)){
    		 hour=parseInt(dateStr.substr(format.indexOf(RegExp.$1),RegExp.$1.length));
    		//date+=hour*60*60000;

    	}date.setHours(hour);
	if(/(m+)/.test(format)){
		 minute=parseInt(dateStr.substr(format.indexOf(RegExp.$1),RegExp.$1.length));
//		date+=minute*60000;

	} date.setMinutes(minute);
	if(/(s+)/.test(format)){
		 seconds=parseInt(dateStr.substr(format.indexOf(RegExp.$1),RegExp.$1.length));
//		date+=seconds*1000;

	}
	date.setSeconds(seconds);

	return new Date(date);
}

DateUtils.prototype.getDayTimeStamp= function (daystr){
	var date = parseDate(daystr,"yyyy-MM-dd");

	return parseInt(date.getTime()/1000/60/24/60);
}
DateUtils.prototype.hhmmStamp2Str= function  (val){
	var hh=parseInt(val/60);
	var mm=val%60;
	if(hh<10)
		hh="0"+hh;
	if(mm<10)
		mm="0"+mm;
	return hh+":"+mm;
}
DateUtils.prototype.YMDStamp2Str= function   (val){
	var date = new Date(val*24*60*60000).format("yyyy-MM-dd");
	return date;
}
/**
 * 根据精确到分钟的时间戳 换得 改天0点0分0秒对应的精确到分钟的时间戳
 * @param val
 * @returns
 */
DateUtils.prototype.getDateTime= function   (val){
	var date = new Date(val*60000);
	 date.setMinutes(0);
	 date.setHours(0);
	 date.setSeconds(0);
	 return parseInt(date.getTime()/6000);
}

DateUtils.prototype.getDateStrByFen= function    (val){
	var date = new Date(val*60000);
	return date.format("yyyy-MM-dd HH:mm")

}
/**
 * 根据精确到分钟的时间戳 换得 该时间戳的时分数值量
 * @param val
 * @returns
 */
DateUtils.prototype.getHMTime= function   (val){
	var date = new Date(val*60000);
	 return parseInt(date.getHours()*60+date.getMinutes());
}
DateUtils.prototype.add4HHMM= function   (timeStart,minutes){
	var arr= timeStart.split(":");
	var h1 =parseInt(arr[0]);
	var m1= parseInt(arr[1]);
	h1=h1+parseInt(minutes/60);
	if(h1<10)
	m1+=minutes%60;
	if(m1==60)
		{m1=0;h1+=1;}
		h1="0"+h1;
	if(m1<10)
		m1="0"+m1;
	return h1+":"+m1;
}
/**
 * 计算两个hh:mm时间的差 分钟
 * @param timeStart
 * @param timeEnd
 * @returns {Number}
 */
DateUtils.prototype.getTimeLongsBetweenHHMM= function  (timeStart,timeEnd){

	var arr= timeStart.split(":");
	var h1 =parseInt(arr[0]);
	var m1= parseInt(arr[1]);
	 arr= timeEnd.split(":");
	var h2= parseInt(arr[0]);
	var m2= parseInt(arr[1]);
	console.log("getTimeLongsBetweenHHMM:"+((h2-h1)*60+ m2-m1));
	return (h2-h1)*60+ m2-m1;
}
/**
 * 增加天数 建议挪入 dateUtils 2015-05-29 11:31:21
 * @param day
 * @param increment
 * @returns {Date}
 */
DateUtils.prototype.DateAdd= function   (day,increment){//date int

	var d=day.getDate();
	var m=day.getMonth();
	var y=day.getFullYear();
	var myDay=new Date();
	myDay.setYear(y);
	myDay.setMonth(m);
	myDay.setDate(d+increment);
	return myDay;

}

DateUtils.prototype.DateAddDay= function   (day,increment){//date int

	var d=day.getDate();
	var m=day.getMonth();
	var y=day.getFullYear();
	var myDay=new Date();
	myDay.setYear(y);
	myDay.setMonth(m);
	myDay.setDate(d+increment);
	return myDay;

}

DateUtils.prototype.DateAddWeek= function   (day,increment){//date int

	var d=day.getDate();
	var m=day.getMonth();
	var y=day.getFullYear();
	var myDay=new Date();
	myDay.setYear(y);
	myDay.setMonth(m);
	myDay.setDate(d+increment*7);
	return myDay;

}


DateUtils.prototype.DateAddHour= function   (day,increment){//date int
    var h = day.getHours();
	var d=day.getDate();
	var m=day.getMonth();
	var y=day.getFullYear();
	var myDay=new Date();
	myDay.setYear(y);
	myDay.setMonth(m);
	myDay.setDate(d);
	myDay.setHours(h+increment);
	return myDay;

}

DateUtils.prototype.DateAddMinute= function   (day,increment){//date int
    var h = day.getHours();
     var minute = day.getMinutes();
	var d=day.getDate();
	var m=day.getMonth();
	var y=day.getFullYear();
	var myDay=new Date();
	myDay.setYear(y);
	myDay.setMonth(m);
	myDay.setDate(d);
	myDay.setHours(h);
    myDay.setMinutes(minute+increment);
	return myDay;

}
DateUtils.prototype.DateAddMonth= function   (day,increment){//date int

	var d=day.getDate();
	var m=day.getMonth();
	var y=day.getFullYear();
	var myDay=new Date();
	myDay.setYear(y);
	myDay.setMonth(m+increment);
	myDay.setDate(d);
	console.log(myDay.format("yyyy-MM--dd"))
	return myDay;


}

DateUtils.prototype.DateAddYear= function   (day,increment){//date int

	var d=day.getDate();
	var m=day.getMonth();
	var y=day.getFullYear();
	var myDay=new Date();
	myDay.setYear(y+increment);
	myDay.setMonth(m);
	myDay.setDate(d);
	return myDay;

}
DateUtils.prototype.DateAddInSelf= function   (day,increment){//date int


	day.setDate(day.getDate()+increment);


}

/**
 * 根据指定日期得到 当前星期的数组
 * @param day
 * @returns {Array} 星期天~星期六
 */
DateUtils.prototype.getDateOfWeek= function    ( day)//date
	{
		var myDay=day;
		var mon;
		var tues;
		var wed;
		var thurs;
		var fri;
		var sat;
		var sun;
		var mydate = this.CaculateDaysWeekNum(myDay.getFullYear(),myDay.getMonth()+1,myDay.getDate());
		//1 代表 日 2 代表1 7代表6 8
			mon=this.DateAdd(myDay,1-mydate);
			tues=this.DateAdd(myDay,2-mydate);
			wed=this.DateAdd(myDay,3-mydate);
			thurs=this.DateAdd(myDay,4-mydate);
			fri=this.DateAdd(myDay,5-mydate);
			sat=this.DateAdd(myDay,6-mydate);
			sun=this.DateAdd(myDay,7-mydate);
		var arr=[mon,tues,wed,thurs,fri,sat,sun];
		return arr;
	}

/**
 * 根据日期算出星期几
 * @param y
 * @param m
 * @param d
 * @returns {Number} 星期几 星期一是1 星期天是7
 */
DateUtils.prototype.CaculateDaysWeekNum= function    ( y, m,d){
	y=new Number(y);
	m=new Number(m);
	d=new Number(d);
	if(m==1||m==2) {
	m+=12;
	y--;
	}
	var week=(d+2*m+Math.floor(3*(m+1)/5)+y+Math.floor(y/4)-Math.floor(y/100)+Math.floor(y/400))%7;
	return week+1;
};


//get the amount of day in appointed month

/**
 * 计算这个年月有多少天
 * @param y
 * @param m
 * @returns 天数
 */
DateUtils.prototype.CaculateMonthDays= function   (y,m){
	y=new Number(y);
	m=new Number(m);
	var MonthDays=[31,28,31,30,31,30,31,31,30,31,30,31];
	if((y%4==0 && y%100!=0) || y%400==0  )
	MonthDays[1]=29;
	return MonthDays[m-1];
}

/*function getAllDaysOfThisWeek(y,m,d){
	var week;
	var weeknum=CaculateDaysWeekNum(y,m,d);

	week[0]=;

}*/


/**
 * 日期转成yyyyMMdd格式字符串
 * @param date
 * @returns {String}
 */
DateUtils.prototype.getdayStrFromDate= function  (date){
	//其实可以使用 format 的
	return date.getFullYear()
				+ "-"
				+ (date.getMonth() < 9 ? ("0" + (date.getMonth() + 1))
						: (date.getMonth() + 1))
				+ "-"
				+ (date.getDate() < 10 ? ("0" + date.getDate())
						: date.getDate()) ;
}
/**
 * 根据时间得到他在 日历格子中的格数 如果flat 为1 代表 第一格的序号是1
 * @param time
 * @param flag
 * @returns {Number}
 */
DateUtils.prototype.getTimeEndIndex= function   (time,flag){
	var hour=parseInt(time.substr(0,2));
//	var min=parseInt(time.substr(3,2));
	var index=hour*2;
	/*if(min<30){

	}else{
		index++;
	}*/
	if(flag==1){
		index++;
	}
	return index;

}

/**
 *把当前日期 转成date time  转成分钟数
 * @param date日期 保留日期的年月日
 * @param time 0~23 代表小时数目
 * @returns 日期和小时代表的分钟数
 */
DateUtils.prototype.getTimes= function  (date,time){
	//var dateTime = parseInt( Date.parse(date+"T"+time+":00")/60000);
//	alert(date+"T"+time+":00");
//	alert( new Date(Date.parse(date+"T"+time+":00")));

	var date =
	this.parseDate(date+"T"+time,"yyyy-MM-ddThh:mm");
	var times = date.getTime();
	var minuteTime = parseInt(date.getTime()/60000);
	console.log(date.format("yyyy-MM-dd hh:mm:ss"));
	return minuteTime;
}
/**
 *
 * @param date 时间戳/(24*60*60000)
 * @param time 时间戳%(24*60*60000)
 * @returns 时间戳/60000
 */
DateUtils.prototype.getTimeStamp= function   (date,time){
	return date*24*60+time;
}
var YMD= {
		year:0,
		month:0,
		day:0
};

/**
 * 到前年
 */
DateUtils.prototype.getPreYear= function   (day){
	//var day = this.dummyDay;
	var y = day.getFullYear();
	var m = day.getMonth();
	var d = day.getDate();
	y--;

	if (d > 28) {
		var endDay = this.CaculateMonthDays(y, m);
		d = d > endDay ? endDay : d;
	}
	day.setYear(y);
	day.setMonth(m);
	day.setDate(d);
	return day;
}


DateUtils.prototype. getNextYear =function (day) {
	//var day = this.dummyDay;
	var y = day.getYear();
	var m = day.getMonth();
	var d = day.getDate();
	y++;

	if (d > 28) {
		var endDay = this.CaculateMonthDays(y, m);
		d = d > endDay ? endDay : d;
	}
	day.setYear(y);
	day.setMonth(m);
	day.setDate(d);
	return day;
}



/**
 * 到上月
 */
DateUtils.prototype. getPreMonth =function    (day) {
	//var day = this.dummyDay;
	var m = day.getMonth();
	var y = day.getFullYear();
	var d = day.getDate();
	if (m == 0) {
		y--;
		m = 11;
	} else {
		m--;
	}
	if (d > 28) {
		var endDay = this.CaculateMonthDays(y, m);
		d = d > endDay ? endDay : d;
	}
	day.setYear(y);
	day.setMonth(m);
	day.setDate(d);
	return day;
}
/**
 * 到下月
 */
DateUtils.prototype. getNextMonth =function    (day) {
	//var day = this.dummyDay;
	var m = day.getMonth();
	var y = day.getFullYear();
	var d = day.getDate();
	if (m == 11) {
		y++;
		m = 0;
	} else {
		m++;
	}
	if (d > 28) {
		var endDay = this.CaculateMonthDays(y, m + 1);
		d = (d > endDay ? endDay : d);
	}
	day.setYear(y);
	day.setMonth(m, d);
	//this.dummyDay = day;
	return day
}


/**
 * 得到指定日期后一天日期
 * @param day
 * @returns {Date}
 */
DateUtils.prototype.getNextDay =function (day){
	var m=day.getMonth();
	var y=day.getFullYear();
	var d=day.getDate();
	/*if(d<CaculateMonthDays(y,m))
		d++;
	else
	{
		if(m<11){
			m++;

		}else{
			y++;
			m=0;

		}
		d=1;
	}*/
	var myDate=new Date();
	myDate.setYear(y);
	myDate.setMonth(m);
	myDate.setDate(day.getDate()+1)
	return myDate;
}
DateUtils.prototype. getFirstMonthDay =function (date){
    var day =this.copyDate(date);
    day.setHours(0);
    day.setMinutes(0);
    day.setSeconds(0);
    day.setDate(1);


    return day;
}
DateUtils.prototype. getLastMonthDay =function  (date){
    var day =this.copyDate(date);
        day.setHours(0);
        day.setMinutes(0);
        day.setSeconds(0);
        day.setDate(this.CaculateMonthDays(day.getFullYear,day.getMonth()+1));
        return day;
}

/**
 * 获取指定日期的前一天
 * @param day
 * @returns {Date}
 */
DateUtils.prototype. getPreDay =function  (day){//date
	var m=day.getMonth();
	var y=day.getFullYear();
	var d=day.getDate();
	/*if(d>1)
		d--;
	else
	{
		if(m>0){
			m--;

		}else{
			y--;
			m=11;

		}
		d=CaculateMonthDays(y,m);
	}*/
	var myDate=new Date();
	myDate.setYear(y);
	myDate.setMonth(m);
	myDate.setDate(day.getDate()-1);
	return myDate;
}

/**
 * 获取指定日期的前一天
 * @param day
 * @returns {Date}
 */
DateUtils.prototype. getPreWeek =function  (day){//date
	var m=day.getMonth();
	var y=day.getFullYear();
	var d=day.getDate();

	var myDate=new Date();
	myDate.setYear(y);
	myDate.setMonth(m);
	myDate.setDate(day.getDate()-7);
	return myDate;
}

/**
 * 获取指定日期的前一天
 * @param day
 * @returns {Date}
 */
DateUtils.prototype. getNextWeek =function   (day){//date
	var m=day.getMonth();
	var y=day.getFullYear();
	var d=day.getDate();

	var myDate=new Date();
	myDate.setYear(y);
	myDate.setMonth(m);
	myDate.setDate(day.getDate()+7);
	return myDate;
}

//
DateUtils.prototype. eqToday =function   (date){
	var now =new Date();
	if(date.getFullYear()==now.getFullYear() &&
			date.getMonth()==now.getMonth() &&
			date.getDate()==now.getDate()
	)
		return true;
	return false;
}
/**
 * 比较开始日期是否小于结束日期
 * @param startDate yyyy-MM-dd
 * @param endDate yyyy-MM-dd
 */
DateUtils.prototype. compareYMD =function (startDate,endDate){
	var startDateInt = YMD2Int(startDate);
	var endDateInt=YMD2Int(endDate);
	if(startDateInt<endDateInt)
		return 1;
	if(startDateInt==endDateInt)
		return 0;
	if(startDateInt>endDateInt)
		return -1;
}
//转成数字形式
DateUtils.prototype. YMD2Int =function (str){
	if(str.length!=10){
		logger.log("日期格式不正确");
		return;
	}
	var year= getYFromYMD(str);
	var month =getMFromYMD(str);
	var date = getDFromYMD(str);
	return year*10000+month*100+date;
}
DateUtils.prototype. getYFromYMD =function  (str){
	return parseInt(str.substr(0,4));
}
DateUtils.prototype. getMFromYMD =function  (str){
	return parseInt(str.substr(6,2));
}
DateUtils.prototype. getDFromYMD =function  (str){
	return parseInt(str.substr(8,2));
}
//var DateUtils={}
DateUtils.prototype. retainDay =function (date){
    date.setSeconds(0);
    date.setMilliseconds(0);
    date.setMinutes(0);
    date.setHours(0);
    return date;
}
DateUtils.prototype. night =function (date){
    date.setSeconds(59);
    date.setMilliseconds(999);
    date.setMinutes(59);
    date.setHours(23);
    return date;
}
DateUtils.prototype.copyDate=function(date){
var dateNew =new Date(date.getTime());

    return dateNew;
}
DateUtils.prototype. getDateSince1972=function(date){
    return date.getTime()/1000/60/60/24;
}
DateUtils.prototype. autoChange=function (originStartDay ,changedStartDay,originEndDay){
    return this.DateAdd(this.parseDate(changedStartDay,"yyyy-MM-dd"),this.getDateSince1972(this.parseDate(originEndDay,"yyyy-MM-dd"))-this.getDateSince1972(this.parseDate( originStartDay,"yyyy-MM-dd"))).format("yyyy-MM-dd");

}
var DateUtil = new DateUtils();

/*
if(module)
module.exports = DateUtil;*/
