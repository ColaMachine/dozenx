<template>
  <div class="video-stream">
    <Row :gutter="16">
      <Col span="17">
       <div class="video">
         <header>
           <span class="title">视频源列表</span>
           <span class="name">{{currentVideo.name}}</span>
         </header>
         <main>
           <Row :gutter="16">
             <Col span="5">
               <ul class="videoList">
                 <li v-for='(item,index) in videoData' :class='{active:index===currentVideo.index}' @click='videoChange(item,index)'>{{item.name}}</li>
               </ul>
             </Col>
             <Col span="19">
                 <video ref='videoPlayer' class="video-js vjs-live stream" data-setup='{"techOrder": ["html5"]}' controls></video>
             </Col>
           </Row>
         </main>
       </div>
      </Col>
      <Col span="7">
      <div class="compare-list">
        <header>
          <span class="title">今日对比清单</span>
          <span class="more" @click='$router.push({path:"/statistics/capture"})'>更多></span>
        </header>
        <main>
          <div class="head">
            <span>抓拍人脸</span>
            <span>底库人脸</span>
          </div>
          <section class="captureWrapper">
            <div class="item" v-for='(item,index) in compareData'>
              <div class="captureImg">
                <img :src="item.regUrl" alt="">
                <p>{{item.userName}}</p>
              </div>
              <i-circle :percent="item.score" :size="55" stroke-color="#F5A623" :stroke-width='8'>
                <span class="demo-Circle-inner" style="font-size:10px">{{item.score}}%</span>
              </i-circle>
              <div class="libraryImg">
                <img :src="item.oriUrl" alt="">
                <p>{{item.checkTime?new Date(item.checkTime).toISOString():''}}</p>
              </div>
            </div>
          </section>


        </main>
      </div>
      </Col>
    </Row>
    <Row>
      <Col span="24">
      <div class="bottom-list face-list">
        <header>
          <span class="title">今日抓拍</span>
          <router-link  to="/face/list" class="more">更多></router-link>
        </header>
        <main>
          <ul class="avatar">
            <li v-for='item in captureData'>
              <img :src="item.url"/>
              <p>{{getDate(item.createtime)}}</p>
              <!--<p>{{getTime(item.createtime)}}</p>-->
            </li>
          </ul>

        </main>
      </div>
      </Col>
    </Row>
  </div>
</template>
<style lang="less">
  @import '../../css/face.less';
</style>
<script>
  import 'video.js/dist/video-js.css'
  import $http from '@/js/http2';
  import videojs from 'video.js'
  import 'videojs-flash'
  export default {
    components: {
    },
    created(){
      // this.initWebSocket();
      window.onunload = function(){
        // 使用FormData对象，但是这时content-type会被设置成"multipart/form-data"。
        // var fd = new FormData();
        // fd.append('room_id', 123);
        // navigator.sendBeacon("/api/mssrv/sys/auth/org/add", fd);

        // 使用Blob来发送 使用blob发送的好处是可以自己定义内容的格式和header
        var data = { ajax_data: 22 };
        var blob = new Blob([JSON.stringify(data)], {type : 'application/json'});
        navigator.sendBeacon('/leave_room?aj=22', blob);

        // 数据也可以使用URLSearchParams 对象，content-type会被设置成"text/plain;charset=UTF-8"
        // var params = new URLSearchParams({ room_id: 123 })
        // navigator.sendBeacon("/cgi-bin/leave_room", params);
      }
      this.getVideo()

      videojs.addLanguage('zh', {
        Play: '播放',
        Pause: '暂停',
        Mute:'音量',
        Fullscreen:'全屏',
        Replay:'重播',
        'Non-Fullscreen':'退出全屏',
        'Current-Time': '当前时间',
        'Remaining-Time': '剩余时间'
      });
    },
    mounted(){
      var options = {
        bigPlayButton: false,
        textTrackDisplay : false,
        language:'zh-CN',
        errorDisplay : false,
        techOrder:['html5','flash'],
        sourceOrder: true,
        // notSupportedMessage: '此视频暂无法播放，请稍后再试', //允许覆盖Video.js无法播放媒体源时显示的默认信息。
        // controls:false,
        controlBar: {
          volumePanel: {
            inline: false //音量条横竖
          }
        }
      };
      let _this = this

      this.player = videojs(this.$refs.videoPlayer, options, function onPlayerReady() {
        this.on('error', function(e) {

            var mediaError = this.error();
            var messageText
             if(mediaError.code == 1){//MEDIA_ERR_ABORTED
                 messageText = "播放被终止";
             }else if(mediaError.code == 2){//MEDIA_ERR_NETWORK
                 messageText = "网络错误";
             }else if(mediaError.code == 3){//MEDIA_ERR_DECODE
                 messageText = "由于视频文件损坏或是该视频使用了你的浏览器不支持的功能，播放终止";
             }else if(mediaError.code == 4){//MEDIA_ERR_SRC_NOT_SUPPORTED
                 messageText = "视频因格式不支持或者服务器或网络的问题无法加载";
             }else if(mediaError.code == 5){//MEDIA_ERR_ENCRYPTED
                 messageText = "资源已被加密";
             }
             _this.modalDialog = _this.player.createModal(messageText);
             _this.player.pause()
        });
      });

      // console.log(this.player.controlBar.children());
      // var rotateBtn = this.player.controlBar.addChild('button',{text:'fuuuu'})
      // rotateBtn.addClass('vjs-icon-rotate')
      this.$on('hook:beforeDestroy',()=>{
        // 销毁组件
        this.player.dispose()

      })
    },

    data() {
      return {
        player:null,
        modalDialog:null,
        currentVideo:'',
        videoData:[],
        compareData:[],
        captureData:[]
      }
    },
    computed:{

    },
    methods:{
      clearSocket(){
        // 停止心跳
        clearInterval(this.captureHeartBeat)
        clearInterval(this.compareHeartBeat)
        // 关闭ws
        this.wsCapture&&this.wsCapture.close()
        this.wsCompare&&this.wsCompare.close()
      },
      initWebSocket(id){
          this.clearSocket()
          // 初始化抓拍通道
          const urlCapture = `ws://${location.host}/socket/mssrv/websocket/camera/capture/${id}`
          this.wsCapture = new WebSocket(urlCapture);
          // 开启时发送心跳
          this.wsCapture.onopen = ()=>{
            this.captureHeartBeat = setInterval(()=>{
              this.wsCapture.send('keep connection')
            },30000)
          }
          this.wsCapture.onmessage = this.wsCaptureOnMessage
          // this.wsCapture.onclose = this.wsCaptureOnClose

          // 初始化对比清单通道
          const urlCompare = `ws://${location.host}/socket/mssrv/websocket/face/checkout/${id}`
          this.wsCompare = new WebSocket(urlCompare);
          this.wsCompare.onopen = ()=>{
            this.compareHeartBeat = setInterval(()=>{
              this.wsCompare.send('keep connection')
            },30000)
          }
          this.wsCompare.onmessage = this.wsCompareOnMessage
          // this.wsCompare.onclose = this.wsCompareOnClose
          this.$on('hook:beforeDestroy',()=>{
              this.clearSocket()
          })
      },

      wsCaptureOnMessage(e){
        if (typeof e.data == 'string') {
            try {
                let data=JSON.parse(e.data);
                if(typeof data == 'object'){
                  this.captureData.unshift(data)
                  this.captureData.splice(10)
                }

            } catch(e) {

            }
        }
      },
      wsCompareOnMessage(e){
        if (typeof e.data == 'string') {
            try {
                let data=JSON.parse(e.data);
                if(typeof data == 'object'  ){
                  this.compareData.unshift(data)
                  this.compareData.splice(4)
                }

            } catch(e) {

            }
        }
      },
      getCompareList(id){
        $http.get('/api/mssrv/checkin/faceCheckinOut/list', {
          params: {
            params:  {camera:id,pageSize: 4, curPage: 1}
          }
        }).then((resp) => {
          this.compareData = resp.data
        });
      },
      getCaptureList(id){
        $http.get('/api/mssrv/checkin/face/capture/list', {
          params: {
            params:  {camera:id,pageSize: 10, curPage: 1}
          }
        }).then((resp) => {
          this.captureData = resp.data
        });
      },
      getVideo(){

        $http.get('/api/mssrv/videosource/getListByParams', {
          params: {
            params:  {pageSize: 10, curPage: 1}
          }
        }).then((resp) => {
          this.videoData = resp.data
        });
      },
      videoChange(video,index){
        // 关闭video模态框
        this.modalDialog&&this.modalDialog.close();
        // 获取对比和抓拍列表
        this.getCompareList(video.id);
        this.getCaptureList(video.id);

        this.initWebSocket(video.id)
        // 设置当前选择视频源 （菜单高亮和title显示）
        this.currentVideo = Object.assign(video,{index})
        // 获取直播流视频源
        this.getHLS(video.id)
      },
      getHLS(id){
        $http.get(`/api/mssrv/camera/show/${id}`, {
          params: {}
        }).then((resp) => {
          // this.player.src([{
          //       type: 'rtmp/mp4',
          //       src: 'rtmp://58.200.131.2:1935/livetv/hunantv'
          //     }]);
          // this.player.load('rtmp://58.200.131.2:1935/livetv/hunantv');
          this.player.src(resp.data)
          this.player.load(resp.data)
          this.player.play()
        });

      },
      getDate(date){
        return date;//new Date(date).toLocaleDateString()
      },
      getTime(date){
        return date;//new Date(date).toLocaleTimeString()
      }
    }
  };
</script>
