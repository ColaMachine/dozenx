<template>
  <form :class="getComputeClass">
    <slot></slot>
  </form>
</template>
<script type="text/javascript">
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