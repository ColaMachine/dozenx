<template>

<div class="zw-jumbotron-wrap clearfix">
<h1 class="zw-jumbotron-title pull-left" >{{title}}</h1>
<span class="zw-jumbotron-btn pull-right"  >

     <zwDropDown trigger="click" placement="bottomRight">
         <zwButton shape="circle" slot="button"   icon="plus" ></zwButton>
         <zwMenu color="white" slot="menu">
             <zwMenuItem v-on:clickFn="clickFn" key="1">扫描添加网关</zwMenuItem>
         </zwMenu>
     </zwDropDown>

</span>
</div>







</template>
<script type="text/javascript">
import zwDropDown from '../navigation/menu/zwDropDown.vue';
import zwButton from '../button/zwButton.vue';
import zwMenu from '../navigation/menu/zwMenu.vue';
import zwMenuItem from '../navigation/menu/zwMenuItem.vue';
export default {
        name: 'zwSlidPanel',
         components:{zwButton,zwDropDown,zwMenuItem,zwMenu},
        props:
            ["title"]
        ,
        data () {
            return {


            };
        },
        computed: {

        },
        mounted () {

        },
        methods: {

            clickFn:function(){
                this.$emit("clickFn");
            }
        },


        events: {

        }
};



</script>
<style>
.zw-jumbotron-wrap{
min-height:150px;
line-height:150px;
/*background-color:#dfdf1d;*/


background:url(/weixin/static/img/smarthome/背景板.png);
background-size:100% 180px;
background-repeat:no-repeat;
}
.zw-jumbotron-title{
padding-left:15px;

color:black;
}
.zw-jumbotron-btn{
position:absolute;
right:10px;
padding-right:15px;

color:black;
font-size:24px;
}
</style>
