<template>
  <div class="zw-code-box-panel"><slot></slot></div>
</template>
<script type="text/javascript">
export default {
        name: 'zwCodeBoxPanel',
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
        events: {

         },
        methods: {

        },
    };
</script>
<style>

.zw-code-box-panel{
    position:relative;
    font-size:12px;
    padding: 17px 16px 15px 20px;
    color: rgba(0,0,0,.65);
    border-top:1px dashed #e9e9e9;
}

</style>