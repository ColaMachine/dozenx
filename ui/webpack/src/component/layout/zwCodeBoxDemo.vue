
<template>
  <div class="clearfix  zw-code-box-demo"><slot></slot></div>
</template>
<script type="text/javascript">
export default {
        name: 'zwCodeBoxDemo',
        components: {  },
        props: {
            disabled:Boolean,
            text:String,
            type:String,
            title: String,
            className:String,
        },
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
    };
</script>
<style>
.zw-code-box-demo{
     border-bottom: 1px solid #e9e9e9;
     padding: 42px 20px 50px;
}
</style>