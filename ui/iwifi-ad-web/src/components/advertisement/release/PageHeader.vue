<template>
  <div class="page-header">
    <div class="page-title">
      <Breadcrumb>
        <BreadcrumbItem>投放管理</BreadcrumbItem>
      </Breadcrumb>
    </div>
    <div class="button-panel">
      <Button type="info" size="large" @click="add">添加投放策略</Button>
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
