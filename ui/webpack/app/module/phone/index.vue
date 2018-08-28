<template>
  <div>
    <div id="router">
      <router-view></router-view>
    </div>

  </div>

</template>
<script>
  import zwLayout from '../../component/layout/zwLayout.vue';
  import zwHeader from '../../component/layout/zwHeader.vue';
  import zwContent from '../../component/layout/zwContent.vue';
  import zwSider from '../../component/layout/zwSider.vue';
  import zwFooter from '../../component/layout/zwFooter.vue';
  import zwRow from '../../component/layout/zwRow.vue';
  import zwCol from '../../component/layout/zwCol.vue';
  import zwIcon from '../../component/icon/zwIcon.vue';
  import zwBox from '../../component/layout/zwBox.vue';
  import zwTabs from '../../component/datadisplay/zwTabs.vue';
  import zwTabPanel from '../../component/datadisplay/zwTabPanel.vue';
  import zwCrousel from '../../component/datadisplay/zwCrousel.vue';
  import zwChocolatePanel from '../../component/datadisplay/zwChocolatePanel.vue';
  import zwSliderText from '../../component/datadisplay/zwSliderText.vue';
  import zwAppImgList from '../../component/datadisplay/zwAppImgList.vue';
  import zwAppBottomBar from '../../component/datadisplay/zwAppBottomBar.vue';

  import zwButton from '../../component/button/zwButton.vue';

  import zwButtonGroup from '../../component/button/zwButtonGroup.vue';
  import zwDropDown from '../../component/navigation/menu/zwDropDown.vue';
  import zwMenu from '../../component/navigation/menu/zwMenu.vue';
  import zwMenuItem from '../../component/navigation/menu/zwMenuItem.vue';
  import zwDataMenuItems from '../../component/navigation/menu/zwDataMenuItems.vue';
  import zwSubMenu from '../../component/navigation/menu/zwSubMenu.vue';
  import zwMenuItemGroup from '../../component/navigation/menu/zwMenuItemGroup.vue';

  import phoneMain from '../../module/phone/main.vue';

  export default {
    components: {
      zwRow,
      zwCol,
      zwIcon,
      zwBox,
      zwHeader,
      zwContent,
      zwFooter,
      zwLayout,
      zwSider,
      zwCrousel,
      zwTabs,
      zwTabPanel,
      zwChocolatePanel,
      zwAppImgList,
      zwSliderText,
      zwAppBottomBar,
      zwDropDown,
      zwMenu,
      zwMenuItem,
      zwMenuItemGroup,
      zwSubMenu,
      zwButton,
      zwButtonGroup,
      zwDataMenuItems,
      phoneMain
    },
    name: "phoneIndex",
    data() {

      return {

      }
    },
    mounted() {

    },
    computed: {

    },
    methods: {

    }
  }
</script>
<style scoped>

</style>