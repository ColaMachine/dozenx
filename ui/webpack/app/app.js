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
import "./css/smarthome.scss"
//import app from "./vueTest"
//const app= require("./head/head.vue");

//document.write("It works1.");
//document.write(require("./content.js"));

import Vue from 'vue'
import VueRouter from 'vue-router'

import head from './module/head/head.vue'
new Vue({
  el: '#head',
  render: h => h(head)
})


Vue.use(VueRouter)

/* 实例化一个vue */

import menu from './component/menu/menu.js'

//var menu = new zMenu();

//var menu = require('./component/menu/menu.js');


//menu.init("menu",menuList,{id:"id",url:"url",pid:"pid",name:"name"});

//



require('./module/route/route.vue');

