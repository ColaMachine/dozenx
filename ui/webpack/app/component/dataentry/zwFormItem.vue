<template>
  <div class="zw-form-item form-group clearfix">
    <div :class="labelWrapper" v-if="label">
      <label>{{label}}</label>:
    </div>
    <div :class="controlWrapper">

      <div class="zw-form-item-control">
        <div class="zw-form-item-children">

          <slot></slot>
        </div>
      </div>
    </div>
  </div>
</template>
<script type="text/javascript">
  export default {
    name: 'zwFormItem',
    components: {},
    props: ["label"],
    data() {
      return {

      };
    },
    computed: {

      controlWrapper: function() {
        var cls = "zw-form-item-control-wrapper ";
        if(this.label) {
          cls += " zw-col-sm-16 zw-col-xs-16 zw-col-lg-16"
        }
        return cls;
      },
      labelWrapper: function() {
        var cls = " zw-form-item-label ";
        if(this.label) {
          cls += " zw-col-sm-6 zw-col-xs-8 zw-col-lg-8"
        }
        return cls;
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
  .zw-form-inline .zw-form-item {
    display: inline-block;
    margin-right: 16px;
    margin-bottom: 0;
  }
  
  .zw-form-item {
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
    margin-bottom: 24px;
    vertical-align: top;
  }
  
  .zw-form-inline .zw-form-item {
    display: inline-block;
    margin-right: 16px;
    margin-bottom: 0;
  }
  
  .zw-form-item-label {
    text-align: right;
    vertical-align: middle;
    line-height: 39.9999px;
    display: inline-block;
    overflow: hidden;
    white-space: nowrap;
  }
  
  .zw-form-item-control {
    line-height: 39.9999px;
    position: relative;
    zoom: 1;
  }
</style>