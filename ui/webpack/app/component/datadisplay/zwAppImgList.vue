<template>

  <ul class="app-ul">
    <li v-for="result in list" key={{result.id}} class="zw-app-box  app-li-it ">
    <a :href='"?id="+ result.id +"#goodView"' >


    <div class=" app-card " >

    <zwLayout class="zw-layout-has-sider ">

    <zwSider class="app-li-it-pic">  <a :href='"?id="+ result.id +"#goodView"'><img onerror="" :src='getPathValue( result.pic)' /></a></zwSider>
    <zwLayout class="app-li-it-content" >
    <zwHeader  class="app-li-it-content-head">  <h1>{{result.title}}</h1></zwHeader>


    <zwContent>  <h2>{{result.subtitle}}</h2>

     <div style=" color:red" class="">
              <span><h1>${{result.price}}起</h1></span>
            </div>

                         <h3><span v-for="item in result.tag1">{{item}} |</span></h3>
                         <h4><span v-for="item in result.tag2">{{item}} |</span></h4>
                         <h5><span v-for="item in result.tag3">{{item}} |</span></h5></zwContent>


    <zwFooter  class="app-li-it-content-foot">   <div class="app-li-it-text"><span className="src-net">{{result.src}}</span><span className="comment-num">评论:{{result.comments}}</span><span className="goods-score">评分:{{result.score}}</span></div>
                    </zwFooter>
    </zwLayout>
    </zwLayout>
      <div style="display:none" class="app-li-it-pic">
        <a :href='"?id="+ result.id +"#placeView"'><img onerror="" :src='getPathValue( result.pic)' /></a>
      </div>
      <div style="display:none"  class="app-li-it-content">
       <div class="app-li-it-content-head">
         <h1>{{result.title}}</h1>

       </div>
        <div class="app-li-it-content-body">

          <h2>{{result.subtitle}}</h2>
          <h3><span v-for="item in result.tag1">{{item}} |</span></h3>
          <h4><span v-for="item in result.tag2">{{item}} |</span></h4>
          <h5><span v-for="item in result.tag3">{{item}} |</span></h5>
        </div>
        <div style=" color:red" class="">
          <span><h1>${{result.price}}起</h1></span>
        </div>
        <div class="app-li-it-content-foot">

          <div class="app-li-it-text"><span className="src-net">{{result.src}}</span><span className="comment-num">评论:{{result.comments}}</span><span className="goods-score">评分:{{result.score}}</span></div>
        </div>
      </div>
      </div>
      </a>
    </li>

  </ul>

</template>
<script type="text/javascript">
  import zwIcon from '../icon/zwIcon.vue';

  import zwLayout from '../../component/layout/zwLayout.vue';
  import zwHeader from '../../component/layout/zwHeader.vue';
  import zwContent from '../../component/layout/zwContent.vue';
  import zwSider from '../../component/layout/zwSider.vue';
  import zwFooter from '../../component/layout/zwFooter.vue';
  //this.src='/static/phone/img/carousel/1.png'
  export default {
    name: 'zwAppImgList',
    components: {
      zwIcon,zwLayout,zwHeader,zwContent,zwSider,zwFooter
    },
    props: ["list"] //"tabs"
      ,
    data() {
      return {

      };
    },
    computed: {

    },
    mounted() {
      /* Ajax.getJSON("/goods/list.json?curpage=1&pagesize=10",null,function(result){
                     if(result.r==AJAX_SUCC){
                         this.list=result.data;
                     }
                 }.Apply(this));*/

    },
    methods: {
        getPathValue:function(value){
          return getPathValue(value);
        },
    },

    events: {

    }
  };
</script>
<style>

</style>