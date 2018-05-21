<template>
<div>
     <zwRow>

            <zwCol class="pull-right" span=24>
                <div sttyle="" class="zw-jumbotron-wrap smart-home-device-view-head-bg " >
                <div class="zw-img-title-img-wrap" >
                <img class="zw-img-title-img"  :src="getImg('/static/img/smarthome/红外'+(entity.deviceStatus.Online==1?'1':'2')+'.png')"  ></img>

                   <span class="zw-img-title-state-spot" :style="entity.online==1?'background-color:#dfdf21':'background-color:gray'" >
                    </span>
                </div>
                <div style="width:70%;color:gray;padding-left:15px;">
                <h1 style="font-size:15px;margin-top:-8px;">在线状态:{{entity.deviceStatus.Online==1?'正常':'离线'}}</h1>
                   <span style="font-weight:bold;font-size:12px;">电量状态:{{entity.deviceStatus.LowPower==1?'低电量':'正常'}}</span>
                    <span style="font-weight:bold;font-size:12px;">报警状态:{{entity.deviceStatus.Status==1?'报警':'正常'}}</span>
                     <span style="font-weight:bold;font-size:12px;">布防状态:{{entity.deviceStatus.Turn==1?'打开':'关闭'}}</span>


            </div>


                </div>


                <zwAppList :list="itemList"></zwAppList>




            </zwCol>

          </zwRow>

           <zwRow class="fix-bottom">
           <zwCol  span=24>
            <zwButton type="yellow"  v-on:clickFn="enableAlarm()" sizeNum="large"  style="width:95%">{{entity.deviceStatus.Turn==1?"撤防":"布防"}}</zwButton>

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

         Ajax.getJSON(PATH+"/smart/home/device/findDeviceBydeviceId",{"type":0,"wifiId":getDiyQueryString("deviceId"),deviceId:getDiyQueryString("deviceId")},function(result){
            if(result[AJAX_RESULT]==AJAX_SUCC){
                this.entity=result.data;
                this.entity.deviceStatus = eval("("+this.entity.deviceStatus +")");
                //alert(this.entity.deviceStatus .Turn);
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
    /**开启布防或者撤防*/
        enableAlarm:function(){
               var turn =1;
               //alert(this.entity.deviceStatus.Turn);
        if(this.entity.deviceStatus.Turn==1){
            turn=0;
        }
         Ajax.post(PATH+"/smart/home/device/infraRedRemoteCmd",{
            "wifiId":getDiyQueryString("wifiId"),
            "deviceId":getDiyQueryString("deviceId"),
            "turn":turn

            },function(result){
                if(result.r==AJAX_SUCC){
                   alert("操作成功");
                   this.entity.deviceStatus.Turn =turn;
                }else{
                    alert(result.msg);
                }
            }.Apply(this));
        },
        getImg:function(img){
            return PATH+img;
        },



        selectTab:function(index){
            this.tabIndex=index;
        }
    }
}
</script>
<style scoped>

</style>

