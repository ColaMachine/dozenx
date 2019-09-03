<template>


       <zwTabs  class="bg-black" :theme="theme" :defaultIndex="defaultIndex" v-on:changeTab="selectTab" style="width:100%" type="appbottom"  panel="no">
          <zwTabPanel tab="网络管理" :img="getImg('/static/img/smarthome/网络管理1.png')" :img2="getImg('/static/img/smarthome/网络管理2.png')"  tabIndex=1>

          </zwTabPanel >
        <zwTabPanel tab="智能家居" :img="getImg('/static/img/smarthome/智能家居1.png')"  :img2="getImg('/static/img/smarthome/智能家居2.png')"  tabIndex=2>
            Content of Tab Pane 21
        </zwTabPanel >
        <zwTabPanel tab="个人中心" :img="getImg('/static/img/smarthome/个人中心1.png')"  :img2="getImg('/static/img/smarthome/个人中心2.png')"   tabIndex=3>
            Content of Tab Pane 31
        </zwTabPanel >

      </zwTabs>
</template>
<script>

import zwTabs from '../../component/datadisplay/zwTabs.vue';
import zwTabPanel from '../../component/datadisplay/zwTabPanel.vue';
/**登录注册页面**/
export default {
    name:"bottomBar",
    components:{zwTabs,zwTabPanel },
    computed:{

    },
      props:
                ["type","theme","panel","defaultIndex","theme"]//"tabs"
            ,
              methods:{

          getImg:function(img){
              return PATH+img;
          },
            selectTab:function(index){

            this.tabIndex=index;
            if(index==2){
                 window.location="#userCenter";
            }else if(index==1){
                 window.location="#zhihuijiatingExample";
            }else if(index==0){
                  window.location="#wangluoguanli";
             }
        },
}
}
</script>
<style scoped>

</style>

