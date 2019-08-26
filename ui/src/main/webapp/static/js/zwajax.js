/**
 *
 *
 * Author: zzw <zzw1986@gmail.com>
 *
 *
 * File: common.js Create Date: 2014-04-10 19:54:40
 *
 *
 */

// AJAX璋冪敤 濡傦細ACWS.ajax('common/service/UserSelect/Init', inputData,
// afterInit,{async:false});
var PATH="/home";

function createXmlHttpRequest(){

    if(window.ActiveXObject){ //如果是IE浏览器

        return new ActiveXObject("Microsoft.XMLHTTP");

    }else if(window.XMLHttpRequest){ //非IE浏览器

        return new XMLHttpRequest();

    }

}
function AjaxClass()
{
    var XmlHttp = false;
    try
    {
        XmlHttp = new XMLHttpRequest();        //FireFox专有
    }
    catch(e)
    {
        try
        {
            XmlHttp = new ActiveXObject("MSXML2.XMLHTTP");
        }
        catch(e2)
        {
            try
            {
                XmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            catch(e3)
            {
                alert("你的浏览器不支持XMLHTTP对象，请升级到IE6以上版本！");
                XmlHttp = false;
            }
        }
    }

    var me = this;
    this.Method = "POST";
    this.Url = "";
    this.Async = true;
    this.Arg = "";
    this.dataType="";
    this.data=null;

    this.CallBack = function(){};
    this.Loading = function(){};

    this.Send = function()
    {
        if (this.Url=="")
        {
            return false;
        }
        if (!XmlHttp)
        {
            return IframePost();
        }

        //处理 data
        if(this.data){
            if(this.dataType && this.dataType.toLowerCase()=="json"&& this.Method!="GET"){
            this.data=JSON.stringify(this.data);
            }else
            {
            var arr=new Array();
                        for(var key in this.data){
                            arr.push(encodeURIComponent( key)+"="+encodeURIComponent(this.data[key]));

                        }
                        this.data= arr.join("&");
                        }

        }
        if(this.Method=="GET"&&this.data){

            if(this.Url.indexOf("?")!=-1){
               if(this.Url.indexOf("=")!=-1){
                    this.Url+="&"+arr.join("&");
               }else{
                 this.Url+=arr.join("&");
               }

            }else{
                this.Url+="?"+arr.join("&");
            }
        }

        XmlHttp.open (this.Method, this.Url, this.Async);

       if (this.dataType&& this.dataType.toLowerCase()=="json")
            {

                XmlHttp.setRequestHeader("Content-Type","application/json");

            }else{

             if (this.Method=="GET")
                    {
                        XmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
                    }
                    if (this.Method=="POST")
                    {
                        XmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
                    }
            }
            var that =this;
        XmlHttp.onreadystatechange = function()
        {
            if (XmlHttp.readyState==4)
            {
                var Result = false;
                if (XmlHttp.status==200)
                {
                    Result = XmlHttp.responseText;
                    if(XmlHttp.getResponseHeader("Content-Type") && XmlHttp.getResponseHeader("Content-Type").indexOf("application/json")!=-1){
                        Result=eval('('+Result+')');
                    }
                }
                XmlHttp = null;
                me.CallBack(Result);
            }
             else
             {
                me.Loading();
             }
        }
        if (this.Method=="POST")
        {


            XmlHttp.send(this.data);
        }
        else
        {
            XmlHttp.send(null);
        }
    }

    //Iframe方式提交
    function IframePost()
    {
        var Num = 0;
        var obj = document.createElement("iframe");
        obj.attachEvent("onload",function(){ me.CallBack(obj.contentWindow.document.body.innerHTML); obj.removeNode() });
        obj.attachEvent("onreadystatechange",function(){ if (Num>=5) {alert(false);obj.removeNode()} });
        obj.src = me.Url;
        obj.style.display = 'none';
        document.body.appendChild(obj);
    }
}

function ajax(options){



/*----------------------------调用方法------------------------------
    var Ajax = new AjaxClass();         // 创建AJAX对象
    Ajax.Method = "POST";               // 设置请求方式为POST
    Ajax.Url = "default.asp"            // URL为default.asp
    Ajax.Async = true;                  // 是否异步
    Ajax.Arg = "a=1&b=2";               // POST的参数
    Ajax.Loading = function(){          //等待函数
        document.write("loading...");
    }
    Ajax.CallBack = function(str)       // 回调函数
    {
        document.write(str);
    }
    Ajax.Send();                        // 发送请求
   -----------------------------------------------------------
    var Ajax = new AjaxClass();         // 创建AJAX对象
    Ajax.Method = "GET";                // 设置请求方式为POST
    Ajax.Url = "default.asp?a=1&b=2"    // URL为default.asp
    Ajax.Async = true;                  // 是否异步
    Ajax.Loading = function(){          //等待函数
        document.write("loading...");
    }
    Ajax.CallBack = function(str)       // 回调函数
    {
        document.write(str);
    }
    Ajax.Send();
    --------------------------------------------------------------------*/
    //1.创建对象

      var innerAjax = new AjaxClass();         // 创建AJAX对象
        innerAjax.Method = options.type;                // 设置请求方式为POST
        innerAjax.Url = options.url;    // URL为default.asp
        innerAjax.Async = true;                  // 是否异步
        innerAjax.dataType =options.dataType;                  // 是否异步
        innerAjax.data =options.data;
//alert("innerAjax:"+innerAjax.Method);
        innerAjax.Loading = function(){          //等待函数
           // document.write("loading...");
        }
        innerAjax.CallBack = options.success ;
        innerAjax.Send();

}
var Ajax={
 get:function(url,data,callback){
    this.AjaxFun(url,data,callback,{type:"GET"});
 },
 getJSON:function(url,data,callback){
    this.AjaxFun(url,data,callback,{type:"GET",dataType:"JSON"});
 },
  getJSONP:function(url,data,callback){
    this.AjaxFun(url,data,callback,{type:"GET",dataType:"jsonp", jsonp: "callback",jsonpCallback:"success_jsonpCallback"});
  },
   postJSON:function(url,data,callback){
      this.AjaxFun(url,data,callback,{type:"POST",dataType:"JSON"});
   },
 post:function(url,data,callback){
    this.AjaxFun(url,data,callback,{type:"POST"});
 },
  del:function(url,data,callback){
     this.AjaxFun(url,data,callback,{type:"DELETE"});
  },
   put:function(url,data,callback){
       this.AjaxFun(url,data,callback,{type:"PUT"});
    },
 AjaxFun1:function(url, inputData, callback, options, callbackOnError){
      $.ajax({
            url:url,
            data:inputData,
            //contentType:"application/json",
           // dataType:options.dataType,
            success:callback,
            error:callbackOnError,
            beforeSend: function(request) {
                             request.setRequestHeader("Content-Type", "application/json");

                         },
            headers: {
                        contentType:"application/json",

                      "Content-Type:":"application/json",
                    }
            }
        );
 },
 AjaxFun:function (url, inputData, callback, options, callbackOnError) {
            //var widgetId = dialog.showWait();

         	var contextUrl = window.location.href;
         	options = options || {};
//         	if(url.indexOf("?")!=-1){
//         	    url+="&r="+Math.floor(Math.random() * ( 1000 + 1));
//         	}else{
//         	     url+="?r="+Math.floor(Math.random() * ( 1000 + 1));
//         	}
         	options.url =  url;
         	options.type = options.type||"POST";
         	//alert(options.type);
         	options.data = inputData;
         	if(options.dataType && options.dataType.toLowerCase()=="json" ){
         	options.contentType="application/json;charset=utf-8";
         	//options.data = JSON.stringify(inputData);
         	}else
         	options.contentType="application/x-www-form-urlencoded";//"application/json;charset=utf-8";//
         	//options.data = encodeURIComponent(JSON.stringify(inputData));
         	if (typeof options.async == 'undifined')
         		options.async = false;
         	var param = options.inputData;
         	options.success = function(outputData) {//alert("success")

//         	    dialog.hideWait(widgetId);
         		if((typeof outputData=="object" && outputData.r=="504" )|| (typeof outputData=="string" && outputData.indexOf("504")!=-1)){

                    dialog.confirm("请重新登录 正为你跳转登录页面",function(){
                        window.location=PATH+"/sys/auth/login.htm?pre="+window.location;
                    });

         		  //  alert("请重新登录 正为你跳转登录页面");

         		}
         		if (typeof callback == 'function') {
                    //if(options.dataType && options.dataType.toLowerCase()=="json" && outputData.substr(0,1)=="{"){
                   //    outputData = eval("("+outputData+")");
                    //}
         			callback(outputData);
         		}
         	};
         	options.error = function(jqXHR, textStatus, errorThrown) {//alert("error")
         		// $("body").unmask();
         		var responseText = jqXHR.responseText || "";
         		if ((jqXHR.status == 500 || jqXHR.status == 1000)
         				&& responseText.indexOf("dwr.engine.http.1000") >= 0) {
         			top.location.replace(acwsContext + '/acwsui/pages/logout.htm');// /j_acegi_logout
         			return;
         		}
         		if (typeof callbackOnError == 'function') {
         			callbackOnError(errorThrown);
         		} else {//alert(typeof callbackOnError);
         			alert('参数不是function');
         		}
         	};
         	delete options['inputData'];
         	ajax(options);



         }
};
/*function Get(url,data,callback){
	AjaxFun(url,data,callback,{type:"get"});
}
function GetJSON(url,data,callback){
	AjaxFun(url,data,callback,{type:"get",dataType:"json"});
}
function Post(url,data,callback){
	AjaxFun(url,data,callback);
}*/
/**
 *
 */
