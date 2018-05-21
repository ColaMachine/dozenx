<template>
<div id="wangluodiv" style="width:100%;height:100%;background-image:url(/weixin/static/img/smarthome/WechatIMG253.jpeg);background-attachment: fixed;background-size: cover;" >


     <zwRow >

            <zwCol   class="pull-right" span=24>

                  <zwJumbotron title="我的家"></zwJumbotron>




  <ul >
                    <li >
                    <zwSlidPanel   canDel="true"  id="1" :imgSrc="getImg('/static/img/smarthome/路由器1.png')" link="#wangguan"  title="网关名称1" subTitle="设备在线">

                    <img :src="getImg('/static/img/smarthome/路由器1.png')" slot="img"></img>

                     <img :src="getImg('/static/img/smarthome/正常.png')" slot="switch"></img>
                    </zwSlidPanel>
                    </li>

                     <li >
                        <zwSlidPanel   canDel="true"  id="1" :imgSrc="getImg('/static/img/smarthome/路由器1.png')" link="#wangguan"  title="网关名称2" subTitle="网关">

                        <img :src="getImg('/static/img/smarthome/路由器1.png')" slot="img"></img>

                         <img :src="getImg('/static/img/smarthome/正常.png')" slot="switch"></img>
                        </zwSlidPanel>
                        </li>

                </ul>

            <div style="height:100px">
            </div>




            </zwCol>

          </zwRow>


  <zwRow class="fix-bottom bg-black">
 <zwCol  span=24>
    <bottomBar theme="purple_yellow" class="fix-bottom" defaultIndex="0"></bottomBar>
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

import zwPanel from '../../component/datadisplay/zwPanel.vue';
import bottomBar from '../smarthome/bottomBar.vue';
export default {
    components:{zwRow,zwCol,zwIcon,bottomBar,zwJumbotron,zwSlidPanel,zwPanel
    },
    data () {

        return {
        list:[],
        tabIndex:2,
      }
    }
    ,
    mounted(){//alert(123);
        //alert(window.innerHeight);
        document.getElementById("wangluodiv").style.height=window.innerHeight+"px";

    },
    computed: {
        getWangluoguanliImg:function(){
            return this.tabIndex==1?'/static/img/smarthome/网络管理2.png':'/static/img/smarthome/网络管理1.png';
        },

        getZhinengjiatingImg:function(){
            return this.tabIndex==2?'/static/img/smarthome/智能家居2.png':'/static/img/smarthome/智能家居1.png';
        },
        getGerenzhongxinImg:function(){
            return this.tabIndex==3?'/static/img/smarthome/个人中心2.png':'/static/img/smarthome/个人中心1.png';
        },
    },
    methods:{
     getImg:function(img){
                return PATH+img;
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
        }else if(deviceSubName=="插座"){
        imgpath+="插座";
        }
        link+="?deviceId="+deviceId+"&wifiId="+wifiId;
        return  link;
    },
        deleteDevice:function(deviceId){
          Ajax.del(PATH+"/smart/home/device/"+deviceId,{},function(result){
            if(result.r==AJAX_SUCC){
                alert("删除成功解绑");
            }else{
                alert(result.msg);
            }
          });
        },
        getDeviceImg:function(deviceSubCode,deviceSubName,Online){
            var imgpath ="/static/img/smarthome/";
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
         }else if(deviceSubName=="插座"){
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
.body{

}
.zw-jumbotron-title{
color:white !important;
}
.zw-jumbotron-wrap{
    background: url("");



}
.zw-app-slid-it{
    height:80px;
    margin:15px;
    padding-left:15px;

    background-color:rgba(255,255,255,0.8);
}
.zw-app-slid-it .zw-app-slid-content{
 padding-top:10px;
       padding-bottom:8px;
       }
</style>

