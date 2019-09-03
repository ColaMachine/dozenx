<template>
<div>
     <zwRow>

            <zwCol class="pull-right" span=24>
                <div sttyle="" class="zw-jumbotron-wrap smart-home-device-view-head-bg " >
                <div class="zw-img-title-img-wrap"  >
                <img class="zw-img-title-img"   :src="getImg('/static/img/smarthome/插座'+(entity.deviceStatus.Online==1?'1':'2')+'.png')" ></img>

                  <span class="zw-img-title-state-spot" :style="entity.online==1?'background-color:#dfdf21':'background-color:gray'" >
                    </span>
                </div>
                <div style="width:70%;color:gray;padding-left:15px;">
                 <h1 style="font-size:15px;margin-top:-8px;">当前状态:{{entity.deviceStatus.Online==1?'正常':'离线'}}</h1>

                 <span style="font-weight:bold;font-size:12px;">开关状态:{{entity.deviceStatus.Power==1?'打开':'关闭'}}</span>

            </div>


                </div>


                <zwAppList :list="itemList"></zwAppList>




            </zwCol>

          </zwRow>
 <zwRow class="fix-bottom">
           <zwCol  span=24>
            <zwButton type="yellow"  v-on:clickFn="enableAlarm()" sizeNum="large"  style="width:95%">{{entity.deviceStatus.Power==1?"关闭":"打开"}}</zwButton>

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

        {title:"异常记录",link:"#deviceErrorListView?deviceId="+getDiyQueryString("deviceId")+"&wifiId="+getDiyQueryString("wifiId"),count:0},

        ],
        tabIndex:2,
      }
    }
    ,
    mounted(){
    //smart/home/device/findDeviceBydeviceId?wifiId=86100c00a0050010000000054d20c674&deviceId=a005046001f2dca313004b1200&userId=9999999999&

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
                       this.itemList[0].count=result.data;
                   }else{
                       alert(result.msg);
                   }
                  } .Apply(this))
    },
    computed: {

    },
    methods:{
        enableAlarm:function(){
            var power =1;
                   //alert(this.entity.deviceStatus.Turn);
            if(this.entity.deviceStatus.Power==1){
                power=0;
            }
            Ajax.post(PATH+"/smart/home/device/plugRemoteCmd",{
                "wifiId":getDiyQueryString("wifiId"),
                "deviceId":getDiyQueryString("deviceId"),
                "power":power

            },function(result){
                if(result.r==AJAX_SUCC){
                   alert("操作成功");
                   this.entity.deviceStatus.Power =power;
                }else{
                    alert(result.msg);
                }
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

