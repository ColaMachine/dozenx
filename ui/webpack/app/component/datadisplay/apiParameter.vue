
<template>

    <tr  >
               <td >
               {{item.name}}
               </td>
                <td >
               <textarea :name="item.name"  :type="isFile(item)" width="200px"></textarea>
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