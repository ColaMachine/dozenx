/**
 * Created by songbo on 2017/8/22.
 */

import React from 'react';
import {
    BrowserRouter as Router,
    Route,
} from 'react-router-dom'
import deviceMangement from '../js/deviceManagement/list/index'
//菜单路由页面，后续每个菜单对应的component在此处添加
//例如：菜单为/vedio,需要添加<Route path="/vedio" component={vedio}/>，点击该菜单自动加载vedio模块
export default class Contents extends React.Component {
    render() {
        return (
            <Router>
            <div className="content">
                <Route path="/device" component={deviceMangement}/>
            </div>
            </Router>
        );
    }
}