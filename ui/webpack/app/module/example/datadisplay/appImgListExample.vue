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
             <zwAppImgList :list="imgList"></zwAppImgList>
        </div>
        <div slot="zwCodeBoxMeta"  title="tab类型">
             zwAppImgList 是适用于手机端图片 信息展示条目
        </div>
        <div slot="zwCodeBoxPanel"  >

            <textarea ref="textarea" id="textarea" name="textarea">
 <zwAppDialogue ></zwAppDialogue>
            </textarea>

       </div>
    </zwBox>
</zwCol>



<zwCol span=24>
    <zwBox class="">
        <div slot="zwCodeBoxDemo">
             <zwPcImgList :list="imgList"></zwPcImgList>
        </div>
        <div slot="zwCodeBoxMeta"  title="tab类型">
            zwPcImgList 是适用于电脑端图片 信息展示条目
        </div>
        <div slot="zwCodeBoxPanel"  >
            <textarea ref="textarea" id="textarea" name="textarea">
                  <zwPcImgList :list="imgList"></zwPcImgList>
            </textarea>

       </div>
    </zwBox>
</zwCol>



<zwCol span=24>
    <zwBox class="">
        <div slot="zwCodeBoxDemo">
             <zwAppImgBlocks :list="imgList"></zwAppImgBlocks>
        </div>
        <div slot="zwCodeBoxMeta"  title="tab类型">
             zwAppImgBlocks 是适用于手机端图片 信息展示条目
        </div>
        <div slot="zwCodeBoxPanel"  >

            <textarea ref="textarea" id="textarea" name="textarea">
 <zwAppImgBlocks ></zwAppImgBlocks>
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


import zwAppImgList from '../../../component/datadisplay/zwAppImgList.vue';
import zwAppImgBlocks from '../../../component/datadisplay/zwAppImgBlocks.vue';
import zwPcImgList from '../../../component/datadisplay/zwPcImgList.vue';

export default {
    components:{zwRow,zwCol,zwIcon,zwBox,zwHeader,zwContent,zwFooter,zwLayout,zwSider,zwAppImgList,zwPcImgList,zwAppImgBlocks},
    data () {

        return {
            imgList:[]
        }
    }
    ,
    computed: {

    },
    methods:{

    },
     mounted (){
          this.imgList=[

        { id:1,title:"华家池(凯旋路)",tag1:["设施齐全","设施齐全","设施齐全"],tag2:["80人","888平米","已售88份"],tag3:["支付宝","有储物柜","小卖部","有空调"],score:"5.5",commentCount:"12",price:"1231起步",subtitle:"莫干山路101010号1幢401是",pic:"static/img/carousel/11.png"},
         {id:1,title:"华家池(凯旋路)",tag1:["设施齐全","设施齐全","设施齐全"],tag2:["80人","888平米","已售88份"],tag3:["支付宝","有储物柜","小卖部","有空调"],score:"5.5",commentCount:"12",price:"1231起步",subtitle:"莫干山路101010号1幢401是",pic:"static/img/carousel/11.png"},
         {id:1,title:"华家池(凯旋路)",tag1:["设施齐全","设施齐全","设施齐全"],tag2:["80人","888平米","已售88份"],tag3:["支付宝","有储物柜","小卖部","有空调"],score:"5.5",commentCount:"12",price:"1231起步",subtitle:"莫干山路101010号1幢401是",pic:"static/img/carousel/11.png"},
         {id:1,title:"华家池(凯旋路)",tag1:["设施齐全","设施齐全","设施齐全"],tag2:["80人","888平米","已售88份"],tag3:["支付宝","有储物柜","小卖部","有空调"],score:"5.5",commentCount:"12",price:"1231起步",subtitle:"莫干山路101010号1幢401是",pic:"static/img/carousel/11.png"},
         {id:1,title:"华家池(凯旋路)",tag1:["设施齐全","设施齐全","设施齐全"],tag2:["80人","888平米","已售88份"],tag3:["支付宝","有储物柜","小卖部","有空调"],score:"5.5",commentCount:"12",price:"1231起步",subtitle:"莫干山路101010号1幢401是",pic:"static/img/carousel/11.png"}

        ];
    }
}
</script>
<style scoped>

</style>
