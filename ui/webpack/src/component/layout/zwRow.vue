
<template>
  <div ref="slot" class="clearfix zw-row">
      <slot  :gutter=6></slot>
  </div>
</template>
<script type="text/javascript">
export default {
        name: 'zwRow',
        components: {  },
        props: {
            disabled:Boolean,
            text:String,
            type:String,
            title: String,
            className:String,
            gutter:String
        },
        data () {
            return {

            };
        },
        computed: {
            styles:function(){
                if(this.gutter>0){
                    return "padding-left:"+gutter+",padding-right:"+gutter;
                }
            }
        },
        mounted () {
           // alert();
            if(this.gutter>0){
               for(var i=0;i<this.$refs.slot.childNodes.length;i++){
                    this.$refs.slot.childNodes[i].style={};
                    if(this.$refs.slot.childNodes[i].style){
                        this.$refs.slot.childNodes[i].style.paddingLeft = 0.5*this.gutter;
                        this.$refs.slot.childNodes[i].style.paddingRight = 0.5*this.gutter;
                    }

               }
            }
        },
        methods: {

        },
    };
</script>
