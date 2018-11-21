
<template>
  <i :class="classes"></i>
</template>
<script type="text/javascript">
export default {
        name: 'zwIcon',
        components: {  },
        props: {
            disabled:Boolean,
            text:String,
            type:String,
            title: String,
           // className:String,
        },
        data () {
            return {

            };
        },
        computed: {
            classes(){
                if(this.type=="windows"){
                    return "zw-icon zw-icon-window fa fa-th-large";
                }
                if(this.type=="arrow-right"){
                 return "zw-icon  zw-icon-arrow  fa fa-angle-right";
                }
                if(this.type=="arrow-down"){
                    return "zw-icon  zw-icon-arrow fa fa-angle-down";
                }
                 if(this.type=="home"){
                    return "zw-icon  zw-icon-arrow fa fa-home";
                }
                  if(this.type=="history"){
                    return "zw-icon  zw-icon-arrow fa fa-history";
                }
                 if(this.type=="plus"){
                    return "zw-icon  fa fa-plus";
                }
                  if(this.type=="shopping-cart"){
                                    return "zw-icon  zw-icon-arrow fa fa-shopping-cart ";
                                }
                                 if(this.type=="suitcase"){
                                                                    return "zw-icon  zw-icon-arrow fa fa-suitcase ";
                                                                }
                if(this.type=="user"){
                    return "zw-icon  zw-icon-user fa fa-user";
                }
                if(this.type=="lock"){
                    return "zw-icon  zw-icon-user fa fa-lock";
                }
                 if(this.type=="close"){
                    return "zw-icon  zw-icon-user fa  fa-times";
                }

                 if(this.type=="swimmer"){
                        return "zw-icon  zw-icon-user fa  fa-swimmer";
                    }
                 if(this.type=="toggle-off"){
                    return "zw-icon fa fa-toggle-off";
                }
              if(this.type=="refresh"){
                    return "zw-icon fa fa-refresh";
                }  if(this.type=="search"){
                                     return "zw-icon fa fa-search";
                                 }

                   if(this.type=="toggle-on"){
                                    return "zw-icon fa fa-toggle-on";
                                }
                 if(this.type=="sign-out"){
                                    return "zw-icon  zw-icon-user fa  fa-sign-out";
                                }
                if(this.type && this.type.indexOf("fa")!=-1){
                    return this.type;
                }
                return "zw-icon   fas fa-"+this.type;
            }
        },
        mounted () {


        },
        methods: {

        },
    };
</script>
