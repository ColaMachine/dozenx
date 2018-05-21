
<template>

 <div v-on:click="clickFunction" role="tabpanel" aria-hidden="false" class="zw-tabs-tabpane zw-tabs-tabpane-active"><slot></slot></div>
</template>
<script type="text/javascript">
import zwIcon from '../icon/zwIcon.vue';

export default {
        name: 'zwTabPanel',
         components:{zwIcon},
        props:   [],//"tab","indexkey"
        data () {
            return {
                collapse:true,
            };
        },
        computed: {

        },
        mounted () {


        },
        methods: {
        clickFunction:function(){alert(123);
            this.$emit("clickFn","");

        }
           },

         events: {

          }
    };


</script>
<style>

</style>
