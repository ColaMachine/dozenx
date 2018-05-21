<template>
<div>
<article>
<section>
<h2>Collapse折叠面板</h2>
<p>可以折叠/展开的内容区域。。</p>
<h3>何时使用#</h3>

<p>
对复杂区域进行分组和隐藏，保持页面的整洁。
手风琴 是一种特殊的折叠面板，只允许单个内容区域展开。
</p>

<h3>代码演示<zwIcon type="windows" title="展开全部代码"></zwIcon></h3>
<zwRow >
<zwCol span=24>
    <zwBox class="">
        <div slot="zwCodeBoxDemo">
            <zwChocolatePanel :data="list">白菜</zwChocolatePanel>
        </div>
        <div slot="zwCodeBoxMeta"  title="tab类型">
            zwAppDialogue 是适用于手机端 在底部展现出 弹出框
        </div>
        <div slot="zwCodeBoxPanel"  >

            <textarea ref="textarea" id="textarea" name="textarea">

            </textarea>

       </div>
    </zwBox>
</zwCol>



</zwRow>




</section>
</article>
</div>
</template>
<script>

import zwLayout from '../../../component/layout/zwLayout.vue';
import zwHeader from '../../../component/layout/zwHeader.vue';
import zwContent from '../../../component/layout/zwContent.vue';
import zwSider from '../../../component/layout/zwSider.vue';
import zwFooter from '../../../component/layout/zwFooter.vue';
import zwRow from '../../../component/layout/zwRow.vue';
import zwCol from '../../../component/layout/zwCol.vue';
import zwIcon from '../../../component/icon/zwIcon.vue';
import zwBox from '../../../component/layout/zwBox.vue';
import zwChocolatePanel from '../../../component/datadisplay/zwChocolatePanel.vue';
import zwCrousel from '../../../component/datadisplay/zwCrousel.vue';
export default {
    components:{zwRow,zwCol,zwIcon,zwBox,zwHeader,zwContent,zwFooter,zwLayout,zwSider,zwChocolatePanel},
    data () {

        return {
            list:[
            {type:"home",url:"www.baidu.com",name:"白菜"},
            {type:"home",url:"www.baidu.com",name:"白菜"},
            {type:"home",url:"www.baidu.com",name:"白菜"},
            {type:"home",url:"www.baidu.com",name:"白菜"},
            {type:"home",url:"www.baidu.com",name:"白菜"},
            {type:"home",url:"www.baidu.com",name:"白菜"},
            {type:"home",url:"www.baidu.com",name:"白菜"},
            {type:"home",url:"www.baidu.com",name:"白菜"},
            {type:"home",url:"www.baidu.com",name:"白菜"},
            {type:"home",url:"www.baidu.com",name:"白菜"},
            ]
        }
    }
    ,
    computed: {

    },
    methods:{

    }
}
</script>
<style scoped>

</style>
