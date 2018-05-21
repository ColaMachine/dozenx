
<template>
  <div :class="classes" :style="styles"><slot></slot></div>
</template>
<script type="text/javascript">
export default {
        name: 'zwCol',
        components: {  },
        props: {
            disabled:Boolean,
            text:String,
            type:String,
            title: String,
            span:String,
            gutter:String,
            className:String,
        },
        data () {
            return {

            };
        },
        computed: {
        styles:function(){
                    console.log("gutter"+this.gutter);
                        if(this.gutter>0){
                            return "padding-left:"+gutter+",padding-right:"+gutter;
                        }
                    },
            classes(){

                return "zw-col-"+ this.span;


            }
        },
        mounted () {


        },
        methods: {

        },
    };
</script>
