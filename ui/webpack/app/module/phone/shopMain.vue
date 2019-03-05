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
 <zwAppImgList :list="goodsList"></zwAppImgList>

</zwCol>

</zwRow>

<zwRow style="margin-top:0px;" >
<zwCol span=24 style="height:200px">


</zwCol>

</zwRow>

  </div>

</template>
<script>

  import zwRow from '../../component/layout/zwRow.vue';

    import zwInput from '../../component/dataentry/zwInput.vue';
  import zwCol from '../../component/layout/zwCol.vue';
  import zwIcon from '../../component/icon/zwIcon.vue';

  import zwAppImgList from '../../component/datadisplay/zwAppImgList.vue';
  import zwButton from '../../component/button/zwButton.vue';
  import zwButtonGroup from '../../component/button/zwButtonGroup.vue';
  import zwDropDown from '../../component/navigation/menu/zwDropDown.vue';
  import zwMenu from '../../component/navigation/menu/zwMenu.vue';
  import zwMenuItem from '../../component/navigation/menu/zwMenuItem.vue';
  import zwDataMenuItems from '../../component/navigation/menu/zwDataMenuItems.vue';
  import zwSubMenu from '../../component/navigation/menu/zwSubMenu.vue';
  import zwMenuItemGroup from '../../component/navigation/menu/zwMenuItemGroup.vue';
  export default {
    components: {
      zwRow,
      zwCol,
      zwIcon,
      zwAppImgList,

       zwMenu,zwDropDown,
            zwMenuItem,
            zwMenuItemGroup,
            zwSubMenu,
            zwButtonGroup,
            zwDataMenuItems,
            zwButton,
    },
    name: "shopMain",
    data() {

      return {
        user:{},
        isLogin:false,
        goodsList: [],


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