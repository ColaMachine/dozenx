<template>

  <span style="display:inline">
    <span :class="groupWrapperClasses">
        <span :class="wrapperClasses">
            <span v-if="icon" class="zw-input-prefix .zw-input-group-addon">
                <zwIcon v-if="icon" :type="icon" ></zwIcon>
            </span>
  <span v-if="this.$slots.prepend" :style="getComputedHeight" class="zw-input-group-prepend .zw-input-group-addon"><slot name="prepend" ></slot></span>

  <span v-if="this.$slots.prefix" :style="getComputedHeight" class="zw-input-prefix "><slot  name="prefix"></slot></span>
 <!-- <template v-if="type=='date'">
      <input :placeholder="placeholder" :id="id" :name="name" ref="text" :style="getComputedHeight" v-model="textvalue" type="date" class="zw-input" />
    </template>
  <template v-if="type=='password'">
    <input :placeholder="placeholder" :id="id" :name="name" ref="text" :style="getComputedHeight" v-model="textvalue" type="password" class="zw-input" />
  </template>
  <template v-if="type=='text'|| type==''|| !type">
    <input :id="id" :name="name" @change="onchange" :style="getComputedHeight" :placeholder="placeholder" ref="text" v-model="textvalue" type="text" class="zw-input" />
  </template>-->
  <input :id="id" :value="value" @input="handleInput"  :name="name" @change="onchange" :style="getComputedHeight" :placeholder="placeholder" ref="text" :type="type" class="zw-input" />
  <span v-if="this.closeSee" :style="getComputedHeight" class="zw-input-suffix " v-on:click="clearContent"><zwIcon  name="suffix" type="close"></zwIcon></span>
  <span v-if="this.$slots.suffix" :style="getComputedHeight" class="zw-input-suffix "><slot  name="suffix"></slot></span>
  <span v-if="this.$slots.append" :style="getComputedHeight" class="zw-input-group-append zw-input-group-addon"><slot  name="append"></slot></span>
  </span>
  </span>
  </span>

</template>
<script type="text/javascript">
  import zwIcon from "../icon/zwIcon.vue"
  export default {
    name: 'zwInput',
    components: {
      zwIcon
    },
    props: {value:{type:String},icon:{type:String},type:{type:String,default:"text"}, placeholder:{type:String,default:""},  clear:{type:Boolean,default:false}, id:{type:String}, name:{type:String}, defaultValue:{type:String}, lineHeight:{type:String}},
    data() {
      return {
        ok: false,
        textvalue: "",
        closeSee: false,
      };
    },
    computed: {
      getComputedHeight: function() {
        if(this.lineHeight) {
          return "height:" + this.lineHeight + ";line-height:" + this.lineHeight;
        }
        return "";
      },
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
      getType: function() {
        var type = "text";
        if(this.type == "password") {
          type = "password";
        }
        return type;
      },
      isGroup: function() {
        return this.$slots.prepend != null;
      },

      groupWrapperClasses: function() {

        var wrapperClasses = "";
        if(this.$slots.prepend != null || this.$slots.append != null) {
          wrapperClasses += " zw-input-group-wrapper  ";
        }
        if(this.$slots.suffix != null || this.$slots.prefix != null || this.clear) {
          wrapperClasses += " ";
        }

        return wrapperClasses;
      },
      wrapperClasses: function() {
        var wrapperClasses = "zw-input-wrapper";
        if(this.$slots.prepend != null || this.$slots.append != null) {
          wrapperClasses += " zw-input-group  ";
        }
        if(this.$slots.suffix != null || this.$slots.prefix != null || this.clear) {
          wrapperClasses += " zw-input-affix";
        }

        return wrapperClasses;

      }
    },
    mounted() {
      if(this.defaultValue) {
        this.textvalue = this.defaultValue;
      }
      if(this.clear && this.textvalue != null && this.textvalue.length > 0) {
        this.closeSee = true;
      } else {
        this.closeSee = false;
      }

    },
    methods: {
     handleInput(event) {
          var value = event.target.value;
          this.$emit('input', value); //触发 input 事件，并传入新值
        },
      onchange: function() {
        console.log("onchange");
        this.$emit('onchange', this.textvalue);
      },
      clearContent: function() {
        this.textvalue = "";
      }
    },
    watch: {
      defaultValue(curVal, oldVal) {
        this.textvalue = defaultValue;
      },
      textvalue(curVal, oldVal) {
        if(this.clear && curVal != null && curVal.length > 0) {
          this.closeSee = true;
        } else {
          this.closeSee = false;
        }
      }
    },

    events: {

    }
  };
</script>
<style>

</style>