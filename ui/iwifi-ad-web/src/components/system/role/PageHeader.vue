<template>
  <div class="page-header">
    <div class="page-title">
      <Breadcrumb>
        <BreadcrumbItem>角色配置</BreadcrumbItem>
      </Breadcrumb>
    </div>
    <div class="button-panel">
      <Button type="info" size="large" @click="add">新增角色</Button>
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
