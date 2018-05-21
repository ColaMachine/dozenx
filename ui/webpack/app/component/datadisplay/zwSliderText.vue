<template>


<div   class="zw-slider-text" ref="wrap" >
 <ul >
 <li   class=" " v-for="item in list" ><a :href="item.url" target="_blank">

    <span class="zw-slider-text-content">
        {{item.name}}
    </span>
 </a></li>

 </ul>
</div>
</template>
<script type="text/javascript">
import zwIcon from '../icon/zwIcon.vue';

export default {
        name: 'zwSliderText',
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

          textSlider(this.$refs.wrap ,{width:640,height:50,during:3000});

        },
        methods: {


        },


        events: {

        }
};



</script>
<style>

</style>
