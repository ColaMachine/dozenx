<template>
<div>




     <zwRow>

        <zwCol class="pull-right" span=24>
              <zwJumbotron v-on:clickFn="scanCode" title="智能家居"></zwJumbotron>
        </zwCol>

    </zwRow>
    <zwRow>
      <zwCol style="padding-left:20px">

              <zwRadioGroup v-on:change="changeModel" :options="options">
              </zwRadioGroup>

        </zwCol>
    </zwRow>
    <zwRow>
        <zwCol>
           <zwCollapse >
                          <zwPanel state="open">
                          <span slot="title" name="title">故障设备</span>


                            <ul slot="body" name="body">
                              <li  v-for="result in list" key={{result.deviceid}} >
                              <zwSlidPanel v-if="result.status==3"  :canDel="result.deviceSubTypeCode=='010'?true:false" v-on:deleteCallBack="deleteDevice(1,result.wifiId)" :id="result.deviceid?result.deviceId:result.wifiId" :imgSrc="getDeviceImg(result.deviceSubTypeCode,result.deviceSubTypeName,result.online)" :link="getDeviceViewLink(result.deviceSubTypeCode,result.deviceid,result.wifiId)"  :title="result.deviceSubTypeName" :subTitle="'设备状态:'+result.online==1?'在线':'离线'" :thrdTitle="'设备型号:'+result.deviceSubTypeCode">

                              <img :src="getDeviceImg(result.deviceSubTypeCode,result.deviceSubTypeName,result.online)" slot="img"></img>

                               <img :src="getSwitchImg()" slot="switch"></img>
                              </zwSlidPanel>
                              </li>
                          </ul>

                          </zwPanel>
                          <zwPanel state="open" >
                          <span slot="title" name="title">在线设备</span>
                              <ul slot="body" name="body">
                                 <li  v-for="result in list" key={{result.id}} >
                                 <zwSlidPanel v-if="!result.online || result.online==1" :canDel="result.deviceSubTypeCode=='010'?true:false" v-on:deleteCallBack="deleteDevice" :id="(result.deviceid?result.deviceid:result.wifiId)"  :imgSrc="getDeviceImg(result.deviceSubTypeCode,result.deviceSubTypeName,result.online)" :link="getDeviceViewLink(result.deviceSubTypeCode,result.deviceid,result.wifiId)" :title="result.deviceSubTypeName" :subTitle="'设备状态:'+(result.online==1?'在线':'离线')" :thrdTitle="'设备型号:'+result.deviceSubTypeCode">

                                 <img :src="getDeviceImg(result.deviceSubTypeCocde,result.deviceSubTypeName,result.online)" slot="img"></img>

                                  <img :src="getSwitchImg()" slot="switch"></img>
                                 </zwSlidPanel>
                                 </li>
                             </ul>
                          </zwPanel>
                          <zwPanel  state="open">
                          <span slot="title" name="title">离线设备</span>
                              <ul slot="body" name="body">
                                 <li  v-if="result.online==0" v-for="result in list" key={{result.id}} >

                                 <zwSlidPanel  :canDel="result.deviceSubTypeCode=='010'?true:false" v-on:deleteCallBack="deleteDevice" :id="result.deviceid?result.deviceId:result.wifiId" :imgSrc="getDeviceImg(result.deviceSubTypeCode,result.deviceSubTypeName,result.online)"  :link="getDeviceViewLink(result.deviceSubTypeCode,result.deviceid,result.wifiId)" :title="result.deviceSubTypeName" :subTitle="'设备状态:'+(result.online==1?'在线':'离线')" :thrdTitle="'设备型号:'+result.deviceSubTypeCode">

                                 <img :src="getDeviceImg(result.deviceSubTypeCode,result.deviceSubTypeName,result.online)" slot="img"></img>

                                  <img :src="getOffLineSwitchImg()" slot="switch"></img>
                                 </zwSlidPanel>
                                 </li>
                             </ul>
                          </zwPanel>

                      </zwCollapse>
                      <div style="height:100px">
                      </div>
          </zwCol>
          </zwRow>
  <zwRow class="fix-bottom">
 <zwCol  span=24>
    <bottomBar  defaultIndex="1"></bottomBar>
 </zwCol>
  </zwRow>


</div>

</template>
<script>

import zwRow from '../../component/layout/zwRow.vue';
import zwCol from '../../component/layout/zwCol.vue';
import zwIcon from '../../component/icon/zwIcon.vue';

  import zwJumbotron from '../../component/datadisplay/zwJumbotron.vue';
  import zwSlidPanel from '../../component/datadisplay/zwSlidPanel.vue';
  import zwCollapse from '../../component/datadisplay/zwCollapse.vue';
import zwPanel from '../../component/datadisplay/zwPanel.vue';
import bottomBar from '../smarthome/bottomBar.vue';
 import zwRadioButton from '../../component/datadisplay/zwRadioButton.vue';
 import zwRadioGroup from '../../component/datadisplay/zwRadioGroup.vue';
 import zwMessage from '../../component/feedback/zwMessage.vue';
export default {
    components:{zwRow,zwCol,zwIcon,bottomBar,zwJumbotron,zwSlidPanel,zwPanel,zwCollapse,zwMessage,
    zwRadioGroup,zwRadioButton
    },
    data () {

        return {
        list:[],
        tabIndex:2,
        options:[
        {  value:0,name:"回家模式" },
        {  value:1,name:"离家模式" },

        ]
      }
    }
    ,
    mounted(){
      this.getDeviceList();

    },
    computed: {
        getWangluoguanliImg:function(){
            return PATH+ (this.tabIndex==1?'/static/img/smarthome/网络管理2.png':'/static/img/smarthome/网络管理1.png');
        },

        getZhinengjiatingImg:function(){
            return PATH+ (this.tabIndex==2?'/static/img/smarthome/智能家居2.png':'/static/img/smarthome/智能家居1.png');
        },
        getGerenzhongxinImg:function(){
            return PATH+ (this.tabIndex==3?'/static/img/smarthome/个人中心2.png':'/static/img/smarthome/个人中心1.png');
        },
    },
    methods:{
    getDeviceList:function(){

      Ajax.getJSON(PATH+"/smart/home/device/listdb",null,function(result){
         if(result.r==AJAX_SUCC){

             this.list=result.data;
             //alert(this.list.length);
         }
        }.Apply(this));
    },
    initWx:function(data){


    //说明文档https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115
    //https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115
    //步骤二：引入JS文件

    //步骤三：通过config接口注入权限验证配置
   //alert(data.signature);

    wx.config({

        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。

        appId: data.appId, // 必填，公众号的唯一标识

        timestamp: data.timestamp, // 必填，生成签名的时间戳

        nonceStr: data.noncestr, // 必填，生成签名的随机串

        signature:data.signature,// 必填，签名，见附录1

        jsApiList: [ 'getLocation','scanQRCode'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2

    });
    //步骤四：通过ready接口处理成功验证

    wx.ready(function(){
        wxInit=true;
        console.log("进入而微信ready");
       // alert("进入而微信ready");
        // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。

          if(wx){
                     wx.scanQRCode({//扫描二维码
                        needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
                        scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
                        success: function (res) {
                            var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
                           // alert("扫描结果"+result);
                            this.addWifi(result);
                         }.Apply(this)
                    });
                    }else{
                    alert("微信初始化失败!");
                    }

    }.Apply(this));
    //步骤五：通过error接口处理失败验证

    wx.error(function(res){
        alert("微信调用失败");
        // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。

    });


    },
    scanCode:function(){//alert("开始scanCode")


        if(!wxInit){//如果wx已经初始化过了 就不需要再次初始化了

        Ajax.getJSON(PATH+"/weixin/signatrue",{url:location.href.split('#')[0]},function(result){
                  var data = result.data;
                  this.initWx(data);


              }.Apply(this));
        }else{
           // this.addWifi("86100c00a0050010000000054d20c674");

                if(wx){
                     wx.scanQRCode({//扫描二维码
                        needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
                        scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
                        success: function (res) {

                            var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
                           // alert("扫描结果"+result);
                            this.addWifi(result);
                         }.Apply(this)
                    });
                    }else{
                    alert("微信初始化失败!");
                    }
        }
    },
    addWifi:function(result){
        Ajax.post(PATH+"/smart/home/device/add",{wifiId:result},function(result){
            // alert("添加ajax返回"+result[AJAX_RESULT] );
            ajaxResultHandler(result);

           //  alert("添加ajaxResultHandler返回");
            //alert(result[AJAX_RESULT] );
            if(result[AJAX_RESULT] == AJAX_SUCC){//alert("添加判断成功" );
              zalert("添加网关成功!");
            //this.list=[];
                this.getDeviceList();
            }else{//alert("添加判断失败" );
                alert(result.msg);
            }
        }.Apply(this)
       );
    },
    getSwitchImg:function(){
        return PATH+"/static/img/smarthome/正常.png";
    },
    getOffLineSwitchImg:function(){
         return PATH+"/static/img/smarthome/离线.png";
    },
    getDeviceViewLink:function(deviceSubCode,deviceId,wifiId){
        var link ="";
      if(deviceSubCode=='010'){//deviceSubName=="我的网关"
        link+="#deviceGateWayView";
        }else if(deviceSubCode=='046' ){//deviceSubName=="智能门锁"
        link+="#deviceLockView";
        }else if(deviceSubCode=='002'){//deviceSubName=="水侵"
        link+="#deviceWaterView";
        }else if(deviceSubCode=='001'){//deviceSubName=="红外"
        link+="#deviceRedView";
        }else if(deviceSubCode=='003'){//deviceSubName=="红外"
        link+="#deviceMockView";
        }else if(deviceSubCode=="011"){
         link+="#deviceSocketView";
        }
        link+="?deviceId="+deviceId+"&wifiId="+wifiId;
        return  link;
    },
        deleteDevice:function(deviceId){

          Ajax.del(PATH+"/smart/home/device/"+deviceId,{},function(result){
            if(result.r==AJAX_SUCC){
                alert("删除成功解绑");
                this.list=[];
                this.getDeviceList();
            }else{
                alert(result.msg);
            }
          }.Apply(this));
        },
        getDeviceImg:function(deviceSubCode,deviceSubName,Online){
            var imgpath =PATH+ "/static/img/smarthome/";
            console.log(deviceSubCode);
          if(deviceSubCode=='010'){//deviceSubName=="我的网关"
             imgpath+="路由器";
          }else if(deviceSubCode=='046' ){//deviceSubName=="智能门锁"
             imgpath+="门锁";
          }else if(deviceSubName=="烟感"){//deviceSubName=="智能门锁"
            imgpath+="门锁";
         }else if(deviceSubCode=='002'){//deviceSubName=="水侵"
             imgpath+="水侵";
        }else if(deviceSubCode=='001'){//deviceSubName=="红外"
            imgpath+="红外";
        }else if(deviceSubCode=='003'){//deviceSubName=="红外"
             imgpath+="烟感";
         }else if(deviceSubCode=="011"){
            imgpath+="插座";
         }
         if(Online==1){
            imgpath+="3";
         }else{
               imgpath+="4";
         }
         imgpath+=".png";
         return imgpath;



        },
        changeModel:function(value){
             var wifiId =null;
                        var redDeviceId =null;
                        var socketDeviceId =null;
           for(var i=0;i<this.list.length;i++){
                           wifiId = this.list[i].wifiId;
                           if(this.list[i].deviceSubTypeCode=="001"){
                                   redDeviceId= this.list[i].deviceid;
                           }
                            if(this.list[i].deviceSubTypeCode=="011"){
                                   socketDeviceId= this.list[i].deviceid;
                           }
                       }
                        if(!wifiId){
                            ztips("你还没有设备!");
                            return;

                        }

                          Ajax.put(PATH+"/smart/home/device/modleRemoteCmd",{
                                                    "wifiId":wifiId,
                                                    "model":value,
                            },function(result){
                                if(result.r==AJAX_SUCC){
                                   ztips("操作成功!",1000);

                                }else{
                                    alert(result.msg);
                                }
                            }.Apply(this));


           /* if(value==1){
                //回家模式
                //打开插座  关闭红外


                 Ajax.post(PATH+"/smart/home/device/modleRemoteCmd",{
                            "wifiId":wifiId,
                            "deviceId":redDeviceId,
                            "turn":0

                            },function(result){
                                if(result.r==AJAX_SUCC){
                                   ztips("关闭红外!",1000);

                                }else{
                                    alert(result.msg);
                                }
                            }.Apply(this));

                Ajax.post(PATH+"/smart/home/device/plugRemoteCmd",{
                                "wifiId":wifiId,
                                "deviceId":socketDeviceId,
                                "power":1

                            },function(result){
                                if(result.r==AJAX_SUCC){
                                   ztips("打开插座");

                                }else{
                                    alert(result.msg);
                                }
                            }.Apply(this));
            }else if(value==2){
                //离家模式
                //关闭插座 打开红外
                 Ajax.post(PATH+"/smart/home/device/infraRedRemoteCmd",{
                    "wifiId":wifiId,
                    "deviceId":redDeviceId,
                    "turn":1

                    },function(result){
                        if(result.r==AJAX_SUCC){
                           ztips("打开红外!",1000);

                        }else{
                            alert(result.msg);
                        }
                    }.Apply(this));


                  Ajax.post(PATH+"/smart/home/device/plugRemoteCmd",{
                                                "wifiId":wifiId,
                                                "deviceId":socketDeviceId,
                                                "power":0

                                            },function(result){
                                                if(result.r==AJAX_SUCC){
                                                   ztips("关闭插座");

                                                }else{
                                                    alert(result.msg);
                                                }
                                            }.Apply(this));

            }*/
        },
        selectTab:function(index){

            this.tabIndex=index;
            if(index==2){
                 window.location="#userCenter";
            }
        }
    }
}
</script>
<style scoped>



</style>

