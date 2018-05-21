
    Vue.component('apiPath', {

        template: '\
            <li   class="panel panel-default">\
            <div class="panel-heading">\
            <h2 class=" panel-title"><a>{{title}} </a><ul  class="pull-right"><li v-on:click="showOrHide" >显示/隐藏</li> </ul></h2>\
            </div>\
            <ul v-if="show"class="api-list panel-body">\
                <li   is="apiUrl"   \
         v-for="(value, key) in content"  \
         v-bind:title="key" \
         v-bind:content="value"\
              v-bind:url="title" \
         ></li> \
            </ul>\
            </li>\
        ',
        data:function(){
       return {show:false};
        },



        props: ['title',"content"],
         methods: {
            showOrHide: function (event) {


              this.show=!this.show;
            }
          }
    })


