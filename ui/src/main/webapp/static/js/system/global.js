var Sys = {};
function $$(str){
	//var ele = document.getElementById(str);

	return document.getElementById(str);
}
function bind(dom,event_name,function_name){
		switch( event_name){
			case 'click':
				if(Sys.ie){
					dom.attachEvent("onclick", function_name);

				}else{
					dom.addEventListener("click",function_name, false);
				}

				break;
            case 'mousemover':

                                dom.addEventListener("mousemover",function_name, false);

		}


}
function judgeBrowser(){

     var ua = navigator.userAgent.toLowerCase();
    // alert(ua);
    // alert(/firefox\/([\d.]+)/.test(ua));
     if (window.ActiveXObject)
         Sys.ie = ua.match(/msie ([\d.]+)/)[1];
     else if (/firefox\/([\d.]+)/.test(ua))
         Sys.firefox = ua.match(/firefox\/([\d.]+)/)[1];
     else if (window.MessageEvent && !document.getBoxObjectFor)
         Sys.chrome = ua.match(/chrome\/([\d.]+)/)[1];
     else if (window.opera)
         Sys.opera = ua.match(/opera.([\d.]+)/)[1];
     else if (window.openDatabase)
         Sys.safari = ua.match(/version\/([\d.]+)/)[1];

     //以下进行测试
    /* if(Sys.ie) alert('IE: '+Sys.ie);
     if(Sys.firefox) alert('Firefox: '+Sys.firefox);
     if(Sys.chrome) alert('Chrome: '+Sys.chrome);
     if(Sys.opera) alert('Opera: '+Sys.opera);
     if(Sys.safari) alert('Safari: '+Sys.safari);*/

}
function isNull(str){
	if(str==null||(str+'').replaceAll(" ","")==''||typeof(str)=='undefined'){
		return true;
	}
	return false;
}


function isNull(it){
	if(it==null || it==''|| it=='undefined'){
		return true;
	}return false;
}
Function.prototype.Apply = function(thisObj)
{
    var _method = this;
    return function(data)
    {
        return _method.apply(thisObj,[data]);
    };
};
function getInfo(o){//取得坐标
		if(isNull(o)){
	     	alert("can't find it:"+o);

     	}
	   	var to=new Object();
	   	to.left=to.right=to.top=to.bottom=0;

	   	var twidth=o.offsetWidth;
	   	var theight=o.offsetHeight;
	   	while(o!=document.body && !isNull(o)){
	   		if(isNull(o.offsetParent)){
	     		alert("can't find it's parentNode1:"+o);
	     	}
	     	to.left+=o.offsetLeft;
	     	to.top+=o.offsetTop;

     		o=o.offsetParent;
	   	}
     	to.right=to.left+twidth;
     	to.bottom=to.top+theight;
	   	return to;
}
function isSuccessFully(str){
	var strs =str.split("||");
	alert(strs[1]);
}



/**
 *日历活动对象
 */
function calendarEvent(id,title,day,startTime,endTime){
	//基础信息
	this.id=id||"";
	this.day=day||"";//格式yyyy-MM-dd
	this.title=title||"";//标题
	this.startTimeSV=startTime||"";//hh:mm
	this.startTime=0;
	this.endTime=0;
	this.endTimeSV="";//hh:mm
	this.isdel=false;
	this.lastChangeTime=new Date().getTime();
	//叠排
	this.startIndex=0;
	this.endIndex=0;
	this.index=0;
	this.cellIndex=0;//第几列
	this.top=0;
	this.left=0;
	this.length=0;
	this.height=0;
	this.MaxRepeatedCount=0;
	this.generation=0;
	/*this.month="";
	this.year="";
	this.start_H="";
	this.start_M="";

	this.end_H=0;
	this.end_M=0;
	this.day=0;
	this.month=0;
	this.year=0;*/

	//this.dieshu=0;//这一行有多少叠数
	return this;
}
function showCalendar(it){
	/*var div=document.createElement("div");
	div.style.position="absolute";
	it.parentNode.appendChild(div);
	div.style.border="1px solid red";
	div.style.width=50;
	div.style.height=100;
	var arr=getInfo(it);

	div.style.top=arr.top+it.offsetHeight;*/

	//Using("System.Web.UI.WebControls.SelectDate");

	var d = new MiniCalendar();
	d.render(it.id);

	/*var span=document.createElement("span");
	var calendar_dialogue_time_input=document.createElement("input");
	calendar_dialogue_time_input.value=it.innerHTML;
	it.style.display="none";
	span.appendChild(calendar_dialogue_time_input);
	it.parentNode.appendChild(span);
	*/

}
function date2intArr(day){

}
function getTimeStrFromDate(date){
	var hour=date.getHours();
	var minute=date.getMinutes();
	//,10
	return ( hour<10?("0"+hour):hour)+":"+(minute<10?("0"+minute):minute);
}
function getTop(time,hourHeight){
	var hour=parseInt(time.substr(0,2));
	var min=parseInt(time.substr(3,2));
	var height=hour*hourHeight;
	height+= min/60*hourHeight;
	return height
}
function isTouchSide(calEvent1,calEvent2){
	//var start1=parseInt($("event_"+calEvent1.id).style.top,10);
	if(calEvent1.id==calEvent2.id)
		return false;
	var start1=parseInt($$("event_"+calEvent1.id).offsetTop);
	//var end1=start1+parseInt($("event_"+calEvent1.id).offsetHeight);
	var end1=start1+parseInt($$("event_"+calEvent1.id).offsetHeight);
	var start2=parseInt($$("event_"+calEvent2.id).offsetTop,10);
	//var start2=parseInt($("event_"+calEvent2.id).style.top,10);
	var end2=start2+parseInt($$("event_"+calEvent2.id).offsetHeight);
	//console.log("starta"+start1+" enda"+end1+" startb"+start2+" endb"+end2 +(start1>start2&&start2<end1 ) );
	//console.log($$("event_"+calEvent1.id).offsetTop);
	if((start1<start2&&start2<end1 ) || (start1<end2 && end2<end1) ||( start1>=start2&&end1<=end2   )){
		console.log("calEvent1.id:"+calEvent1.id+" calEvent2id:"+calEvent2.id+"starta"+start1+" enda"+end1+" startb"+start2+" endb"+end2 +(start1>start2&&start2<end1 ) );
		return true;
	}else{
	return false;
	}
}

function chongxinfenbu(globalRelatedEvents,relatedEventsO,leftEventsO){
	//globalRelatedEvents.concat(relatedEvents);
	var newRelatedEvents=new Array();//挨着的
	var newLeftArray=new Array();//待选区
	var relatedEvents=relatedEventsO.slice(0);
	var leftEvents=leftEventsO.slice(0);
	for(var i =0;i<relatedEvents.length;i++){
		for(var j=0;j<leftEvents.length;j++){
			////console.log("relatedEvents[i]"+relatedEvents[i]+"relatedEvents.length"+relatedEvents.length);
			//console.log(isTouchSide(relatedEvents[i],leftEvents[j]));
			if(leftEvents[j]!=null&&isTouchSide(relatedEvents[i],leftEvents[j])){
				newRelatedEvents.push(leftEvents[j]);leftEvents[j]=null;
			}
		}
	}
	for(var i=0;i<leftEvents.length;i++){
		if(leftEvents[i]!=null)
		newLeftArray.push(leftEvents[i]);
	}
	if(newRelatedEvents.length==0){
		return globalRelatedEvents;
	}else{

		//console.log("1关联的event个数为"+globalRelatedEvents.length);
		globalRelatedEvents=	globalRelatedEvents.concat(newRelatedEvents);
		//console.log("2关联的event个数为"+globalRelatedEvents.length);
		chongxinfenbu(globalRelatedEvents,newRelatedEvents,newLeftArray);
	}


	return globalRelatedEvents;

}

function getSect(){
	var sectClass=new Object();
	sectClass.generation=0;
	sectClass.Childs=new Array();
	sectClass.pushChilds=function(vampire){

		vampire.sect=this;
		this.Childs.push(vampire);
	};
	sectClass.getGeneration=function(i){
		for(var i=0;i<this.Childs.length;i++){
			if(this.Childs[i].generation==i){
				return this.Childs[i];
			}
		}
		return null;

	};
	sectClass.getRelatedGeneration=function(i,vampire){
		for(var i=0;i<this.Childs.length;i++){
			if(this.Childs[i].generation==i&&isTouchSide(this.Childs[i],vampire)){
				return this.Childs[i];
			}
		}
		return null;

	};
	return sectClass;

}

function bubbleSort(arr){

	for(var i=0;i<arr.length;i++){
		console.log("arr[i].id:"+arr[i].id);
		console.log($$("event_"+arr[i].id));
		arr[i].top=parseInt($$("event_"+arr[i].id).offsetTop);

		arr[i].bottom=arr[i].top+parseInt($$("event_"+arr[i].id).offsetHeight);
		arr[i].MaxRepeatedCount=0;
		arr[i].generation=0;
		//console.log("tttttttt:top:"+parseInt("259")+"top2:259");
	//	console.log("title:"+arr[i].title+"-top:"+parseInt(getInfo($$("event_"+arr[i].id)).top)+"top2:"+getInfo($$("event_"+arr[i].id)).top);
	}

    var temp;//先定义缓存
    for(var i=0;i<arr.length-1;i++){//前面的泡
        for(var J=i+1;J<arr.length;J++){//后面的泡
                if(arr[i].top>arr[J].top){//若前泡>后泡
                	//console.log("交换"+arr[i].top +":"+arr[J].top);
                        temp=arr[i];
                        arr[i]=arr[J];
                        arr[J]=temp;//交换
                    }
                 }
        }
        	//console.log("title:"+arr[0].title);
    return arr;
}

if(!window.attachEvent && window.addEventListener)
{
  Window.prototype.attachEvent = HTMLDocument.prototype.attachEvent=
  HTMLElement.prototype.attachEvent=function(en, func, cancelBubble)
  {
    var cb = cancelBubble ? true : false;
    this.addEventListener(en.toLowerCase().substr(2), func, cb);
  };
  Window.prototype.detachEvent = HTMLDocument.prototype.detachEvent=
  HTMLElement.prototype.detachEvent=function(en, func, cancelBubble)
  {
    var cb = cancelBubble ? true : false;
    this.removeEventListener(en.toLowerCase().substr(2), func, cb);
  };
}
var color_list=[

'rgb(172, 114, 94)',
'rgb(208, 107, 100)',
'rgb(248, 58, 34)',
'rgb(250, 87, 60)',
 'rgb(255, 117, 55)',
  'rgb(255, 173, 70)',

  'rgb(66, 214, 146)',
  'rgb(22, 167, 101)',
  'rgb(123, 209, 72)',

  'rgb(179, 220, 108)',
  'rgb(251, 233, 131)',
  'rgb(250, 209, 101)',


 ' rgb(146, 225, 192)',
  'rgb(159, 225, 231)',
 ' rgb(159, 198, 231)',
  'rgb(73, 134, 231)',
  'rgb(154, 156, 255)',
  'rgb(185, 154, 255)',

  'rgb(194, 194, 194)',
  'rgb(202, 189, 191)',
  'rgb(204, 166, 172)',
  '#F691B2',
  'rgb(205, 116, 230)',
 ' rgb(164, 122, 226)',
  ];

/*window.System =new Object();
System._instances = {};
window.Instance = function(hashCode) {//console.log("Instance" +System._instances[hashCode]);

	return System._instances[hashCode];
};*/
/*
var DateTime={};
DateTime.parseDateTime=function(str){

}
DateTime.toDateTimeStr=function(){

}

function new_DateTime(times){

}*/
var Tool={};
Tool.isNull=function(it){
	if(it==null || typeof it=='undefinded'){
		return true;
	}
	return null;
}
var StringUtil={};
StringUtil.isNull=function(it){
	if(it==null || typeof it=='undefinded' || it==''){
		return true;
	}
	return null;
}
