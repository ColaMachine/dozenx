<template>
<div>

 <div id="router">

                   <!-- 路由出口 -->
                   <!-- 路由匹配到的组件将渲染在这里 -->
                   <router-view></router-view>
                 </div>
     <zwRow>

            <zwCol class="pull-right" span=24>
              <zwMenu mode="horizontal" theme="white">
                <zwMenuItem key="mail">
                  <zwIcon type="home" />首页
                </zwMenuItem>
                 <zwMenuItem key="mail" >

                  <a href="/static/html/vue/zhihuijiating.html" target="blank" rel="noopener noreferrer"> <zwIcon type="home" />智慧家庭</a>

                                </zwMenuItem>

                <zwMenuItem>
                  <zwDropDown v-show="!isLogin">
                    <zwButton slot="button" sizeNum="">登录</zwButton>
                    <div class="zw-menu bg-white" slot="menu">

                      <iframe style="width:250px;height:400px" src="http://127.0.0.1:8080/static/html/login_iframe.html" />

                    </div>
                  </zwDropDown>

                </zwMenuItem>
                <zwSubMenu>
                  <template slot="title"> dozenx</template>
                  <zwMenuItem key="setting:1">
                    <zwIcon type="user" />修改个人信息</zwMenuItem>
                  <zwMenuItem key="setting:1">
                    <zwIcon type="sign-out" />退出登录</zwMenuItem>
                  <zwMenuItemGroup title="购物">
                    <zwMenuItem key="setting:1">我的收藏</zwMenuItem>
                    <zwMenuItem key="setting:2">我的购物车</zwMenuItem>
                  </zwMenuItemGroup>
                  <zwMenuItemGroup title="直播">
                    <zwMenuItem key="setting:3">我的收藏</zwMenuItem>
                    <zwMenuItem key="setting:4">我的观看记录</zwMenuItem>
                  </zwMenuItemGroup>

                </zwSubMenu>

              </zwMenu>

            </zwCol>

          </zwRow>



     <zwTabs>


            <zwTabPanel tab="Tab1" tabIndex=1>
                     <zwCrousel :imglist="imglist"> </zwCrousel>
                        <zwChocolatePanel :data="iconlist">白111菜</zwChocolatePanel>
                         <zwSliderText :list="textlist"> </zwSliderText>
                             <zwAppImgList :list="goodsList"></zwAppImgList>
                                <zwAppBottomBar></zwAppBottomBar>
            </zwTabPanel >
              <zwTabPanel tab="Tab2" tabIndex=2>
                  Content of Tab Pane 21
              </zwTabPanel >
              <zwTabPanel tab="Tab3" tabIndex=3>
                  Content of Tab Pane 31
              </zwTabPanel >


</zwTabs  >


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


import zwButtonGroup from '../../component/button/zwButtonGroup.vue';
  import zwDropDown from '../../component/navigation/menu/zwDropDown.vue';
  import zwMenu from '../../component/navigation/menu/zwMenu.vue';
  import zwMenuItem from '../../component/navigation/menu/zwMenuItem.vue';
  import zwDataMenuItems from '../../component/navigation/menu/zwDataMenuItems.vue';
  import zwSubMenu from '../../component/navigation/menu/zwSubMenu.vue';
  import zwMenuItemGroup from '../../component/navigation/menu/zwMenuItemGroup.vue';

export default {
    components:{zwRow,zwCol,zwIcon,zwBox,zwHeader,zwContent,zwFooter,zwLayout,zwSider,zwCrousel,zwTabs,zwTabPanel,zwChocolatePanel,zwAppImgList,zwSliderText,zwAppBottomBar,
     zwDropDown, zwMenu, zwMenuItem, zwMenuItemGroup, zwSubMenu, zwButtonGroup, zwDataMenuItems
    },
    data () {

        return {
        goodsList:[],
  iconlist:[
            {type:"home",url:"www.baidu.com",name:"白菜123123"},
            {type:"home",url:"www.baidu.com",name:"白菜"},
            {type:"home",url:"www.baidu.com",name:"白菜"},
            {type:"home",url:"www.baidu.com",name:"白菜"},
            {type:"home",url:"www.baidu.com",name:"白菜"},
            {type:"home",url:"www.baidu.com",name:"白菜"},
            {type:"home",url:"www.baidu.com",name:"白菜"},
            {type:"home",url:"www.baidu.com",name:"白菜"},
            {type:"home",url:"www.baidu.com",name:"白菜"},
            {type:"home",url:"www.baidu.com",name:"白菜"},
            ],
            textlist:[
                                {type:"home",url:"www.baidu.com",name:"台新党青年怒斥被抓:不存在大陆提供金钱的事"},
                                {type:"home",url:"www.baidu.com",name:"网传南昌大学一女生遭副院长性侵长达7个月"},
                                {type:"home",url:"www.baidu.com",name:"财政部长人民日报撰文:加快建立现代财政制度"},
                                {type:"home",url:"www.baidu.com",name:"广东这名厅官落马 曾因一张摆拍照闻名全国"},
                                {type:"home",url:"www.baidu.com",name:"温度 | 5岁男童地铁惊厥吐血 众人接力送医"},
                                {type:"home",url:"www.baidu.com",name:"网传吉林警察打人视频 警方发全景监控回应"},
                                {type:"home",url:"www.baidu.com",name:"华西村负债389亿 集团要职由吴仁宝家族成员分管"},
                                {type:"home",url:"www.baidu.com",name:"天津纪委遭问:黄兴国案涉多名干部 怎么肃清流毒?"},
                                {type:"home",url:"www.baidu.com",name:"湖北一单位3任书记塌方腐败 线索来自开房记录"},
                                {type:"home",url:"www.baidu.com",name:"女子把11万元钻戒扔了 8名环卫工翻13吨垃圾找到"},
                                ]
        }
    }
    ,
    mounted(){
    Ajax.getJSON("/goods/list.json?curpage=1&pagesize=10",null,function(result){
                                      if(result.r==AJAX_SUCC){
                                          this.goodsList=result.data;
                                      }
                                  }.Apply(this));
    },
    computed: {

    },
    methods:{

    }
}
</script>
<style scoped>

</style>

