<template>
   <div class="RightTop">
          <Breadcrumb>
             <BreadcrumbItem>广告策略列表</BreadcrumbItem>
          </Breadcrumb>
          <Button type="warning" @click="serachStr()" size="large">创建策略</Button> 
     </div>
</template>
<script>

export default {
  data() {
    return {
      modal2: false
    };
  },
  methods: {
    serachStr(){
      this.$emit('on-add');
    }
  }
};
</script>
<style>

</style>


