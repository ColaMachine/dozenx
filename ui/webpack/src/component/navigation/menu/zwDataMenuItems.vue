
<template>
  <li>
         <div @click='toggle'>
             <i v-if='isFolder' class="fa " :class="[open?'fa-folder-open':'fa-folder']"></i>
             <!--isFolder判断是否存在子级改变图标-->
             <i v-if='!isFolder' class="fa fa-file-text"></i> {{model.data.menuName}}

         </div>

         <ul  v-show="open" v-if='isFolder'>
             <zwDataMenuItems v-for='cel in model.childTreeNode' :model='cel'></zwDataMenuItems>



         </ul>
     </li>
</template>
<script type="text/javascript">
export default {
        name: 'zwDataMenuItems',
        components: {  },
        props:["mode","model"],
        data () {
            return {
                open: false,

            };
        },
        computed: {
            isFolder: function() {
                return this.model.childTreeNode && this.model.childTreeNode.length
            }
        },
        mounted () {
        },
        watch:{
　　　　　　　　
　　　　　　},
        methods: {
           toggle: function() {
               if(this.isFolder) {
                   this.open = !this.open
               }
           },
       }
    };
</script>
