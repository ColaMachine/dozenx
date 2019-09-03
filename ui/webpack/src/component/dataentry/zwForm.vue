<template>
  <form :class="getComputeClass">
    <slot></slot>
  </form>
</template>
<script type="text/javascript">
/**
## 属性 layout="inline"
form 有两种模式 inline 和非inline  inline的话 所有的输入框都会在一行显示 排列不下会换行  样式为zw-form-inline
不加inline  每个formitem都独占一行 适用于 登录框
##






**/
  export default {
    name: 'zwForm',
    components: {},
    props: [
      "layout"

    ],
    data() {
      return {

      };
    },
    computed: {
      getComputeClass: function() {
        var cls = "zw-form ";
        if(this.layout == "inline") {
          cls += " zw-form-inline"
        }
        return cls
      }
    },
    mounted() {

    },
    methods: {

    },

    events: {

    }
  };
</script>
<style>
  .zw-form {
    font-family: "Chinese Quote", -apple-system, BlinkMacSystemFont, "Segoe UI", "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "Helvetica Neue", Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol";
    font-size: 14px;
    font-variant: tabular-nums;
    line-height: 1.5;
    color: rgba(0, 0, 0, 0.65);
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    margin: 0;
    padding: 0;
    list-style: none;
  }
  
  .zw-form {
    font-family: "Chinese Quote", -apple-system, BlinkMacSystemFont, "Segoe UI", "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "Helvetica Neue", Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol";
    font-size: 14px;
    font-variant: tabular-nums;
    line-height: 1.5;
    color: rgba(0, 0, 0, 0.65);
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    margin: 0;
    padding: 0;
    list-style: none;
  }
</style>