<template>
<div id="table" class="zw-tables  "  >
     <div id="tableGrid" class="grid table" ref="tableGrid" ></div>
    <div id="tableGrid-Pager" ref="tablePager" class="pager"></div>
 </div>

</template>
<script type="text/javascript">
import zwIcon from '../icon/zwIcon.vue';

export default {
        name: 'zwTable',
         components:{zwIcon},
        props:
            ["param"]//"tabs"
        ,
        data () {
            return {

            };
        },
        computed: {

        },
        mounted () {
            console.log(this.param);
        //    alert("zwtable mounted");
             if(this.param){
               this.param.grid = this.$refs.tableGrid;
                this.param.pager = this.$refs.tablePager;
                this.jqGrid=new jqGrid(this.param.grid  ,this.param);
            }
        },
        methods: {


        },


         events: {

          }
    };



</script>
<style>

</style>
