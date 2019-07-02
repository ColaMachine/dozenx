<template>
  <div>
<ShopNav></ShopNav>
<zwRow >

<zwCol class="pull-right" span=24>
<zwGoodArtical :data="goodData"></zwGoodArtical>
</zwCol>

</zwRow>
 </div>

  </div>

</template>
<script>

  import zwRow from '../../component/layout/zwRow.vue';
import ShopNav from './ShopNav.vue'
    import zwInput from '../../component/dataentry/zwInput.vue';
  import zwCol from '../../component/layout/zwCol.vue';
  import zwIcon from '../../component/icon/zwIcon.vue';
  import zwBox from '../../component/layout/zwBox.vue';

  import zwCrousel from '../../component/datadisplay/zwCrousel.vue';


  import zwAppBottomBar from '../../component/datadisplay/zwAppBottomBar.vue';
  import zwButton from '../../component/button/zwButton.vue';
  import zwButtonGroup from '../../component/button/zwButtonGroup.vue';

 import zwAppTopBar from '../../component/navigation/zwAppTopBar.vue';

 import zwJumbotron from '../../component/datadisplay/zwJumbotron.vue';

  import zwForm from '../../component/dataentry/zwForm.vue';
  import zwFormItem from '../../component/dataentry/zwFormItem.vue';

  import Comments from '../../component/datadisplay/Comments.vue';
  import zwBase from '../../component/zwBase.vue';
 import zwPanel from '../../component/datadisplay/zwPanel.vue';
  import zwAlbum from '../../component/datadisplay/zwAlbum.vue';

  import zwAppBuyBottomBar from '../../component/datadisplay/zwAppBuyBottomBar.vue';


   import zwGoodArtical from '../../component/datadisplay/zwGoodArtical.vue'

  //import {PATH,getQueryString,Ajax} from "../../../../src/main/webapp/static/js/zwcommon"

  export default {
  extends:zwBase,

    components: {
    zwBase,zwPanel,zwGoodArtical,ShopNav,
      zwRow,
      zwCol,
      zwIcon,
      zwBox,

      zwCrousel,

      zwAppBottomBar,

      zwButtonGroup,
      zwButton,
      zwInput,
      zwAppTopBar,
      zwJumbotron,
      zwForm,zwAlbum,Comments,
      zwFormItem,zwAppBuyBottomBar
    },
    name: "phoneMain",
    data() {

      return {
      id:getQueryString("id"),
        goodData:{},
      images:[],

       }



    },
    created(){
 Ajax.getJSON(PATH+"/goods/view.json?id="+this.id, null, function(result) {
                               if(result.r == AJAX_SUCC && result.data) {

                                   this.goodData =result.data;

                                   var ary =[];
                                   ary.push({url:'',pic:window.getPathValue(this.data.img)});
                                    ary.push({url:'',pic:window.getPathValue(this.data.img1)});
                                     ary.push({url:'',pic:window.getPathValue(this.data.img2)});
                                      ary.push({url:'',pic:window.getPathValue(this.data.img3)});
                                        console.log("imgList",this.imgList)
                                      this.imgList= ary;




        currLink =location.href;
        title = this.data.name;

        imgUrl = "http://www.dapark.top/home/"+this.data.img;
        //alert(document.domain);
        desc = this.data.name;


       Ajax.getJSON(PATH+"/weixin/signatrue",{url:window.location.href},function(sinatrueResult){
          var sinatrueResultData = sinatrueResult.data;
           initWx(sinatrueResultData);


       });

                                    //  console.log("---------------------------")
                                   //   console.log(ary)
                                   //    console.log("wx",wx);
                                     //  alert("wx onMenuShareTimeline1111111"+this.data.name);

                               }
                             }.Apply(this));
    },
    mounted() {



    },
    computed: {

    },
    methods: {


    }
  }
</script>
<style scoped>


</style>