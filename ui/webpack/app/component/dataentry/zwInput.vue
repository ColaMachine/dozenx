
<template>


<span style="display:inline">
<span :class="wrapperClasses">
 <span v-if="icon" class="zw-input-prefix .zw-input-group-addon">
 <zwIcon v-if="icon" :type="icon" ></zwIcon></span>
<span  v-if="this.$slots.prepend" class="zw-input-group-prepend .zw-input-group-addon"><slot name="prepend" ></slot></span>

<span   v-if="this.$slots.prefix"  class="zw-input-prefix zw-input-group-addon"><slot  name="prefix"></slot></span>
<template v-if="type=='password'">
 <input :placeholder="placeholder" ref="text"  v-model="textvalue"  type ="password" class="zw-input" />
 </template >
 <template v-if="type!='password'">
  <input :id="id" :name="name" @change="onchange" :placeholder="placeholder" ref="text"  v-model="textvalue"  type ="text" class="zw-input" />
  </template >
  <span   v-if="this.closeSee"  class="zw-input-suffix zw-input-group-addon" v-on:click="clearContent"><zwIcon  name="suffix" type="close"></zwIcon></span>
 <span   v-if="this.$slots.suffix"  class="zw-input-suffix zw-input-group-addon "><slot  name="suffix"></slot></span>
    <span   v-if="this.$slots.append"  class="zw-input-group-append zw-input-group-addon"><slot  name="append"></slot></span>
 </span>
</span>


</template>
<script type="text/javascript">
import zwIcon from "../icon/zwIcon.vue"
export default {
        name: 'zwInput',
         components:{zwIcon},
        props: ["icon","placeholder","type","clear","id","name","defaultValue"],
        data () {
            return {
                    ok:false,
                    textvalue:"",
                    closeSee:false,
            };
        },
        computed: {

        /*canClear:function(){
            if(this.clear ){console.log("canclear");
               // if(this.$refs.text!=null && this.$refs.text.value!=null && this.$refs.text.value.length>0){
               console.log("canclear"+this.textvalue);
                 if(this.textvalue!=null && this.textvalue.length>0){console.log(this.textvalue);
                    return true;
                }
            }
            return false;

        },*/
        getType:function(){
            var type ="text";
            if(this.type=="password"){
                type="password";
            }
            return type;
        },
 isGroup:function(){
                return this.$slots.prepend !=null;
            },
            wrapperClasses:function(){
                var wrapperClasses = "zw-input-wrapper";
                if(this.$slots.prepend !=null || this.$slots.append!=null ){
                    wrapperClasses+=" zw-input-group";
                }
                if(this.$slots.suffix !=null || this.$slots.prefix!=null  || this.clear ){
                    wrapperClasses+=" zw-input-affix";
                }

                return wrapperClasses;

            }
        },
        mounted () {
          if(this.defaultValue){
                    this.textvalue = this.defaultValue;
                }
        if(this.clear && this.textvalue!=null && this.textvalue.length>0){
            this.closeSee=true;
          }else{
            this.closeSee=false;
          }

        },
        methods: {
            onchange:function(){console.log("onchange");
                this.$emit('onchange',this.textvalue);
            },
            clearContent:function(){
                this.textvalue="";
            }
        },
        watch:{
            defaultValue(curVal,oldVal){
                this.textvalue= defaultValue;
            },
          textvalue(curVal,oldVal){
          if(this.clear &&  curVal!=null && curVal.length>0){
            this.closeSee=true;
          }else{
            this.closeSee=false;
          }
            }
        },

         events: {


          }
    };
</script>
<style>

</style>