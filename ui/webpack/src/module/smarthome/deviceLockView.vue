<template>
<div>
     <zwRow>

            <zwCol class="pull-right" span=24>
                <div sttyle="" class="zw-jumbotron-wrap smart-home-device-view-head-bg" >
                <div class="zw-img-title-img-wrap" >
                <img class="zw-img-title-img"  :src="getImg('/static/img/smarthome/门锁'+(entity.deviceStatus.Online==1?'1':'2')+'.png')"  ></img>

                 <span class="zw-img-title-state-spot" :style="entity.deviceStatus.Online==1?'background-color:#dfdf21':'background-color:gray'" >
                    </span>
                </div>
                <div  class="device-view-title" style="">
                     <h1 >在线状态:{{entity.deviceStatus.Online==1?'正常':'离线'}}</h1>
                   <span >电量状态:{{entity.deviceStatus.LowPower==1?'低电量':'正常'}}</span>
                   <span >报警状态:{{entity.deviceStatus.Status==1?'报警':'正常'}}</span>

                   <span >拆除状态:{{entity.deviceStatus.Removal==1?'报警':'正常'}}</span>
                    <span >最近操作结果:{{entity.deviceStatus.OpResult==0?'失败':'成功'}}</span>
            </div>


                </div>


           <zwAppList :list="itemList"></zwAppList>

                <zwButton v-on:clickFn="openDoor()" sizeNum="large" :loading="false"  :loading_delay=5 class="fix-bottom" type="yellow" style="back-ground-color:yellow;width:95%;font-size:14px"> 开门</zwButton>



            </zwCol>

          </zwRow>

</div>

</template>
<script>

import zwRow from '../../component/layout/zwRow.vue';
import zwCol from '../../component/layout/zwCol.vue';
import zwIcon from '../../component/icon/zwIcon.vue';
import zwButton from '../../component/button/zwButton.vue';
import zwAppList from '../../component/datadisplay/zwAppList.vue';


/**门锁的查看页面**/
export default {
     components:{zwRow,zwCol,zwIcon,zwButton,zwAppList, },


    data () {

        return {
        entity:{},
        itemList:[
        {title:"设置密码",link:"#deviceLockPwdView?deviceId="+getDiyQueryString("deviceId")+"&wifiId="+getDiyQueryString("wifiId")},
        {title:"异常记录",link:"#deviceErrorListView?deviceId="+getDiyQueryString("deviceId")+"&wifiId="+getDiyQueryString("wifiId"),count:0},

        ],
        tabIndex:2,
      }
    }
    ,
    mounted(){
    //smart/home/device/findDeviceBydeviceId?wifiId=86100c00a0050010000000054d20c674&deviceId=a005046001f2dca313004b1200&userId=9999999999&
        this.getInfo();//更新状态 更新未读消息数量


    },
    computed: {

    },
    methods:{
    getInfo:function(){

         Ajax.getJSON(PATH+"/smart/home/device/findDeviceBydeviceId",{"type":0,"wifiId":getDiyQueryString("deviceId"),deviceId:getDiyQueryString("deviceId")},function(result){
            if(result.r==AJAX_SUCC){
                this.entity=result.data;
                  this.entity.deviceStatus = eval("("+this.entity.deviceStatus +")");
            }else{
                alert(result.msg);
            }
           } .Apply(this));

        Ajax.getJSON(PATH+"/smart/home/alarm/needReadNum",{"wifiId":getDiyQueryString("deviceId"),deviceId:getDiyQueryString("deviceId")},function(result){
                   if(result.r==AJAX_SUCC){
                       this.itemList[1].count=result.data;
                   }else{
                       alert(result.msg);
                   }
                  } .Apply(this))
    }
    ,
    openDoor:function(){
        zprompt("请输入门锁密码","请输入门锁密码",this.sendDoorOpenCmd);


    },
    sendDoorOpenCmd:function(inputValue){
        inputValue = document.getElementById("zpromptText").value;
               console.log(inputValue);
        console.log(inputValue.length);
        if(!inputValue){
                 alert("请输入密码");
                 return ;
        }
        if(inputValue.length!=8){
                         alert("请输入8位密码");
                         return ;
                }
        if( !isPositiveInteger(inputValue.substr(2))){


            alert("请输入数字密码");
            return;
        }
        Ajax.post(PATH+"/smart/home/device/openGateRemoteCmd",{
            "wifiId":getDiyQueryString("wifiId"),
            "deviceId":getDiyQueryString("deviceId"),
            "pwd":inputValue

            },function(result){
                if(result.r==AJAX_SUCC){
                   // alert("开门成功");

                }else{
                    alert(result.msg);

                }
                 this.getInfo();
            }.Apply(this));
    },

    getImg:function(img){
        return PATH+img;
    },
    }
}
</script>
<style scoped>

</style>

