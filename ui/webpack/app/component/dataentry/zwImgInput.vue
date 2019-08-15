<template>


<form method="post" action="#" enctype="multipart/form-data">
    <p>
        <button class="btn-upload btn-lg">选择图片</button>


        <input type="file" name="avatar" id="cropper-input">
        Support formats: JPG, PNG
        <canvas style="display:none" id="canvasImg" ></canvas>
    </p>
    <p>
<button @click="saveImg(1)" type="button" class="btn-upload btn-lg">确定</button>
    </p>
    <div class="preview-container">
        <div class="image-container target" id="cropper-target">
            <img src="data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7" class="noavatar">
            <div class="cropper" style="display: none;"><div class="cropper-mask"></div><div class="resizer"><div class="wrapper"><img src="data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7"></div><div class="ord-n resize-bar"></div><div class="ord-s resize-bar"></div><div class="ord-w resize-bar"></div><div class="ord-e resize-bar"></div><div class="ord-nw resize-handle"></div><div class="ord-n resize-handle"></div><div class="ord-ne resize-handle"></div><div class="ord-w resize-handle"></div><div class="ord-e resize-handle"></div><div class="ord-sw resize-handle"></div><div class="ord-s resize-handle"></div><div class="ord-se resize-handle"></div></div></div></div>
        <div class="large-wrapper">
            <div class="image-container large" id="preview-large">
                <img src="data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7" class="noavatar">
            </div>
            <p>Large</p>
        </div>
        <div>
            <div class="image-container medium" id="preview-medium">
                <img src="data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7" class="noavatar">
            </div>
            <p>Medium</p>
            <div class="image-container small" id="preview-small">
                <img src="data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7" class="noavatar">
            </div>
            <p>Small</p>
        </div>
    </div>
</form>

</template>
<script type="text/javascript">
window.Cropper = require('../.././cropper');


  export default {

    components: {

    },
    props: [],
    data() {
      return {
        images: []
      };
    },
    computed: {

    },
    mounted() {

        var canvasImg = document.getElementById("canvasImg");

        canvasImg.width = 200;
        canvasImg.height = 200;
var ctx = canvasImg.getContext('2d');

 

var cropper = new Cropper({
    element: document.getElementById('cropper-target'),
    previews: [
      document.getElementById('preview-large'),
      document.getElementById('preview-medium'),
      document.getElementById('preview-small')
    ],
    onCroppedRectChange: function(rect) {
      console.log(rect);

      var img=new Image();
      img.onload=function() {
          ctx.drawImage(img,rect.left,rect.top,rect.width,rect.height ,0,0,200,200);
        }
      img.src=document.getElementById("cropper-target").childNodes[0].src;
    }
});







var input = document.getElementById('cropper-input');
input.onchange = function() {
  if (typeof FileReader !== 'undefined') {
    var reader = new FileReader();
    reader.onload = function (event) {
      cropper.setImage(event.target.result);
    };
    if (input.files && input.files[0]) {
      reader.readAsDataURL(input.files[0]);
    }

  } else { // IE10-
    input.select();
    input.blur();

    var src = document.selection.createRange().text;
    cropper.setImage(src);
  }
};

    },
    methods: {
      getPathValue: function(value) {
        return getPathValue(value);
      },

      saveImg:function(){


          var imageURL    = document.getElementById("canvasImg").toDataURL("image/png")
         this.$emit('saveImg',imageURL);
        // console.log(src);
      }
    },
    watch: {

    },

    events: {

    }
  };
</script>
<style>


    #nav {
    position: fixed;
    top: 0;
    width: 100%;
    height: 50px;
    text-align: center;
    background: rgba(255, 255, 255, .9);
    box-shadow: 0 0 15px 3px rgba(0, 0, 0, .05);
    padding-top: 10px;
    z-index: 1000;
    }

    #logo {
    display: inline-block;

    background-size: 40px;
    line-height: 40px;
    padding-left: 40px;
    margin-right: 80px;
    color: #5c5c5c;
    }

    #logo:hover {
    color: #5c5c5c;
    }

    a {
    display: inline-block;
    text-decoration: none;
    color: #046bc2;
    }

    a:hover {
    color: #07bcfd;
    }

    #nav a {
    width: 140px;
    }

    #main {
    width: 820px;
    margin: 80px auto 0 auto;
    }

    footer {
    text-align: center;
    font-size: 14px;
    margin-top: 10px;
    color: #555;
    }

    body {
    color: #333;
    font-size: 14px;
    }

    p {
    padding: 10px 0;
    line-height: 1.5em;
    margin: 0;
    }

    p, li {
    color: #555;
    }

    li {
    padding: 5px 0;
    }

    h4 {
    font-size: 1.4em;
    margin-bottom: 1em;
    }

    h5 {
    font-size: 1.1em;
    margin: 15px;
    }

    code {
    font: 85%/1.6em 'Monaco', 'Menlo', 'Courier New', monospace;
    padding: 4px;
    color: #444;
    background: #DFF2F7;
    tab-size: 4;
    white-space: pre;
    word-break: break-all;
    }

    pre code {
    display: block;
    font-size: 12px;
    padding: 15px;
    word-break: normal;
    margin: 1em 0;
    overflow-x: auto;
    }









    .resizer {
    position: absolute;
    box-sizing: border-box;
    border: 1px dashed gray;
    background-color: transparent;
    cursor: move;
    }

    .resizer .resize-handle {
    position: absolute;
    background-color: #333;
    opacity: 0.5;
    filter: alpha(opacity=50);
    font-size: 1px;
    height: 7px;
    width: 7px;
    }

    .resizer .resize-handle.ord-n {
    cursor: n-resize;
    left: 50%;
    margin-left: -4px;
    margin-top: -1px;
    top: 0;
    }

    .resizer .resize-handle.ord-s {
    cursor: s-resize;
    bottom: 0;
    left: 50%;
    margin-bottom: -1px;
    margin-left: -4px;
    }

    .resizer .resize-handle.ord-e {
    cursor: e-resize;
    margin-right: -1px;
    margin-top: -4px;
    right: 0;
    top: 50%;
    }

    .resizer .resize-handle.ord-w {
    cursor: w-resize;
    left: 0;
    margin-left: -1px;
    margin-top: -4px;
    top: 50%;
    }

    .resizer .resize-handle.ord-nw {
    cursor: nw-resize;
    left: 0;
    margin-left: -1px;
    margin-top: -1px;
    top: 0;
    }

    .resizer .resize-handle.ord-ne {
    cursor: ne-resize;
    margin-right: -1px;
    margin-top: -1px;
    right: 0;
    top: 0;
    }

    .resizer .resize-handle.ord-se {
    cursor: se-resize;
    bottom: 0;
    margin-bottom: -1px;
    margin-right: -1px;
    right: 0;
    }

    .resizer .resize-handle.ord-sw {
    cursor: sw-resize;
    bottom: 0;
    left: 0;
    margin-bottom: -1px;
    margin-left: -1px;
    }

    .resizer .resize-bar.ord-n, .resizer .resize-bar.ord-s {
    position: absolute;
    height: 7px;
    width: 100%;
    }

    .resizer .resize-bar.ord-e, .resizer .resize-bar.ord-w {
    position: absolute;
    height: 100%;
    width: 7px;
    }

    .resizer .resize-bar.ord-n {
    cursor: n-resize;
    margin-top: -1px;
    }

    .resizer .resize-bar.ord-s {
    cursor: s-resize;
    bottom: 0;
    margin-bottom: -1px;
    }

    .resizer .resize-bar.ord-e {
    cursor: e-resize;
    margin-right: -1px;
    right: 0;
    }

    .resizer .resize-bar.ord-w {
    cursor: w-resize;
    margin-left: -1px;
    }

    .resizer .inner-rect {
    position: absolute;
    left: 0;
    top: 0;
    right: 0;
    bottom: 0;
    background-color: white;
    opacity: 0;
    filter: alpha(opacity=0);
    }

    .cropper {
    position: absolute;
    box-sizing: border-box;
    }

    .cropper .cropper-mask {
    width: 100%;
    height: 100%;
    opacity: 0.4;
    filter: alpha(opacity=40);
    display: block;
    background-color: black;
    }

    .cropper .resizer .wrapper {
    position: absolute;
    left: 0;
    top: 0;
    right: 0;
    bottom: 0;

    overflow: hidden;
    }

    .cropper .resizer .wrapper img {
    position: absolute;
    -webkit-user-select: none;
    -webkit-user-drag: none;
    }






















    form {
    margin-left: 18px;
    }

    form > p {
    padding-top: 22px;
    line-height: 40px;
    font-size: 14px;
    position: relative;
    color: #666;
    margin-bottom: 30px;
    overflow: hidden;
    display:inline-block;
    }

    form > p .btn-upload {
    width: 120px;
    padding: 12px 20px;
    font-size: 14px;
    border: 1px solid #1e89e0;
    background-color: transparent;
    color: #1e89e0;
    margin-right: 20px;
    }

    form > p input {
    position: absolute;
    font-size: 480px;
    top: 22px;
    left: -30px;
    height: 42px;
    width: 150px;
    opacity: 0;
    -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";
    cursor: pointer;
    }

    form .btn-submit {
    font-size: 14px;
    padding: 12px 20px;
    width: 120px;
    margin-right: 20px;
    }

    .preview-container {
    overflow: hidden;
    color: #999;
    margin-bottom: 20px;
    }

    .preview-container .noavatar {
    background-color: #efeeef;
    }

    .preview-container > div {
    float: left;
    height: 320px;
    }

    .large-wrapper {
    border-left: 1px solid #eee;
    padding-left: 30px;
    margin-right: 30px;
    }

    .image-container {
    position: relative;
    overflow: hidden;
    border: 1px solid #bbb;
    margin-bottom: 5px;
    }

    .image-container > img {
    position: absolute;
    }

    .image-container.target {
    width: 320px;
    height: 320px;
    margin-right: 30px;
    }

    .image-container.target img {
    width: 320px;
    height: 320px;
    }

    .image-container.large {
    width: 210px;
    height: 210px;
    }

    .image-container.large img {
    width: 210px;
    height: 210px;
    }

    .image-container.large .noavatar {
    background-position: -320px 0;
    }

    .image-container.medium {
    width: 48px;
    height: 48px;
    }

    .image-container.medium img {
    width: 48px;
    height: 48px;
    }

    .image-container.medium .noavatar {
    background-position: -324px -210px;
    }

    .image-container.small {
    width: 20px;
    height: 20px;
    margin-top: 30px;
    }

    .image-container.small img {
    width: 20px;
    height: 20px;
    }

    .image-container.small .noavatar {
    background-position: -373px -210px;
    }



</style>