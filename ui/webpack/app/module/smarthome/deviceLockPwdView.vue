<template>
<div>
    <zwRow>
        <zwCol class="pull-right" span=24>
            <zwPanel state="open"  >
              <span slot="title" name="title">密码管理</span>
              <p  slot="body" name="body">

                <zwSlidPanel  title="管理员密码"> </zwSlidPanel>
                <zwSlidPanel v-if="hasTempPwd"  v-on:deleteCallBack="deleteTempPwd" canDel="true" title="临时密码"> </zwSlidPanel>
              </p>
            </zwPanel>




        <zwButton sizeNum="large" v-on:clickFn="goToAddTempPwd" class="fix-bottom" type="yellow" style="back-ground-color:yellow;width:100%"> 添加临时密码</zwButton>



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
/**门锁的密码查看页面**/
export default {
    name:"lockPwdModify",
    components:{zwRow,zwCol,zwIcon,zwBox,zwHeader,zwContent,zwFooter,zwLayout,zwSider,zwCrousel,zwTabs,zwTabPanel,zwChocolatePanel,zwAppImgList,zwSliderText,zwAppBottomBar,
     zwDropDown, zwMenu, zwMenuItem, zwMenuItemGroup, zwSubMenu, zwButtonGroup, zwDataMenuItems,zwJumbotron,zwSlidPanel,zwCollapse,zwPanel,zwButton,zwAppList
    },
    data () {

        return {
        hasTempPwd:false
      }
    }
    ,
    mounted(){
        Ajax.getJSON(PATH+"/smart/home/device/findGatewayPwd?",{deviceId:getDiyQueryString("deviceId")},function(result){
             if(result.r==AJAX_SUCC){
                 if(result.data && result.data.length>0){
                        this.hasTempPwd=true;
                 }
             }else{
                alert(result.msg);
             }
         }.Apply(this));
    },
    computed: {

    },
    methods:{
        deleteTempPwd:function(){//删除临时密码
            Ajax.post(PATH+"/smart/home/device/deleteTempPwdRemoteCmd",
            {
                wifiId:getDiyQueryString("wifiId"),
                deviceId:getDiyQueryString("deviceId")
            },
            function(result){
                if(result.r==AJAX_SUCC){//删除成功
                    this.hasTempPwd=true;
                }else{
                   alert(result.msg);
                }
            }.Apply(this)
            )
        },
        goToAddTempPwd:function(){//添加临时密码
            window.location="#deviceLockPwdModify?deviceId="+getDiyQueryString("deviceId")+"&wifiId="+getDiyQueryString("wifiId");
        }

    }
}
</script>
<style scoped>


</style>

