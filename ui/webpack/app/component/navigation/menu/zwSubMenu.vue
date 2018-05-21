
<template>
  <li :class="getSubMenuClasses" v-on:mouseenter="showMenu" v-on:mouseleave="hideMenu"   >
   <a href="javascript:void(0)"      v-on:click="toggleMenu" >

   <span class="zw-menu-item-text"><zwIcon type="windows"> </zwIcon><slot  name="title"></slot> <zwIcon type="arrow-down"></zwIcon></span></a>
    <ul v-show="menuShow" class=" zw-menu-sub "><slot ></slot></ul>
  </li>
</template>
<script type="text/javascript">
import zwIcon from "../../icon/zwIcon.vue"
export default {
        name: 'zwSubMenu',
        components: { zwIcon },
        props:{

        },
        data () {
            return {
                "active":false,
                "open":false,
                "menuShow":false,

            };
        },
        computed: {
            getSubMenuClasses:function(){
           var  classes= " zw-menu-submenu";
            if(this.active){
                classes+= " zw-menu-submenu-active";
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
                this.open=!this.open;
            },
            hideMenu:function(){
                this.active=false;
            },
            showMenu:function(){
                this.active = true;
            },
            toggleMenu:function(){console.log("toggle "+this.menuShow);
                this.menuShow=!this.menuShow;
                 this.active = !this.active;
                 this.open=!this.open;
            }
        },
    };
</script>
