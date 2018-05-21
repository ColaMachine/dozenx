<template>
  <ul class="search-bar">
    <li>
      <Input v-model="value.platformName" style="width:200px;" placeholder="平台名称" clearable></Input>
    </li>
    <li>
      <Button type="ghost" class="search-btn" @click="search">查询</Button>
    </li>
  </ul>
</template>
<script>
  export default {
    props: ['value'],
    data() {
      return {};
    },
    methods: {
      search() {
        this.$emit('on-search');
      },
    },
  };
</script>
