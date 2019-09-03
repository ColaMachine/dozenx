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
       <zwMenu mode="horizontal">
               <zwMenuItem key="mail">
                 <Icon type="mail" />Navigation One
               </zwMenuItem>
               <zwMenuItem key="app" disabled>
                 <Icon type="appstore" />Navigation Two
               </zwMenuItem>
               <zwSubMenu title={<span><Icon type="setting" />Navigation Three - Submenu</span>}>
                 <zwMenuItemGroup title="Item 1">
                   <zwMenuItem key="setting:1">Option 1</zwMenuItem>
                   <zwMenuItem key="setting:2">Option 2</zwMenuItem>
                 </zwMenuItemGroup>
                 <zwMenuItemGroup title="Item 2">
                   <zwMenuItem key="setting:3">Option 3</zwMenuItem>
                   <zwMenuItem key="setting:4">Option 4</zwMenuItem>
                 </zwMenuItemGroup>
               </zwSubMenu>
               <zwMenuItem key="alipay">
                 <a href="https://ant.design" target="_blank" rel="noopener noreferrer">Navigation Four - Link</a>
               </zwMenuItem>
             </zwMenu>
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

</zwCol>



<zwCol span=12>

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
import zwSubMenu from '../../../component/navigation/menu/zwSubMenu.vue';
import zwMenuItemGroup from '../../../component/navigation/menu/zwMenuItemGroup.vue';
import zwCol from '../../../component/layout/zwCol.vue';
export default {
    components:{zwButton,zwIcon,zwBox,zwRow,zwCol,zwDropDown,zwMenu,zwMenuItem,zwMenuItemGroup,zwSubMenu,zwButtonGroup},
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
