/*if(import)
import {Tool} from "./zwcommon"*/
function findByName(str){
    return document.getElementsByName(str);
}
function getChild(parentNode,name){
    if(!parentNode.childNodes)
    return null;
    for(var i =0;i<parentNode.childNodes.length;i++){
        var _name =name;
        var attr="";
        if(name[0]=="#"){
           attr=parentNode.childNodes[i].id;
           _name=name.substr(1);
        }else if(name[0]=="."){
             attr=parentNode.childNodes[i].className;
              _name=name.substr(1);
        }else{
            attr=parentNode.childNodes[i].id;

        }
        if(attr==_name){
            return parentNode.childNodes[i];
        }else{
            var element=getChild(parentNode.childNodes[i],name);
            if(element){
                return element;
            }
        }
    }
    return null;

}
function $(str){
    if(str.indexOf("input[name=")!=-1){
        var firstIndex = str.indexOf("\'");
        var name = str.substr(str.indexOf("\'")+1);
        var secondIndex = name.indexOf("\'");
        var name = name.substr(0,secondIndex);
        console.log("name="+name);

        return wrap(document.getElementsByName(name));
    }
    if(str.indexOf("#")!=-1){
        return wrap(document.getElementById(str.substr(1)));
    }
    if(str.indexOf(".")!=-1){
        return wrap(document.getElementsByClassName(str.substr(1)));
    }
}
function wrap(object){
    return new MyObject(object);
}
function MyObject (object){
    this.obj = object;
}

 var isDOM = ( typeof HTMLElement === 'object' ) ?
                function(obj){
                    return obj instanceof HTMLElement;
                } :
                function(obj){
                    return obj && typeof obj === 'object' && obj.nodeType === 1 && typeof obj.nodeName === 'string';
                }

MyObject.prototype.val = function(value){
        if(!this.obj)return;
    if(value){
    if(this.obj instanceof NodeList){
        for(var item in this.obj){

            setValue(this.obj[item],value);
        }
        }else{
            setValue(this.obj,value);

        }
        }else{
        //获取值
       if(this.obj instanceof NodeList){


                 return getVal(this.obj[0]);

              }else{
                 return this.obj.value;

              }

        }
    }
MyObject.prototype.attr = function(attrKey,attrVal){
 if(!this.obj)return;
  if(attrVal || attrVal ==""){
    if(this.obj instanceof NodeList){
        for(var item in this.obj){
            if( this.obj[item])
            this.obj[item].setAttribute(attrKey,attrVal);
           // setValue(this.obj[item],value);
        }
        }else{
            if(this.obj)
            this.obj.setAttribute(attrKey,attrVal);
        }
        }else{
             if(this.obj instanceof NodeList){
                    for(var item in this.obj){
                        if( this.obj[item])
                        return this.obj[item][attrKey];
                       // setValue(this.obj[item],value);
                    }
                    }else{
                        if(this.obj)
                        return this.obj[attrKey];
                    }

        }
    }

    MyObject.prototype.text = function(value){
      if(!this.obj)return;
        if(value || value ==""){
           if(this.obj instanceof NodeList){
               for(var item in this.obj){

                   this.obj[item].innerText =value;
               }
               }else{
                        this.obj.innerText =value;

               }
               }else{
               //获取值
              if(this.obj instanceof NodeList){


                        return this.obj[0].innerText;

                     }else{
                        return this.obj.innerText;

                     }

               }
        }
function setValue(item,value){
    if (item && typeof item != 'undefineded') {


if (item.tagName.toUpperCase() == 'INPUT' || item.tagName == 'TEXTAREA') {
            				if(item.type.toUpperCase()=="RADIO"){
                                if(item.value==value){
                                    item.checked=true;
                                }
            				}else if(item.type.toUpperCase()=="CHECKBOX"){
                                     if(item.value==value){
                                        item.checked=true;
                                    }
                            }else{
                                    item.value=value;
                            }


            			} else if (item.tagName.toUpperCase() == 'SELECT') {
            				setSelectValue(item,value)
            			} else if (ele.tagName.toUpperCase() == 'CHECKBOX') {
            				setCheckBoxValue(item,value);
            			} else if (ele.tagName == 'TEXTAREA') {
                              obj.value=value;
            			}else   {
                               obj.value=value;
                        }

            }
}

var zzw=function( selector, context){
   	return  zzw.fn.init( selector, context );
}


zzw.fn=zzw.prototype={

init:function(selector,context){
    if(typeof selector=="object"){
     selector.find=find;
        return selector;
    }
    if(selector.charAt(0)=='#'){
        var dom = document.getElementById(selector.replace("#",''));
        if(dom){
            dom.find=find;
        }
        return dom;
    }else
    if(selector[0]=='.'){
        var dom = document.getElementsByClassName(selector.replace(".",''));
                if(dom){
                    dom.find=find;
                    return dom;
                }
                return dom;
    }else{
            var dom = document.getElementById(selector.replace("#",''));
            if(dom){
                dom.find=find;
            }
            return dom;

    }


}
}

zzw.extend=function(obj1,obj2){

}
zzw.fn.init.prototype = zzw.fn;
zzw.extend = zzw.fn.extend = function(obj1,obj2){
    for(var key in obj2){
        obj1[key]=obj2[key];
    }

}
function find(selector){
     if(selector[0]=='#'){
            var dom = getChild(this,selector);
            if(dom){
                dom.find=find;
            }
            return dom;
        }
        if(selector[0]=='.'){
         var dom =getChild(this,selector);
         if(dom){
                         dom.find=find;
                          return dom;
                     }
           return dom;
        }
}
var $$=zzw;
function extend(obj1,obj2){

    for (i in obj2)
    		obj1[i] = obj2[i];
    return obj1;
}
function isArray(obj) {
    //console.log(Object.prototype.toString.call(obj));
  return  Object.prototype.toString.call(obj) === '[object Array]' || Object.prototype.toString.call(obj) ==='[object HTMLCollection]';//obj.length || Array.isArray(obj) ||
}
function trim(str){
     return str.replace(/(^\s*)|(\s*$)/g,'');
}

function getVal(str){
    var element =null;
    if(typeof str =="string"){
         element = document.getElementById(id);

    }else if(typeof str =="object"){
        element=str;
    }
       if(element.tagName.toLowerCase=="select"){
                var  myselect=element.selectedIndex;
                return element.options[index].value;
        }
            return element.value;
}
function getChildByName(parentNode,name){

    if(parentNode){
    if(!parentNode.childNodes)
         return null;
    }else{
        console.log("parentNode is null:"+name);
        return null;
    }
    for(var i =0;i<parentNode.childNodes.length;i++){
        if(parentNode.childNodes[i].name==name){
            return parentNode.childNodes[i];
        }else{
            var element=getChildByName(parentNode.childNodes[i],name);
            if(element){
                return element;
            }
        }
    }
    return null;

}

function parseDom(arg) {

　　 var objE = document.createElement("div");

　　 objE.innerHTML = arg;

　　 return objE.childNodes[0];

};

function html(arg) {

　　

　　 return parseDom(arg);

};
/*
function getChildByTagName(parentNode,name){
    var result = [];
    if(!parentNode){
       console.log("parentNode is null:"+name);
         return null;
    }
    if(isArray(parentNode)){
        if(parentNode.length==0){
              console.log("parentNode size is 0 :"+name);
             return null;
        }

        for(var i==0){

        }
    }
    if(!parentNode){

    }
    if(!parentNode.childNodes)
         return null;

    for(var i =0;i<parentNode.childNodes.length;i++){
        if(parentNode.childNodes[i].tagName==name){
            return parentNode.childNodes[i];
        }else{
            var element=getChildByTagName(parentNode.childNodes[i],name);
            if(element){
                return element;
            }
        }
    }
    return null;

}
*/
function css(dom,cssJson){

     if(isArray(dom) ){
        for(var i=0;i<dom.length;i++){
           for(var j in cssJson){
           //try{

              dom[i].style[j]= cssJson[j];
//              }catch(e){
//                alert(e);
//               console.log(i);
//                  console.log(dom);
//              }
          }
        }
     }else{
    for(var i in cssJson){
       // try{
        dom.style[i]= cssJson[i];
//        }catch(e){ alert(e);
//            console.log(i);
//           console.log(dom);
//        }
    }
    }
}
function width(dom,num){
    if((num+"").indexOf("px")===-1){
        num+="px";
    }
    if(isArray(dom)){
          for(var i=0;i<dom.length;i++){

                dom[i].width = num;
                dom[i].style.width = num;
          }
      }else{
            dom.style.width = num;

          dom.width = num;
      }
}


function height(dom,num){
if((num+"").indexOf("px")===-1){
        num+="px";
    }
    if(isArray(dom)){
          for(var i=0;i<dom.length;i++){

                dom[i].height = num;
                dom[i].style.height = num;
          }
      }else{
          dom.height = num;
           dom.style.height = num;
      }
}

function getWidth(obj){

    if(obj.style.width){
         return parseInt(obj.style.width);
    }
    if(obj.width){
             return parseInt(obj.width);
        }
  //  return parseInt(obj.style.width||obj.width);
  return obj.offsetWidth;
}
function getHeight(obj){


    return parseInt(obj.style.height||obj.height||obj.offsetHeight);
}
function setSelectValue(id,value){
var ele;
if(isDOM(id)){
    ele=id;
}else{
 ele = document.getElementById(id) ;
if(ele){
    ele.value =value;
}
}
var selid = ele.childNodes;
for(var i=0;i<selid.length;i++){
  if(selid[i].value == value){//content.belong_type为后台返回来的值
        selid[i].selected = true;
}else{
    if(selid[i].nodeType!=3 && selid[i].getAttribute("selected")){
         selid[i].removeAttribute("selected");
    }
}
}

}
function getSelectedValue(id){
var obj = $$(id); //定位id

var index = obj.selectedIndex; // 选中索引

var text = obj.options[index].text; // 选中文本

var value = obj.options[index].value; // 选中值

return value;
}
function getStyleWidth(obj){

    var style = null;
    if (window.getComputedStyle) {
        style = window.getComputedStyle(obj, null);    // 非IE
    } else {
        style = obj.currentStyle;  // IE
    }
    return parseInt(style.width);
}
function getLeft(obj){
    return parseInt(obj.left);
}
function getStyleHeight(obj){

    var style = null;
    if (window.getComputedStyle) {
        style = window.getComputedStyle(obj, null);    // 非IE
    } else {
        style = obj.currentStyle;  // IE
    }
    return parseInt(style.height);
}
function getWinHeight(){
var  winWIdth,winHeight;
    if (window.innerWidth)
    winWidth = window.innerWidth;
    else if ((document.body) && (document.body.clientWidth))
    winWidth = document.body.clientWidth;
    // 获取窗口高度
    if (window.innerHeight)
    winHeight = window.innerHeight;
    else if ((document.body) && (document.body.clientHeight))
    winHeight = document.body.clientHeight;
    // 通过深入 Document 内部对 body 进行检测，获取窗口大小
    if (document.documentElement && document.documentElement.clientHeight && document.documentElement.clientWidth)
    {
    winHeight = document.documentElement.clientHeight;
    winWidth = document.documentElement.clientWidth;
    }

    return winHeight;
}
function getWinWidth(){
var  winWIdth,winHeight;
    if (window.innerWidth)
    winWidth = window.innerWidth;
    else if ((document.body) && (document.body.clientWidth))
    winWidth = document.body.clientWidth;
    // 获取窗口高度
    if (window.innerHeight)
    winHeight = window.innerHeight;
    else if ((document.body) && (document.body.clientHeight))
    winHeight = document.body.clientHeight;
    // 通过深入 Document 内部对 body 进行检测，获取窗口大小
    if (document.documentElement && document.documentElement.clientHeight && document.documentElement.clientWidth)
    {
    winHeight = document.documentElement.clientHeight;
    winWidth = document.documentElement.clientWidth;
    }

    return winWidth;
}
function removeClass(dom,className){
     if(isArray(dom)){
          for(var i=0;i<dom.length;i++){
            if(dom[i].className.indexOf(className )>-1 ){
                dom[i].className= dom[i].className.replace(className,"" );
            }
          }
      }else{
        if(dom.className.indexOf(className )>-1 ){
            dom.className= dom.className.replace(className,"" );
        }

      }
}
function removeDom(dom){
if(dom.parentNode){
    dom.parentNode.removeChild(dom);
}
}
function addClass(dom,className){
     if(isArray(dom)){
          for(var i=0;i<dom.length;i++){
            if(dom[i].className.indexOf(className )>-1 ){

            }else{
                 dom[i].className+= addClassStr(  dom[i].className,className);
            }
          }
      }else{
        if(dom.className.indexOf(className )>-1 ){

        }else{
            dom.className=addClassStr(  dom.className,className);
        }

      }
}
function addClassStr(oriClass,className){
    oriClass=trim(oriClass);
    var ary = oriClass.split(" ");
    ary.push(className);

    for(var i=0;i<ary.length;i++){
        ary[i] = trim(ary[i]);
    }
    return ary.join(" ");

}
function append(dom,html){

    if(isArray(dom)){
        for(var i=0;i<dom.length;i++){

              dom[i].innerHTML += html;

        }
    }else{
        dom.innerHTML += html;
    }


}

function setHtml(dom,html){
    if(isArray(dom)){
            for(var i=0;i<dom.length;i++){

                  dom[i].innerHTML = html;

            }
        }else{
            dom.innerHTML = html;
        }
}



function getRelativeInfo(o){//取得坐标
		if(isNull(o)){
	     	alert("can't find it:"+o);

     	}
	   	var to=new Object();
	   	to.left=to.right=to.top=to.bottom=0;

	   	var twidth=o.offsetWidth;
	   	var theight=o.offsetHeight;
	   	while(o!=document.body && !isNull(o)){
	   	     var  demo = window.getComputedStyle(o, null);
	   	    if(demo.position && demo.position=="relative"){
	   	        break;
	   	    }
	   		if(isNull(o.offsetParent)){

	   		    console.log("can't find it's parentNode1:"+o);
	   		    console.log(o);
	     		//alert("can't find it's parentNode1:"+o);
	     	}
	     	to.left+=o.offsetLeft;
	     	to.top+=o.offsetTop;

     		o=o.offsetParent;
	   	}
     	to.right=to.left+twidth;
     	to.width=twidth;
     	to.height=theight;
     	to.bottom=to.top+theight;
	   	return to;
}

function show(dom){
    dom.style.display="block";
}
function hide(dom){
    dom.style.display="none";
    console.log("影长");
}
function toggle(dom){
    if( dom.style.display=="block" || dom.style.display=="" || dom.style.display=="undefined"  ){
         dom.style.display="none";
    }else{
         dom.style.display="block";
    }

}


function getRadioValueByName(str) {
    var radios = document.getElementsByName(str);
    var value;
    for(var i=0;i<radios .length;i++){
        if(radios[i].checked){
          value = radios[i].value;
            break;
        }
    }
    return value;

}
function isDom(obj){
return obj && typeof obj === 'object' && obj.nodeType === 1 && typeof obj.nodeName === 'string';
}


function getInfo(o){//取得坐标
		if(Tool.isNull(o)){
	     	alert("can't find it:"+o);

     	}
	   	var to=new Object();
	   	to.left=to.right=to.top=to.bottom=0;

	   	var twidth=o.offsetWidth;
	   	var theight=o.offsetHeight;
	   	while(o!=document.body && !Tool.isNull(o)){
	   		if(Tool.isNull(o.offsetParent)){
	     		//alert("can't find it's parentNode1:"+o);
	     	}
	     	to.left+=o.offsetLeft;
	     	to.top+=o.offsetTop;

     		o=o.offsetParent;
	   	}
     	to.right=to.left+twidth;
     	to.bottom=to.top+theight;
	   	return to;
}

function bind(dom,event_name,function_name){

      //  dom.addEventListener(function_name,function_name, false);
        dom.attachEvent(event_name,function_name,false);
		/*switch( event_name){
			case 'click':
				//if(Sys.ie){
				//	dom.attachEvent("onclick", function_name);

			//	}else{
					dom.addEventListener("click",function_name, false);
			//	}

				break;
            case 'mousemover':

                    dom.addEventListener("mousemover",function_name, false);
             case 'onmousedown':

                    dom.addEventListener("mousedown",function_name, false);
                case 'dblclick':

                       dom.addEventListener("dblclick",function_name, false);
		}
*/

}/*
if(module)
module.exports={$$,getInfo,bind};*/