
<template>
  <li class="zw-menu-item"><a v-on:click="clickFn"></zwIcon><span class="zw-menu-item-text"><slot></slot></span></a>

 </li>
</template>
<script type="text/javascript">
// <hr style="float:right;width:1px;height:50px"></hr> 想通过这个来增加风格县
import zwIcon from "../../icon/zwIcon.vue"
export default {
        name: 'zwMenuItem',
        components: {zwIcon  },
        props:{

                   },
        data () {
            return {

            };
        },
        computed: {


        },
        mounted () {
        },
        watch:{
　　　　　　　　
　　　　　　},
        methods: {
            clickFn:function(){
                this.$emit("clickFn");
            }
        },
    };
</script>
