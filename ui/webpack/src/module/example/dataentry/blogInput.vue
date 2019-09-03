<template>

                          <zwRichInput @submit="saveContent" url="/article/save"></zwRichInput>

</template>
<script>

import zwRichInput from '../../../component/dataentry/zwRichInput.vue';
//调用者需要实现 submitCallBack 回调用方法
export default {
    components:{zwRichInput},
    props:["pid"],
    data () {

        return {

        }
    }
    ,
    mounted(){
        //alert(this.pid);
    },
    computed: {

    },
    methods:{
        saveContent:function( data){
        data.pid=this.pid;
        if(!data.pid){
            data.pid=0;
        }
        data.type="1";data.title="123123";
            Ajax.post(PATH+"/msginfo/save.json",data,function(result){
                console.log(result.r);
                if(result.r==AJAX_SUCC){
                    dialog.tips("提交成功");
                    this.submitCallBack();

                }
            }.Apply(this));
        },
        submitCallBack:function(){
            this.$emit("submitCallBack",null);
        },

    }
}
</script>
<style scoped>

</style>
