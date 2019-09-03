
<template>
    <h1 class="zw-app-title">
   <slot></slot>

  </h1>
</template>
<script type="text/javascript">

import zwIcon from '../icon/zwIcon.vue';
export default {
        name: 'zwAppTitle',
         components:{zwIcon},
        props: [],
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
.zw-app-title {
padding-top:40px;
padding-bottom:20px;
font-weight:500;
padding-left:20px;
}
</style>