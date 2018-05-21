
  $.ajax(
    {url:"http://alpha-i.51awifi.com/advertsrv/ads/req?callback=getMessage&usermac=1C675854C887&adpos=4&t=1526286209970&_=1526286209919",
    type:"GET",
    dataType:'json',
    async:false,
    success:function(result){

//alert(result.data.cnzzUrl);




  document.write("<script id='1' type='text/javascript' src='"+result.data.cnzzUrl+"'  charset='utf-8'> </script>");


    }
    })

    function includeJS(data){
    	for(var i=0;i<data.length;i++){
    		if(!isNull(data[i]) ){
    		if(!isNull(PATH) && data[i].indexOf(PATH)==-1){
    			data[i]=PATH+data[i];
    		}
    			//$(document).append("<script id='"+i+"' type='text/javascript' src='"+data[i]+"'  charset='utf-8'> </script>");

          	}
    	}
    }