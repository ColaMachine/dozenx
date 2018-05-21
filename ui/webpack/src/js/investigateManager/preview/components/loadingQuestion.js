/**
 * Copyright(C),1988-1999,aWiFi Operations Center
 * Author:    作者 Stanley Huang   Version:1.0   Date:2018.1.10
 * Description: 问答题
 * Others:无
 */
import React from 'react';
import {Spin} from 'antd';

export  default  class LoadingQuestion extends React.Component {
    render() {
        return (
            <div className="Loading-question">
                <Spin size='large'/>
                <label >正加载，请稍候...</label>
            </div>);
    }
}
