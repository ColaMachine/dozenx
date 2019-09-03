<template>

  <div class="app-img-blocks">
    <div v-for="result in list" :key="result.id" class=" app-img-block ">
      <div class="app-img-block-pic">
        <a :href='"?id="+ result.id +"#goodView"'><img onerror="" :src='getPathValue( result.pic)' /></a>
      </div>
      <center class="app-li-it-content">
        


        <div class="app-li-it-content-body">
            <p class="title">{{result.title}}</p>

        </div>
         <div style=" color:red" class="sub-title">
          <p>${{result.price}}起</p>
        </div>

      </center>
    </div>

  </div>

</template>
<script type="text/javascript">
  import zwIcon from '../icon/zwIcon.vue';
  //this.src='/static/phone/img/carousel/1.png'
  export default {
    name: 'zwAppImgBlocks',
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
        getPathValue:function(value){
          return getPathValue(value);
        },
    },

    events: {

    }
  };
</script>
<style>
.app-img-block{
display:inline-block;
width:48%;
}

.app-img-blocks>div:nth-child(2n+1) {
    margin-right: 4%;
    /* border-right: 1px #e1e1e1 solid; */
}
center h5{
text-align:center;
}

.app-img-blocks  .title {
    padding: 0 0.08rem;
    height: 0.7rem;
    line-height: 0.7rem;
    color: #333;
    font-size: 0.24rem;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}
.body{
    font-size:14px;
}
.app-img-blocks .sub-title {
    height: 0.36rem;
    line-height: 0.36rem;
    margin-top: 0.08rem;
    margin-bottom: 0.2rem;
    font-size: 0.3rem;
}
.app-img-block img{
width:100%;
}
</style>