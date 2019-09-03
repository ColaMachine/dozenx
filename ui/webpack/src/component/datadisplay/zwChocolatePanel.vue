<template>

 <ul  class="zw-icon-list list ma0 pa0 flex flex-row flex-wrap justify-start items-start">
 <li   :class="getCls" v-for="item in data" ><a v-on:click ="goLink(item.url)" >
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
            ["data","rowNum"]//"tabs"
        ,
        data () {
            return {

            };
        },
        computed: {
            getCls(){
                var size =this.rowNum || this.data.length;
                return " dib ma0 pb3 w-grid-"+size
                +" w-grid-4-m w-grid-"+size
                +"-l w-grid-"+size
                +"-xl ";
            }
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
