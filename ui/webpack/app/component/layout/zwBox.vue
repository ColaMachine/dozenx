
<template>
  <div class="zw-box">
    <zwCodeBoxDemo ><slot name="zwCodeBoxDemo"></slot></zwCodeBoxDemo>
    <zwCodeBoxMeta  v-on:toggleCodePanel="toggleCodePanel" ><slot name="zwCodeBoxMeta"></slot></zwCodeBoxMeta>
    <zwCodeBoxPanel v-if="codePanelShow" ><zwCode><slot name="zwCodeBoxPanel"></slot> </zwCode>  </zwCodeBoxPanel >
  </div>
</template>
<script type="text/javascript">
import zwCodeBoxDemo from '../../component/layout/zwCodeBoxDemo.vue';
import zwCodeBoxMeta from '../../component/layout/zwCodeBoxMeta.vue';
import zwCodeBoxPanel from '../../component/layout/zwCodeBoxPanel.vue';
import zwCode from '../../component/layout/zwCode.vue';
export default {
        name: 'zwBox',
         components:{zwCode,zwCodeBoxDemo,zwCodeBoxMeta,zwCodeBoxPanel},
        props: {
            disabled:Boolean,
            text:String,
            type:String,
            title: String,
            className:String,

        },
        data () {
            return {
                codePanelShow:false,
            };
        },
        computed: {

        },
        mounted () {


        },
        methods: {
            toggleCodePanel:function(){
               // alert("触发父亲的toggleCodePanel");
                this.codePanelShow =!this.codePanelShow ;
            }
        },


         events: {
            'dialog-dispatch' () {

              this.$broadcast('dialog-broadcast')
            }

          }
    };
</script>
<style>
.zw-box{
    border: 1px solid #e9e9e9;
        border-radius: 4px;
        display: inline-block;
        width:90%;
        position: relative;
        margin: 16px 16px 16px 16px;
        -webkit-transition: all .2s;
        transition: all .2s;
        margin-left:30px;
}
</style>