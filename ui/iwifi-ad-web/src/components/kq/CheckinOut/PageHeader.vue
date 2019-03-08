<template>
  <div class="page-header">
    <div class="page-title">
      <Breadcrumb>
        <BreadcrumbItem>考勤机考勤</BreadcrumbItem>
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
