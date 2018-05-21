<template>
  <div>
    <div>
      <CheckGroup v-for="item in formData" v-model="item.value"
                  labelField="name"
                  valueField="id"
                  :key="item.renderKey"
                  :checkAllLabel="item.checkAllLabel"
                  :options="item.options"></CheckGroup>
      <Row>
        <Col span="8">&nbsp;</Col>
        <Col span="8">
        <Button type="primary" size="large" @click="submit" long>提交</Button>
        </Col>
        <Col span="8">&nbsp;</Col>
      </Row>
    </div>
  </div>
</template>
<script type="text/javascript">
  import CheckGroup from '@/components/CheckGroup.vue';

  export default {
    components: {
      CheckGroup
    },
    props: ['formData', 'resetFlag'],
    data() {
      return {};
    },
    methods: {
      submit() {
        let selection = [];
        this.formData.forEach((item) => {
          selection = selection.concat(item.value);
        });
        console.log('selection', selection);
        this.$emit('on-submit', selection);
      }
    },
    watch: {}
  };
</script>
<style lang="less">
  .check-group-checkbox-all {
    font-weight: bold;
  }

  .check-group-checkbox-group {
    margin-left: 20px;
  }

  .check-group-checkbox {
    margin: 10px;
  }
</style>
