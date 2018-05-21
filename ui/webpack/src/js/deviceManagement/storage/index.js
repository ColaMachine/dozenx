/**
 * 版本升级管理--新增加
 */

import React from 'react'
import Multiple from './multiple'
import Single from './single'
import { Input, Button,message,Icon,Tabs} from 'antd';
import $http from '../../../util/http';

const TabPane = Tabs.TabPane;

export default class DeviceStorage extends React.Component {
    constructor() {
        super()
        this.state = {
            version: '',
            model: '',
            company: '',
            uploadFileName: '',
            submitting: false,
        };
    }


    callback(key) {
        //console.log(key);
    }

    goBack = () => {
        window.history.back();
    }

    render() {

        return (
            <div className="right-management">
                <div className="page-title-me" style={{width: '100%'}}>设备管理&nbsp;/&nbsp;设备入库</div>
                <div className="upload-window-wrap">
                    <Tabs defaultActiveKey="1" onChange={this.callback}>
                        <TabPane tab="单个入库" key="1"><Single /></TabPane>
                        <TabPane tab="批量入库" key="2"><Multiple/></TabPane>
                    </Tabs>
                </div>
            </div>
        );
    }
}

