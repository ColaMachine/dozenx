
<template>
 <div class="zw-message">
     <span>
         <div class="zw-message-notice">
            <div class="zw-message-notice-content">
                <div class="zw-message-custom-content zw-message-success">
                    <i class="anticon anticon-check-circle"></i>
                    <span>This is a prompt message for success, and it will disappear in 10 seconds</span>
                </div>
            </div>

        </div>
          <div class="zw-message-notice">
                    <div class="zw-message-notice-content">
                        <div class="zw-message-custom-content zw-message-success">
                            <i class="anticon anticon-check-circle"></i>
                            <span>This is a prompt message for success, and it will disappear in 10 seconds</span>
                        </div>
                    </div>

                </div>


                  <div class="zw-message-notice">
                            <div class="zw-message-notice-content">
                                <div class="zw-message-custom-content zw-message-success">
                                    <i class="anticon anticon-check-circle"></i>
                                    <span>This is a prompt message for success, and it will disappear in 10 seconds</span>
                                </div>
                            </div>

                        </div>
    </span>
</div>
</template>
<script type="text/javascript">

export default {
        name: 'zwMessage',
         components:{},
        props: {


        },
        data () {
            return {

            };
        },
        computed: {

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
.zw-message {
    font-family: "Monospace Number", "Chinese Quote", -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "Helvetica Neue", Helvetica, Arial, sans-serif;
    font-size: 14px;
    line-height: 1.5;
    color: rgba(0, 0, 0, 0.65);
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    margin: 0;
    padding: 0;
    list-style: none;
    position: fixed;
    z-index: 1010;
    width: 100%;
    top: 16px;
    left: 0;
    pointer-events: none;
}


.zw-message-notice:first-child {
    margin-top: -8px;
}


.zw-message-notice {
    padding: 8px;
    text-align: center;
}

.zw-message-notice-content {
    padding: 10px 16px;
    border-radius: 4px;
    -webkit-box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
    background: #fff;
    display: inline-block;
    pointer-events: all;
}

</style>