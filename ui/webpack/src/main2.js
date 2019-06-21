/*
const PATH = require("path");
console.log("path:"+PATH)
cats = require('../app/cats.js');
console.log("define cats");*/
//require("!style-loader!css-loader!../app/css/style.css");
//require("!style-loader!css-loader!../app/css/sass.scss");
import "../app/css/common.scss"
import "../app/css/btn.scss"
import "../app/css/menu.scss"
import "../app/css/dropmenu.scss"
import "../app/css/layout.scss"
import "../app/css/form.scss"
import "../app/css/tab.scss"
import "../app/css/app_dialog.scss"
import "../app/css/table.scss"
import "../app/css/iconlist.scss"
import "../app/css/slider_text.scss"
import "../app/css/widget.scss"


//import  "../../src/main/webapp/static/js/imageUtil.js"
//import "../../src/main/webapp/static/js/dom.js"
//import  "../../src/main/webapp/static/js/animation.js"

//
//
//import {PATH,getQueryString,Ajax} from "../../src/main/webapp/static/js/zwcommon"



//import app from "../app/vueTest"
//const app= require("../app/head/head.vue");

//document.write("It works1.");
//document.write(require("../app/content.js"));

import Vue from 'vue'
//Vue.prototype.common=common;
import VueRouter from 'vue-router'
//这个作为主页面

import vuePhoneIndex from '../app/module/phone/shopIndex.vue'

//初始化一个路由
new Vue({
  el: '#app',
  render: h => h(vuePhoneIndex)
})
Vue.use(VueRouter)
require('../app/module/route/shopRoute.vue');

