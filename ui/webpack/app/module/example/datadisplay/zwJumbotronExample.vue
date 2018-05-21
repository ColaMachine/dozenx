<template>
<div>
<article>
<section>
<h2>zwJumbotron 巨幕</h2>
<p>巨幕</p>
<h3>何时使用#</h3>

<p>
在手机端 最上面用大写的字体提示某些重要的主题 留白处有按钮 使得整个页面更加简洁 大方.
</p>

<h3>代码演示<zwIcon type="windows" title="展开全部代码"></zwIcon></h3>
<zwRow >
<zwCol span=24>
    <zwBox class="">
        <div slot="zwCodeBoxDemo">

             <zwJumbotron title="智慧家庭"></zwJumbotron>

        </div>
        <div slot="zwCodeBoxMeta"  title="tab类型">
            zwSlidPanel 是适用于手机端 滑动删除默写项目

        </div>
        <div slot="zwCodeBoxPanel"  >

            <textarea ref="textarea" id="textarea" name="textarea">
 <zwAppDialogue ></zwAppDialogue>
            </textarea>

       </div>
    </zwBox>
</zwCol>



</zwRow>





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


import zwJumbotron from '../../../component/datadisplay/zwJumbotron.vue';
export default {
    components:{zwRow,zwCol,zwIcon,zwBox,zwHeader,zwContent,zwFooter,zwLayout,zwSider,zwJumbotron},
    data () {

        return {

        }
    }
    ,
    computed: {

    },
    mounted(){

    },
    methods:{

    }
}
</script>
<style scoped>

</style>
