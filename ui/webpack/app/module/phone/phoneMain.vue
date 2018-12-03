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
            <a href="/home/sys/auth/login.htm?pre=http%3a%2f%2falpha-i.51awifi.com%2fhome%2fstatic%2fhtml%2fvue%2fvuePhoneIndex.html%3fid%3d123%23%2fphoneMain">登录 </a>
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
 <zwCrousel style="height:150px;" height=150 :imglist="imglist"> </zwCrousel>

   <zwChocolatePanel   :rowNum=5 :data="iconlist">白菜</zwChocolatePanel>

</zwCol>

</zwRow>
<zwRow style="margin-top:0px;" >
<zwCol span=24>
    <zwTabs>

      <zwTabPanel tab="最新上线" tabIndex=1>

     最新上线

      </zwTabPanel>
      <zwTabPanel tab="加入我们" tabIndex=2>
        加入我们
      </zwTabPanel>
      <zwTabPanel tab="广告计划" tabIndex=3>
      广告计划
      </zwTabPanel>

    </zwTabs>

</zwCol>

</zwRow>
<zwRow style="margin-top:0px;" >
<zwCol span=24>
       <zwSliderText :list="noticeList"> </zwSliderText>

</zwCol>

</zwRow>

<zwRow style="margin-top:0px;" >
<zwCol span=24>
 <zwAppImgBlocks :list="goodsList"></zwAppImgBlocks>

</zwCol>

</zwRow>
<zwRow style="margin-top:0px;" >
<zwCol span=24>
 <zwAppImgList :list="goodsList"></zwAppImgList>

</zwCol>

</zwRow>

<zwRow style="margin-top:0px;" >
<zwCol span=24 style="height:200px">


</zwCol>

</zwRow>


 <zwBottomBar> </zwBottomBar>

  </div>

</template>
<script>
  import zwBottomBar from './bottomBar.vue';
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


   import zwAppImgBlocks from '../../component/datadisplay/zwAppImgBlocks.vue';

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
      zwIcon,zwBottomBar,
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
      zwAppImgList,zwAppImgBlocks,
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
        goodsList:[],
        imgList2:[


                ],
        imglist:[
                                      {titile:"1",subTitle:"2",pic:"http://www.internetke.com/jsEffects/2014052304/images/1.jpg",url:"/home/index.htm"},
                                      {titile:"1",subTitle:"2",pic:"http://www.internetke.com/jsEffects/2014052304/images/2.jpg",url:"/home/index.htm"},
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
          type: "swimmer",
          url: "www.baidu.com",
          name: "游泳馆"
        }, {
          type: "table-tennis",
          url: "www.baidu.com",
          name: "兵乓馆"
        }, {
          type: "far fa-racquet",
          url: "www.baidu.com",
          name: "羽毛球"
        }, {
          type: "basketball-ball",
          url: "www.baidu.com",
          name: "篮球"
        }, {
          type: "home",
          url: "www.baidu.com",
          name: "射箭"
        }, {
          type: "home",
          url: "www.baidu.com",
          name: "瑜伽"
        }, {
          type: "home",
          url: "www.baidu.com",
          name: "派对"
        }, {
          type: "home",
          url: "www.baidu.com",
          name: "运动会"
        }, {
          type: "home",
          url: "www.baidu.com",
          name: "会议"
        }, {
                   type: "home",
                   url: "www.baidu.com",
                   name: "其他"
                 }],
        textlist: [{
          type: "swimmer",
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
     //Ajax.getJSON(PATH+"/placesrv/place/list?pageSize=20&curPage=1", null, function(result) {
     Ajax.getJSON(PATH+"/goods/list.json?pageSize=20&curPage=1", null, function(result) {

                    if(result.r == AJAX_SUCC && result.data) {

                    var ary=new Array(result.data.length);
                   for(var i=0;i<result.data.length;i++){
                    var item={};
                    item.title = result.data[i].name;
                     item.src = result.data[i].createTime.substring(0,10);
                     item.comments = result.data[i].comments;
                    item.subtitle = result.data[i].address;
                    item.pic= result.data[i].cover;
                     item.price= result.data[i].price;
                       item.score= result.data[i].score;
                       item.tag1=[result.data[i].remark];
                       item.id=result.data[i].id;
                       ary[i]=item;
                   }
                     this.imgList2=ary;
                    }
                  }.Apply(this));

        Ajax.getJSON(PATH+"/user/info.json", null, function(result) {
                if(result.r == AJAX_SUCC && result.data) {
                  this.isLogin=true;
                  this.user=result.data;
                }
              }.Apply(this));
      Ajax.getJSON(PATH+"/goods/list.json?curPage=1&pageSize=10", null, function(result) {
        if(result.r == AJAX_SUCC) {

        for(var i=0;i<result.data.length;i++){
            result.data[i].pic= result.data[i].img;
            result.data[i].title= result.data[i].name;
        }

          this.goodsList = result.data;
        }
      }.Apply(this));
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