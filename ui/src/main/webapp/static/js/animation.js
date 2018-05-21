
function animation(it, attrname, finalValue, timeOut, fn) {



    if(it.dataset.aniamtionKey){//console.log("clear"+it.animationKey);

        window.clearInterval(it.dataset.aniamtionKey);
        it.dataset.aniamtionKey=null;
    }
	var speed = 20;
	//finalValue = 100;
	//var finalValues = it.childNodes;
	var totalHeight = 0;
	for (var i = 0; i < it.childNodes.length; i++) {
		totalHeight += it.childNodes[i].offsetHeight;
		//alert(it.childNodes[i].height);
	}

	//finalValue = totalHeight;
	if(totalHeight<=0){
	//    finalValue=it.offsetHeight;
	}
	//if(finalValue<=0){
	   // finalValue=100;
	//}
	console.log("finalValue:" + finalValue);
	val = 0;
	var val = parseInt(it.style[attrname].replace("px", ""));
	//it.style[attrname] = "0%";
	it.style.display = "block";

	it.style.overflowY = "hidden";
	it.style.overflowX = "hidden";
	if (val) {
		//console.log("value exist:" + val);
	} else {
		val = 0;
	}
	// if( value>0){
	var  startVal = val;
	var distance = val - finalValue;
	var permeter = distance / timeOut * speed;console.log();
    if(permeter!=0){

      it.dataset.aniamtionKey =  setInterval(function changeAttr() {


                                     	val -= permeter;
                                     	//console.log("value:" + val);
                                     	if (Math.abs(val - startVal)>Math.abs(finalValue - startVal) ||   Math.abs(val - finalValue) < Math.abs(permeter)) {//如果当前的值 超过目标值 那么就停止
                                     		//it.setAttribute(attrname,finalValue);

                                     		it.style[attrname] = finalValue + "px";
                                     		it.style.overflowY = "";
                                     		it.style.overflowX = "";
                                            if(fn){
                                     		fn.call();}it.style.display = "";
                                     		//it.style.height = "";

                                     		clearInterval( it.dataset.aniamtionKey);
                                     		it.dataset.aniamtionKey =null;
                                     	} else {
                                     		//   it.setAttribute(attrname,value);
                                     		it.style[attrname] = val + "px";
                                                it[attrname]= val + "px";
                                                //it.style.border="1px solid red";
                                                it.id="as";
                                     		//console.log("now"+attrname + ":" + it[attrname]);
                                     //		setTimeout(function() {
                                     //			//  it.setAttribute(attrname,it.getAttribute(attrname)-permeter);
                                     //			changeAttr(it, attrname, val, permeter, finalValue, fn);
                                     //		}, speed);
                                     	}
                                     },speed)
//    setTimeout(function() {
//    		// it.style[attrname]=it.style[attrname]-permeter;
//    		// it.setAttribute(attrname,it.getAttribute(attrname)-permeter);
//    		changeAttr(it, attrname, val, permeter, finalValue, fn, speed);
//    	}, speed);
//    }

	//  }
	}
}

function changeAttr(it, attrname, val, permeter, finalValue, fn, speed) {
	val -= permeter;
	console.log("value:" + val);
	if (Math.abs(val - finalValue) < Math.abs(permeter)) {
		//it.setAttribute(attrname,finalValue);
		it.style[attrname] = finalValue + "px";
		it.style.overflowY = "";
		it.style.overflowX = "";

		fn.call();it.style.display = "";
		it.style.height = "";
	} else {
		//   it.setAttribute(attrname,value);
		it.style["height"] = val + "px";

		console.log("now"+attrname + ":" + it.style[attrname]);
//		setTimeout(function() {
//			//  it.setAttribute(attrname,it.getAttribute(attrname)-permeter);
//			changeAttr(it, attrname, val, permeter, finalValue, fn);
//		}, speed);
	}
}