<template>
  <ul class="search-bar">
    <li>
      <Input v-model="value.name" style="width:200px;" placeholder="素材/内容" clearable></Input>
    </li>
    <li>
      <Button type="ghost" class="search-btn" @click="search">查询</Button>
    </li>
  </ul>
</template>
<script>

  export default {
    components: {},
    props: ['value'],
    data() {
      return {}
    },
    methods: {
      search() {
        this.$emit('on-search');
      },
    },
    created() {
    },
  };
</script>
