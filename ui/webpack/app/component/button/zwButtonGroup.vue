
<template>
  <div class="zw-btn-group"> <slot></slot></div>
</template>
<script type="text/javascript">
export default {
        name: 'zwButtonGroup',
        components: {  },
        props:{
               disabled:Boolean,
       },
        data () {
            return {
            };
        },
        computed: {

            size:function(){

            },


            isDisabled:function(){

                if(this.disabled==true){
                    return true;
                }

                return false;
            },
            classes:function(){
                var classStr="";

                 return classStr;
            }
        },
        mounted () {

        },
        watch:{
　　　　　　　
　　　　　　　　
　　　　},
        methods: {

        },
    };
</script>
