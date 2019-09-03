<template>
  <div>
    <article>
      <section>
        <h2>Menu导航菜单</h2>
        <p>为页面和功能提供导航的菜单列表。</p>
        <h3>何时使用</h3>
        <p>导航菜单是一个网站的灵魂，用户依赖导航在各个页面中进行跳转。一般分为顶部导航和侧边导航，顶部导航提供全局性的类目和功能，侧边导航提供多级结构来收纳和排列网站架构。</p>
        <p>更多布局和导航的使用可以参考：通用布局。</p>
        <h3>代码演示<zwIcon type="windows" title="展开全部代码"></zwIcon></h3>
        <zwRow>
          <zwCol span=24>

            <!---------水平菜单--------------->
            <zwBox class="">

              <div slot="zwCodeBoxDemo">
                <zwAppTopBar >
                    <a slot="left" :href="getPathValue('/static/html/vue/vuePhoneIndex.html#/phoneMain')">
                  <img  class=""  :src="getPathValue('/static/img/header/back.png')">
                  </a>
                <span slot="middle"> 商品详情</span>

                           <a slot="right" href="">
                <img  class="" :src="getPathValue('/static/img/header/information.png')">
                </a>
                </zwAppTopBar>

              </div>
              <div slot="zwCodeBoxMeta" title="顶部导航">

                水平的顶部导航菜单。
              </div>
              <div slot="zwCodeBoxPanel">

                <textarea ref="textarea" id="textarea" name="textarea">
                 <zwAppTopBar mode="horizontal" color="white"></zwAppTopBar>

                </textarea>

              </div>

            </zwBox>

          </zwCol>

          <!---------树菜单--------------->


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
  import zwDataMenuItems from '../../../component/navigation/menu/zwDataMenuItems.vue';
  import zwSubMenu from '../../../component/navigation/menu/zwSubMenu.vue';
  import zwMenuItemGroup from '../../../component/navigation/menu/zwMenuItemGroup.vue';
  import zwCol from '../../../component/layout/zwCol.vue';

   import zwAppTopBar from '../../../component/navigation/zwAppTopBar.vue';

  export default {
    components: {
      zwButton, zwIcon, zwBox, zwRow, zwCol, zwDropDown, zwMenu, zwMenuItem, zwMenuItemGroup, zwSubMenu, zwButtonGroup, zwDataMenuItems,zwAppTopBar
    },
    data() {
      return {
        msg: 'Hello World!',
        loading: false,
        data: [{
          "id": "1",
          "data": {
            "menuName": "项目管理",
            "menuCode": "",
          },
          "childTreeNode": [{
            "data": {
              "menuName": "项目",
              "menuCode": "BusProject",
            },
            "childTreeNode": []
          }, {
            "data": {
              "menuName": "我的任务",
              "menuCode": "BusProject",
            },
            "childTreeNode": []
          }, {
            "data": {
              "menuName": "人员周报",
              "menuCode": "BusProject",
            },
            "childTreeNode": []
          }]
        }, {
          "id": "2",
          "data": {
            "menuName": "数据统计",
            "menuCode": "BusClock",
          },
          "childTreeNode": []
        }, {
          "id": "3",
          "data": {
            "menuName": "人事管理",
            "menuCode": "",
          },
          "childTreeNode": []
        }, {
          "id": "4",
          "data": {
            "menuName": "基础管理",
            "menuCode": "",
          },
          "childTreeNode": []
        }]
      }
    },
    computed: {
      getLoadingVal: function() {
        return this.loading;
      }
    },
    methods: {
    getPathValue:function(value){
                    return getPathValue(value);
                },
      changeLoading: function() {
        console.log("123");
        this.loading = !this.loading;
        // this.loading={delay:5};
        console.log(this.loading);
      },
      loadChild: function() {
      }
    }
  }
</script>
<style scoped>
.header{
 position:relative !important;
}
</style>