
<template>
    <ul class="zw-album">
        <li   v-for="item in images" key={{item.id}} class="zw-album-it pull-left">
            <img  @click="showWindow(item.url);" :src="getPathValue(item.url)"></img>
        </li>
    </ul>
</template>
<script type="text/javascript">
import zwBase from '../zwBase.vue';
import zwIcon from '../icon/zwIcon.vue';
export default {
        name: 'zwAlbum',
        extends:"zwBase",
         components:{zwIcon,zwBase},
        props: ["images"

        ],
        data () {
            return {

            };
        },
        computed: {

        },
        mounted () {


        },
        methods: {
            getPathValue:function(value){
                return getPathValue(value);
            },
            showWindow:function(url){
                var imgs=[];
                //console.log(this.images);
                for(var i=0;i<this.images.length;i++){
                    imgs.push(getPathValue(this.images[i].url));
                }
                console.log(url);
                zImageWindow({"img":getPathValue(url)});
            }
        },


         events: {

          }
    };


</script>
<style>
.zw-album{
    display: block;
}
.zw-album-it{
    width: 33%;
    padding-right: 10px;
    padding-top: 10px;
    display: inline-block;

       box-sizing: border-box;
}
.zw-album-it>img{
       opacity: 1;
       transition: opacity 0.2s ease 0s;
       height: auto;
       width: 100%;
       left: 0px;
       margin-top: -6.17105px;
}
</style>