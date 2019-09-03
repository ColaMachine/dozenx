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

             <zwCollapse >
                <zwPanel >
                <span slot="title" name="title">title123</span>
                <p  slot="body" name="body">321<br>123</p>
                </zwPanel>
                <zwPanel >
                <span slot="title" name="title">title123</span>
                <p  slot="body" name="body">321<br>123</p>
                </zwPanel>
                <zwPanel >
                <span slot="title" name="title">title123</span>
                <p  slot="body" name="body">321<br>123</p>
                </zwPanel>

            </zwCollapse>
        </div>
        <div slot="zwCodeBoxMeta"  title="按钮类型">
            可以同时展开多个面板。
        </div>
        <div slot="zwCodeBoxPanel"  >

            <textarea ref="textarea" id="textarea" name="textarea">
                <zwCollapse >
                                <zwPanel >
                                <span slot="title" name="title">title123</span>
                                <p  slot="body" name="body">321<br>123</p>
                                </zwPanel>
                                <zwPanel >
                                <span slot="title" name="title">title123</span>
                                <p  slot="body" name="body">321<br>123</p>
                                </zwPanel>
                                <zwPanel >
                                <span slot="title" name="title">title123</span>
                                <p  slot="body" name="body">321<br>123</p>
                                </zwPanel>

                            </zwCollapse>
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


import zwCollapse from '../../../component/datadisplay/zwCollapse.vue';
import zwPanel from '../../../component/datadisplay/zwPanel.vue';

export default {
    components:{zwRow,zwCol,zwIcon,zwBox,zwHeader,zwContent,zwFooter,zwLayout,zwSider,zwCollapse,zwPanel},
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
        changeLoading:function(){

          this.loading=!this.loading;

        }
    }
}
</script>
<style scoped>

</style>
