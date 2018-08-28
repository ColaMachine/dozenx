<template>

  <ul class="app-li pc">
    <li v-for="result in list" key={{result.id}} class="zw-box app-li-it ">
      <div class="app-li-it-pic">
        <a :href='"/static/html/editor/index.html?id=3&goodsid="+ result.id '><img onerror="" :src='"/"+ result.pic' /></a>
      </div>
      <div class="app-li-it-content">

        <div class="app-li-it-content-top-corner">
          <span>3000起</span>
        </div>

        <div class="app-li-it-content-body">
            <h1>{{result.title}}</h1>
          <h2>{{result.subtitle}}</h2>
          <h3><span v-for="item in result.tag1">{{item}} |</span></h3>
          <h4><span v-for="item in result.tag2">{{item}} |</span></h4>
          <h5><span v-for="item in result.tag3">{{item}} |</span></h5>
        </div>

        <div class="app-li-it-content-foot">

          <div class="app-li-it-text"><span className="src-net">{{result.src}}|23:12</span><span className="comment-num">评论:2</span><span className="goods-score">评分:{{result.score}}</span></div>
        </div>

        </a>
      </div>

    </li>;

  </ul>

</template>
<script type="text/javascript">
  import zwIcon from '../icon/zwIcon.vue';
  //this.src='/static/phone/img/carousel/1.png'
  export default {
    name: 'zwAppImgList',
    components: {
      zwIcon
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

    },

    events: {

    }
  };
</script>
<style>

</style>