<template>



        <div id="router">

          <!-- 路由出口 -->
          <!-- 路由匹配到的组件将渲染在这里 -->
          <router-view></router-view>
        </div>

</template>
<script>
  import zwRow from '../../component/layout/zwRow.vue';
  import zwCol from '../../component/layout/zwCol.vue';
  import zwIcon from '../../component/icon/zwIcon.vue';
  import zwBox from '../../component/layout/zwBox.vue';
  import zwButton from '../../component/button/zwButton.vue';
  import zwLayout from '../../component/layout/zwLayout.vue';
  import zwHeader from '../../component/layout/zwHeader.vue';
  import zwContent from '../../component/layout/zwContent.vue';
  import zwSider from '../../component/layout/zwSider.vue';
  import zwFooter from '../../component/layout/zwFooter.vue';
  import zwCollapse from '../../component/datadisplay/zwCollapse.vue';
  import zwPanel from '../../component/datadisplay/zwPanel.vue';
  import apiPath from '../../component/datadisplay/apiPath.vue';
  import zwMenu from '../../component/navigation/menu/zwMenu.vue';
  import zwMenuItem from '../../component/navigation/menu/zwMenuItem.vue';
  import zwSubMenu from '../../component/navigation/menu/zwSubMenu.vue';
  import zwMenuItemGroup from '../../component/navigation/menu/zwMenuItemGroup.vue';
  import zwDropDown from '../../component/navigation/menu/zwDropDown.vue';
  import zwForm from '../../component/dataentry/zwForm.vue';
  import zwFormItem from '../../component/dataentry/zwFormItem.vue';
  import zwInput from '../../component/dataentry/zwInput.vue';
  export default {
    components: {
      zwRow, zwCol, zwIcon, zwBox, zwHeader, zwContent, zwFooter, zwLayout, zwSider, zwCollapse, zwPanel, apiPath,
      zwButton, zwMenu, zwMenuItem, zwMenuItemGroup, zwSubMenu, zwDropDown, zwInput, zwFormItem, zwForm
    },
    data() {
      return {
        msg: 'Hello World!',
        isLogin: false,
        loginName: "",
      }
    },
    mounted() {
     // $.ajax(PATH + "/userinfo", function(data) {
     //   })
        //判断是否已经登录
    }
  }
</script>
<style scoped>
  #example {
    background: red;
    height: 100vh;
  }
</style>