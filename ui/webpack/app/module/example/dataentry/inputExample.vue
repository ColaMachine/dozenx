<template>
<div>
<article>
<section>
<h2>Input输入框</h2>
<p>通过鼠标或键盘输入内容，是最基础的表单域的包装。</p>
<h3>何时使用</h3>
<p>需要用户输入表单域内容时。
   提供组合型输入框，带搜索的输入框，还可以进行大小选择。</p>
<h3>代码演示<zwIcon type="windows" title="展开全部代码"></zwIcon></h3>
<zwRow >
<zwCol span=12>


    <zwBox class="">
        <div slot="zwCodeBoxDemo">
            <zwInput placeholder="Basic usage"></zwInput>

        </div>
        <div slot="zwCodeBoxMeta"  title="基本使用">
           基本使用
        </div>
        <div slot="zwCodeBoxPanel"  >

            <textarea ref="textarea" id="textarea" name="textarea">
           <zwInput placeholder="Basic usage"></zwInput>
             </textarea>

       </div>
    </zwBox>

    <zwBox class="">
            <div slot="zwCodeBoxDemo">
               <zwInput placeholder="Basic usage">
                <span slot="prepend">http:</span>
                <span slot="append">.com</span>
               </zwInput>
<zwInput placeholder="Basic usage">


                <img slot="append" src="data:image/png;base64,/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0a
                          HBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIy
                          MjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAAaAFADASIA
                          AhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQA
                          AAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3
                          ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWm
                          p6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEA
                          AwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSEx
                          BhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElK
                          U1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3
                          uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD3+sfx
                          Vqd5o3hm91GwgWa4gVWCOhYY3AMSAQcBcn2xWxWT4o/5FLWv+vCf/wBFtUz+F2EzjbPx3rGteC9R
                          v9PitP7RsoibhdpXyhkkSICSHGwHgkEFDwwIFULr4vz2T6Np1hokmvX11YJNKbeUo/mjcJF8tYyc
                          jYTx2OenNUbbRL+X4c6XrWkI0l2ltc2VxborsZ4ZJXXACnkqWLYx7n7oFc3r+i3dsvw8SKS70jWL
                          uWeye5XeksSGcBflyCMCZz2yG64xWeGlKU0p9iLs7rS/i9NLrh0zXPCl/pEhtZbpPMcl2WNGc/K6
                          JwQjgHPUAepHa2Pieyv/AAm3iOKK4FmsMsxRlHmbYywPGcZ+U45rx7R9PtvA3xMlj8erJftfKVsN
                          buZWaNl2mNhICSCCrBW3Z2YH8J3Vb+H9h40utBnfw5q9lZ2YumDxzoCxk2rk8xtxjb37V1ziuhDq
                          STSO5uPiXp+neB9P8UalY3KQ3k5gWC2KysrAvzlioxiM/nWZpnxy8JajqUFm6ahZiZtn2i6jRYkP
                          bcQ5IGeM4wM84GTXl+sf8kUT/sYh/wCkxq5ruu39tquiXXjLxJo3irTILku2naZOrfw43uFRVO3I
                          wHPPI6FjRyI3o+/TTZ9HUUUVkAUUUUAFZ2qW+js9rearbWjm2kzBcXMSt5DcHIYj5OVHORyAOuK0
                          aKAGyRpNE8ciK8bgqysMhgeoI9KoaLoWm+HrN7TS7b7PA8hlZd7PliAM5Yk9AKvxxpDEkcaKkaAK
                          qqMBQOgA9Kpa5I8Ph/UpI3ZJEtZWVlOCpCHBB9aBWW5m33gbw1qOiRaNcaTF/Z8UomWKNmjzIFKh
                          iVILHBxkk571V0n4a+D9Dv1vrDQ4VuUxseV3m2EEEModiAwIGGHI9a6uindjWishskaTRPHIivG4
                          KsrDIYHqCPSqH9mS2/8AyD72S3UfdgkUSwj8DhgMdFVlAwOOudGikB//2Q==" ></img>


                                    <zwButton  slot="append" icon="refresh" loading :loading_delay=5 ></zwButton>

               </zwInput>


            </div>
             <div slot="zwCodeBoxMeta"  title="前置后置说明">

               input 可以加前置 后置
               用于配置一些固定组合。样式上采用display:table display:table-cell 并且vertical-align:center可解决;

            </div>
            <div slot="zwCodeBoxPanel"  >

                <textarea ref="textarea" id="textarea" name="textarea">
                <zwInput placeholder="Basic usage">
                               <span slot="prepend">http:</span>
                               <span slot="append">.com</span>
                              </zwInput>
                </textarea>

            </div>
        </zwBox>

        <zwBox class="">
            <div slot="zwCodeBoxDemo">

                <zwInput placeholder="Basic usage">
                                               <zwIcon slot="prefix" type="windows"></zwIcon>
                                               <zwIcon slot="suffix" type="windows"></zwIcon>
                                              </zwInput>

                <zwInput placeholder="Basic usage">
                                   <zwIcon slot="prefix" type="windows"></zwIcon>
                                   <select slot="suffix" type="windows">
                                     <option valule="minute">分</option>
                                   <option valule="hour">小时</option>
                                    <option valule="day">天</option>
                                    <option valule="month">月</option>
                                    <option valule="year">年</option>
                                   </select>
                              </zwInput>
            </div>
             <div slot="zwCodeBoxMeta"  title="前缀和后缀">

              在输入框上添加前缀或后缀图标。
            </div>
            <div slot="zwCodeBoxPanel"  >

                <textarea ref="textarea" id="textarea" name="textarea">
                   <zwButton type="dashed" shape="circle" icon="search" loading :loading_delay=5></zwButton>
                   <zwButton type="dashed"  icon="search"loading  >search</zwButton>
                   <zwButton type="danger" shape="circle" icon="search" :loading="getLoadingVal"  v-on:click.native="changeLoading" :loading_delay=5></zwButton>
                   <zwButton type="danger"  icon="search" :loading="getLoadingVal" v-on:click.native="changeLoading" >search</zwButton>
                   export default {
                       components:{zwButton,zwIcon,zwBox,zwRow,zwCol},
                       data () {

                           return {
                               msg: 'Hello World!',
                               loading:false,
                           }
                       }
                       ,
                       computed: {
                           getLoadingVal:function(){
                               return this.loading;
                           }
                       },
                       methods:{
                           changeLoading:function(){
                                console.log("123");
                             this.loading=!this.loading;
                               // this.loading={delay:5};
                                 console.log(this.loading);
                           }
                       }
                   }
                </textarea>

            </div>
        </zwBox>

</zwCol>



<zwCol span=12>

</zwCol>




</zwRow>

</section>
</article>
</div>
</template>
<script>
import zwButton from '../../../component/button/zwButton.vue';
import zwButtonGroup from '../../../component/button/zwButtonGroup.vue';
import zwIcon from '../../../component/icon/zwIcon.vue';
import zwRow from '../../../component/layout/zwRow.vue';
import zwBox from '../../../component/layout/zwBox.vue';
import zwDropDown from '../../../component/navigation/menu/zwDropDown.vue';
import zwMenu from '../../../component/navigation/menu/zwMenu.vue';
import zwMenuItem from '../../../component/navigation/menu/zwMenuItem.vue';
import zwInput from '../../../component/dataentry/zwInput.vue';

import zwCol from '../../../component/layout/zwCol.vue';
export default {
    components:{zwButton,zwIcon,zwBox,zwRow,zwCol,zwDropDown,zwMenu,zwMenuItem,zwButtonGroup,zwInput},
    data () {

        return {
            msg: 'Hello World!',
            loading:false,
        }
    }
    ,
    computed: {
        getLoadingVal:function(){
            return this.loading;
        }
    },
    methods:{
        changeLoading:function(){
             console.log("123");
          this.loading=!this.loading;
            // this.loading={delay:5};
              console.log(this.loading);
        }
    }
}
</script>
<style scoped>

</style>
