<template>
	<div id="tt" style="width:100%;height:100%">
        test
	</div>

	<!-- 遍历本月的日期 -->
</template>

<script>

import calendarview from '../../calendarview1'
export default {
  name: 'CalendarView',
  props: {
  },
  computed: {
  },
  mounted(){

  var d = new CalendarView();
  	var ca =d;

  	d.attachTo("tt");
  	d.init();
  },
  methods: {
  }
};
</script>


<style lang="scss" >



.dp_table {
	/*border: solid 10px  #C3D9FF;*/

	font-family:Tahoma;
	cursor:pointer;
	width:160px;
	text-align:center;
}
.dp_table tr{
	height: 20px;
}
/* ����calendar ��һ���ϵ���������ʽ*/
.dp_table  th {
	width:14.2%;
	/*background-color: #C3D9FF;*/
	font-weight:normal;

}
.dp_ym_th {

	text-align:left;
}
.dp_ym_th span{
	font-size:15px;
	text-align:left;
}

.dp_prev_month {
	background: url("../../assets/images/../../assets/images/combined_v43_vr.png") no-repeat scroll -243px -35px
		transparent;
	cursor: pointer;
	height: 15px;
	width: 15px;
background-repeat:no-repeat;
	 display:block;
}

.dp_next_month {
	background-position: -258px -35px;
}
/* ����calendar ��һ��tr������ƶ��ı���ɫ*/
.dp_title_tr:hover{background-color:#C3D9FF;}

/* ����calendar �ڶ���*/
.dp_week_title_tr th {
	font-size: 12px;
	text-align:center;

}


.dp_table  td {

	font-size: 12px;
}
/*.dp_table*/ .othermonth{
	color: gray;
}
.dp_table tr td:hover{ background-color: #C3D9FF }

.selectedTr {
	background-color: #AACCEE;
}
.todayTd{
	/*border:1 solid black;*/
	background-color: yellow;
}
.selectedTd{
	background-color: #557799;
	color: white;
}

/*.mouseOverTd{
	background-color: green;
}
.selectedday{
	background-color: #557799;
	color: white;
}

*/
/***����������ڸ�***/
.dp_canopy_div{
	font-weight: normal;
	font-size: 15px;
	text-align: left;
	font-family: Tahoma;
	cursor: pointer;
	width:160px;
	height:20px;
}
.dp_canopy_div:hover{background-color:#C3D9FF;}


/*********�ҵ�����***********/
.mycal_h2{
	font-weight: normal;
	font-size: 15px;
	text-align: left;
	font-family: Tahoma;
	cursor: pointer;
	width:160px;
	height:20px;
	position:relative;
	margin-top:0px;
}
.mycal_arrow_sp {
    display: inline-block;
    width: 11px;
    height: 11px;
    line-height: 10px;
    vertical-align: middle;
    background: url("../../assets/images/combined_v43_vr.png") repeat scroll -141px -81px  transparent;
}
.mycal_title_sp {

}
.clstMenu {
    background: url("../../assets/images/combined_v43_vr.png") repeat scroll -217px 0px transparent;
    border: 1px solid rgb(181, 181, 181);
    border-radius: 1px 1px 1px 1px;
    height: 11px;
    font-size: 1px;
    overflow: hidden;
    position: absolute;
    right: 5px;
    text-indent: -100px;
    top: 5px;
    width: 11px;
    display:inline-block;
}

.mycal_h2:hover{background-color:#C3D9FF;}


.dp_types_div {
	width:130px;
	margin-top:-10px;
	margin-left: 12px;
}
.dp_types_div div {
	width:130px;
	display:inline-block;
	height:20px;
	line-height: 20px;
	font-size:12px;
}
.dp_types_div div:hover{background-color:#C3D9FF;}
.dp_types_div span{
	display:inline-block;
	vertical-align:middle;
}
.dp_types_icon_sp{
	width:15px;
	height:15px;
	background-color:red;
	border-radius: 2px 2px 2px 2px;

}

/*********************�˵�***/

.ccp-rb-color {
    border: 1px solid white;
    cursor: pointer;
    height: 14px;
    margin: 0px 4px 4px 0px;
    width: 14px;
    vertical-align: top;
}
.ccp-rb-color:hover {border:1px solid black}

.goog-inline-block {
    position: relative;
    display: inline-block;
}

.goog-filterobsmenuitem {
    color: rgb(51, 51, 51);
    cursor: pointer;
    list-style: none outside none;
    white-space: nowrap;
}


.calcolor-menuitem {
    border-top: 1px solid rgb(235, 235, 235);
    padding-top: 17px;
    padding-bottom: 5px;
}
.goog-menuitem, .goog-tristatemenuitem, .goog-filterobsmenuitem {
    position: relative;
    color: rgb(51, 51, 51);
    cursor: pointer;
    list-style: none outside none;
    margin: 0px;
    padding: 6px 8em 6px 30px;
    white-space: nowrap;
}
.goog-menuitem {
    color: rgb(51, 51, 51);
    cursor: pointer;
    list-style: none outside none;
    margin: 0px;
    padding: 6px 44px 6px 16px;
    white-space: nowrap;
    transition: background 0.13s ease 0s;
}
/* * {
    font-weight: normal !important;
} */

.gcal-popup {
    visibility: hidden;
}
.goog-menu {
    cursor: default;
    font-size: 13px;
}
.goog-menu {
    white-space: nowrap;
}
body {
    font: small Arial,sans-serif;
}

.bcp-selected {
    background: url("../../assets/images/combined_v43_vr.png") repeat scroll -95px -50px transparent;
    height: 10px;
    width: 10px;
    left: -1px;
    top: -1px;
    margin: 3px;
    overflow: hidden;
    position: relative;
    vertical-align: top;
}




.wk_mv_table{
word-break: keep-all;
}
.wk_mv_table td{
word-break: keep-all;
}
html{-webkit-box-sizing: content-box;
-moz-box-sizing: content-box;
box-sizing: content-box;
}
*{-webkit-box-sizing: content-box;
-moz-box-sizing: content-box;
box-sizing: content-box;
}
.calHeader .zippy-arrow {
	height: 12px;
	position: relative;
	vertical-align: middle;
	width: 12px;
}

.zippy-arrow2 {
	display: inline-block;
	width: 11px;
	height: 11px;
	line-height: 10px;
	vertical-align: middle;
	background: url("../../assets/images/combined_v43_vr.png") repeat scroll -153px -81px;

}

.zippy-arrow {
	display: inline-block;
	width: 11px;
	height: 11px;
	line-height: 10px;
	vertical-align: middle;
	background: url("../../assets/images/combined_v43_vr.png") repeat scroll -141px -81px
		transparent;
}

.bubbleclose {
	background: url("../../assets/images/combined_v43_vr.png") repeat scroll -228px -48px
		transparent;
	cursor: pointer;
	height: 13px;
	right: 5px;
	top: 5px;
	width: 13px;
	position: absolute;
}

.table2 { /*锟斤拷锟叫癸拷锟斤拷锟斤拷锟斤拷锟斤拷锟絫able*/
	TABLE-LAYOUT: fixed;
	height: 100px;
	width: 100%;
	border: solid 1px #A2BBDD;
}

.table2 tbody tr td {
	border-right: 1px solid #DDDDDD;
	position:relative;/**because in load html in chrome the div-tg-time 's 100% height doesn't work it's height is 0**/
	/*display:inline-block;*/

}

/* 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷*/
.firsttable { /*border: solid 1px  #C3D9FF;*/
	width: 1012px;
}

.wk-weektop { /*background-color: #C3D9FF; */
	font-size: 12px;
	color: blue;
	/*border:1px solid red;*/
	width: 100%;
	border: red;
	/* cellspacing:2;*/
}

.wk-weektop tr {
	padding-top: 10px;
}

.wk-weektop tr td {
	padding-bottom: 3px;
	/*border:1px solid red;*/
}

.innertoptable {
	height: 10px;
	border-left: 1px solid #E8EEF7;
	border-top: 1px solid #E8EEF7;
	border-bottom: 1px solid #E8EEF7;
	/*border:1px solid red;*/
}

.innertoptable tr td {
	border-right: 1px solid #E8EEF7;
	width: 129px;
	height: 15px;
	border-collapse: collapse;
	empty-cells: show;
	/*background-color:#E8EEF7;*/
	/* bordercolorlight="#000000"; bordercolordark="#eeeeee" ;*/
	/*border-left:solid 1px #E8EEF7;
	border-right:solid 1px #E8EEF7;*/
}

.wk-allday {
	width: 921px;
}

.dragTable {
	font-size: 12px;
	border-top: 1px solid #3366cc;
	margin-bottom: 10px;
	width: 100%;
	background-color: #FFFFFF;
}

td {
	vertical-align: top;
}

.dragTR {
	cursor: move;
	color: #7787cc;
	background-color: #e5eef9;
	height: 20px;
	padding-left: 5px;
	font-weight: bold;
}

.style1 {
	color: #9966FF
}

.td-time-pri {
	width: 75px;
}

.wk-tzlabel {
	width: 75px;
}

.tg-time-pri {
	background-color: #E8EEF7;
	color: #446688;
	border-bottom: 1px solid #DDDDDD;
	/*border-right:1px solid #DDDDDD;*/
	font-size: 12px;
	padding-left: 18px;
	padding-right: 3px;
	height:39px;
	/*padding-top:1px;
	height: 40px;*/

}

.tg-time-pri div {
	position: absolute;
	width: 100%;
	height: 100%;
	z-index: 1;
}

.div-tg-time {
/** 	position: absolute; because in load html in chrome the div-tg-time 's 100% height doesn't work it's height is 0 the origin value is relative**/
	width: 100%;
	height: 100%;
	padding: 0px 0px 0px 0px;
	z-index: 1;
}

.td-time-pri-today { /*td锟斤拷锟斤拷锟斤拷锟缴拷锟斤拷锟�/
	width: 100%;
	height: 100%;
	background: rgba(213, 202, 240, 0.2);


}

.tg-col {
	background-color: #E8EEF7;
}

.dateDIV { /*border-left:1px solid #DDDDDD;
	border-right:1px solid #DDDDDD;
	border-bottom:1px solid #DDDDDD;*/
	height: 40px;
	align: left;
	width: 132px;
	margin-left: -1px;
	/*	//overflow:scroll; //++
	//white-space:normal;word-break:break-all;table-layout:fixed
	//padding:0px 0px 0px 0px;*/
}

.calendarEventDialog {
	position: absolute;
	display: none;
	/*border:1px solid #0060C0;*/
	background-color: #ffffff;
	height: 80px;
	z-index: 2000;
	border: 1px solid #696;
	padding: 8px;
	text-align: center;
	width: 200px;
	/*
border-radius: 8px;*/
	box-shadow: #666 0px 0px 10px;
	background: white;
	width: 300px;
	height: 90px;
}

.calendarEventDialog label {
	font-weight: bold;
	font-size: 15px;
}

.calendarEventDialog>div {
	text-align: left;
	padding: 3px;
}

/*.btn {
	margin-top: 5px;
margin-bottom: 5px;
margin-left:5px;
color: #333;
background-color: #FFF;

border: 1px solid #CCC;
box-sizing: border-box;
border-radius: 4px;

}
.btn.focus, .btn:focus, .btn:hover {
    color: #333;
background-color: #E6E6E6;
border-color: #ADADAD;
}*/


.calendarEventBar DT {
	z-index: 1;
	margin: 0px;
	/*BACKGROUND-COLOR: #a32929;*/
	width: 100%;

}

.calendarEventBar {
	border: 0;
	background-color: white;
	width: 90%;
	cursor:pointer;
	min-height:23px;
}
.calendarEventBar dt{
    display:none;
}

.calendarEventBar dl {
	alter: left;
	border: 1px solid #a32929;/* #e0b9b9;*/
	margin: 0px 0px 0px 0px;
	width: 100%;
	height: auto;/*100%;*/
	font: normal 12px/22px "宋体";
	background-color: #F6C4CE;
	overflow: hidden;
}

.calendarEventBar_side {
	padding: 3px 3px 3px 3px;
}

.calendarEventBar DD {
	text-align: left;
	margin: 0px;
	HEIGHT: 100% ;
	 width: 100%;
	left: -111px;
	overflow: hidden;
	line-height:12px;
	font-size:12px;

}

.calendarEventBar DIV {
	HEIGHT: 1px;
	cursor: s-resize;
	position: absolute;
	bottom: 25px;
	width: 100%;
	text-align: center;
	/*border:1px solid blue;*/
}

.tg-timedevents {
	HEIGHT: 101px;
	/*overflow:scroll;rightnow*/
}

.wk-scrolltimedevents {

	overflow-x: hidden;
	overflow-y: scroll;
}

.horizontal-line-container {
	width: 100%;
	height: 0px;
	position: relative;
	/*border:1px solid red;*/
}

.horizontal-line-container2 {
	/*z-index: -5;*/
	width: 100%;
	position: absolute;
	/*border:1px solid red;*/
}

.horizontal-line {
	z-index: -14;
height: 39px;
	width: 100%;
	border-bottom: 1px solid #DDDDDD;
}

.horizontal-line div {
	z-index: -3;
	border-bottom: 1px dotted #DDDDDD;
	/*height: 30px;*/
}

.bored {
	border: 1px solid red;
}
/*.xb1, .xb2, .xb3, .xb4 {display:block; overflow:hidden; }
.xb1, .xb2, .xb3 {height:1px;}
.xb2, .xb3, .xb4 {background:#C3D9FF; border-left:1px solid #C3D9FF; border-right:1px solid #C3D9FF;}
.xb1 {margin:0 5px; background:#C3D9FF;}
.xb2 {margin:0 3px; border-width:0 2px;}
.xb3 {margin:0 2px;}
.xb4 {height:2px; margin:0 1px;}*/

.ca-ty-ch-wrp a{
    cursor:pointer;

    padding-top:4px;
    padding-left:5px;
    padding-right:5px;
    padding-bottom:4px;
    border:1px solid #ddd;
    color: #337ab7;
    /*border-left:1px solid rgb(162, 187, 221);
    border-top:1px solid rgb(162, 187, 221);
    border-bottom:1px solid rgb(162, 187, 221);*/
margin-left:-1px;
}
.ca-ty-ch-wrp a:hover{

   background-color: #eee;
   border-color: #ddd;
}
.ca-ty-ch-wrp a:first-child{
border-top-left-radius: 4px;
border-bottom-left-radius: 4px;

  /*  border-right:1px solid rgb(162, 187, 221);*/

}
.ca-ty-ch-wrp a:last-child{
border-top-right-radius: 4px;
border-bottom-right-radius: 4px;

  /*  border-right:1px solid rgb(162, 187, 221);*/

}
.active{
z-index: 2;
color: #fff ;
cursor: default;
background-color: #337ab7;
border-color: #337ab7;
}
.ca-ty-ch-wrp .active{
color: #fff ;
}
.ca-ty-ch-wrp .active:hover{

background-color: #3aaaaa;
}
.wk_mv_table  td{
border:1px solid #aaa;

width:140px;
height:150px;
position:relative;
/*padding-top:25px;*/
}
.wk_mv_table  td:hover{ background-color: #C3D9FF }
.wk_mv_table{
width:100%;
}


#editForm{
position:absolute;
top:50px;
left:50px;
height:500px;
background-color:#fff;
widht:500px;
border:1px solid red;
z-index:2000;
}

.monthHead{

background-color: #E8EEF7;

color: #446688;
border-bottom: 1px solid #DDDDDD;
/* border-right: 1px solid #DDDDDD; */
font-size: 12px;
padding-left: 18px;
padding-right: 3px;
height: 39px;

}
.monthBody{


background-color: #fff;

color: #446688;
border-bottom: 1px solid #DDDDDD;
/* border-right: 1px solid #DDDDDD; */
font-size: 12px;
padding-left: 18px;
padding-right: 3px;
height: 39px;
}

.monthBody .calendarEventBar{
display:inline-block;
width:auto;
height:40px;
margin-left:20px;
}
.monthBody .calendarEventBar DD {

    line-height: 16px;
    font-size: 16px;
}
.month-list{
width:98%;
}

.month .calendarEventBar dd{
      line-height:24px;
       }

       #date_picker{
        display:none;
       }
</style>
