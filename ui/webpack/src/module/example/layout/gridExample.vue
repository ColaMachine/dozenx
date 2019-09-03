<template>
<div>
<article>
<section>
<h2>Grid栅栏</h2>
<p>24格栅栏</p>
<h3>设计理念</h3>
<p>
在多数业务情况下，需要在设计区域内解决大量信息收纳的问题，因此在 12 栅格系统的基础上，我们将整个设计建议区域按照 24 等分的原则进行划分。
划分之后的信息区块我们称之为『盒子』。建议横向排列的盒子数量最多四个，最少一个。『盒子』在整个屏幕上占比见上图。设计部分基于盒子的单位定制盒子内部的排版规则，以保证视觉层面的舒适感。
</p>
<p>布局的栅格化系统，我们是基于行（row）和列（col）来定义信息区块的外部框架，以保证页面的每个区域能够稳健地排布起来。下面简单介绍一下它的工作原理：
   通过row在水平方向建立一组column（简写col）
   你的内容应当放置于col内，并且，只有col可以作为row的直接元素
   栅格系统中的列是指1到24的值来表示其跨越的范围。例如，三个等宽的列可以使用.col-8来创建
   如果一个row中的col总和超过 24，那么多余的col会作为一个整体另起一行排列</p>
<h3>代码演示<zwIcon type="windows" title="展开全部代码"></zwIcon></h3>
<zwRow >
<zwCol span=24>
    <zwBox class="">
        <div slot="zwCodeBoxDemo">
           <zwRow >
                <zwCol span=12 > <div class="zw-gutter-box">col-12</div> </zwCol>
                <zwCol span=12 > <div class="zw-gutter-box">col-12</div> </zwCol>

                <zwCol span=8 > <div class="zw-gutter-box">col-8</div> </zwCol><zwCol span=8 >
                <div class="zw-gutter-box">col-8</div> </zwCol>
                <zwCol span=8 > <div class="zw-gutter-box">col-8</div> </zwCol>


                <zwCol span=6 > <div class="zw-gutter-box">col-6</div></zwCol>
                <zwCol span=6 > <div class="zw-gutter-box">col-6</div> </zwCol>
                <zwCol span=6 > <div class="zw-gutter-box">col-6</div></zwCol>
                <zwCol span=6 > <div class="zw-gutter-box">col-6</div> </zwCol>
           </zwRow>
        </div>
        <div slot="zwCodeBoxMeta"  title="按钮类型">
            zwCol通过设置span来调整宽度大小 默认是24格
        </div>
        <div slot="zwCodeBoxPanel"  >

            <textarea ref="textarea" id="textarea" name="textarea">
            <zwCol span=12 > <div class="zw-gutter-box">col-12</div> </zwCol>
                <zwCol span=12 > <div class="zw-gutter-box">col-12</div> </zwCol>

                <zwCol span=8 > <div class="zw-gutter-box">col-8</div> </zwCol>
                <zwCol span=8 ><div class="zw-gutter-box">col-8</div> </zwCol>
                <zwCol span=8 > <div class="zw-gutter-box">col-8</div> </zwCol>


                <zwCol span=6 > <div class="zw-gutter-box">col-6</div></zwCol>
                <zwCol span=6 > <div class="zw-gutter-box">col-6</div> </zwCol>
                <zwCol span=6 > <div class="zw-gutter-box">col-6</div></zwCol>
                <zwCol span=6 > <div class="zw-gutter-box">col-6</div> </zwCol>
            </textarea>

       </div>
    </zwBox>




     <zwBox class="">
            <div slot="zwCodeBoxDemo">
                <zwRow gutter=16>

                <zwCol span=12 class="zw-gutter-row">
                    <div class="zw-gutter-box">col-12</div>
                </zwCol>
                <zwCol span=12  class="zw-gutter-row">
                    <div class="zw-gutter-box">col-12</div>
                </zwCol>
                <zwCol span=8  class="zw-gutter-row">
                    <div class="zw-gutter-box">col-8</div>
                </zwCol>
                <zwCol span=8  class="zw-gutter-row">
                    <div class="zw-gutter-box">col-8</div>
                </zwCol>
                <zwCol span=8  class="zw-gutter-row">
                    <div class="zw-gutter-box">col-8</div>
                </zwCol>

                <zwCol span=6  class="zw-gutter-row"><div class="zw-gutter-box">col-6</div></zwCol>
                <zwCol span=6  class="zw-gutter-row"><div class="zw-gutter-box">col-6</div> </zwCol>
                <zwCol span=6  class="zw-gutter-row"><div class="zw-gutter-box">col-6</div> </zwCol>
                <zwCol span=6  class="zw-gutter-row"><div class="zw-gutter-box">col-6</div> </zwCol>
                </zwRow>
            </div>
            <div slot="zwCodeBoxMeta"  title="按钮类型">
                zwRow可以放置一个属性gutter, gutter会影响zwCol的margin-left 和margin-right属性,由于slot不能传递属性到子组件里,所以这里是在zwRow里遍历子元素 设置style.paddingLeft paddingRight = 1/2 gutter来达到目的的
            </div>
            <div slot="zwCodeBoxPanel"  >

                <textarea ref="textarea" id="textarea" name="textarea">
                        <zwRow gutter=16>

                            <zwCol span=12 class="zw-gutter-row">
                                <div class="zw-gutter-box">col-12</div>
                            </zwCol>
                            <zwCol span=12  class="zw-gutter-row">
                                <div class="zw-gutter-box">col-12</div>
                            </zwCol>
                            <zwCol span=8  class="zw-gutter-row">
                                <div class="zw-gutter-box">col-8</div>
                            </zwCol>
                            <zwCol span=8  class="zw-gutter-row">
                                <div class="zw-gutter-box">col-8</div>
                            </zwCol>
                            <zwCol span=8  class="zw-gutter-row">
                                <div class="zw-gutter-box">col-8</div>
                            </zwCol>

                            <zwCol span=6  class="zw-gutter-row"><div class="zw-gutter-box">col-6</div></zwCol>
                            <zwCol span=6  class="zw-gutter-row"><div class="zw-gutter-box">col-6</div> </zwCol>
                            <zwCol span=6  class="zw-gutter-row"><div class="zw-gutter-box">col-6</div> </zwCol>
                            <zwCol span=6  class="zw-gutter-row"><div class="zw-gutter-box">col-6</div> </zwCol>
                        </zwRow>
                 </textarea>

           </div>
        </zwBox>

</zwCol>



<zwCol class="col-6">

</zwCol>




</zwRow>

</section>
</article>
</div>
</template>
<script>
import zwRow from '../../../component/layout/zwRow.vue';
import zwCol from '../../../component/layout/zwCol.vue';
import zwIcon from '../../../component/icon/zwIcon.vue';
import zwBox from '../../../component/layout/zwBox.vue';
export default {
    components:{zwRow,zwCol,zwIcon,zwBox},
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
             console.log("123");
          this.loading=!this.loading;
            // this.loading={delay:5};
              console.log(this.loading);
        }
    }
}
</script>
<style scoped>
.zw-code-box-demo  div[class*="col-"] .zw-gutter-box{
    margin-top:15px;
    height:50px;
    color:white;
    text-align:center;
    line-height:50px;
}
.zw-code-box-demo div[class*="col-"]:nth-child(odd) .zw-gutter-box{

    background-color:rgba(0,160,233,.7);
}
.zw-code-box-demo  div[class*="col-"]:nth-child(even) .zw-gutter-box{

    background-color:#00a0e9;
}
</style>
