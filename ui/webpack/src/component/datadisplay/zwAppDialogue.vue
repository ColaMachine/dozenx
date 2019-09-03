
<template>

<div class="m-Map" ><div id='allmap' style="width:100vw,height:100vh"></div>

    <div  id="zw-app-bottom-dialog" class="zw-app-bottom-dialog">
        <div class="item">
            <div id="mapDialogueName" class="mainTitle">主标题</div>
            <div id="mapDialogueDist" class="subTitle">描述</div>
        </div>
        <div class="item">
            <div class="title">项目一：</div>
            <div id="mapDialogueAddress" class="content">123123</div>
        </div>
        <div class="item">
            <div class="title">项目二：</div>
            <div id="mapDialogueContent" class="content">123123</div>
        </div>
        <div class="buttons">

        </div>
        <div class="buttons">

            <zwButton type="blue" sizeNum="large" color="red" > 确认</zwButton>
            <zwButton type="red" sizeNum="large" color="red" > 取消</zwButton>

        </div>

    </div>
</div>

</template>
<script type="text/javascript">
import zwIcon from '../icon/zwIcon.vue';
import zwButton from '../button/zwButton.vue';
export default {
        name: 'zwAppDialogue',
         components:{zwIcon,zwButton},
        props:   [],
        data () {
            return {
                collapse:true,
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
