
<template>


<label v-on:click="choose" class="zw-radio-button-wrapper " :class='checked?"zw-radio-button-wrapper-checked":""'>
    <span class="zw-radio-button " :class='checked?"zw-radio-button-checked":""'>
        <input type="radio" class="zw-radio-button-input" value="on">
            <span class="zw-radio-button-inner"></span>
    </span>
    <span><slot></slot></span>
</label>


</template>
<script type="text/javascript">

import zwIcon from '../icon/zwIcon.vue';
export default {
        name: 'zwRadioButton',

         components:{zwIcon},
        props: [
            "checked","value"
        ],
        data () {
            return {

            };
        },
        computed: {

        },
        mounted () {

            //alert(this.value);
        },
        methods: {
            choose:function(){
           //this.$parent.changeSelect(this.value);
               this.$emit("changeSelect",this.value);
            }
        },


         events: {

          }
    };
</script>
<style>

.zw-radio-button-wrapper {
    margin: 0;
    height: 32px;
    line-height: 30px;
    color: rgba(0, 0, 0, 0.65);
    display: inline-block;
    -webkit-transition: all 0.3s ease;
    transition: all 0.3s ease;
    cursor: pointer;
    border: 1px solid #d9d9d9;
    border-left: 0;
    border-top-width: 1.02px;
    background: #fff;
    padding: 0 15px;
    position: relative;
}


.zw-radio-button-wrapper:first-child {
    border-radius: 4px 0 0 4px;
    border-left: 1px solid #d9d9d9;
}




.zw-radio-button-wrapper > .zw-radio-button {
    margin-left: 0;
    display: block;
    width: 0;
    height: 0;
}



.zw-radio-button-wrapper .zw-radio-inner, .zw-radio-button-wrapper input[type="checkbox"], .zw-radio-button-wrapper input[type="radio"] {
    opacity: 0;
    width: 0;
    height: 0;
}

.zw-radio-button-wrapper-checked {
    background: #fff;
    border-color: #1890ff;
    color: #1890ff;
    -webkit-box-shadow: -1px 0 0 0 #1890ff;
    box-shadow: -1px 0 0 0 #1890ff;
    z-index: 1;
}

.zw-radio-button-wrapper-checked:first-child {
    border-color: #1890ff;
    -webkit-box-shadow: none !important;
    box-shadow: none !important;
}
.zw-radio-button-wrapper-checked {
    background: #fff;
    border-color: #1890ff;
    color: #1890ff;
    -webkit-box-shadow: -1px 0 0 0 #1890ff;
    box-shadow: -1px 0 0 0 #1890ff;
    z-index: 1;
}
.zw-radio-button-wrapper:last-child {
    border-radius: 0 4px 4px 0;
}
</style>