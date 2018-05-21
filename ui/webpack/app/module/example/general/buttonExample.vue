<template>
<div>
<article>
<section>
<h2>button按钮</h2>
<p>按钮用于开始一个即时操作。</p>
<h3>何时使用</h3>
<p>标记了一个（或封装一组）操作命令，响应用户点击行为，触发相应的业务逻辑。</p>
<h3>代码演示<zwIcon type="windows" title="展开全部代码"></zwIcon></h3>
<zwRow >
<zwCol span=12>

    <!---------按钮类型--------------->
    <zwBox class="">
        <div slot="zwCodeBoxDemo">
            <zwButton type="primary">primary</zwButton>
            <zwButton >default</zwButton>
            <zwButton type="dashed">dashed</zwButton>
            <zwButton type="danger">danger</zwButton>
        </div>
        <div slot="zwCodeBoxMeta"  title="按钮类型">
            按钮有四种类型：主按钮、次按钮、虚线按钮、危险按钮。主按钮在同一个操作区域最多出现一次。
        </div>
        <div slot="zwCodeBoxPanel"  >

            <textarea ref="textarea" id="textarea" name="textarea">
                <zwButton type="primary">primary</zwButton>
                 <zwButton >default</zwButton>
                 <zwButton type="dashed">dashed</zwButton>
                 <zwButton type="danger">danger</zwButton>
             </textarea>

       </div>
    </zwBox>
<!---------按钮尺寸--------------->
    <zwBox class="">
            <div slot="zwCodeBoxDemo">
                <zwButton sizeNum="large" >large</zwButton>
                <zwButton  >default</zwButton>
                <zwButton   sizeNum="small">small</zwButton>
                <br />
                <zwButton type="dashed" shape="circle" icon="search"></zwButton>
                <zwButton type="dashed"  icon="search">search</zwButton>
                <zwButton type="danger" shape="circle" icon="search"></zwButton>
                <zwButton type="danger"  icon="search">search</zwButton>
            </div>
             <div slot="zwCodeBoxMeta"  title="按钮尺寸">

                按钮有大、中、小三种尺寸。
                通过设置 size 为 large small 分别把按钮设为大、小尺寸。若不设置 size，则尺寸为中。
            </div>
            <div slot="zwCodeBoxPanel"  >

                <textarea ref="textarea" id="textarea" name="textarea">
                     <zwButton sizeNum="large" >large</zwButton>
                                    <zwButton  >default</zwButton>
                                    <zwButton   sizeNum="small">small</zwButton>
                                    <br />
                                    <zwButton type="dashed" shape="circle" icon="search"></zwButton>
                                    <zwButton type="dashed"  icon="search">search</zwButton>
                                    <zwButton type="danger" shape="circle" icon="search"></zwButton>
                                    <zwButton type="danger"  icon="search">search</zwButton>
                </textarea>

            </div>
        </zwBox>

<!---------loading 等待按钮--------------->
        <zwBox class="">
            <div slot="zwCodeBoxDemo">

                <zwButton type="dashed" shape="circle" icon="search" loading :loading_delay=5></zwButton>
                <zwButton type="dashed"  icon="search"loading  >search</zwButton>
                <zwButton type="danger" shape="circle" icon="search" :loading="false"  :loading_delay=5></zwButton>
                <zwButton type="danger"  icon="search" :loading="false"  :loading_delay=5>search</zwButton>
            </div>
             <div slot="zwCodeBoxMeta"  title="等待按钮">

                等待按钮 通过设置loading来控制是否出在等待状态 通过设置loading_delay来自动走出loading状态
            </div>
            <div slot="zwCodeBoxPanel"  >

                <textarea ref="textarea" id="textarea" name="textarea">
                   <zwButton type="dashed" shape="circle" icon="search" loading :loading_delay=5></zwButton>
                   <zwButton type="dashed"  icon="search"loading  >search</zwButton>
                   <zwButton type="danger" shape="circle" icon="search" :loading="getLoadingVal"  v-on:click.native="changeLoading" :loading_delay=5></zwButton>
                   <zwButton type="danger"  icon="search" :loading="getLoadingVal" v-on:click.native="changeLoading" >search</zwButton>
                   export default {
                       components:{zwButton,zwIcon,zwBox,zwRow,zwCol},
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
                </textarea>

            </div>
        </zwBox>
        <!---------按钮组--------------->
        <zwBox >
            <div slot="zwCodeBoxDemo">
                <zwButtonGroup>
                    <zwButton type="dashed"  icon="search" loading :loading_delay=5></zwButton>
                    <zwButton type="dashed"  icon="search"loading  >search</zwButton>
                    <zwButton type="danger"  icon="search" :loading="getLoadingVal"  v-on:click.native="changeLoading" :loading_delay=5></zwButton>
                    <zwButton type="danger"  icon="search" :loading="getLoadingVal" v-on:click.native="changeLoading" >search</zwButton>
                </zwButtonGroup>
            </div>
             <div slot="zwCodeBoxMeta"  title="等待按钮">

                按钮有大、中、小三种尺寸。
                通过设置 size 为 large small 分别把按钮设为大、小尺寸。若不设置 size，则尺寸为中。
            </div>
            <div slot="zwCodeBoxPanel"  >

                <textarea ref="textarea" id="textarea" name="textarea">
                   <zwButton type="dashed" shape="circle" icon="search" loading :loading_delay=5></zwButton>
                   <zwButton type="dashed"  icon="search"loading  >search</zwButton>
                   <zwButton type="danger" shape="circle" icon="search" :loading="getLoadingVal"  v-on:click.native="changeLoading" :loading_delay=5></zwButton>
                   <zwButton type="danger"  icon="search" :loading="getLoadingVal" v-on:click.native="changeLoading" >search</zwButton>

                </textarea>

            </div>
        </zwBox>
</zwCol>



<zwCol span=12>
    <zwBox class="">
         <div slot="zwCodeBoxDemo">

            <zwButton type="primary" shape="circle" icon="search"></zwButton>
            <zwButton type="primary"  icon="search">search</zwButton>

            <zwButton  shape="circle" icon="search"></zwButton>
            <zwButton   icon="search">search</zwButton>

            <br />

            <zwButton type="dashed" shape="circle" icon="search"></zwButton>
            <zwButton type="dashed"  icon="search">search</zwButton>

            <zwButton type="danger" shape="circle" icon="search"></zwButton>
            <zwButton type="danger"  icon="search">search</zwButton>




        </div>.
         <div slot="zwCodeBoxMeta"  title="图标按钮">

          当需要在 Button 内嵌入 Icon 时，可以设置 icon 属性，或者直接在 Button 内使用 Icon 组件。
          如果想控制 Icon 具体的位置，只能直接使用 Icon 组件，而非 icon 属性。
        </div>
         <div slot="zwCodeBoxPanel"  >

            <textarea ref="textarea" id="textarea" name="textarea">
                 <zwButton type="primary" shape="circle" icon="search"></zwButton>
                        <zwButton type="primary"  icon="search">search</zwButton>

                        <zwButton  shape="circle" icon="search"></zwButton>
                        <zwButton   icon="search">search</zwButton>

                        <br />

                        <zwButton type="dashed" shape="circle" icon="search"></zwButton>
                        <zwButton type="dashed"  icon="search">search</zwButton>

                        <zwButton type="danger" shape="circle" icon="search"></zwButton>
                        <zwButton type="danger"  icon="search">search</zwButton>
             </textarea>

       </div>
    </zwBox>




    <zwBox class="">
             <div slot="zwCodeBoxDemo">

                <zwButton type="primary"  shape="circle" icon="search" disabled></zwButton>
                <zwButton type="primary"  disabled icon="search">search</zwButton>

                <zwButton  shape="circle" icon="search" disabled ></zwButton>
                <zwButton   icon="search" disabled >search</zwButton>

            </div>.
             <div slot="zwCodeBoxMeta"  title="disabled禁用">

                To mark a button as disabled, add the disabled property to the Button.
            </div>
             <div slot="zwCodeBoxPanel"  >

                <textarea ref="textarea" id="textarea" name="textarea">
                        <zwButton type="primary"  shape="circle" icon="search" disabled></zwButton>
                        <zwButton type="primary"  disabled icon="search">search</zwButton>

                        <zwButton  shape="circle" icon="search" disabled ></zwButton>
                        <zwButton   icon="search" disabled >search</zwButton>
                 </textarea>

           </div>
        </zwBox>

</zwCol>
<zwCol span=12>
        <zwBox class="">
             <div slot="zwCodeBoxDemo">

                <zwDropDown  placement="bottomRight">
                    <zwButton slot="button" type="primary"   icon="down" >bottomRight</zwButton>
                    <zwMenu slot="menu">
                        <zwMenuItem key="1">1st item</zwMenuItem>
                        <zwMenuItem key="2">2nd item</zwMenuItem>
                        <zwMenuItem key="3">3rd item</zwMenuItem>
                    </zwMenu>
                </zwDropDown>
                <zwDropDown placement="bottomLeft">
                    <zwButton slot="button" type="primary"   icon="down" >bottomLeft</zwButton>
                    <zwMenu slot="menu">
                        <zwMenuItem key="1">1st item</zwMenuItem>
                        <zwMenuItem key="2">2nd item</zwMenuItem>
                        <zwMenuItem key="3">3rd item</zwMenuItem>
                    </zwMenu>
                </zwDropDown>
                  <zwDropDown placement="topLeft">
                    <zwButton slot="button" type="primary"   icon="down" >topLeft</zwButton>
                    <zwMenu slot="menu">
                        <zwMenuItem key="1">1st item</zwMenuItem>
                        <zwMenuItem key="2">2nd item</zwMenuItem>
                        <zwMenuItem key="3">3rd item</zwMenuItem>
                    </zwMenu>
                </zwDropDown>
                 <zwDropDown placement="topRight">
                    <zwButton slot="button" type="primary"   icon="down" >topRight</zwButton>
                    <zwMenu slot="menu">
                        <zwMenuItem key="1">1st item</zwMenuItem>
                        <zwMenuItem key="2">2nd item</zwMenuItem>
                        <zwMenuItem key="3">3rd item</zwMenuItem>
                    </zwMenu>
                </zwDropDown>
            </div>



             <div slot="zwCodeBoxMeta"  title="多按钮 下拉">

                To mark a button as disabled, add the disabled property to the Button.
            </div>
             <div slot="zwCodeBoxPanel"  >

                <textarea ref="textarea" id="textarea" name="textarea">
                        <div slot="zwCodeBoxDemo">
                                       <zwDropDown placement="bottomRight">
                                           <zwButton slot="button" type="primary"   icon="down" >下拉查询菜单</zwButton>
                                           <zwMenu slot="menu">
                                               <zwMenuItem key="1">1st item</zwMenuItem>
                                               <zwMenuItem key="2">2nd item</zwMenuItem>
                                               <zwMenuItem key="3">3rd item</zwMenuItem>
                                           </zwMenu>
                                       </zwDropDown>
                                   </div>
                 </textarea>

           </div>
        </zwBox>




         <zwBox class="">
                     <div slot="zwCodeBoxDemo">

                        <zwDropDown  trigger="click" placement="bottomRight">
                            <zwButton slot="button" type="primary"   icon="down" >click trigger</zwButton>
                            <zwMenu slot="menu">
                                <zwMenuItem key="1">1st item</zwMenuItem>
                                <zwMenuItem key="2">2nd item</zwMenuItem>
                                <zwMenuItem key="3">3rd item</zwMenuItem>
                            </zwMenu>
                        </zwDropDown>
                        <zwDropDown trigger="mouse" placement="bottomLeft">
                            <zwButton slot="button" type="primary"   icon="down" >mouse trigger</zwButton>
                            <zwMenu slot="menu">
                                <zwMenuItem key="1">1st item</zwMenuItem>
                                <zwMenuItem key="2">2nd item</zwMenuItem>
                                <zwMenuItem key="3">3rd item</zwMenuItem>
                            </zwMenu>
                        </zwDropDown>

                    </div>



                     <div slot="zwCodeBoxMeta"  title="trigger ">

                      trigger 有两种 click 或者其他  click 就是鼠标点击才有反应  其他就是鼠标移入就会显示
                    </div>
                     <div slot="zwCodeBoxPanel"  >

                        <textarea ref="textarea" id="textarea" name="textarea">
                                <div slot="zwCodeBoxDemo">
                                             <zwDropDown  trigger="click" placement="bottomRight">
                                                                         <zwButton slot="button" type="primary"   icon="down" >click trigger</zwButton>
                                                                         <zwMenu slot="menu">
                                                                             <zwMenuItem key="1">1st item</zwMenuItem>
                                                                             <zwMenuItem key="2">2nd item</zwMenuItem>
                                                                             <zwMenuItem key="3">3rd item</zwMenuItem>
                                                                         </zwMenu>
                                                                     </zwDropDown>
                                                                     <zwDropDown trigger="mouse" placement="bottomLeft">
                                                                         <zwButton slot="button" type="primary"   icon="down" >mouse trigger</zwButton>
                                                                         <zwMenu slot="menu">
                                                                             <zwMenuItem key="1">1st item</zwMenuItem>
                                                                             <zwMenuItem key="2">2nd item</zwMenuItem>
                                                                             <zwMenuItem key="3">3rd item</zwMenuItem>
                                                                         </zwMenu>
                                                                     </zwDropDown>
                                           </div>
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
import zwButton from '../../../component/button/zwButton.vue';
import zwButtonGroup from '../../../component/button/zwButtonGroup.vue';
import zwIcon from '../../../component/icon/zwIcon.vue';
import zwRow from '../../../component/layout/zwRow.vue';
import zwBox from '../../../component/layout/zwBox.vue';
import zwDropDown from '../../../component/navigation/menu/zwDropDown.vue';
import zwMenu from '../../../component/navigation/menu/zwMenu.vue';
import zwMenuItem from '../../../component/navigation/menu/zwMenuItem.vue';

import zwCol from '../../../component/layout/zwCol.vue';
export default {
    components:{zwButton,zwIcon,zwBox,zwRow,zwCol,zwDropDown,zwMenu,zwMenuItem,zwButtonGroup},
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

</style>
