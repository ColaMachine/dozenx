<template>
<div >
<zwRow class="zw-bottom-bar fix-bottom" style="background-color:#fff;width:100%;display:block">
<zwCol span="6">
<a  class="v-icon-txt" ><div style="line-height:1rem;text-align:center"><zwIcon type="home"></zwIcon><span>主页</span></div></a>
</zwCol>


<zwCol   span="6">

<a  class="v-icon-txt" ><div style="line-height:1rem;text-align:center"><zwIcon type="car"></zwIcon><span>购物车</span></div></a>
</zwCol>
<zwCol span="6"  style="background-color:#e3b895"     ><a style="color:white">加入购物车</a>
</zwCol>
<zwCol style="background-color:#fe635f" span="6"><a style="color:white" >立即购买</a>
</zwCol>
</zwRow>

</div>
</template>
<script type="text/javascript">
import zwIcon from '../icon/zwIcon.vue';
import zwRow from '../../component/layout/zwRow.vue';
import zwCol from '../../component/layout/zwCol.vue';
export default {
        name: 'zwAppBottomBar',
         components:{zwIcon,zwRow,zwCol},
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
.zw-bottom-bar a{
display:inline-block;
text-align:center;
vertical-align:center;
width:100%;
height:3rem;
line-height:3rem;
font-size:1rem;
color:#333;

}
.v-icon-txt{
 text-align:center;

}

.v-icon-txt span{
 display:block;
  text-align:center;
line-height:1.5rem;
}

.v-icon-txt i,.v-icon-txt  img{
    width:1rem;
line-height:1rem;
}
/*
.zw-icon-home {
    width: 0.48rem;
    height: 0.48rem;
    top: 0.08rem;
    position: relative;
    background: url(../images/nav-icon.png) left top no-repeat;
    background-size: 0.48rem 2.4rem;
}*/
</style>
