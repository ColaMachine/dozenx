<template>

                          <zwRichInput @submit="saveContent" url="/article/save"></zwRichInput>

</template>
<script>

import zwRichInput from '../../../component/dataentry/zwRichInput.vue';

export default {
    components:{zwRichInput},
    data () {

        return {

        }
    }
    ,
    computed: {

    },
    methods:{
        saveContent:function( data){
        data.type="1";data.title="123123";
            Ajax.post(PATH+"/artical/save.json",data,function(result){
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
