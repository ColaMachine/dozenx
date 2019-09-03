
<template>
  <ul class="zw-app-list">
     <li class="zw-app-list-it"  v-for="item in list">
     <span v-if="render" v-html="render(item)">

     </span>
     <a span v-if="!render"  :href="item.link"> <span>{{item.title}}</span>
     <i v-if="item.count" class="zw-app-list-red-circle">
     {{item.count}}</i><zwIcon class="arrow"    type="arrow-right"></zwIcon></a>

     </li>

  </ul>
</template>
<script type="text/javascript">

import zwIcon from '../icon/zwIcon.vue';
export default {
        name: 'zwAppList',
         components:{zwIcon},
        props: ["list","render"

        ],
        data () {
            return {

            };
        },
        computed: {

        },
        mounted () {


        },
        methods: {

        },


         events: {

          }
    };
</script>
<style>
.zw-app-list-it{
line-height: 21px; */
    box-sizing: border-box;
    -moz-box-sizing: border-box;
    -webkit-box-sizing: border-box;
    font-size: 14px;
    /* line-height: 22px; */
    padding: 8px 0 8px 12px;
    color: rgba(0, 0, 0, 0.85);
    cursor: pointer;
    position: relative;
    transition: all .3s;
    line-height: 21px;
    box-sizing: border-box;
    -moz-box-sizing: border-box;
    -webkit-box-sizing: border-box;
    border-bottom: 1px solid #d9d9d9;
}
.zw-app-list-it h1{/**标题**/
    font-size:18px;
}
.zw-app-list-it h2{/**副标题**/
    font-size:14px;
    line-height:20px;
    color:gray;
}

.zw-app-list-it a{
display:inline-block;
width:100%;
}
.zw-app-list{
background-color:white;
}
.zw-app-list .arrow{
float:right;
margin-right:15px;
}
.zw-app-list-red-circle{
margin-left:5px;
    display:inline-block;
    color:white;
    font-size:3px;

    background-color:red;
    border-radius:15px;
    width:13px;
    height:13px;
    text-align:center;
    line-height:13px;
    vertical-align:middle;

}
</style>