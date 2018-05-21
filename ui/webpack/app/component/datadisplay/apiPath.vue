
<template>



      <zwPanel >

        <div slot="title">
            <span v-on:click="showOrHide" class=" zw-row-title"><a>{{title}} </a><ul  class="pull-right"><li>显示/隐藏</li> </ul></span>
        </div>

      <div  slot="body" name="body">
        <li   is="apiUrl"
            v-for="(value, key) in content"
            v-bind:title="key"
            v-bind:content="value"
                 v-bind:url="title"
            ></li>
      </div>

      </zwPanel>




</template>
<script type="text/javascript">


import zwCollapse from './zwCollapse.vue';
import zwPanel from './zwPanel.vue';
import apiUrl from './apiUrl.vue';
export default {
        name: 'apiPath',
         components:{apiUrl,zwPanel,zwCollapse},
        props: ['title',"content"],
        data () {
            return {
                show:false
            };
        },
        computed: {

        },
        mounted () {

        },
        methods: {

showOrHide: function (event) {


              this.show=!this.show;
            }
        },


         events: {

          }
    };
</script>
<style>

</style>