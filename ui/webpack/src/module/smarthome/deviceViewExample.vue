<template>
<div>
     <zwRow>

            <zwCol class="pull-right" span=24>
                <div sttyle="" class="zw-jumbotron-wrap " >
                <div style="width:30%;position:relative;padding:20px;width:20px;height:20px;border-radius:5px;background-color:white;text-align:center;vertical-align:middle">
                <img style="display:block;height:20px" src="/static/img/smarthome/门锁1.png" class=" " ></img>
                  <span style="width:8px;height:8px;display:block;position:absolute;background-color:#dfdf21;top:5px;left:45px;border-radius:5px">
                    </span>
                </div>
                <div style="width:70%;color:gray;padding-left:15px;">
                <h1 style="font-size:15px;margin-top:-8px;">当前状态:正常</h1>
                   <h2 style="font-weight:bold;font-size:12px;">设备电量低</h2>
</div>


                </div>


           <zwAppList :list="itemList"></zwAppList>

        <zwButton size="big" class="fix-bottom" style="back-ground-color:yellow;width:100%"> 开门</zwButton>



            </zwCol>

          </zwRow>

</div>

</template>
<script>

import zwLayout from '../../component/layout/zwLayout.vue';
import zwHeader from '../../component/layout/zwHeader.vue';
import zwContent from '../../component/layout/zwContent.vue';
import zwSider from '../../component/layout/zwSider.vue';
import zwFooter from '../../component/layout/zwFooter.vue';
import zwRow from '../../component/layout/zwRow.vue';
import zwCol from '../../component/layout/zwCol.vue';
import zwIcon from '../../component/icon/zwIcon.vue';
import zwBox from '../../component/layout/zwBox.vue';
import zwTabs from '../../component/datadisplay/zwTabs.vue';
import zwTabPanel from '../../component/datadisplay/zwTabPanel.vue';
import zwCrousel from '../../component/datadisplay/zwCrousel.vue';
import zwChocolatePanel from '../../component/datadisplay/zwChocolatePanel.vue';
import zwSliderText from '../../component/datadisplay/zwSliderText.vue';
import zwAppImgList from '../../component/datadisplay/zwAppImgList.vue';
import zwAppBottomBar from '../../component/datadisplay/zwAppBottomBar.vue';

import zwCollapse from '../../component/datadisplay/zwCollapse.vue';

import zwButtonGroup from '../../component/button/zwButtonGroup.vue';
  import zwDropDown from '../../component/navigation/menu/zwDropDown.vue';
  import zwMenu from '../../component/navigation/menu/zwMenu.vue';
  import zwMenuItem from '../../component/navigation/menu/zwMenuItem.vue';
  import zwDataMenuItems from '../../component/navigation/menu/zwDataMenuItems.vue';
  import zwSubMenu from '../../component/navigation/menu/zwSubMenu.vue';
  import zwMenuItemGroup from '../../component/navigation/menu/zwMenuItemGroup.vue';
  import zwJumbotron from '../../component/datadisplay/zwJumbotron.vue';
  import zwSlidPanel from '../../component/datadisplay/zwSlidPanel.vue';
    import zwButton from '../../component/button/zwButton.vue';
import zwPanel from '../../component/datadisplay/zwPanel.vue';
import zwAppList from '../../component/datadisplay/zwAppList.vue';



export default {
    components:{zwRow,zwCol,zwIcon,zwBox,zwHeader,zwContent,zwFooter,zwLayout,zwSider,zwCrousel,zwTabs,zwTabPanel,zwChocolatePanel,zwAppImgList,zwSliderText,zwAppBottomBar,
     zwDropDown, zwMenu, zwMenuItem, zwMenuItemGroup, zwSubMenu, zwButtonGroup, zwDataMenuItems,zwJumbotron,zwSlidPanel,zwCollapse,zwPanel,zwButton,zwAppList
    },
    data () {

        return {
        itemList:[
        {title:"设置密码",link:"www.baidu.com"},
        {title:"异常记录",link:"www.baidu.com"},

        ],
        tabIndex:2,
      }
    }
    ,
    mounted(){//alert(123);
    Ajax.getJSON(PATH+"/smart/home/device/list?userId=9999999999",null,function(result){
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
        getDeviceImg:function(deviceSubName,Online){
            var imgpath ="/static/img/smarthome/";
          if(deviceSubName=="我的网关"){
             imgpath+="路由器";
          }else if(deviceSubName=="智能门锁"){
             imgpath+="门锁";
          }else if(deviceSubName=="烟感"){
            imgpath+="门锁";
         }else if(deviceSubName=="水侵"){
             imgpath+="水侵";
        }else if(deviceSubName=="红外"){
            imgpath+="红外";
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
        }
    }
}
</script>
<style scoped>

.zw-jumbotron-wrap{
display:flex;
vertical-align:middle;
padding-top:35px;
padding-left:25px;
min-height:150px;
line-height:150px;
/*background-color:#dfdf1d;*/


background:url(/static/img/smarthome/背景板.png);
background-size:100% 180px;
background-repeat:no-repeat;
}


</style>

