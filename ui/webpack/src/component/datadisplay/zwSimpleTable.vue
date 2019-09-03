<template>
 <ul class="zw-app-list">

     <li class="zw-app-list-it dot_under_line"  v-for="item in list">
     <span v-if="render" v-html="render(item)"  >

     </span>
     <a  v-if="!render"  :href="item.link"   v-for="colItem in param" :style=getWidth(colItem.width)>

         <span v-if="colItem.render" v-html="colItem.render(item)"  > </span>
              <span v-else>
            {{item[colItem.name]}}
        </span>
        <i v-if="item.count" class="zw-app-list-red-circle"/>
     {{item.count}}</i>
     </a>

     </li>

  </ul>

</template>
<script type="text/javascript">
import zwIcon from '../icon/zwIcon.vue';

export default {
        name: 'zwSimpleTable',
         components:{zwIcon},
        props:
            ["list","param","render"]//"tabs"
        ,
        data () {
            return {

            };
        },
        computed: {
       // getWidth:function(item){
         //   return item.width;
        //}
        },
        mounted () {
            this.totalWidth=0;
            for(var i=0;i<this.list.length;i++){
                this.totalWidth+=this.list[i].width;
            }

        },
        methods: {

        getWidth:function(num){
            //console.log("width:"+colItem.width+"%");
            return "display:inline-block;width:"+parseInt(num/this.totalWidth*100)+"%";
        }
        },


         events: {

          }
    };



</script>
<style>

</style>
