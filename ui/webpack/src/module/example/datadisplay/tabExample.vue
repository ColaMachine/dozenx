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

             <zwTabs  >
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
        <div slot="zwCodeBoxMeta"  title="tab类型">
            tabs 含有tabbar 和tabpanel 两个部分
        </div>
        <div slot="zwCodeBoxPanel"  >

            <textarea ref="textarea" id="textarea" name="textarea">
                 <zwTabs  >
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
            </textarea>

       </div>
    </zwBox>
</zwCol>

<zwCol span=24>
    <zwBox class="">
        <div slot="zwCodeBoxDemo">

             <zwTabs type="appbottom" >
                <zwTabPanel tab="Tab1" icon="home"  icons="home"  tabIndex=1>
                    Content of Tab Pane 11
                </zwTabPanel >
              <zwTabPanel tab="Tab2" icon="home"  tabIndex=2>
                  Content of Tab Pane 21
              </zwTabPanel >
              <zwTabPanel tab="Tab3" icon="home"  tabIndex=3>
                  Content of Tab Pane 31
              </zwTabPanel >

            </zwTabs>
        </div>
        <div slot="zwCodeBoxMeta"  title="按钮类型">
            用于手机地部。
        </div>
        <div slot="zwCodeBoxPanel"  >

            <textarea ref="textarea" id="textarea" name="textarea">
                  <zwTabs type="appbottom" >
                                <zwTabPanel tab="Tab1" icon="home"  icons="home"  tabIndex=1>
                                    Content of Tab Pane 11
                                </zwTabPanel >
                              <zwTabPanel tab="Tab2" icon="home"  tabIndex=2>
                                  Content of Tab Pane 21
                              </zwTabPanel >
                              <zwTabPanel tab="Tab3" icon="home"  tabIndex=3>
                                  Content of Tab Pane 31
                              </zwTabPanel >

                            </zwTabs>
            </textarea>

       </div>
    </zwBox>
</zwCol>


<zwCol span=24>
    <zwBox class="">
        <div slot="zwCodeBoxDemo">

             <zwTabs type="appbottom" theme="purple_yellow" panel="no">
                <zwTabPanel tab="Tab1" icon="home"  icons="home"  tabIndex=1>

                </zwTabPanel >
              <zwTabPanel tab="Tab2" icon="home"  tabIndex=2>
                  Content of Tab Pane 21
              </zwTabPanel >
              <zwTabPanel tab="Tab3" icon="user"  tabIndex=3>
                  Content of Tab Pane 31
              </zwTabPanel >

            </zwTabs>
        </div>
        <div slot="zwCodeBoxMeta"  title="按钮类型">
           换一种theme
        </div>
        <div slot="zwCodeBoxPanel"  >

            <textarea ref="textarea" id="textarea" name="textarea">
                     <zwTabs type="appbottom" theme="purple_yellow" panel="no">
                                    <zwTabPanel tab="Tab1" icon="home"  icons="home"  tabIndex=1>

                                    </zwTabPanel >
                                  <zwTabPanel tab="Tab2" icon="home"  tabIndex=2>
                                      Content of Tab Pane 21
                                  </zwTabPanel >
                                  <zwTabPanel tab="Tab3" icon="user"  tabIndex=3>
                                      Content of Tab Pane 31
                                  </zwTabPanel >
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
import zwTabs from '../../../component/datadisplay/zwTabs.vue';
import zwTabPanel from '../../../component/datadisplay/zwTabPanel.vue';
export default {
    components:{zwRow,zwCol,zwIcon,zwBox,zwHeader,zwContent,zwFooter,zwLayout,zwSider,zwTabPanel,zwTabs},
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
