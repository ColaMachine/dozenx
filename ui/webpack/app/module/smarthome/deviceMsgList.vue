<template>
<div>
     <zwRow>

            <zwCol class="pull-right" span=24>



              <zwPanel state="open"  >
              <span slot="title" name="title">最近三天</span>

              <p  slot="body" name="body">

                    <zwAppList :list="itemList" :render="listItemRender" ></zwAppList>

              </p>

            </zwPanel>


            </zwCol>

          </zwRow>

</div>

</template>
<script>

import zwRow from '../../component/layout/zwRow.vue';
import zwCol from '../../component/layout/zwCol.vue';
import zwIcon from '../../component/icon/zwIcon.vue';
import zwPanel from '../../component/datadisplay/zwPanel.vue';
import zwAppList from '../../component/datadisplay/zwAppList.vue';



export default {
    components:{zwRow,zwCol,zwIcon,zwAppList,zwPanel
    },
    data () {

        return {
        itemList:[


        ],
        tabIndex:2,
      }
    }
    ,
    mounted(){//alert(123);
    Ajax.getJSON(PATH+"/smart/home/alarm/list",{},function(result){
                                         if(result.r==AJAX_SUCC){
                                             this.itemList=result.data;
                                         }
                                     }.Apply(this));
    },
    computed: {

    },
    methods:{
        listItemRender:function(item){
            return "<h1>"+item.category+"</h1>"+"<h2>"+item.createTimeStr+"</h2>"
        }
    }
}
</script>
<style scoped>


</style>

