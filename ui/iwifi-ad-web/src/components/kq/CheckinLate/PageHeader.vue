<template>
  <div class="page-header">
    <div class="page-title">
      <Breadcrumb>
        <BreadcrumbItem>迟到记录</BreadcrumbItem>
      </Breadcrumb>
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
