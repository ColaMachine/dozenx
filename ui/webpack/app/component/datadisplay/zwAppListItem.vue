
<template>

     <li class="zw-app-list-it" > <span><slot></slot></span><zwIcon class="arrow" type="arrow-right"></zwIcon></li>



</template>
<script type="text/javascript">

import zwIcon from '../icon/zwIcon.vue';
export default {
        name: 'zwAppListItem',

         components:{zwIcon},
        props: ["list"

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
    padding: 8px 0 8px 32px;
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
.zw-app-list .arrow{
float:right;
margin-right:15px;
}
</style>