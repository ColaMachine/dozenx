
<template>
  <zwForm :class="getComputeClass" >
   <zwFormItem>
        <zwInput placeholder="请输入邮箱地址" id="email" name="email"></zwInput>

   </zwFormItem>


        <zwFormItem>
        <zwInput placeholder="请输入密码" id="passwordAgain" name="passwordAgain"  type="passwordAgain"></zwInput>
        </zwFormItem>
        <zwFormItem>
        <zwInput placeholder="请输再次入密码"  id="passwordAgain" name="passwordAgain"  type="passwordAgain"></zwInput>
        </zwFormItem>
        <zwFormItem>
        <zwInput placeholder="请输入右侧图形验证码">

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
                                 KsrDIYHqCPSqH9mS2/8AyD72S3UfdgkUSwj8DhgMdFVlAwOOudGikB//2Q=="></img>

                         <zwButton slot="append" icon="refresh" loading :loading_delay=5></zwButton>

                       </zwInput>
        </zwFormItem>
         <zwFormItem>
       <zwInput placeholder="邮箱验证码">


                        <zwButton slot="append"  loading :loading_delay=5>邮箱验证码</zwButton>

                      </zwInput>
        </zwFormItem>
  </zwForm>
</template>
<script type="text/javascript">

export default {
        name: 'zwRegisterEamil',
         components:{},
        props: [
            "layout"

        ],
        data () {
            return {

            };
        },
        computed: {
            getComputeClass:function(){
                var cls = "zw-form ";
                if(this.layout=="inline"){
                    cls+=" zw-form-inline"
                }
                return cls
            }
        },
        mounted () {


        },
        methods: {

        },


         events: {


          }
    };
</script>
<style>
.zw-form {
    font-family: "Chinese Quote", -apple-system, BlinkMacSystemFont, "Segoe UI", "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "Helvetica Neue", Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol";
    font-size: 14px;
    font-variant: tabular-nums;
    line-height: 1.5;
    color: rgba(0, 0, 0, 0.65);
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    margin: 0;
    padding: 0;
    list-style: none;
}

.zw-form {
    font-family: "Chinese Quote", -apple-system, BlinkMacSystemFont, "Segoe UI", "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "Helvetica Neue", Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol";
    font-size: 14px;
    font-variant: tabular-nums;
    line-height: 1.5;
    color: rgba(0, 0, 0, 0.65);
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    margin: 0;
    padding: 0;
    list-style: none;
}

</style>