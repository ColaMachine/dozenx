<template>
<div>
<article>
<section>
<h2>alert弹出框</h2>
<p>可以弹出指定的框体</p>
<h3>何时使用#</h3>

<p>
用于提示信息 或提示进行选择 或者提示输入
</p>

<h3>代码演示<zwIcon type="windows" title="展开全部代码"></zwIcon></h3>
<zwRow >
<zwCol span=12>
    <zwBox class="">
        <div slot="zwCodeBoxDemo">

             <zwButton v-on:clickFn="talert">alert</zwButton>
             <zwButton v-on:clickFn="tconfirm">confirm</zwButton>
              <zwButton v-on:clickFn="tprompt">prompt</zwButton>
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
<zwCol span=12>
    <zwBox class="">
        <div slot="zwCodeBoxDemo">

             <button v-on:click="talert()">alert</button>
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


import zwButton from '../../../component/button/zwButton.vue';
export default {
    components:{zwRow,zwCol,zwIcon,zwBox,zwHeader,zwContent,zwFooter,zwLayout,zwSider,zwButton},
    data () {

        return {

        }
    }
    ,
    computed: {

    },
    methods:{
        tprompt:function(){
            zprompt("请输入密码");
        },
        tconfirm:function(){
                    zconfirm("请输入密码");
                },
        talert:function(){
            zalert('hello');
        }

    }
}
</script>
<style scoped>

</style>
