<template>
  <div>
<ShopNav></ShopNav>
<zwRow  style="margin-top:20px;">

<zwCol class="pull-right" span=24>
<zwGoodArtical :data="goodData"></zwGoodArtical>
</zwCol>

</zwRow>
 </div>

  </div>

</template>
<script>

  import zwRow from '../../component/layout/zwRow.vue';
   import zwGoodArtical from '../../component/datadisplay/zwGoodArtical.vue';



    import ShopNav from './ShopNav.vue'

  import zwCol from '../../component/layout/zwCol.vue';
  import zwIcon from '../../component/icon/zwIcon.vue';

  export default {
//  extends:zwBase,

    components: {
    zwGoodArtical,ShopNav,
      zwRow,
      zwCol,

    },
    name: "PcGoodPage",
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
                                   ary.push({url:'',pic:window.getPathValue(this.goodData.img)});
                                    ary.push({url:'',pic:window.getPathValue(this.goodData.img1)});
                                     ary.push({url:'',pic:window.getPathValue(this.goodData.img2)});
                                      ary.push({url:'',pic:window.getPathValue(this.goodData.img3)});
                                        console.log("imgList",this.imgList)
                                      this.imgList= ary;




        //currLink =location.href;
        //title = this.goodData.name;

        //imgUrl = "http://www.dapark.top/home/"+this.goodData.img;
        //alert(document.domain);
        //desc = this.goodData.name;

/*
       Ajax.getJSON(PATH+"/weixin/signatrue",{url:window.location.href},function(sinatrueResult){
          var sinatrueResultData = sinatrueResult.data;
           initWx(sinatrueResultData);


       });*/

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