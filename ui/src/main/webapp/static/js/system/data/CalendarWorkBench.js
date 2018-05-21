/*---------------------------------------------------------------------------*\
|  Subject:    Calendar 2.0
|  NameSpace:  Calendar.View
|  Author:     Cola.Machine
|  Created:    2010-01-27
|  Version:    2010-01-27
|-------------------------------------------------------------
|
\*---------------------------------------------------------------------------*/



function CalendarWorkBeanch(){System.call(this);this.index=this.hashcode;}
t=Calendar.Extends(System, "Calendar");
t.count=0;
t.getUniqueId=function(){return "ca"+(t.count++).toString(36);};
t.selectedWeek=new Array();
//variable
t.dummyDay=new Date();
t.selectedDay=null;
t.today=new Date();
//go date转到制定的日期
//转换模式 日月周
//
Calendar.addProperty("dummyDay", new Date(), Function.WRITE);
Calendar.addProperty("selectedDay", new Date(), Function.WRITE);
Calendar.addProperty("today", new Date(), Function.WRITE);
Calendar.prototype.goPreYear=function(){
	var day = this.dummyDay;
	var y=day.getFullYear();
	var m=day.getMonth();
	var d=day.getDate();
	y--;
	
	if(d>28){
		var endDay=CaculateMonthDays(y,m);
		d=d>endDay?endDay:d;
	}
	day.setYear(y);
	day.setMonth(m);
	day.setDate(d);
	this.dummyDay=day;
};
Calendar.prototype.goNextYear=function(){
	var day = this.dummyDay;
	var y=day.getYear();
	var m=day.getMonth();
	var d=day.getDate();
	y++;
	
	if(d>28){
		var endDay=CaculateMonthDays(y,m);
		d=d>endDay?endDay:d;
	}
	day.setYear(y);
	day.setMonth(m);
	day.setDate(d);
	this.dummyDay=day;
};
Calendar.prototype.goPreMonth=function(){
	var day = this.dummyDay;
	var m=day.getMonth();
	var y=day.getFullYear();
	var d=day.getDate();
	if(m==0)
	{
		y--;
		m=11;
	}else{
		m--;
	}
	if(d>28){
		var endDay=CaculateMonthDays(y,m);
		d=d>endDay?endDay:d;
	}
	day.setYear(y);
	day.setMonth(m);
	day.setDate(d);
	this.dummyDay =day;
};
Calendar.prototype.goNextMonth=function(){
	var day = this.dummyDay;
	var m=day.getMonth();
	var y=day.getFullYear();
	var d=day.getDate();
	if(m==11)
	{
		y++;
		m=0;
	}else{
		m++;
	}
	if(d>28){
		var endDay=CaculateMonthDays(y,m+1);
		d=(d>endDay?endDay:d);
	}
	day.setYear(y);
	day.setMonth(m,d);
	this.dummyDay=day;
};
Calendar.prototype.goPreDay=function(){
	var day = this.dummyDay;
	var m=day.getMonth();
	var y=day.getFullYear();
	var d=day.getDate();
	if(d>1)
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
	}
	day.setYear(y);
	day.setMonth(m,d);
	this.dummyDay =day;
};
Calendar.prototype.goNextDay=function(){
	var day = this.dummyDay;
	var m=day.getMonth();
	var y=day.getFullYear();
	var d=day.getDate();
	if(d<CaculateMonthDays(y,m))
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
	}
	day.setYear(y);
	day.setMonth(m,d);
	this.dummyDay =day;
};