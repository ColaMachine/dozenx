
<template>
  <div ref="main"  v-on:mouseenter="showMenu" v-on:mouseleave="hideMenu"  v-on:click="toggleMenu"  class="zw-dropdown" >
  <slot name="button" ></slot>
  <div ref="overlay" style="display:none" class="zw-dropdown-overlay">
        <slot  name="menu"></slot>
  </div>
  </div>
</template>
<script type="text/javascript">
export default {
        name: 'zwDropDown',
        components: {  },
        props:["trigger","placement"],
        data () {
            return {
               menuShow:false,
            };
        },
        computed: {


        },
        mounted () {
        },
        watch:{
　　　　　　　
　　　　　　　　
　　　　　　},
        methods: {

            showMenu:function(){console.log("enter ");

            if(this.trigger=="click"){
                return;
            }
            this.$refs.overlay.style.display="block";
            //console.log("this.placement0 "+this.placement);
                 var mainPosition = getRelativeInfo( this.$refs.main);
                 var overLayPosition = getRelativeInfo( this.$refs.overlay);

                   console.log("offsetHeight"+this.$refs.overlay.offsetHeight);
                                           console.log("overLayPosition.height"+overLayPosition.height);

                if(this.placement){
                    if(this.placement=="bottomLeft"){
                        this.$refs.overlay.style.top=mainPosition.bottom+"px";
                        this.$refs.overlay.style.left=mainPosition.left+"px";
                    }else if(this.placement=="bottomRight"){
                        console.log("bottomRight");
                      this.$refs.overlay.style.top=mainPosition.bottom+"px";
                       console.log("overLayPosition.width"+overLayPosition.width);
                         console.log("mainPosition.width"+mainPosition.width);
                      this.$refs.overlay.style.left=mainPosition.left-(overLayPosition.width-mainPosition.width)+"px";
                  }else if(this.placement=="topLeft"){
                        //console.log("topLeft");
                        this.$refs.overlay.style.top=mainPosition.top-overLayPosition.height+"px";
                        //console.log("overLayPosition.width"+overLayPosition.width);
                       // console.log("mainPosition.width"+mainPosition.width);
                        this.$refs.overlay.style.left=mainPosition.left+"px";
                 }else if(this.placement=="topRight"){

                          this.$refs.overlay.style.top=mainPosition.top-overLayPosition.height+"px";
                          //console.log("overLayPosition.width"+overLayPosition.width);
                         // console.log("mainPosition.width"+mainPosition.width);
                          this.$refs.overlay.style.left=mainPosition.left-(overLayPosition.width-mainPosition.width)+"px";
                   }
                }else{
                //得到组件的top
                    this.$refs.overlay.style.top=mainPosition.bottom+"px";
                    this.$refs.overlay.style.left=mainPosition.left+"px";


                }

                   console.log("offsetHeight"+this.$refs.overlay.offsetHeight);
                //this.menuShow=true;
            },
            hideMenu:function(){console.log("leave ");
                if(this.trigger=="click"){
                    return;
                }
                //this.menuShow=false;
                 this.$refs.overlay.style.display="none";
            },
             toggleMenu:function(){console.log("toggle "+this.menuShow);

              if(this.trigger=="click"){
                 //this.menuShow=!this.menuShow;
                console.log(this.$refs.overlay.style.display);
                 if( this.$refs.overlay.style.display=="block"){ console.log("set none");

                    this.$refs.overlay.style.display="none";
                 }else
                 if( this.$refs.overlay.style.display=="none"){console.log("set block");
                     this.$refs.overlay.style.display="block";



               var mainPosition = getRelativeInfo( this.$refs.main);
                              var overLayPosition = getRelativeInfo( this.$refs.overlay);

                            console.log("mainPosition.top"+mainPosition.top);
                            console.log("mainPosition.height"+mainPosition.height);

                             if(this.placement){
                                 if(this.placement=="bottomLeft"){
                                     this.$refs.overlay.style.top=mainPosition.bottom+"px";
                                     this.$refs.overlay.style.left=mainPosition.left+"px";
                                 }else if(this.placement=="bottomRight"){
                                     console.log("bottomRight");
                                   this.$refs.overlay.style.top=mainPosition.bottom+"px";
                                    console.log("overLayPosition.width"+overLayPosition.width);
                                      console.log("mainPosition.width"+mainPosition.width);
                                   this.$refs.overlay.style.left=mainPosition.left-(overLayPosition.width-mainPosition.width)+"px";
                               }else if(this.placement=="topLeft"){
                                     //console.log("topLeft");
                                     this.$refs.overlay.style.top=mainPosition.top-overLayPosition.height+"px";
                                     //console.log("overLayPosition.width"+overLayPosition.width);
                                    // console.log("mainPosition.width"+mainPosition.width);
                                     this.$refs.overlay.style.left=mainPosition.left+"px";
                              }else if(this.placement=="topRight"){

                                       this.$refs.overlay.style.top=mainPosition.top-overLayPosition.height+"px";
                                       //console.log("overLayPosition.width"+overLayPosition.width);
                                      // console.log("mainPosition.width"+mainPosition.width);
                                       this.$refs.overlay.style.left=mainPosition.left-(overLayPosition.width-mainPosition.width)+"px";
                                }
                                }else{
                                      this.$refs.overlay.style.top=mainPosition.bottom+"px";
                                                                         this.$refs.overlay.style.left=mainPosition.left+"px";
                                }
                  }


             }
               //
            }
        },
    };
</script>
<style scoped>
.zw-dropdown-overlay{
    position:absolute;
}
</style>
