
<template>

    <tr  >
               <td >
               {{item.name}}
               </td>
                <td >

               <textarea v-if="!isFile(item)"  :name="item.name"  :type="isFile(item)" width="200px"></textarea>
               <input v-if="isFile(item)" input :name="item.name"  :type="isFile(item)" width="200px"></input>
               </td>
                <td >
               {{item.description}}
               </td>
           	 <td >
               {{item.in}}
               </td>
                <td >
               {{item.type}}
               </td>
                 <td >
               {{item.required}}
               </td>
               </tr>


</template>
<script type="text/javascript">


export default {
        name: 'apiParameter',
         components:{},
        props: ['item'],
        data () {
            return {

            };
        },
        computed: {

        },
        mounted () {


        },
        methods: {
            isFile(item){
        		return item.type=="file"?"file":"text";
        	}
        },


         events: {

          }
    };
</script>
<style>

</style>