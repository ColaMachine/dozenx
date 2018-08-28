<template>
  <div>

<zwRow style="display:none">

<zwCol class="pull-right" span=24>
<zwAppTopBar >
  <div slot="left">
  杭州
  </div>

              <zwInput  slot="middle"  lineHeight="42px" style="font-size:12px;" placeholder="搜索内容/名称/地址">
            <zwButton  slot="prefix"  icon="search"></zwButton>
           </zwInput>
           <a slot="right" href="">
<img  class="header-right-img right" src="/static/img/header/information.png">
</a>
</zwAppTopBar>

</zwCol>

</zwRow>



    <zwRow>

      <zwCol class="pull-right" span=24>
        <zwMenu style="width:20%" mode="horizontal" theme="white">
          <zwMenuItem key="mail">
            杭州<zwIcon type="arrow-down" />
          </zwMenuItem>
          <zwMenuItem  style="width:50%" key="mail" >

            <zwInput  style="font-size:12px;" placeholder="搜索内容/名称/地址">
 <zwButton  slot="prefix"  icon="search"></zwButton>
</zwInput>
          </zwMenuItem>


          <zwMenuItem style="width:20%" v-show="!isLogin">
            <a href="/sys/auth/login.htm">登录 </a>
          </zwMenuItem>
          <zwSubMenu style="width:20%" v-show="isLogin">
            <template slot="title"> {{user.userName}}</template>
            <zwMenuItem key="setting:1">
              <zwIcon type="user" />修改个人信息</zwMenuItem>
            <zwMenuItem key="setting:1">

              <zwIcon type="sign-out" />
                 <a href="/sys/auth/logout.htm">退出登录 </a>
              </zwMenuItem>
            <zwMenuItemGroup title="购物">
              <zwMenuItem key="setting:1">我的收藏</zwMenuItem>
              <zwMenuItem key="setting:2">我的购物车</zwMenuItem>
            </zwMenuItemGroup>

          </zwSubMenu>

        </zwMenu>

      </zwCol>

    </zwRow>
<zwRow style="margin-top:0px;" >
<zwCol span=24>
 <zwCrousel style="height:100px;" height=100 :imglist="imglist"> </zwCrousel>

   <zwChocolatePanel   :data="iconlist">白111菜</zwChocolatePanel>

</zwCol>
</zwRow>
    <zwTabs>

      <zwTabPanel tab="Tab1" tabIndex=1>

      Content of Tab Pane 21123123

      </zwTabPanel>
      <zwTabPanel tab="Tab2" tabIndex=2>
        Content of Tab Pane 21
      </zwTabPanel>
      <zwTabPanel tab="Tab3" tabIndex=3>
        Content of Tab Pane 31
      </zwTabPanel>

    </zwTabs>


       <zwSliderText :list="noticeList"> </zwSliderText>
            <zwAppImgList :list="goodsList"></zwAppImgList>

 <zwAppImgList :list="imgList2"></zwAppImgList>



       <zwTabs  class="bg-black fix-bottom" theme="purple_yellow" defaultIndex="1" v-on:changeTab="selectTab" style="width:100%" type="appbottom"  panel="no">
          <zwTabPanel  tab="首页" :img="getImg('/static/img/smarthome/网络管理1.png')" :img2="getImg('/static/img/smarthome/网络管理2.png')"  tabIndex=1>

          </zwTabPanel >

          <zwTabPanel tab="附近" :img="getImg('/static/img/smarthome/智能家居1.png')"  :img2="getImg('/static/img/smarthome/智能家居2.png')"  tabIndex=2>
                      Content of Tab Pane 21
                  </zwTabPanel >
                   <zwTabPanel tab="发现" :img="getImg('/static/img/smarthome/智能家居1.png')"  :img2="getImg('/static/img/smarthome/智能家居2.png')"  tabIndex=3>
                                        Content of Tab Pane 21
                                    </zwTabPanel >
        <zwTabPanel tab="订单" :img="getImg('/static/img/smarthome/智能家居1.png')"  :img2="getImg('/static/img/smarthome/智能家居2.png')"  tabIndex=4>
            Content of Tab Pane 21
        </zwTabPanel >
        <zwTabPanel tab="我的" :img="getImg('/static/img/smarthome/个人中心1.png')"  :img2="getImg('/static/img/smarthome/个人中心2.png')"   tabIndex=5>
            Content of Tab Pane 31
        </zwTabPanel >

      </zwTabs>
  </div>

</template>
<script>
  import zwLayout from '../../component/layout/zwLayout.vue';
  import zwHeader from '../../component/layout/zwHeader.vue';
  import zwContent from '../../component/layout/zwContent.vue';
  import zwSider from '../../component/layout/zwSider.vue';
  import zwFooter from '../../component/layout/zwFooter.vue';
  import zwRow from '../../component/layout/zwRow.vue';

    import zwInput from '../../component/dataentry/zwInput.vue';
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
  import zwButton from '../../component/button/zwButton.vue';
  import zwButtonGroup from '../../component/button/zwButtonGroup.vue';
  import zwDropDown from '../../component/navigation/menu/zwDropDown.vue';
  import zwMenu from '../../component/navigation/menu/zwMenu.vue';
  import zwMenuItem from '../../component/navigation/menu/zwMenuItem.vue';
  import zwDataMenuItems from '../../component/navigation/menu/zwDataMenuItems.vue';
  import zwSubMenu from '../../component/navigation/menu/zwSubMenu.vue';
  import zwMenuItemGroup from '../../component/navigation/menu/zwMenuItemGroup.vue';
 import zwAppTopBar from '../../component/navigation/zwAppTopBar.vue';
  export default {
    components: {
      zwRow,
      zwCol,
      zwIcon,
      zwBox,
      zwHeader,
      zwContent,
      zwFooter,
      zwLayout,
      zwSider,
      zwCrousel,
      zwTabs,
      zwTabPanel,
      zwChocolatePanel,
      zwAppImgList,
      zwSliderText,
      zwAppBottomBar,
      zwDropDown,
      zwMenu,
      zwMenuItem,
      zwMenuItemGroup,
      zwSubMenu,
      zwButtonGroup,
      zwDataMenuItems,
      zwButton,
      zwInput,
      zwAppTopBar,
    },
    name: "phoneMain",
    data() {

      return {
        user:{},
        isLogin:false,

        imgList2:[

                { id:1,title:"华家池(凯旋路)",tag1:["设施齐全","设施齐全","设施齐全"],tag2:["80人","888平米","已售88份"],tag3:["支付宝","有储物柜","小卖部","有空调"],score:"5.5",commentCount:"12",price:"1231起步",subtitle:"莫干山路101010号1幢401是",pic:"static/img/carousel/11.png"},
                 {id:1,title:"华家池(凯旋路)",tag1:["设施齐全","设施齐全","设施齐全"],tag2:["80人","888平米","已售88份"],tag3:["支付宝","有储物柜","小卖部","有空调"],score:"5.5",commentCount:"12",price:"1231起步",subtitle:"莫干山路101010号1幢401是",pic:"static/img/carousel/11.png"},
                 {id:1,title:"华家池(凯旋路)",tag1:["设施齐全","设施齐全","设施齐全"],tag2:["80人","888平米","已售88份"],tag3:["支付宝","有储物柜","小卖部","有空调"],score:"5.5",commentCount:"12",price:"1231起步",subtitle:"莫干山路101010号1幢401是",pic:"static/img/carousel/11.png"},
                 {id:1,title:"华家池(凯旋路)",tag1:["设施齐全","设施齐全","设施齐全"],tag2:["80人","888平米","已售88份"],tag3:["支付宝","有储物柜","小卖部","有空调"],score:"5.5",commentCount:"12",price:"1231起步",subtitle:"莫干山路101010号1幢401是",pic:"static/img/carousel/11.png"},
                 {id:1,title:"华家池(凯旋路)",tag1:["设施齐全","设施齐全","设施齐全"],tag2:["80人","888平米","已售88份"],tag3:["支付宝","有储物柜","小卖部","有空调"],score:"5.5",commentCount:"12",price:"1231起步",subtitle:"莫干山路101010号1幢401是",pic:"static/img/carousel/11.png"}

                ],
        imglist:[
                                      {titile:"1",subTitle:"2",pic:"http://www.internetke.com/jsEffects/2014052304/images/1.jpg"},
                                      {titile:"1",subTitle:"2",pic:"http://www.internetke.com/jsEffects/2014052304/images/2.jpg"},
                                      {titile:"1",subTitle:"2",pic:"http://www.internetke.com/jsEffects/2014052304/images/3.jpg"},
                                      {titile:"1",subTitle:"2",pic:"http://www.internetke.com/jsEffects/2014052304/images/4.jpg"},
                                      {titile:"1",subTitle:"2",pic:"http://www.internetke.com/jsEffects/2014052304/images/5.jpg"},

                                     ],
        goodsList: [],

        noticeList: [{
            url: "www.baidu.com",
            name: "周末快来了是时候出去玩了"
            },{
            url: "www.baidu.com",
            name: "天气预报周末晴朗无云"
            }, {
            url: "www.baidu.com",
            name: "你有新的消息等待接收"
            }, ],
        iconlist: [{
          type: "home",
          url: "www.baidu.com",
          name: "白菜123123"
        }, {
          type: "home",
          url: "www.baidu.com",
          name: "白菜"
        }, {
          type: "home",
          url: "www.baidu.com",
          name: "白菜"
        }, {
          type: "home",
          url: "www.baidu.com",
          name: "白菜"
        }, {
          type: "home",
          url: "www.baidu.com",
          name: "白菜"
        }, {
          type: "home",
          url: "www.baidu.com",
          name: "白菜"
        }, {
          type: "home",
          url: "www.baidu.com",
          name: "白菜"
        }, {
          type: "home",
          url: "www.baidu.com",
          name: "白菜"
        }, {
          type: "home",
          url: "www.baidu.com",
          name: "白菜"
        }, {
          type: "home",
          url: "www.baidu.com",
          name: "白菜"
        }, ],
        textlist: [{
          type: "home",
          url: "www.baidu.com",
          name: "台新党青年怒斥被抓:不存在大陆提供金钱的事"
        }, {
          type: "home",
          url: "www.baidu.com",
          name: "网传南昌大学一女生遭副院长性侵长达7个月"
        }, {
          type: "home",
          url: "www.baidu.com",
          name: "财政部长人民日报撰文:加快建立现代财政制度"
        }, {
          type: "home",
          url: "www.baidu.com",
          name: "广东这名厅官落马 曾因一张摆拍照闻名全国"
        }, {
          type: "home",
          url: "www.baidu.com",
          name: "温度 | 5岁男童地铁惊厥吐血 众人接力送医"
        }, {
          type: "home",
          url: "www.baidu.com",
          name: "网传吉林警察打人视频 警方发全景监控回应"
        }, {
          type: "home",
          url: "www.baidu.com",
          name: "华西村负债389亿 集团要职由吴仁宝家族成员分管"
        }, {
          type: "home",
          url: "www.baidu.com",
          name: "天津纪委遭问:黄兴国案涉多名干部 怎么肃清流毒?"
        }, {
          type: "home",
          url: "www.baidu.com",
          name: "湖北一单位3任书记塌方腐败 线索来自开房记录"
        }, {
          type: "home",
          url: "www.baidu.com",
          name: "女子把11万元钻戒扔了 8名环卫工翻13吨垃圾找到"
        }, ]
      }
    },
    mounted() {
        Ajax.getJSON("/user/info.json", null, function(result) {
                if(result.r == AJAX_SUCC && result.data) {
                  this.isLogin=true;
                  this.user=result.data;
                }
              }.Apply(this));
      /*Ajax.getJSON("/goods/list.json?curpage=1&pagesize=10", null, function(result) {
        if(result.r == AJAX_SUCC) {
          this.goodsList = result.data;
        }
      }.Apply(this));*/
    },
    computed: {

    },
    methods: {
getImg:function(img){
              return PATH+img;
          },
    }
  }
</script>
<style scoped>

</style>