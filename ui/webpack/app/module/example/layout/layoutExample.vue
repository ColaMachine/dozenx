<template>
<div>
<article>
<section>
<h2>Layout布局</h2>
<p>协助进行页面级整体布局。</p>
<h3>设计规则</h3>
<h3>尺寸</h3>
<p>
一级导航项偏左靠近 logo 放置，辅助菜单偏右放置。
顶部导航（大部分系统）：一级导航高度 64px，二级导航 48px。
顶部导航（展示类页面）：一级导航高度 80px，二级导航 56px。
顶部导航高度的范围计算公式为：48+8n。
侧边导航宽度的范围计算公式：200+8n。
</p>

<h3>交互</h3>
<p>
一级导航和末级的导航需要在可视化的层面被强调出来；
当前项应该在呈现上优先级最高；
当导航收起的时候，当前项的样式自动赋予给它的上一个层级；
左侧导航栏的收放交互同时支持手风琴和全展开的样式，根据业务的要求进行适当的选择。
</p>

<h3>视觉</h3>
<p>
导航样式上需要根据信息层级合理的选择样式：
大色块强调
建议用于底色为深色系时，当前页面父级的导航项。
高亮火柴棍
当导航栏底色为浅色系时使用，可用于当前页面对应导航项，建议尽量在导航路径的最终项使用。
字体高亮变色
从可视化层面，字体高亮的视觉强化力度低于大色块，通常在当前项的上一级使用。
字体放大
12px、14px 是导航的标准字号，14 号字体用在一、二级导航中。字号可以考虑导航项的等级做相应选择。
</p>


<h3>组件概述</h3>
<p>
Layout：布局容器，其下可嵌套 Header Sider Content Footer 或 Layout 本身，可以放在任何父容器中。
Header：顶部布局，自带默认样式，其下可嵌套任何元素，只能放在 Layout 中。
Sider：侧边栏，自带默认样式及基本功能，其下可嵌套任何元素，只能放在 Layout 中。
Content：内容部分，自带默认样式，其下可嵌套任何元素，只能放在 Layout 中。
Footer：底部布局，自带默认样式，其下可嵌套任何元素，只能放在 Layout 中。
</p>

<h3>代码演示<zwIcon type="windows" title="展开全部代码"></zwIcon></h3>
<zwRow >
<zwCol span=24>
    <zwBox class="">
        <div slot="zwCodeBoxDemo">

        header content footer
           <zwLayout>
                 <zwHeader>Header</zwHeader>
                 <zwContent>Content</zwContent>
                 <zwFooter>Footer</zwFooter>
           </zwLayout>
           sider
            <zwLayout>
                    <zwHeader>Header</zwHeader>
                   <zwLayout class="zw-layout-has-sider ">
                   <zwSider>Sider</zwSider>
                    <zwContent>Content</zwContent>
                     </zwLayout>
                    <zwFooter>Footer</zwFooter>
              </zwLayout>

              <zwLayout>
                                  <zwHeader>Header</zwHeader>
                                 <zwLayout class="zw-layout-has-sider ">

                                  <zwContent>Content</zwContent>
                                  <zwSider>Sider</zwSider>
                                   </zwLayout>
                                  <zwFooter>Footer</zwFooter>
                            </zwLayout>

            <zwLayout class="zw-layout-has-sider ">

                  <zwSider>Sider</zwSider>
                   <zwLayout >
                <zwHeader>Header</zwHeader>


                <zwContent>Content</zwContent>


                <zwFooter>Footer</zwFooter>
                 </zwLayout>
            </zwLayout>
        </div>
        <div slot="zwCodeBoxMeta"  title="按钮类型">

        </div>
        <div slot="zwCodeBoxPanel"  >

            <textarea ref="textarea" id="textarea" name="textarea">
             header content footer
                       <zwLayout>
                             <zwHeader>Header</zwHeader>
                             <zwContent>Content</zwContent>
                             <zwFooter>Footer</zwFooter>
                       </zwLayout>
                       sider
                        <zwLayout>
                                <zwHeader>Header</zwHeader>
                               <zwLayout class="zw-layout-has-sider ">
                               <zwSider>Sider</zwSider>
                                <zwContent>Content</zwContent>
                                 </zwLayout>
                                <zwFooter>Footer</zwFooter>
                          </zwLayout>

                          <zwLayout>
                                              <zwHeader>Header</zwHeader>
                                             <zwLayout class="zw-layout-has-sider ">

                                              <zwContent>Content</zwContent>
                                              <zwSider>Sider</zwSider>
                                               </zwLayout>
                                              <zwFooter>Footer</zwFooter>
                                        </zwLayout>

                        <zwLayout class="zw-layout-has-sider ">

                              <zwSider>Sider</zwSider>
                               <zwLayout >
                            <zwHeader>Header</zwHeader>


                            <zwContent>Content</zwContent>


                            <zwFooter>Footer</zwFooter>
                             </zwLayout>
                        </zwLayout>
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
export default {
    components:{zwRow,zwCol,zwIcon,zwBox,zwHeader,zwContent,zwFooter,zwLayout,zwSider},
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
