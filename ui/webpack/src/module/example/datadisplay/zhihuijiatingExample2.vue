<template>
<div>
     <zwRow>

            <zwCol class="pull-right" span=24>
                  <zwJumbotron title="智能家居"></zwJumbotron>


            <zwCollapse >
                <zwPanel state="open">
                <span slot="title" name="title">故障设备</span>


                  <ul slot="body" name="body">
                        <li  v-for="result in list" key={{result.id}} >
                        <zwSlidPanel :imgSrc="result.pic" :title="result.title" :subTitle="result.subTitle">
                        <img src="/static/img/smarthome/插座3.png" slot="img"></img>

                         <img src="/static/img/smarthome/正常.png" slot="switch"></img>
                        </zwSlidPanel>
                        </li>
                </ul>

                </zwPanel>
                <zwPanel state="open" >
                <span slot="title" name="title">在线设备</span>
                    <p  slot="body" name="body">321<br>123</p>
                </zwPanel>
                <zwPanel  state="open">
                <span slot="title" name="title">离线设备</span>
                    <p  slot="body" name="body">321<br>123</p>
                </zwPanel>

            </zwCollapse>




             <zwTabs class="fix-bottom" style="width:100%" type="appbottom"  panel="no">
                <zwTabPanel @click="selectTab(1)" tab="网络管理" img="/static/img/smarthome/网络管理1.png" img2="/static/img/smarthome/网络管理2.png"  tabIndex=1>

                </zwTabPanel >
              <zwTabPanel @click="selectTab(2)" tab="智能家居" img="/static/img/smarthome/智能家居1.png"  img2="/static/img/smarthome/智能家居2.png"  tabIndex=2>
                  Content of Tab Pane 21
              </zwTabPanel >
              <zwTabPanel @click="selectTab(3)" tab="个人中心" img="/static/img/smarthome/个人中心1.png"  img2="/static/img/smarthome/个人中心2.png"   tabIndex=3>
                  Content of Tab Pane 31
              </zwTabPanel >

            </zwTabs>
            </zwCol>

          </zwRow>

</div>

</template>
<script>

import zwLayout from '../../../component/layout/zwLayout.vue';
import zwHeader from '../../../component/layout/zwHeader.vue';
import zwContent from '../../../component/layout/zwContent.vue';
import zwSider from '../../../component/layout/zwSider.vue';
import zwFooter from '../../../component/layout/zwFooter.vue';
import zwRow from '../../../component/layout/zwRow.vue';
import zwCol from '../../../component/layout/zwCol.vue';
import zwIcon from '../../../component/icon/zwIcon.vue';
import zwBox from '../../../component/layout/zwBox.vue';
import zwTabs from '../../../component/datadisplay/zwTabs.vue';
import zwTabPanel from '../../../component/datadisplay/zwTabPanel.vue';
import zwCrousel from '../../../component/datadisplay/zwCrousel.vue';
import zwChocolatePanel from '../../../component/datadisplay/zwChocolatePanel.vue';
import zwSliderText from '../../../component/datadisplay/zwSliderText.vue';
import zwAppImgList from '../../../component/datadisplay/zwAppImgList.vue';
import zwAppBottomBar from '../../../component/datadisplay/zwAppBottomBar.vue';

import zwCollapse from '../../../component/datadisplay/zwCollapse.vue';

import zwButtonGroup from '../../../component/button/zwButtonGroup.vue';
  import zwDropDown from '../../../component/navigation/menu/zwDropDown.vue';
  import zwMenu from '../../../component/navigation/menu/zwMenu.vue';
  import zwMenuItem from '../../../component/navigation/menu/zwMenuItem.vue';
  import zwDataMenuItems from '../../../component/navigation/menu/zwDataMenuItems.vue';
  import zwSubMenu from '../../../component/navigation/menu/zwSubMenu.vue';
  import zwMenuItemGroup from '../../../component/navigation/menu/zwMenuItemGroup.vue';
  import zwJumbotron from '../../../component/datadisplay/zwJumbotron.vue';
  import zwSlidPanel from '../../../component/datadisplay/zwSlidPanel.vue';
import zwPanel from '../../../component/datadisplay/zwPanel.vue';

export default {
    components:{zwRow,zwCol,zwIcon,zwBox,zwHeader,zwContent,zwFooter,zwLayout,zwSider,zwCrousel,zwTabs,zwTabPanel,zwChocolatePanel,zwAppImgList,zwSliderText,zwAppBottomBar,
     zwDropDown, zwMenu, zwMenuItem, zwMenuItemGroup, zwSubMenu, zwButtonGroup, zwDataMenuItems,zwJumbotron,zwSlidPanel,zwCollapse,zwPanel
    },
    data () {

        return {
        list:[],
        tabIndex:2,
      }
    }
    ,
    mounted(){
    Ajax.getJSON("/goods/list.json?curpage=1&pagesize=10",null,function(result){
                                         if(result.r==AJAX_SUCC){
                                             this.list=result.data;
                                         }
                                     }.Apply(this));
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
        selectTab:function(index){
            this.tabIndex=index;
        }
    }
}
</script>
<style scoped>

</style>

