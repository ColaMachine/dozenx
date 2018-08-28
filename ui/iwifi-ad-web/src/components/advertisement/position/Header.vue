<template>
   <div class="RightTop">
          <Breadcrumb>
             <BreadcrumbItem>广告位管理</BreadcrumbItem>
          </Breadcrumb>
          <Button type="warning" @click="addSerach">添加广告位</Button>
     </div>
</template>
<script>
import AppModel from "./AppModel.vue";
export default {
  components: {
    AppModel
  },
  data() {
    return {
      modal2: false
    };
  },
  methods: {
    addSerach(){
      this.$emit('on-add');
    },
  }
};
</script>
<style>

</style>


