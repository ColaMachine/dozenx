<template>
  <div class="page-header">
    <div class="page-title">
      <Breadcrumb>
        <BreadcrumbItem>素材管理</BreadcrumbItem>
      </Breadcrumb>
    </div>
    <div class="button-panel">
      <Button type="info" size="large" @click="add">添加素材</Button>
    </div>
  </div>
</template>
<script>
  export default {
    methods: {
      add() {
        this.$emit('on-add');
      }
    }
  };
</script>
