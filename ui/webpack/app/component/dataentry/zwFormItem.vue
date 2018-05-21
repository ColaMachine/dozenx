
<template>
  <div class="zw-form-item form-group">
   <label v-if="label">{{label}}</label><slot ></slot>

  </div>
</template>
<script type="text/javascript">

export default {
        name: 'zwFormItem',
         components:{},
        props: [  "label"  ],
        data () {
            return {

            };
        },
        computed: {

        },
        mounted () {


        },
        methods: {

        },


         events: {


          }
    };
</script>
<style>

</style>