<template>
<div>

<article>
<section>
<h2>Input输入框</h2>
<p>通过鼠标或键盘输入内容，是最基础的表单域的包装。</p>
<h3>何时使用</h3>
<p>需要用户输入表单域内容时。
   提供组合型输入框，带搜索的输入框，还可以进行大小选择。</p>
<h3>代码演示<zwIcon type="windows" title="展开全部代码"></zwIcon></h3>
<zwRow >
<zwCol span=12>
         <zwBox class="">
                    <div slot="zwCodeBoxDemo">
                        <div>
                          <zwRichInput @submit="saveContent" url="/article/save"></zwRichInput>
                        </div>
                    </div>
                     <div slot="zwCodeBoxMeta"  title="前缀和后缀">
                    </div>
                    <div slot="zwCodeBoxPanel"  >
                        <textarea ref="textarea" id="textarea" name="textarea">
                        </textarea>
                    </div>
                </zwBox>
</zwCol>
<zwCol span=12>
</zwCol>
</zwRow>
</section>
</article>
</div>
</template>
<script>
import zwButton from '../../../component/button/zwButton.vue';
import zwButtonGroup from '../../../component/button/zwButtonGroup.vue';
import zwIcon from '../../../component/icon/zwIcon.vue';
import zwRow from '../../../component/layout/zwRow.vue';
import zwBox from '../../../component/layout/zwBox.vue';
import zwDropDown from '../../../component/navigation/menu/zwDropDown.vue';
import zwMenu from '../../../component/navigation/menu/zwMenu.vue';
import zwMenuItem from '../../../component/navigation/menu/zwMenuItem.vue';
import zwInput from '../../../component/dataentry/zwInput.vue';

import zwCol from '../../../component/layout/zwCol.vue';
import zwPanel from '../../../component/datadisplay/zwPanel.vue';

import zwRichInput from '../../../component/dataentry/zwRichInput.vue';

export default {
    components:{zwButton,zwIcon,zwBox,zwRow,zwCol,zwDropDown,zwMenu,zwMenuItem,zwButtonGroup,zwInput,zwPanel,zwRichInput},
    data () {

        return {
            msg: 'Hello World!',
            loading:false,
        }
    }
    ,
    computed: {
        getLoadingVal:function(){
            return this.loading;
        }
    },
    methods:{
        saveContent:function( content){
            Ajax.post("/artical/save.json",{type:1,title:"123123",content:content},function(result){
                console.log(result.r);

            });
        },
        changeLoading:function(){
             console.log("123");
          this.loading=!this.loading;
            // this.loading={delay:5};
              console.log(this.loading);
        }
    }
}
</script>
<style scoped>

</style>
