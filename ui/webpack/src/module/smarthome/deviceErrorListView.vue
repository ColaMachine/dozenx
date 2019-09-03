<template>
<div>
     <zwRow>

            <zwCol class="pull-right" span=24>


              <zwPanel state="open"  >
              <span slot="title" name="title">最近三天</span>

              <p  slot="body" name="body">

                    <zwAppList :list="itemList" :render="listItemRender" ></zwAppList>

              </p>

            </zwPanel>

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


        ],
        tabIndex:2,
      }
    }
    ,
    mounted(){//alert(123);
    Ajax.getJSON(PATH+"/smart/home/alarm/list",{deviceId:getDiyQueryString("deviceId")},function(result){
                                         if(result.r==AJAX_SUCC){
                                             this.itemList=result.data;
                                         }
                                     }.Apply(this));
    },
    computed: {

    },
    methods:{
        listItemRender:function(item){
            return "<h1>"+item.category+"</h1>"+"<h2>"+item.createTimeStr+"</h2>"
        }
    }
}
</script>
<style scoped>


</style>

