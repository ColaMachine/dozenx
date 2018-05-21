/**
*基于Checkbox/CheckboxGroup组件进一层封装全选逻辑
*/
<template>
  <div class="check-group">
    <Checkbox class="check-group-checkbox check-group-checkbox-all"
              :indeterminate="indeterminate"
              :value="checkAll"
              @click.prevent.native="handleCheckAll">{{checkAllLabel}}
    </Checkbox>
    <!--绑定value字段 -->
    <div class="check-group-checkbox-group">
      <CheckboxGroup v-model="selection">
        <Checkbox v-for="item in options" :key="item[valueField]" :label="item[valueField]"
                  class="check-group-checkbox">
          {{item[labelField]}}
        </Checkbox>
      </CheckboxGroup>
    </div>
  </div>
</template>
<script>
  export default {
    name: "check-group",
    props: {
      //全选label
      checkAllLabel: {
        type: String,
        default() {
          return '全部';
        }
      },
      //选项list
      options: Array,
      //选项的label字段名
      labelField: {
        type: String,
        default() {
          return 'label';
        }
      },
      //选项的value字段名
      valueField: {
        type: String,
        default() {
          return 'value';
        }
      },
      value: Array
    },
    computed: {
      indeterminate() {
        if (this.selection.length > 0 && this.selection.length < this.options.length) {
          return true;
        } else {
          return false;
        }
      },
      checkAll() {
        if (this.selection.length == this.options.length) {
          return true;
        } else {
          return false;
        }
      }
    },
    data() {
      return {
        // indeterminate: false,
        // checkAll: false,
        selection: this.value
      }
    },
    methods: {
      handleCheckAll() {
        if (this.checkAll) {
          this.selection = [];
        } else {
          this.selection = this.options.map((item) => {
            return item[this.valueField];
          });
        }
      },
      // checkAllGroupChange(data) {
      //   if (data.length === this.options.length) {
      //     this.checkAll = true;
      //   } else if (data.length > 0) {
      //     this.checkAll = false;
      //   } else {
      //     this.checkAll = false;
      //   }
      // }
    },
    watch: {
      selection(newVal, oldVal) {
        this.$emit('input', newVal);
      }
    }
  }
</script>
<style scoped>

</style>
