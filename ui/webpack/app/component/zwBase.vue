
<template>
  <header class="header bg-blue">
  	    	<nav>
  	    		<a id="login" href="login.html"><div class="header-left center-text zw-col-6 left small-text">
  	    			<img class="header-left-img" :src="getPathValue('/static/img/header/wifi.png')">
  	    			<span class="header-left-span right-text" slot="left"><slot ref="left" name="left"></slot></span>
  	    		</div></a>
  	    		<div class="center-text zw-col-11 left normal-text"  slot="middle"><slot ref="middle" name="middle"></slot></div>
  	    		<div class="header-right center-text zw-col-6 left small-text">

  	    			<slot ref="middle" name="right"></slot>

  	    		</div>
  	    	</nav>
      	</header>
</template>
<script type="text/javascript">
export default {


        name: 'zwBase',
        components: {  },
        props:[],
        data () {
            return {

            };
        },
        computed: {


        },
        mounted () {
        },
        watch:{
　　　　　　　
　　　　　　　　
　　　　　　},
        methods: {
            getPathValue:function(value){
                return getPathValue(value);
            }
}
    };
</script>
<style scoped>

</style>
