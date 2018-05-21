<template>


 <div  ref="wrap" class="slider">
 <ul id="sa">
 <li><a href="http://www.internetke.com" target="_blank"><img src="http://www.internetke.com/jsEffects/2014052304/images/1.jpg" alt="科e互联网站建设团队"/></a></li>
 <li><a href="http://www.internetke.com" target="_blank"><img src="http://www.internetke.com/jsEffects/2014052304/images/2.jpg" alt="网页特效集锦"/></a></li>
 <li><a href="http://www.internetke.com" target="_blank"><img src="http://www.internetke.com/jsEffects/2014052304/images/3.jpg" alt="JS代码素材库"/></a></li>
 <li><a href="http://www.internetke.com" target="_blank"><img src="http://www.internetke.com/jsEffects/2014052304/images/4.jpg" alt="用心建站用心服务"/></a></li>
 <li><a href="http://www.internetke.com" target="_blank"><img src="http://www.internetke.com/jsEffects/2014052304/images/5.jpg" alt="学会用才能学会写"/></a></li>
 </ul>
 </div>
</template>
<script type="text/javascript">
import zwIcon from '../icon/zwIcon.vue';

export default {
        name: 'zwCrousel',
         components:{zwIcon},
        props:
            ["imglist"]//"tabs"
        ,
        data () {
            return {

            };
        },
        computed: {

        },
        mounted () {
            yxMobileSlider(this.$refs.wrap ,{width:640,height:320,during:3000});

        },
        methods: {


        },


        events: {

        }
};



</script>
<style>

</style>
