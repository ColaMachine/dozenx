
<template>
  <li class="zw-menu-item-group ">
    <div class="zw-menu-item-group-title">{{title}}</div>
    <ul class="zw-menu-item-group-list" ><slot></slot></ul>
    </li>
</template>
<script type="text/javascript">
export default {
        name: 'zwMenuItemGroup',
        components: {  },
        props:["title"],
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

        },
    };
</script>
