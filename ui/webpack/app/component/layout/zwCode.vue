
<template>
  <code id="hello" class="zw-code"  ref="code" ><slot ref="abc"></slot></code>


</template>
<script type="text/javascript">

export default {
        name: 'zwCode',
        components: {  },
        props: {
            disabled:Boolean,
            text:String,
            type:String,
            title: String,
            className:String,
            rawHtml:String,
        },
        data () {
            return {

            };
        },
        computed: {

        },
        mounted () {
            //将textarea里的所有代码都照样输出 会碰到字符串转义问题 这里现在只处理了 < > 号
            this.$refs.code.innerText=this.$refs.code.querySelectorAll("textarea")[0].innerHTML .replace(new RegExp("&lt;","gm"),"<") .replace(new RegExp("&gt;","gm"),">");

        },
        methods: {

        },
    };
</script>
