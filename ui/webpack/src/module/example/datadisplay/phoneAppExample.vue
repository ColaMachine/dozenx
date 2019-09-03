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

             <zwTabs>
                            <zwTabPanel tab="Tab1" tabIndex=1>

             <zwCrousel :imglist="imglist"> </zwCrousel>
                <zwChocolatePanel :data="iconlist">白菜</zwChocolatePanel>
                 <zwSliderText :list="textlist"> </zwSliderText>
            <zwAppImgList :list="goodsList"></zwAppImgList>
                            </zwTabPanel >
                          <zwTabPanel tab="Tab2" tabIndex=2>
                              Content of Tab Pane 21
                          </zwTabPanel >
                          <zwTabPanel tab="Tab3" tabIndex=3>
                              Content of Tab Pane 31
                          </zwTabPanel >
</zwTabs  >
        </div>
        <div slot="zwCodeBoxMeta"  title="tab类型">
            zwAppDialogue 是适用于手机端 在底部展现出 弹出框
        </div>
        <div slot="zwCodeBoxPanel"  >

            <textarea ref="textarea" id="textarea" name="textarea">
 <zwAppDialogue ></zwAppDialogue>
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
import zwTabs from '../../../component/datadisplay/zwTabs.vue';
import zwTabPanel from '../../../component/datadisplay/zwTabPanel.vue';
import zwCrousel from '../../../component/datadisplay/zwCrousel.vue';
import zwChocolatePanel from '../../../component/datadisplay/zwChocolatePanel.vue';
import zwSliderText from '../../../component/datadisplay/zwSliderText.vue';
import zwAppImgList from '../../../component/datadisplay/zwAppImgList.vue';
export default {
    components:{zwRow,zwCol,zwIcon,zwBox,zwHeader,zwContent,zwFooter,zwLayout,zwSider,zwCrousel,zwTabs,zwTabPanel,zwChocolatePanel,zwAppImgList,zwSliderText},
    data () {

        return {
        goodsList:[],
  iconlist:[
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
            ],
            textlist:[
                                {type:"home",url:"www.baidu.com",name:"台新党青年怒斥被抓:不存在大陆提供金钱的事"},
                                {type:"home",url:"www.baidu.com",name:"网传南昌大学一女生遭副院长性侵长达7个月"},
                                {type:"home",url:"www.baidu.com",name:"财政部长人民日报撰文:加快建立现代财政制度"},
                                {type:"home",url:"www.baidu.com",name:"广东这名厅官落马 曾因一张摆拍照闻名全国"},
                                {type:"home",url:"www.baidu.com",name:"温度 | 5岁男童地铁惊厥吐血 众人接力送医"},
                                {type:"home",url:"www.baidu.com",name:"网传吉林警察打人视频 警方发全景监控回应"},
                                {type:"home",url:"www.baidu.com",name:"华西村负债389亿 集团要职由吴仁宝家族成员分管"},
                                {type:"home",url:"www.baidu.com",name:"天津纪委遭问:黄兴国案涉多名干部 怎么肃清流毒?"},
                                {type:"home",url:"www.baidu.com",name:"湖北一单位3任书记塌方腐败 线索来自开房记录"},
                                {type:"home",url:"www.baidu.com",name:"女子把11万元钻戒扔了 8名环卫工翻13吨垃圾找到"},
                                ]
        }
    }
    ,
    mounted(){
    Ajax.getJSON("/goods/list.json?curpage=1&pagesize=10",null,function(result){
                                      if(result.r==AJAX_SUCC){
                                          this.goodsList=result.data;
                                      }
                                  }.Apply(this));
    },
    computed: {

    },
    methods:{

    }
}
</script>
<style scoped>

</style>
