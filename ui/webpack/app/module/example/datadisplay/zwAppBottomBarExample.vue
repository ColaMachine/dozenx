<template>
<div>
<article>
<section>
<h2> the phone bottom bar with button groups</h2>
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

             <zwAppBottomBar></zwAppBottomBar>
        </div>
        <div slot="zwCodeBoxMeta"  title="tab类型">
            zwAppDialogue 是适用于手机端 在底部展现出 浮动的

        </div>
        <div slot="zwCodeBoxPanel"  >

            <textarea ref="textarea" id="textarea" name="textarea">
 <zwAppDialogue ></zwAppDialogue>
            </textarea>

       </div>
    </zwBox>
</zwCol>



</zwRow>



<zwCol span=24>
    <zwBox class="">
        <div slot="zwCodeBoxDemo">

             <zwTabs type="card" >
                <zwTabPanel tab="Tab1" tabIndex=1>
                    Content of Tab Pane 11
                </zwTabPanel >
              <zwTabPanel tab="Tab2" tabIndex=2>
                  Content of Tab Pane 21
              </zwTabPanel >
              <zwTabPanel tab="Tab3" tabIndex=3>
                  Content of Tab Pane 31
              </zwTabPanel >

            </zwTabs>
        </div>
        <div slot="zwCodeBoxMeta"  title="按钮类型">
            可以同时展开多个面板。
        </div>
        <div slot="zwCodeBoxPanel"  >

            <textarea ref="textarea" id="textarea" name="textarea">

            </textarea>

       </div>
    </zwBox>
</zwCol>



<zwCol span=24>
    <zwBox class="">
        <div slot="zwCodeBoxDemo">

             <div class="edit-nav row-10 back-gray">
             			<div class="row-9 edit-save-btn">
             				<button class="back-blue normal-text" id="preview">预览</button>
             			</div>

             			<div class="row-10 back-white clearfix nav-height">
             				<a href="/msv2/html/merchant/customer/customer.html">
             				<div class="left row-2-5 center-text">
             					<div class="nav-img">
             					<img src="http://m.51awifi.com/msv2/static/images/nav/kehu.png"></div>
             					<div class="gray-5 smaller-text">客户</div>
             				</div>
             			</a>
             			<a href="/msv2/html/merchant/app/app_sale.html">
             				<div class="left row-2-5 center-text">
             					<div class="nav-img">
             						<img src="http://m.51awifi.com/msv2/static/images/nav/yingy.png">
             					</div>
             					<div class="gray-5 smaller-text">应用</div>
             				</div>
             			</a>
             			<a href="/msv2/html/merchant/merchant_manage.html">
             				<div class="left row-2-5 center-text">
             					<div class="nav-img">
             						<img src="http://m.51awifi.com/msv2/static/images/nav/wode.png">
             					</div>
             					<div class="gray-5 smaller-text">我的</div>
             				</div>
             			</a>
             			<a href="/msv2/html/edit.html">
             				<div class="left row-2-5 center-text">
             					<div class="nav-img">
             						<img src="http://m.51awifi.com/msv2/static/images/nav/bianji1.png">
             					</div>
             					<div class="gray-active smaller-text">编辑</div>
             				</div>
             			</a>
             			</div>
             		</div>
        </div>
        <div slot="zwCodeBoxMeta"  title="按钮类型">
            可以同时展开多个面板。
        </div>
        <div slot="zwCodeBoxPanel"  >

            <textarea ref="textarea" id="textarea" name="textarea">

            </textarea>

       </div>
    </zwBox>
</zwCol>




<zwCol span=24>
    <zwBox class="">
        <div slot="zwCodeBoxDemo">
             <zwTabs  class="bg-black " theme="" defaultIndex="1" v-on:changeTab="selectTab" style="width:100%" type="appbottom"  panel="no">
                      <zwTabPanel  tab="首页" :img="getPathValue('/static/img/smarthome/网络管理1.png')" :img2="getPathValue('/static/img/smarthome/网络管理2.png')"  tabIndex=1>

                      </zwTabPanel >

                      <zwTabPanel tab="附近" :img="getPathValue('/static/img/smarthome/智能家居1.png')"  :img2="getPathValue('/static/img/smarthome/智能家居2.png')"  tabIndex=2>
                                  Content of Tab Pane 21
                              </zwTabPanel >
                               <zwTabPanel tab="发现" :img="getPathValue('/static/img/smarthome/智能家居1.png')"  :img2="getPathValue('/static/img/smarthome/智能家居2.png')"  tabIndex=3>
                                                    Content of Tab Pane 21
                                                </zwTabPanel >
                    <zwTabPanel tab="订单" :img="getPathValue('/static/img/smarthome/智能家居1.png')"  :img2="getPathValue('/static/img/smarthome/智能家居2.png')"  tabIndex=4>
                        Content of Tab Pane 21
                    </zwTabPanel >
                    <zwTabPanel tab="我的" :img="getPathValue('/static/img/smarthome/个人中心1.png')"  :img2="getPathValue('/static/img/smarthome/个人中心2.png')"   tabIndex=5>
                        Content of Tab Pane 31
                    </zwTabPanel >

                  </zwTabs>
        </div>
        <div slot="zwCodeBoxMeta"  title="按钮类型">
            可以同时展开多个面板。
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

 import zwTabs from '../../../component/datadisplay/zwTabs.vue';
  import zwTabPanel from '../../../component/datadisplay/zwTabPanel.vue';

import zwAppBottomBar from '../../../component/datadisplay/zwAppBottomBar.vue';
export default {
    components:{zwRow,zwCol,zwIcon,zwBox,zwHeader,zwContent,zwFooter,zwLayout,zwSider,zwAppBottomBar,zwTabs,zwTabPanel},
    data () {

        return {

        }
    }
    ,
    computed: {

    },
    methods:{
 getPathValue:function(value){
          return getPathValue(value);
        },
    }
}
</script>
<style scoped>


.nav-img {
    padding: 0.5rem 0 0.2rem 0;
    height: 2rem;
}

.gray-5 {
    color: #A1B4CD;
}
.smaller-text {
    font-size: 1.2rem;
}



.back-white {
    background-color: #FFFFFF;
}
.row-10 {
    width: 100%;
    margin: 0 auto;
}

.clearfix {
    zoom: 1;
}

.center-text {
    text-align: center;
}
.row-2-5 {
    width: 25%;
    margin: 0 auto;
}
.left {
    float: left;
}

</style>
