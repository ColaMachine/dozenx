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

</style>