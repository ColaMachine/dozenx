import React from 'react';
import {Row, Col, Button, Table, Pagination, Popconfirm, notification} from 'antd';
import axios from 'axios';

import DateFormat from '../../util/dateFormat';

import '../../css/blacklist.scss';
import $http from '../../util/http'


export default class DisabledUserManagement extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            pagination: {
                total: 0,
                pageNo: 1,
                pageSize: 10,
                showSizeChanger: true,
                showQuickJumper: true,
                onChange: function (pageNo, pageSize) {
                    console.log('onChange');
                    this.search(pageNo, pageSize);
                }.bind(this),
                onShowSizeChange: function (currentPageNo, pageSize) {
                    this.search(1, pageSize);
                }.bind(this)
            },
            dataSource: []
        }
        this.search = this.search.bind(this);
    }

    columns = [{
        title: '用户名称',
        dataIndex: 'userName'
    }, {
        title: '账号',
        dataIndex: 'phone'
    }, {
        title: '设备数量',
        dataIndex: 'deviceNumber',
        render: (text, record) => {
            return `${record.deviceNumber}台`;
        }
    }, {
        title: '手动解禁',
        render: (text, record) => {
            return <Popconfirm title="确认解禁该用户？" onConfirm={() => {
                this.setUserFree(record);
            } } okText="确认" cancelText="取消">
                <a href="#">解禁</a>
            </Popconfirm>
        },
    }, {
        title: '违规直播',
        dataIndex: 'illegalCount',
        render: (text, record) => {
            return `${record.illegalCount}次`;
        }
    }, {
        title: '最近一次登录时间',
        dataIndex: 'loginTime',
        render: (text, record) => {
            return DateFormat.format.date(record.loginTime, 'yyyy-MM-dd HH:mm:ss');
        }
    },];

    setUserFree(record) {
        console.log('setUserFree', record);
        axios.post('/forbidden/releaseforbidden', {
            phone: record.phone,
            userId: record.userId,
            token: sessionStorage.token
        }).then(({data}) => {
            console.log(data);
            if (!$http.needLogin(data.code)) {
                notification.success({
                    message: '解禁用户成功',
                    description: ''
                });
                this.search();
            } else {
                notification.error({
                    message: data.code + '失败',
                    description: data.msg
                });
            }
        });
    }

    search(pageNo = this.state.pagination.pageNo, pageSize = this.state.pagination.pageSize) {
        axios.post('/forbidden/searchforbidden', {
            pageNo: pageNo,
            pageSize: pageSize,
            token: sessionStorage.token
        }).then(({data}) => {
            let {list:dataSource=[]} = data;
            //console.log(dataSource);
            dataSource.forEach((item,index)=>{
                item.key=index;
            })
            if (!$http.needLogin(data.code)) {
                this.setState({
                    dataSource: data.list,
                    pagination: Object.assign({}, this.state.pagination, {
                        total: data.total,
                        pageSize: data.pageSize,
                        current: data.pageNum
                    })
                })
            }
        });
    }

    componentDidMount() {
        this.search();
    }

    render() {
        return <div className="page-body">
            <div className="page-title" style={{width: '100%'}}>禁播用户</div>
            <Row className="page-header">
                <Col span={6}>
                    <div className="page-title">用户总数：</div>
                    <div className="page-value">{this.state.pagination.total}</div>
                </Col>
                <Col style={{textAlign: 'right'}}>
                    <Button type="primary" onClick={() => {
                        this.search()
                    }}>查询</Button>
                </Col>
            </Row>
            <Table columns={this.columns} className="iplus-table" style={{marginTop: '10px'}} pagination={this.state.pagination} dataSource={this.state.dataSource}/>
        </div>;
    }
}