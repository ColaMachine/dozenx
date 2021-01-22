<template>
  <div>

<ShopNav></ShopNav>

<zwRow  style="margin-top:20px;">

<zwCol class="pull-right" span=24>

    <div class="content-inner">
        <section class="content wrap" id="feed-wrap">
            <article class="wrap-main">
<zwArticle :data="data"></zwArticle>
<div class="aside-right" id="feed-side">
    <sideStuff ></sideStuff>
</div>
            </article>
        </section>
    </div>
</zwCol>

</zwRow>
 </div>

  </div>

</template>
<script>

  import zwRow from '../../component/layout/zwRow.vue';
  import ShopNav from '../shop/ShopNav.vue'
  import zwInput from '../../component/dataentry/zwInput.vue';
  import zwCol from '../../component/layout/zwCol.vue';

  import zwBase from '../../component/zwBase.vue';

  import zwAppBuyBottomBar from '../../component/datadisplay/zwAppBuyBottomBar.vue';


   import zwArticle from '../../component/datadisplay/zwArticle.vue'
 import sideStuff from '../../component/datadisplay/sideStuff.vue'

  export default {
  extends:zwBase,

    components: {
    zwBase,zwArticle,ShopNav,
      zwRow,
      zwCol,sideStuff

    },
    name: "PcArticlePage",
    data() {

      return {
      id:getQueryString("id"),
        data:{},


       }



    },
    created(){
 Ajax.getJSON(PATH+"/article/view.json?id="+this.id, null, function(result) {
                               if(result.r == AJAX_SUCC && result.data) {

                                   this.data =result.data.bean;

                                   var ary =[];


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

.aside-right {
float: right;
    width: 328px;
    margin-left: 10px;
    background-color: #f5f5f5;
}

.content-inner {
    width: 1200px;
    margin: 0 auto;
}

#feed-wrap {
    position: relative;
    margin-bottom: 30px;
}
.content {
    width: 1080px;
    margin: 0 auto;
}


</style>