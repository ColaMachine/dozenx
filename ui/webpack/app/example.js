/*
const PATH = require("path");
console.log("path:"+PATH)
cats = require('./cats.js');
console.log("define cats");*/
//require("!style-loader!css-loader!./css/style.css");
//require("!style-loader!css-loader!./css/sass.scss");
import "./css/common.scss"
import "./css/btn.scss"
import "./css/menu.scss"
import "./css/dropmenu.scss"
import "./css/layout.scss"
import "./css/form.scss"
import "./css/tab.scss"
import "./css/app_dialog.scss"
import "./css/table.scss"
import "./css/iconlist.scss"
import "./css/slider_text.scss"
import "./css/widget.scss"
import "./css/codedemo.scss"
//import "mavon-editor/dist/css/index.css"


//
import mavonEditor from 'mavon-editor'
//require ('mavon-editor/dist/css/index.css')
//// use
Vue.use(mavonEditor)
//


//import app from "./vueTest"
//const app= require("./head/head.vue");

//document.write("It works1.");
//document.write(require("./content.js"));

import Vue from 'vue'


import head from './module/web/example.vue'

import router from "./module/route/route.js"




new Vue({
  el: '#head',
  router,
  render: h => h(head)
})






