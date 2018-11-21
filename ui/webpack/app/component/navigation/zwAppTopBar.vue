
<template>
  <header class="header bg-blue">
  	    	<nav>
  	    		<div id="login"><div class="header-left center-text zw-col-6 left small-text">
  	    				<slot ref="left" name="left"></slot>

  	    		</div></div>
  	    		<div class="center-text zw-col-11 left normal-text"  slot="middle"><slot ref="middle" name="middle"></slot></div>
  	    		<div class="header-right center-text zw-col-6 left small-text">
  	    			<slot ref="middle" name="right"></slot>

  	    		</div>
  	    	</nav>
      	</header>
</template>
<script type="text/javascript">

 import zwBase from '../../component/zwBase.vue';

export default {
        extends:zwBase, //继承组件

        name: 'zwAppTopBar',
        components: {zwBase  },
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
}
    };
</script>
<style scoped>
.header {
    height: 60px;
    line-height: 60px;
    color: #ffffff;
    /* box-shadow: 0 2px 0 #2372CC, 0 1px 0 #438EE4; */
    box-shadow: 0 2px 5px rgba(0,0,0,0.26);
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    z-index: 10;
}



.row-5 {
    width: 50%;
    margin: 0 auto;
}
.left {
    float: left;
}
.center-text {
    text-align: center;
}
.normal-text {
    font-size: 1.6rem;
}


.header-left, .header-right {
    /* background: url(../img/header/back.png) no-repeat 15px center; */
    /* background-size: 20%; */
    /* color: transparent; */
    /* padding: 30px 0; */
    height: 60px;
    line-height: 60px;
    color: #ffffff;
}
.row-2-5 {
    width: 25%;
    margin: 0 auto;
}
.left {
    float: left;
}
.center-text {
    text-align: center;
}
.small-text {
    font-size: 1.3rem;
}

.header-left img{
    display: block;
    position: relative;
    top: 30%;
    left: 10%;
    height: 40%;
     float: left;
}

.header-left span {
    position: relative;
    top: -24px;
    color: #ffffff;
    padding-left: 42px;
}

.right-text {
    text-align: right;
}

.header-right img {
    display: block;
    position: relative;
    top: 30%;
    height: 40%;
    right: 15px;
     float: right;
}
.right {
    float: right;
}
</style>
