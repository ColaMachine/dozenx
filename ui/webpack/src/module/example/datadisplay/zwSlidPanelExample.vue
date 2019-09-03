<template>
<div>
<article>
<section>
<h2>zwSlidPanelExample 滑动面板</h2>
<p>滑动面板</p>
<h3>何时使用#</h3>

<p>
在手机端 有些列表项目需要有开关按钮 或者左滑删除的功能可以用到
</p>

<h3>代码演示<zwIcon type="windows" title="展开全部代码"></zwIcon></h3>
<zwRow >
<zwCol span=24>
    <zwBox class="">
        <div slot="zwCodeBoxDemo">
        <ul>
 <li  v-for="result in list" key={{result.id}} >
             <zwSlidPanel :imgSrc="result.pic" :title="result.title" :subTitle="result.subTitle"></zwSlidPanel>
             </li>
             </ul>
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


import zwSlidPanel from '../../../component/datadisplay/zwSlidPanel.vue';
export default {
    components:{zwRow,zwCol,zwIcon,zwBox,zwHeader,zwContent,zwFooter,zwLayout,zwSider,zwSlidPanel},
    data () {

        return {
            list:[
            {titile:"1",subTitle:"2",pic:"/static/img/smarthome/烟感1.png"},
            {titile:"1",subTitle:"2",pic:"/static/img/smarthome/烟感1.png"},
            {titile:"1",subTitle:"2",pic:"/static/img/smarthome/烟感1.png"},
            {titile:"1",subTitle:"2",pic:"/static/img/smarthome/烟感1.png"},
            {titile:"1",subTitle:"2",pic:"/static/img/smarthome/烟感1.png"},
            {titile:"1",subTitle:"2",pic:"/static/img/smarthome/烟感1.png"},
            {titile:"1",subTitle:"2",pic:"/static/img/smarthome/烟感1.png"},
            ]
        }
    }
    ,
    computed: {

    },
    mounted(){
      /* Ajax.getJSON("/goods/list.json?curpage=1&pagesize=10",null,function(result){
                                      if(result.r==AJAX_SUCC){
                                          this.list=result.data;
                                      }
                                  }.Apply(this));*/
    },
    methods:{

    }
}
</script>
<style scoped>

</style>
