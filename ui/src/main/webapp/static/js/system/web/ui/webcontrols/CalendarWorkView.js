Using("System.Data.CalendarView");
function CalendarWorkView(){CalendarView.call(this);this.setHashCode();this.index = this.hashCode;}
t=CalendarWorkView.Extends(CalendarView, "CalendarWorkView");

<b class="xb1" style="width:1002px; "></b>
<b class="xb2"  style="width:1006px; "></b>
<b class="xb3"  style="width:1008px; "></b>
<b class="xb4"  style="width:1010px; "></b>
<table class="firsttable" cellpadding="0" cellspacing="0">
<tr>
<td>
<DIV id="topcontainerwk" style="height:0px " >
<TABLE class="wk-weektop" cellSpacing=0 cellPadding=0 >
<tr>
<TD class="wk-tzlabel" style="WIDTH: 76px; height:3" rowSpan=3>&nbsp;</TD>
<TD title="1/25 (周一)" scope=col align="center" >
<DIV class="wk-dayname style1"><SPAN class="ca-cdp20537 wk-daylink" style="CURSOR: pointer">1/25 (周一)</SPAN></DIV></TD>
<TD title="1/26 (周二)" scope=col align="center">
<DIV class="wk-dayname"><SPAN class="ca-cdp20538 wk-daylink" style="CURSOR: pointer">1/26 (周二)</SPAN></DIV></TD>
<TD title="1/27 (周三)" scope=col align="center">
<DIV class="wk-dayname"><SPAN class="ca-cdp20539 wk-daylink" style="CURSOR: pointer">1/27 (周三)</SPAN></DIV></TD>
<TD title="1/28 (周四)" scope=col align="center">
<DIV class="wk-dayname wk-today"><SPAN class="ca-cdp20540 wk-daylink" style="CURSOR: pointer">1/28 (周四)</SPAN></DIV></TD>
<TD title="1/29 (周五)" scope=col align="center">
<DIV class="wk-dayname"><SPAN class="ca-cdp20541 wk-daylink" style="CURSOR: pointer">1/29 (周五)</SPAN></DIV></TD>
<TD title="1/30 (周六)" scope=col align="center">
<DIV class="wk-dayname"><SPAN class="ca-cdp20542 wk-daylink" style="CURSOR: pointer">1/30 (周六)</SPAN></DIV></TD>
<TD title="1/31 (周日)" scope=col align="center">
<DIV class="wk-dayname"><SPAN class="ca-cdp20543 wk-daylink" style="CURSOR: pointer">1/31 (周日)</SPAN></DIV></TD>
<TD class="wk-dummyth" style="WIDTH: 16px" rowSpan=3>&nbsp;</TD>
</TR>
<TR height="10px">
<TD class="wk-allday" colSpan=7 height="24px">
<!--whole day event  start-->
<DIV id="weekViewAllDaywk" style="height:100%">
<TABLE class="innertoptable" cellSpacing=0 cellPadding=0 width="100%"  height="100%" >
<TR  height="100%">
<TD class="st-c st-s" rowSpan=2></td>
<TD class="st-c st-s" rowSpan=2></td>
<TD class="st-c st-s" rowSpan=2></td>
<TD class="st-c st-s" rowSpan=2></td>
<TD class="st-c st-s" rowSpan=2></td>
<TD class="st-c st-s" rowSpan=2></td>
<TD class="st-c st-s" rowSpan=2></td>
</tr>
</TABLE>
</div>
<!--whole day event end -->
</td>
</tr>

</table>

</div>
<!--topcontainerwk end-->
<!-- scrolltimedevents start-->

</td>
</tr>
<tr>
<td>
<DIV class="wk-scrolltimedevents" id="scrolltimedeventswk" style="HEIGHT: 419px;">
<TABLE style="TABLE-LAYOUT: fixed; overflow:scroll;height:300px" cellSpacing=0 cellPadding=0 closure_hashCode_tc9hoq="69" class="table2">
<TBODY>
<TR>
<TD>
<!-- timedevents start-->
<TABLE class="tg-timedevents" id="parentTable" style="HEIGHT: 983px" cellSpacing=0 cellPadding=0 width="100%">
<TBODY>

<TR>
<TD  class="tg-times-pri" width="75px;">
<DIV class="tg-time-pri" style="HEIGHT: 41px">上午12点</DIV>
<DIV class="tg-time-pri" style="HEIGHT: 41px">上午1点</DIV>
<DIV class="tg-time-pri" style="HEIGHT: 41px">上午2点</DIV>
<DIV class="tg-time-pri" style="HEIGHT: 41px">上午3点</DIV>
<DIV class="tg-time-pri" style="HEIGHT: 41px">上午4点</DIV>
<DIV class="tg-time-pri" style="HEIGHT: 41px">上午5点</DIV>
<DIV class="tg-time-pri" style="HEIGHT: 41px">上午6点</DIV>
<DIV class="tg-time-pri" style="HEIGHT: 41px">上午7点</DIV>
<DIV class="tg-time-pri" style="HEIGHT: 41px">上午8点</DIV>
<DIV class="tg-time-pri" style="HEIGHT: 41px">上午9点</DIV>
<DIV class="tg-time-pri" style="HEIGHT: 41px">上午10点</DIV>
<DIV class="tg-time-pri" style="HEIGHT: 41px">上午11点</DIV>
<DIV class="tg-time-pri" style="HEIGHT: 41px">下午12点</DIV>
<DIV class="tg-time-pri" style="HEIGHT: 41px">下午1点</DIV>
<DIV class="tg-time-pri" style="HEIGHT: 41px">下午2点</DIV>
<DIV class="tg-time-pri" style="HEIGHT: 41px">下午3点</DIV>
<DIV class="tg-time-pri" style="HEIGHT: 41px">下午4点</DIV>
<DIV class="tg-time-pri" style="HEIGHT: 41px">下午5点</DIV>
<DIV class="tg-time-pri" style="HEIGHT: 41px">下午6点</DIV>
<DIV class="tg-time-pri" style="HEIGHT: 41px">下午7点</DIV>
<DIV class="tg-time-pri" style="HEIGHT: 41px">下午8点</DIV>
<DIV class="tg-time-pri" style="HEIGHT: 41px">下午9点</DIV>
<DIV class="tg-time-pri" style="HEIGHT: 41px">下午10点</DIV>
<DIV class="tg-time-pri" style="HEIGHT: 41px">下午11点</DIV>
<DIV class="tg-nowptr" id="tgnowptr" style="LEFT: 0px; TOP: 461px"></DIV>
</TD>
<TD  align="center">
<div class="dateDIV">&nbsp;</div><div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>

<DIV class="tg-col-eventwrapper" id="tgCol0" style="MARGIN-BOTTOM: -1008px; HEIGHT: 1008px">
<DIV class="tg-gutter">


<DL class="" style="position:absolute;BORDER-LEFT-COLOR: #a32929; BORDER-BOTTOM-COLOR: #a32929; BORDER-TOP-COLOR: #a32929; HEIGHT: 16px; BACKGROUND-COLOR: #d96666; BORDER-RIGHT-COLOR: #a32929;width:100px;">
<DT style="BACKGROUND-COLOR: #a32929">6:30起床<I class="cic cic-tmr">&nbsp;</I><I class="cic cic-rcr">&nbsp;</I></dt> 
<DD><SPAN class="ca-elp12">&nbsp;</SPAN>
</DD></DL>

</td>
<td align="center">
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>


</td><td  align="center">
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>

<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
</td><td  align="center">
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>

<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
</td>
<td  align="center">
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>

<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
</td><td align="center">
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>

<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
</td>
<td  align="center">
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>

<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
<div class="dateDIV">&nbsp;</div>
</td>

</tr>

</tbody>
</table>
<!--timedevents end -->
</td>
</tr>
</tbody>
</table>

</td>
</tr>
</table>

<b class="xb4" style="width:1010px; "></b><b class="xb3" style="width:1007px; "></b><b class="xb2" style="width:1006px; "></b><b class="xb1" style="width:1002px; "></b>
