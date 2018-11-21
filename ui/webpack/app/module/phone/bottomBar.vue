<template>


  <zwTabs  class="bg-black fix-bottom" theme="purple_yellow" defaultIndex="1" v-on:changeTab="selectTab" style="width:100%" type="appbottom"  panel="no">
          <dataitem  tab="首页" :img="getPathValue('/static/img/smarthome/网络管理1.png')" :img2="getPathValue('/static/img/smarthome/网络管理2.png')"  tabIndex=1>

          </dataitem >

          <dataitem tab="附近" :img="getPathValue('/static/img/smarthome/智能家居1.png')"  :img2="getPathValue('/static/img/smarthome/智能家居2.png')"  tabIndex=2>
                      Content of Tab Pane 21
                  </dataitem >
       <dataitem tab="发现" :img="getPathValue('/static/img/smarthome/智能家居1.png')"  :img2="getPathValue('/static/img/smarthome/智能家居2.png')"  tabIndex=3>
                            Content of Tab Pane 21
                        </dataitem >
        <dataitem tab="订单" :img="getPathValue('/static/img/smarthome/智能家居1.png')"  :img2="getPathValue('/static/img/smarthome/智能家居2.png')"  tabIndex=4>
            Content of Tab Pane 21
        </dataitem >
        <dataitem tab="我的" :img="getPathValue('/static/img/smarthome/个人中心1.png')"  :img2="getPathValue('/static/img/smarthome/个人中心2.png')"   tabIndex=5>
            Content of Tab Pane 31
        </dataitem >

      </zwTabs>



</template>
<script>
  import zwTabs from '../../component/datadisplay/zwTabs.vue';

 import zwBase from '../../component/zwBase.vue';
  export default {
    components: {
     zwBase,

      zwTabs,

    },
    name: "bottomBar",
    data() {

      return {

      }
    },
    mounted() {

    },
    computed: {

    },
    methods: {
    selectTab:function(index){
        if(index==0){
            window.location="#phoneMain";
        }
          if(index==1){
                    window.location="#map";
                }
                  if(index==4){
                                    window.location="#userCenter";
                                }
    },
    getPathValue:function(value){

    return getPathValue(value);
    }
    }
  }
</script>
<style scoped>

</style>