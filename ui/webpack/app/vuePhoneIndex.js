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
//import app from "./vueTest"
//const app= require("./head/head.vue");

//document.write("It works1.");
//document.write(require("./content.js"));

import Vue from 'vue'

//这个作为主页面

import vuePhoneIndex from './module/phone/vuePhoneIndex.vue'

import router from "./module/route/route.js"
//初始化一个路由
new Vue({
  el: '#head',
  render: h => h(vuePhoneIndex)
})


