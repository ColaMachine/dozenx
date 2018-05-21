<template>
  <div class="serach">
          <Input placeholder="广告名称" clearable style="width: 200px"  v-model="d_name.name" :maxlength="4"></Input>
          <Button @click="serach">查询</Button>
  </div>
</template>
<script>
export default {
  props:{
    value:{
      type :Object,
      default(){
        return {}
      }
    }
  },
  data () {
    return {
      d_name : this.value 
    }
  },
  methods: {
    serach(){
      this.$emit('on-click')
    }
  }
}
</script>

