<template>
  <div>

<zwRow >

<zwCol class="pull-right" span=24>
<zwAppTopBar >
    <a slot="left" :href="getPathValue('/static/html/vue/vuePhoneIndex.html#/phoneMain')">
  <img  class=""  :src="getPathValue('/static/img/header/back.png')">
  </a>
<span slot="middle"> 浙大华家池</span>

           <a slot="right" href="">
<img  class="" :src="getPathValue('/static/img/header/information.png')">
</a>
</zwAppTopBar>

</zwCol>

</zwRow>
    <zwRow>
      <zwCol class="pull-right" span=24>
            <zwJumbotron style="margin-top:40px" title="" img="https://y.zdmimg.com/201801/18/5a608117f29b1794.jpg_d480.jpg"></zwJumbotron>
      </zwCol>

    </zwRow>

    <zwRow>

          <zwCol class="pull-right" span=24>
            <div style="text-align:center">

         <!--    <h1> {{data.name}}</h1>
            <h2> {{data.address}}</h2>
             <h3>  {{data.tag1}}</h3>

               <h3> 营业</h3> -->

                <zwForm>

                                 <zwFormItem label="店铺名称">
                                   {{data.name}}
                                 </zwFormItem>
                                 <zwFormItem label="店铺介绍">
                                {{data.remark}}

                                 </zwFormItem>
                                 <zwFormItem label="店铺地址">
                                   {{data.address}}
                                 </zwFormItem>
                                 <zwFormItem label="联系电话">
                                  {{data.telno}}
                                 </zwFormItem>
                                 <zwFormItem label="营业时间">
                                  {{data.tag1}}
                                 </zwFormItem>

                               </zwForm>
</div>
          </zwCol>

        </zwRow>
 <zwRow>

          <zwCol class="pull-right" span=24>
              <zwPanel :canFold=false state="open" style="">
                                  <span slot="title" name="title">店铺环境</span>
                                   <p  slot="body" name="body">

                                        <zwAlbum :images="images" ></zwAlbum>

                                    </p>

                                </zwPanel>
             </zwCol>

                  </zwRow>

     <zwRow>
<zwCol  style="min-width:300px" class="zw-col-sm-24 pull-left" span=12>

 <zwPanel :canFold=false state="open" style="">
                                                   <span slot="title" name="title">发布评论</span>
                                                    <p  slot="body" name="body">

                                                         <blogInput @submitCallBack="refreshBlogView"></blogInput>
                                                     </p>

                                                 </zwPanel>

<zwPanel :canFold=false state="open" style="">
<span slot="title" name="title">最新评论</span>
<p  slot="body" name="body">

<blogViewList   ref="blogViewList" ></blogViewList>
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

 import zwJumbotron from '../../component/datadisplay/zwJumbotron.vue';

  import zwForm from '../../component/dataentry/zwForm.vue';
  import zwFormItem from '../../component/dataentry/zwFormItem.vue';

  import blogInput from '../../module/example/dataentry/blogInput.vue';
  import blogViewList from '../../component/datadisplay/blogViewList2.vue';
  import zwBase from '../../component/zwBase.vue';
 import zwPanel from '../../component/datadisplay/zwPanel.vue';
  import zwAlbum from '../../component/datadisplay/zwAlbum.vue';
  export default {
  extends:zwBase,

    components: {
    zwBase,zwPanel,
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
      zwJumbotron,
      zwForm,zwAlbum,
      zwFormItem,blogInput,blogViewList
    },
    name: "phoneMain",
    data() {

      return {
      images:[],
        user:{},
       data:{},


      }
    },
    mounted() {
     Ajax.getJSON(PATH+"/placesrv/place/view?id="+getQueryString("id"), null, function(result) {
                    if(result.r == AJAX_SUCC && result.data) {

                        this.data =result.data;
                    }
                  }.Apply(this));

       Ajax.getJSON(PATH+"/pubimage/list?pid="+getQueryString("id"), {curPage:1,pageSize:10}, function(result) {
                          if(result.r == AJAX_SUCC && result.data) {
                                var ary = result.data;
                                var fianlAry = [];
                                for(var i=0;i<ary.length;i++){
                                    var jso = {};
                                    jso.id=ary[i].id;
                                    jso.url=ary[i].relPath+"/"+ary[i].name;
                                    fianlAry.push(jso);
                                }
                              this.images =fianlAry;
                          }
                        }.Apply(this));
    },
    computed: {

    },
    methods: {
        refreshBlogView:function(){
           // this.$refs.blogViewList.refresh();
        }
    }
  }
</script>
<style scoped>

</style>