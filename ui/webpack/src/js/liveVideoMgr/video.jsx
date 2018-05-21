/**
 * Created by songbo on 2017/8/22.
 */
import React from 'react'
import Hls from 'hls.js';

class videoPanel extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            monitorModel: false
        };
        this.toggleFullScreen = this.toggleFullScreen.bind(this);
    }


    componentDidMount() {
        if (!this.initVideo()) {
            console.error('video 初始化失败');
            return;
        }
        let url = this.props.videoInfo.videoUrl;
        this.loadVideo(url);
    }

    shouldComponentUpdate(nextProps, nextState) {
        return false; //不更新组件
    }

    initVideo() {
        if (this.video && this.hls)
            return true;

        if (!Hls.isSupported())
            return false;

        var video = this.video = document.getElementById('video');
        this.hls = new Hls();

        if (!video) {
            console.error('video element error');
            return false;
        }
        return true;
    }

    stop() {
        this.video.pause();
    }

    loadVideo(url) {
        if (!url) {
            console.log('未找到时路径');
            url = 'https://video-dev.github.io/streams/x36xhzz/x36xhzz.m3u8';
        }
        let hls = this.hls;
        hls.detachMedia();
        hls.attachMedia(this.video);
        hls.loadSource(url);
        this.video.play();
    }

    toggleFullScreen() {
        if (document.webkitIsFullScreen || document.mozFullScreen) {
            this.exitFullscreen();
        } else {
            this.toFullScreen();
        }
    };

    toFullScreen() {
        var element = this.video;
        if (element.requestFullScreen) {
            element.requestFullScreen();
        } else if (element.mozRequestFullScreen) {
            element.mozRequestFullScreen();
        } else if (element.webkitRequestFullScreen) {
            element.webkitRequestFullScreen();
        }
    };

    //退出全屏
    exitFullscreen() {
        var de = document;
        if (de.exitFullscreen) {
            de.exitFullscreen();
        } else if (de.mozCancelFullScreen) {
            de.mozCancelFullScreen();
        } else if (de.webkitCancelFullScreen) {
            de.webkitCancelFullScreen();
        }
    };

    render() {
        return ( <video preload id='video' onDoubleClick={this.toggleFullScreen}>

        </video>);
    }
}


export default videoPanel