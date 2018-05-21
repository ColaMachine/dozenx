<template>
  <div class="page-header">
    <div class="page-title">
      <Breadcrumb>
        <BreadcrumbItem>平台列表</BreadcrumbItem>
      </Breadcrumb>
    </div>
    <div class="button-panel">
      <Button type="info" size="large" @click="add">新增合作平台</Button>
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
