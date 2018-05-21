
<template>


                                <zwPanel :class="content.httpType">

                                     <div   slot="title" class="zw-panel-heading"  >
<span class="zw-panel-title "><span class="zw-row-title-main" >{{content.httpType}}</span><span class="zw-row-title-sub ">{{content.url}}&nbsp;&nbsp;&nbsp;</span><a></a><span class="pull-right" >{{content.summary}}</span></span>
                                      </div>

                                <div  slot="body" name="body">

                                 <form  class="panel-body" ref="formToSubmit" :id="getId"   :action=content.url :method=content.httpType  :enctype=formType(content) >

                                              <span>contenttype:</span>
                                              <span>{{content.consumes}}</span>
                                                  <span>备注</span>
                                                  <span ><span class="">{{content.description}}</span></span>
                                                  <table class="api-list table">
                                                      <tr>
                                                      <th style="width: 100px; max-width: 100px" data-sw-translate="">参数</th>
                                                      <th style="width: 310px; max-width: 310px" data-sw-translate="">值</th>
                                                      <th style="width: 200px; max-width: 200px" data-sw-translate="">描述</th>
                                                      <th style="width: 100px; max-width: 100px" data-sw-translate="">参数位置</th>
                                  						 <th style="width: 100px; max-width: 100px" data-sw-translate="">参数类型</th>
                                                      <th style="width: 220px; max-width: 230px" data-sw-translate="">是否必填</th>
                                                      </tr>
                                                      <tr   is="apiParameter"
                                                       v-for="item in content.parameters"
                                                       v-bind:item="item"
                                                       ></tr>
                                                  </table>
                                                  <ul class="panel panel-warning">
                                                      <div  class=" panel-heading">
                                                          <h3 class="panel-title">

                                                          <zwButton type="primary"  icon="search" :loading="false"  :loading_delay=5 v-on:click.native="sendRequest" :click="sendRequest" >发送请求</zwButton>



                                                            &nbsp;&nbsp;<zwButton type="primary"  :loading="false"  :loading_delay=5 v-on:click.native="saveTestData"   >保存当前数据为测试数据</zwButton>&nbsp;&nbsp;

                                                            <zwButton type="primary" :loading="false"  :loading_delay=5  v-on:click.native="getTestData"   >加载测试数据</zwButton>&nbsp;&nbsp;
                                                           <zwButton type="primary"  :loading="false"  :loading_delay=1 v-on:click.native="hideRequestData"   >隐藏</zwButton>&nbsp;&nbsp;

                                                            </h3>
                                                            <p style="max-width:1024px">
                                                            返回参数说明
                                                              <span>{{content.response}}</span>
                                                              </p>
                                                      </div>
                                                      <div v-if="showResponse">
                                                      <div class="panel-body">
                                                          <div class="panel panel-default">
                                                              <div class="panel-heading">测试数据:</div>
                                                              <div class="panel-body">
                                                                <p v-for="(item,index) in testData"  >{{index}} {{item}}&nbsp;&nbsp;<a v-on:click="setTestData(index)"> 应用</a>&nbsp;&nbsp;  <a v-on:click="deleteTestData(index)">删除</a></p>
                                                          </div>
                                                        </div>
                                                         <div class="panel panel-default">
                                                            <div class="panel-heading">请求url:</div>
                                                            <div class="panel-body">
                                                               {{content.url}}
                                                            </div>
                                                          </div>
                                                        <div class="panel panel-default">
                                                            <div class="panel-heading">请求数据:</div>
                                                            <div class="panel-body">
                                                               {{requestData}}
                                                            </div>
                                                          </div>
                                                        <div class="panel panel-default">
                                                            <div class="panel-heading">响应码:</div>
                                                            <div class="panel-body">
                                                               {{status}}
                                                            </div>
                                                          </div>
                                                      <div class="panel panel-default">
                                                        <div class="panel-heading">请求结果</div>
                                                        <div class="panel-body">
                                                          {{result}}
                                                        </div>
                                                      </div>
                                                      </div>
                                                  </div>
                                              </ul>
                                              </form>

                                </div>
                                </zwPanel>



</template>
<script type="text/javascript">

import apiParameter from './apiParameter.vue';
import zwPanel from './zwPanel.vue';
import zwButton from '../button/zwButton.vue';
export default {
        name: 'apiUrl',
         components:{apiParameter,zwButton,zwPanel},
        props: [ 'title', "content", "url"],
        data () {
            return {
                    showResponse : false,
                    show : false,
                    result : "",

                    success : "",
                    status : "",
                    requestData : "",
                    testData : [],
                    transitionName: 'expand',
                    sending:false,
                    formId:0,
            };
        },
        computed: {
            getId:function(){
                return (this.content.httpType+this.content.url).replace(new RegExp("[/\{\}]","gm"),"");
            },

            getSending:function(){
                return this.sending;
            }
        },
        mounted () {
            this.formId = (this.content.httpType+this.content.url).replace(new RegExp("[/\{\}]","gm"),"");

        },
        methods: {
                showOrHide : function(event) {

                        this.show = !this.show;

                    },

                sendRequest : function() {//发送请求
                    this.sending=true;//修改状态为
                    //console.log("formId" + this.formId);
                    var contentType = "application/x-www-form-urlencoded";//初步制定contentType
                    //console.log("url" + this.content.url);
                     //获取json数据  get the form json

                     var bodyJsonFlag=false;


                     var json = {};
                     	var arr = $(  this.$refs.formToSubmit).serializeArray();
                     	for (var i = 0; i < arr.length; i++) {
                     		json["" + arr[i].name] = arr[i].value;
                     	}
                  //  var json = changeForm2Jso("#"+this.formId);//将参数转换成json格式
                    //==========为了兼容传输的数据是array 数组这种类型============

                      var url=   this.content.url+"?1=1";    //获取请求 get the request url 默认加上?号

                     for (var i = 0; i < this.content.parameters.length; i++) {

                       if (this.content.parameters[i].type.toLocaleLowerCase  () == 'array') {//如果参数的格式是数组的话
                            if(json[this.content.parameters[i].name]){//alert("怎么会有数组的");
                                if(json[this.content.parameters[i].name].indexOf("[")!=-1  ){
                                    json[this.content.parameters[i].name] = eval('('+json[this.content.parameters[i].name]+')');//如果有数组参数就转换成字符串json格式
                                }
                            }

                       }
                       if(this.content.parameters[i].in.toLocaleLowerCase()=='body' ){
                           // json = json["body请求体"];//eval('{'++'}');
                           // break;
                           bodyJsonFlag=true;
                       }

                       if(this.content.parameters[i].in .toLocaleLowerCase  ()== 'query'){//如果有url参数 就放到url参数里面
                            //如果是查询参数就拼接到url里
                            url+="&"+this.content.parameters[i].name+"="+json[this.content.parameters[i].name];
                            delete json[this.content.parameters[i].name];//如果在query 里的 就删除掉

                       }

                       if(this.content.parameters[i].in.toLocaleLowerCase  () == 'path'){//如果有url参数 就放到url参数里面
                           //如果是url path 参数就拼接到url里
                         //  url+="/"+json[this.content.parameters[i].name];

                            if(this.content.url.indexOf("{")>0){    //如果含有 if contain path variable
                                console.log("进行替换");
                              var replaceId = url.substr(url.indexOf("{")+1,url.indexOf("}")-url.indexOf("{")-1);

                               url=url.replace("{"+replaceId+"}",json[replaceId]); //replace it put the variable into url

                               //alert(replaceId);
                           }//alert(url);
                      }
                    }



                    //console.log(this.content.consumes[0]);
                     //alert(this.content.consumes[0]);
                    if(this.content.consumes instanceof Array   ){//如果协议有多个
                        for(var i=0;i<this.content.consumes.length;i++){
                            var s = this.content.consumes[i];

                            if(s.toLocaleLowerCase  ().indexOf("application/json")>=0){
                                contentType = "application/json";


                            }
                        }
                    }
                    else
                    if(this.content.consumes.toLocaleLowerCase  ().indexOf("application/json")>=0){
                         contentType = "application/json";


                    }
                    //this.requestData = json;
                    /*
                    if(contentType.toLocaleLowerCase  () == "application/json"){
                     if(this.content.httpType.toLocaleLowerCase  ()=="get" ){//如果是get 协议的话 这里的判断有点偏业务了
                                                //组装成 params
                                                //json = json.toJSONString();
                                                json=JSON.stringify(json);
                                                json = {"params":json};//console.log("get params=");
                                                //console.log(this.requestData);
                                            }else  {
                                                 json=JSON.stringify(json);
                                                 //this.requestData = json;
                                            }
                    }*/
                    this.requestData = json;
                    //如果是文件提交 就用第一种方案 一般都是post + form 表单提交方式
                    var isFileSubmit = false;

                    for (var i = 0; i < this.content.parameters.length; i++) {
                        if (this.content.parameters[i].type.toLocaleLowerCase  () == 'file') {
                            isFileSubmit = true;
                        }
                       /* if (this.content.parameters[i].type.toLocaleLowerCase  () == 'array') {
                           json[this.content.parameters[i].name] = eval('('+json[this.content.parameters[i].name]+')');
                       }*/
                    }
                      var that =this;

                    if (isFileSubmit) {
                        var options = {
                            url : window.APIPATH+ url+"&url="+window.APIDOMAIN,//加上前缀 加上url 加上 代理url
                            success : function(xml) {
                                this.result = xml;

                            }.Apply(this),
                            complete : function(xhr, textStatus) {that.sending=true;
                                //console.log("complete xhr" + xhr);
                                this.status = xhr.status;
                                this.success = textStatus;
                                this.showResponse = true;
                                //console.log("complete textStatus"
                                  //      + textStatus);
                            }.Apply(this)
                        };
                        $("#" + this.formId).ajaxSubmit(options);
                        return;
                    }
                   // alert(this.content.httpType);
                   console.log("before ajax"+"url:"+window.APIPATH+url+"&url="+window.APIDOMAIN);

                   if(bodyJsonFlag){
                    json=JSON.stringify(json);
                   }
                    $.ajax({
                        type : this.content.httpType,
                        url :  window.APIPATH+url+"&url="+window.APIDOMAIN,
                        data : json,
                        type:this.content.httpType,
                        dataType : "json",
                        contentType :contentType ,
                        success : function(xml) {
                            this.result = xml;

                        }.Apply(this),
                        complete : function(xhr, textStatus) {that.sending=true;
                            //console.log("complete xhr" + xhr);
                            this.status = xhr.status;
                            this.success = textStatus;
                            this.showResponse = true;
                            //console.log("complete textStatus"
                              //      + textStatus);
                        }.Apply(this)
                    });

                },

                saveTestData : function() {
                      var json = {};
                        var arr = $(  this.$refs.formToSubmit).serializeArray();
                        for (var i = 0; i < arr.length; i++) {
                            json["" + arr[i].name] = arr[i].value;
                        }
                   // var json = changeForm2Jso("#" + this.formId);
                    //console.log(json);
                    var sendData = {};
                    this.testData.push(json);
                    sendData.testData = JSON.stringify(this.testData);
                    sendData.url = this.content.url;
                    Ajax.post("/api/test/data/save", sendData,
                            function(data) {

                            }.Apply(this));

                },
                hideRequestData : function() {
                    this.showResponse = !this.showResponse
                },
                getTestData : function() {
                    var sendData = {};

                    sendData.url = this.content.url;


                    Ajax.getJSON( "/api/test/data/get", sendData,
                            function(data) {

                                this.testData =getJSON(data);//eval( '('+data+')');

                            }.Apply(this));
                },
                formType : function(content) {
                    //console.log(content);
                    for (var i = 0; i < content.parameters.length; i++) {
                        if (content.parameters[i].type == 'file') {
                            return "multipart/form-data";
                        }
                    }
                    return "application/x-www-form-urlencoded";

                },
                setTestData : function(index) {
                    //console.log(index);
                    fillJso2Form("#" + this.formId,
                            this.testData[index]);
                },
                deleteTestData : function(index) {

                    removeByValue(this.testData, this.testData[index]);
                    //console.log("开始删除");
                    var sendData = {};

                    sendData.testData = JSON.stringify(this.testData);
                    sendData.url = this.content.url;
                    //console.log(this.testData);
                    Ajax.post("/api/test/data/save", sendData,
                            function(data) {
                                this.getTestData();
                            }.Apply(this));

                }
        },


         events: {

          }
    };
</script>
<style>
.api-list {
color:black;
}
</style>