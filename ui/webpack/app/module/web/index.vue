<template>

  <zwLayout :style="getWinWidth">

    <zwHeader>

      <ShopNav></ShopNav>
    </zwHeader>
    <zwLayout class="zw-layout-has-sider ">

      <zwContent :style="getWinWidth">
<!--
      <div id="router">
              <router-view></router-view>
            </div>-->
        <div >
          <zwRow style="margin:12px;">

            <zwCol style="min-width:300px" class="zw-col-sm-24 pull-left" span=15>

              <zwPanel :canFold=false state="open" style="">
                <span slot="title" name="title">发布消息</span>
                <p slot="body" name="body">
                  <zwPcImgList2 :list="imgList"></zwPcImgList2>

                </p>

              </zwPanel>

            </zwCol>

            <zwCol style="min-width:300px" class="zw-col-sm-10 pull-left" span=6>
              <zwPanel :canFold=false state="open" style="">
                <span slot="title" name="title">最新消息</span>
                <p slot="body" name="body">
                  <zwSimpleTable :list="list" :param="tableParam"> </zwSimpleTable>

                </p>

              </zwPanel>

              <zwPanel :canFold=false state="open" style="">
                <span slot="title" name="title">相册</span>
                <p slot="body" name="body">

                  <zwCrousel :imglist="imglist" width="300" height=150> </zwCrousel>

                </p>

              </zwPanel>

              <zwPanel :canFold=false state="open" style="">
                <span slot="title" name="title">最新消息</span>
                <p slot="body" name="body">

                  <blogInput @submitCallBack="refreshBlogView"></blogInput>
                </p>

              </zwPanel>
              <zwPanel :canFold=false state="open" style="">
                <span slot="title" name="title">最新消息</span>
                <p slot="body" name="body">

                  <blogViewList ref="blogViewList"></blogViewList>
                </p>

              </zwPanel>
            </zwCol>
          </zwRow>
          <zwRow>
            <zwCol class="pull-left" span=6>
            </zwCol>
            <zwCol class="pull-left" span=12>

            </zwCol>
            <zwCol class="pull-left" span=6>
            </zwCol>
          </zwRow>
          <!-- 路由出口 -->
          <!-- 路由匹配到的组件将渲染在这里 -->

        </div>
      </zwContent>
    </zwLayout>

    <zwFooter>

    </zwFooter>

  </zwLayout>
</template>
<script>
  import zwRow from '../../component/layout/zwRow.vue';
  import zwCol from '../../component/layout/zwCol.vue';
  import zwIcon from '../../component/icon/zwIcon.vue';
  import zwBox from '../../component/layout/zwBox.vue';
  import zwButton from '../../component/button/zwButton.vue';
  import zwLayout from '../../component/layout/zwLayout.vue';
  import zwHeader from '../../component/layout/zwHeader.vue';
  import zwContent from '../../component/layout/zwContent.vue';
  import zwSider from '../../component/layout/zwSider.vue';
  import zwFooter from '../../component/layout/zwFooter.vue';
  import zwCollapse from '../../component/datadisplay/zwCollapse.vue';
  import zwPanel from '../../component/datadisplay/zwPanel.vue';
  import apiPath from '../../component/datadisplay/apiPath.vue';
 // import zwMenu from '../../component/navigation/menu/zwMenu.vue';
 // import zwMenuItem from '../../component/navigation/menu/zwMenuItem.vue';
//  import zwSubMenu from '../../component/navigation/menu/zwSubMenu.vue';
 // import zwMenuItemGroup from '../../component/navigation/menu/zwMenuItemGroup.vue';
 // import zwDropDown from '../../component/navigation/menu/zwDropDown.vue';
//  import zwForm from '../../component/dataentry/zwForm.vue';
//  import zwFormItem from '../../component/dataentry/zwFormItem.vue';
//  import zwInput from '../../component/dataentry/zwInput.vue';

  import zwSimpleTable from '../../component/datadisplay/zwSimpleTable.vue';
  import zwTable from '../../component/datadisplay/zwTable.vue';

  import zwCrousel from '../../component/datadisplay/zwCrousel.vue';
  import blogInput from '../../module/example/dataentry/blogInput.vue';
  import blogViewList from '../../component/datadisplay/blogViewList2.vue';


  import zwPcImgList2 from '../../component/datadisplay/zwPcImgList2.vue';
 import ShopNav from '../../module/shop/ShopNav.vue';
  export default {
    components: {
      zwRow,
      zwCol,
      zwIcon,
      zwBox,ShopNav,
      zwHeader,
      zwContent,
      zwFooter,
      zwLayout,
      zwSider,
      zwCollapse,
      zwPanel,
      apiPath,
      zwButton,
    /*  zwMenu,
      zwMenuItem,
      zwMenuItemGroup,
      zwSubMenu,
      zwDropDown,
      zwInput,
      zwFormItem,
      zwForm,*/
      zwTable,
      zwSimpleTable,
      zwCrousel,
      blogInput,
      blogViewList,
      zwPcImgList2
    },
    data() {
      return {
        PATH: PATH,
        msg: 'Hello World!',
        isLogin: false,
        loginName: "",
        user: {},
        imgList: [],
        list: [{
          titile: "1",
          subTitle: "2",
          pic: "/static/img/smarthome/烟感1.png"
        }, {
          titile: "1",
          subTitle: "2",
          pic: "/static/img/smarthome/烟感1.png"
        }, {
          titile: "1",
          subTitle: "2",
          pic: "/static/img/smarthome/烟感1.png"
        }, {
          titile: "1",
          subTitle: "2",
          pic: "/static/img/smarthome/烟感1.png"
        }, {
          titile: "1",
          subTitle: "2",
          pic: "/static/img/smarthome/烟感1.png"
        }, {
          titile: "1",
          subTitle: "2",
          pic: "/static/img/smarthome/烟感1.png"
        }, {
          titile: "1",
          subTitle: "2",
          pic: "/static/img/smarthome/烟感1.png"
        }, ],

        imglist: [{
            titile: "1",
            subTitle: "2",
            pic: "http://www.internetke.com/jsEffects/2014052304/images/1.jpg"
          }, {
            titile: "1",
            subTitle: "2",
            pic: "http://www.internetke.com/jsEffects/2014052304/images/2.jpg"
          }, {
            titile: "1",
            subTitle: "2",
            pic: "http://www.internetke.com/jsEffects/2014052304/images/3.jpg"
          }, {
            titile: "1",
            subTitle: "2",
            pic: "http://www.internetke.com/jsEffects/2014052304/images/4.jpg"
          }, {
            titile: "1",
            subTitle: "2",
            pic: "http://www.internetke.com/jsEffects/2014052304/images/5.jpg"
          },

        ],
        tableParam: [{
          name: 'titile',
          width: 10,
        }, {
          name: 'subTitle',
          width: 10,
        }, {
          name: 'pic',
          width: 80,
        }, ],
      }
    },
    methods: {
      goLoginOut: function() {
        window.location = PATH + "/sys/auth/logout.htm";
      },
      goLogin: function() {
        window.location = PATH + "/sys/auth/login.htm";
      },
      refreshBlogView: function() {
        this.$refs.blogViewList.refresh();
      }
    },
    mounted: function() {
      this.imgList = [

        {
          id: 1,
          title: "华家池(凯旋路)",
          tag1: ["设施齐全", "设施齐全", "设施齐全"],
          tag2: ["80人", "888平米", "已售88份"],
          tag3: ["支付宝", "有储物柜", "小卖部", "有空调"],
          score: "5.5",
          commentCount: "12",
          price: "1231起步",
          subtitle: "莫干山路101010号1幢401是",
          pic: "static/img/carousel/11.png"
        }, {
          id: 1,
          title: "华家池(凯旋路)",
          tag1: ["设施齐全", "设施齐全", "设施齐全"],
          tag2: ["80人", "888平米", "已售88份"],
          tag3: ["支付宝", "有储物柜", "小卖部", "有空调"],
          score: "5.5",
          commentCount: "12",
          price: "1231起步",
          subtitle: "莫干山路101010号1幢401是",
          pic: "static/img/carousel/11.png"
        }, {
          id: 1,
          title: "华家池(凯旋路)",
          tag1: ["设施齐全", "设施齐全", "设施齐全"],
          tag2: ["80人", "888平米", "已售88份"],
          tag3: ["支付宝", "有储物柜", "小卖部", "有空调"],
          score: "5.5",
          commentCount: "12",
          price: "1231起步",
          subtitle: "莫干山路101010号1幢401是",
          pic: "static/img/carousel/11.png"
        }, {
          id: 1,
          title: "华家池(凯旋路)",
          tag1: ["设施齐全", "设施齐全", "设施齐全"],
          tag2: ["80人", "888平米", "已售88份"],
          tag3: ["支付宝", "有储物柜", "小卖部", "有空调"],
          score: "5.5",
          commentCount: "12",
          price: "1231起步",
          subtitle: "莫干山路101010号1幢401是",
          pic: "static/img/carousel/11.png"
        }, {
          id: 1,
          title: "华家池(凯旋路)",
          tag1: ["设施齐全", "设施齐全", "设施齐全"],
          tag2: ["80人", "888平米", "已售88份"],
          tag3: ["支付宝", "有储物柜", "小卖部", "有空调"],
          score: "5.5",
          commentCount: "12",
          price: "1231起步",
          subtitle: "莫干山路101010号1幢401是",
          pic: "static/img/carousel/11.png"
        }

      ];
      Ajax.getJSON(PATH + "/user/info.json", null, function(result) {
        if(result.r == AJAX_SUCC && result.data) {
          this.isLogin = true;
          this.user = result.data;
        }
      }.Apply(this));


       Ajax.getJSON(PATH + "/goods/list.json?curPage=1&pageSize=10", null, function(result) {
              if(result.r == AJAX_SUCC && result.data) {

                for(var key in result.data){

                    result.data[key].url=PATH+"/static/html/vue/PcGoodView.html?id="+result.data[key].id;

                    result.data[key].pic=result.data[key].img;
                }
                this.imgList = result.data;
                                //var result =new Array();

              }
            }.Apply(this));


      //this.$refs.blogViewList.width=getWindowWidth()+"px";
      // $.ajax(PATH + "/userinfo", function(data) {
      //    })
      //判断是否已经登录
    },
    computed: {
      getWinWidth: function() {
        return "width:" + getWinWidth() + "px";
      }
    }
  }
</script>
<style scoped>
  #example {
    background: red;
    height: 100vh;
  }
  body{
      background-color: #f5f5f5;
  }
</style>