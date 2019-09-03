<template>
<div class="app_bt_fl_nav_bar table">
<div class="select cell col-20" ><i class="fa fa-home"></i><span>首页</span></div>
<div class="cell col-20" ><i class="fa fa-female"></i><span>服饰</span></div>
<div class="cell col-20" ><i class="fa fa-tv"></i><span>电器</span></div>
<div class="cell col-20" ><i class="fa fa-star-o"></i><span>最爱</span></div>
<div class="cell col-20" ><i class="fa fa-user"></i><span>我</span></div>
</div>

</template>
<script type="text/javascript">
import zwIcon from '../icon/zwIcon.vue';

export default {
        name: 'zwAppBottomBar',
         components:{zwIcon},
        props:
            ["list"]//"tabs"
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


        },


        events: {

        }
};



</script>
<style>
.app_bt_fl_nav_bar{
    position:fixed;
    width:100%;
    bottom:0px;
    left:0px;
    border-top:1px solid red;
    min-height:37px;
    font-size:17px;
    display:table;
    text-align:center;
    vertical-align:center;
    background-color:white;
}
.app_bt_fl_nav_bar>.cell{
    display:table-cell;
    line-height:100%;
    text-align:center;
    vertical-align:center;
    padding-top:10px;
    padding-bottom:10px;
    color:gray;


}
.app_bt_fl_nav_bar .fa{
    color:#a0a042;
}
.cell.select{
    border-bottom: 3px solid #ea1a1a;
}

</style>
