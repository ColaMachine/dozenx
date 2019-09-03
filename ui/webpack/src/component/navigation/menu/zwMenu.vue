
<template>
  <div :class="getClasses"><div class="nav-inner clearfix"><slot></slot></div></div>
</template>
<script type="text/javascript">
export default {
        name: 'zwMenu',
        components: {  },
        props:["mode","color"],
        data () {
            return {
            };
        },
        computed: {
            getClasses:function(){
                var classes = "zw-menu fnt-1  color-black  ";
                if(this.mode=="horizontal"){
                    classes+= " zw-menu-horizontal ";
                }
                 if(this.color=="white"){
                    classes+= " zw-menu-white";
                }
                return classes;
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
