import {Row, Col, Popover, Button, Icon} from 'antd';
import React from 'react'
import {Link} from 'react-router-dom'
import $http from '../../util/http'
export default class topHeader extends React.Component {
    constructor(p) {
        super(p);
        this.user = sessionStorage.user;
    }

    logout() {
        var self = this
        var config = {
            data: {
                phone: sessionStorage.user
            },
            _that: self
        }
        $http.authorQuite();
        $http.post('/userservice/logout', config).then(function () {
            window.location.hash = "/login"
        })
    }

    render() {
        const poweroff = (
            <div>
                <Link to="/login">退出</Link>
            </div>
        )

        const tip = (
            <div>
                别心急，该模块内容尚未开始开发，请耐心等待
            </div>
        )
        const setting = (
            <div>
                别心急，该模块内容尚未开始开发，请耐心等待
            </div>
        )

        return (
            <div className="topHeader">

                <div className="awifi-title">公众运营管理平台</div>

                <ul className="panel">
                    <li>
                        <span style={{fontSize: 20}}>{this.user}</span>
                    </li>

                    <li>
                        <Popover content={setting}>
                            <Icon type="setting"/></Popover>
                    </li>

                    <li>
                        <Popover content={tip}>
                            <Icon type="info-circle-o"/></Popover>
                    </li>


                    <li onClick={this.logout.bind(this)}>
                        <Popover content={poweroff}>
                            <Icon className="red" type="poweroff"/></Popover>
                    </li>
                </ul>
            </div>
        )
    }
}