function CalendarEventDialogue(){
var eventDialogue=new object();
eventDialogue.start_H="";
eventDialogue.start_M;
eventDialogue.end_H;
eventDialogue.end_M;
eventDialogue.year;
eventDialogue.day;
eventDialogue.month;
eventDialogue.startDate;
eventDialogue.endDate;
eventDialogue.init=function(eventDialogue){
	eventDialogue.startDate=new Date();
}
eventDialogue.show=function(eventDialogue,calendar){
	calendar.addEvent(eventDialogue);

}
}