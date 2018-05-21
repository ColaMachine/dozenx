<template>

 <ul  class="zw-icon-list">
 <li   class=" " v-for="item in data" ><a v-on:click ="goLink(item.url)" >
    <zwIcon :type="item.type"></zwIcon>
    <span class="zw-icon-desc">
    <span class="zw-badge">{{item.name}}</span>
    </span>
 </a></li>

 </ul>

</template>
<script type="text/javascript">
import zwIcon from '../icon/zwIcon.vue';

export default {
        name: 'zwChocolatePanel',
         components:{zwIcon},
        props:
            ["data"]//"tabs"
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
            goLink:function(url){
            if(url){
                window.location=url;
            }

            }

        },


        events: {

        }
};



</script>
<style>

</style>
