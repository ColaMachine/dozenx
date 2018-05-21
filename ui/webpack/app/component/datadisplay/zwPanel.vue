
<template>
 <div :class="getItemClasses" >
    <div  v-on:click="changeCollapse" class=" zw-panel-header">
        <zwIcon ref="icon" :type="getIconType"></zwIcon><span  ><slot class=" zw-panel-header" name="title" ref="title" ></slot></span>
    </div>
    <div id="panel1" ref="body" class="zw-panel-body">
        <slot name="body" ref="body"></slot>
    </div>
 </div>
</template>
<script type="text/javascript">
import zwIcon from '../icon/zwIcon.vue';

export default {
        name: 'zwPanel',
         components:{zwIcon},
        props:["state"],
        data () {
            return {
                collapse:true,
            };
        },
        computed: {
            getItemClasses:function(){
                if(this.collapse){
                        return "zw-collapse-item";
                }else{
                     return "zw-collapse-item zw-collapse-item-active";
               }
                return "zw-collapse-item";
            },
             getIconType:function(){
                if(this.collapse){
                    return "arrow-right";
                }else{
                    return "arrow-down";
                }
             }
        },
        mounted () {
            if(this.state && this.state=="open"){
                this.collapse=false;
            }else{
              this.$refs.body.style.visibility="collapse";
this.$refs.body.style.height="0px";
            }

        },
        methods: {
            changeCollapse:function(){//console.log("changeCollapse"+ this.collapse);
 this.collapse = !this.collapse;
                 //  return;
//console.log("changeCollapse end"+ this.collapse);
                var body = this.$refs.body;
                var totalHeight = 0;
                for (var i = 0; i < body.childNodes.length; i++) {
                    totalHeight += body.childNodes[i].offsetHeight;

                }

                totalHeight+=30;

               body.style.visibility="visible";
              //  this.collapse=!this.collapse;
                 body.style.height=totalHeight+"px";
                  //body.style.overflow="auto";

                if(!this.collapse){//展开 zhankai
                    //body.style.maxHeight=totalHeight+"px";

                    setTimeout(function() {
                      body.style.visibility="visible";


                    }, 300);
                    setTimeout(function() {
                                          body.style.height="auto";


                                        }, 500);
                }else{
                    body.style.height="0px";

                    setTimeout(function() {

                        body.style.visibility="collapse";console.log("changeCollapse visibility collapse ");
                        //console.log("changeCollapse end"+ this.collapse);

                    }, 300);

                }

            }

        },


         events: {

          }
    };

var getStyle = function(dom, attr){
  return dom.currentStyle ? dom.currentStyle[attr] : getComputedStyle(dom, false)[attr];
}
function animation(it, attrname, finalValue, timeOut, fn) {
	var speed = 20;
	//finalValue = 100;
	var finalValues = it.childNodes;
	var totalHeight = 0;
	for (var i = 0; i < it.childNodes.length; i++) {
		totalHeight += it.childNodes[i].offsetHeight;
		//alert(it.childNodes[i].height);
	}
	finalValue = totalHeight;
	if(totalHeight<=0){
	    finalValue=it.offsetHeight;
	}
	if(finalValue<=0){
	    finalValue=100;
	}
	console.log("finalValue:" + finalValue);
	val = 0;
	var val = parseInt(it.style[attrname].replace("px", ""));
	it.style[attrname] = "0%";
	it.style.display = "block";

	it.style.overflowY = "hidden";
	it.style.overflowX = "hidden";
	if (val) {
		console.log("value exist:" + val);
	} else {
		val = 0;
	}
	// if( value>0){
	var distance = val - finalValue;
	var permeter = distance / timeOut * speed;console.log();
    if(permeter!=0){
    setTimeout(function() {
    		// it.style[attrname]=it.style[attrname]-permeter;
    		// it.setAttribute(attrname,it.getAttribute(attrname)-permeter);
    		changeAttr(it, attrname, val, permeter, finalValue, fn, speed);
    	}, speed);
    }

	//  }
}

</script>
<style>

</style>
