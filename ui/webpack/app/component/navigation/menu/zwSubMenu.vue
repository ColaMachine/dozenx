
<template>
  <li :class="getSubMenuClasses" v-on:mouseenter="showMenu" v-on:mouseleave="hideMenu"   >
   <a href="javascript:void(0)"      v-on:click="toggleMenu" >

   <span class="zw-menu-item-text"><zwIcon type="windows"> </zwIcon><slot  name="title"></slot> <zwIcon type="arrow-down"></zwIcon></span></a>
    <ul ref="subMenu" :style="addfixSubMenuClass" v-show="menuShow" class="zw-menu-sub"><slot ></slot></ul>
  </li>
</template>
<script type="text/javascript">
import zwIcon from "../../icon/zwIcon.vue"
export default {
        name: 'zwSubMenu',
        components: { zwIcon },
        props:{
            triggerType:{
                              type: String,
                              default: "click"
                            },//菜单的次展开是由鼠标移动来触发 还是单机来触发
        },
        data () {
            return {
                addfixSubMenuClass:"",
                //"active":false,
                //"open":false,
                "menuShow":false,

            };
        },
        computed: {
            getSubMenuClasses:function(){
           var  classes= " zw-menu-submenu";
            if(this.active){
               // classes+= " zw-menu-submenu-active";

            }
              if(this.menuShow){
                            classes+= " zw-menu-submenu-active";
                                            classes+= " zw-menu-submenu-open";
                        }
            if(this.open){
                classes+= " zw-menu-submenu-open";
            }
            return classes;
            }
        },
        mounted () {
        },

        watch:{
　　　　　　　　
　　　　　　},
        methods: {
            openMenu:function(){
              //  this.open=!this.open;
              this.menuShow=!this.menuShow;
            },
            hideMenu:function(){
            if(this.triggerType !='move'){
                            return;
                        }
               // this.active=false;
                 this.menuShow = false;
            },
            showMenu:function(){
            if(this.triggerType !='move'){
                return;
            }
                console.log("movein");
              //  this.active = true;
                this.menuShow = true;

            },
            toggleMenu:function(){console.log("toggle "+this.menuShow);
            console.log(this.triggerType);
            if(this.triggerType =='move'){
                            return;
                        }

                this.menuShow=!this.menuShow;
                 this.active = !this.active;
                 this.open=!this.open;

                 //获取子菜单的位置

                 var info = getInfo(this.$refs.subMenu);
                    console.log(info);
                    console.log(getWinWidth());
                    if(info.right  > getWinWidth()){

                    this.addfixSubMenuClass="left:-"+(info.right-getWinWidth())+"px";


                    }
            }
        },
    };
</script>
