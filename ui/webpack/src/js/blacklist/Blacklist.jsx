import React from 'react';
import {Row, Col, Button, Table, Pagination, Popconfirm, notification} from 'antd';
import '../../css/blacklist.scss';
import axios from 'axios';
import {config} from '../../util/config';
import $http from '../../util/http'


export default class Blacklist extends React.Component {
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
        title: '设备SN码',
        dataIndex: 'sn',
    }, {
        title: '违规次数',
        dataIndex: 'illegalCount',
        render: (text, record) => {
            return `${record.illegalCount}次`;
        },
    }, {
        title: '所属账户',
        dataIndex: 'phone',
    }, {
        title: '手动解禁',
        render: (text, record) => {
            return <Popconfirm title="确认解禁该设备？" onConfirm={() => {
                this.setUserFree(record);
            } } okText="确认" cancelText="取消">
                <a href="#">解禁</a>
            </Popconfirm>
        },
    }, {
        title: '禁播时间',
        dataIndex: 'bannedDate',
        render: (text, record) => {
            return `${record.bannedDate}天`;
        },
    },];

    setUserFree(record) {
        console.log('setUserFree', record);
        axios.delete('/liveblack/removeliveblack', {
            params: {
                params: JSON.stringify({
                    mac: record.mac,
                    token: sessionStorage.token
                })
            }
        }).then(({data}) => {
            //console.log(data);
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
        axios.get('/liveblack/info', {
            params: {
                params: JSON.stringify({
                    pageNo: pageNo,
                    pageSize: pageSize,
                    token: sessionStorage.token
                })
            }
        }).then(({data}) => {
            console.log(data);

            if (!$http.needLogin(data.code)) {
                let {list:dataSource=[]}=data;
                dataSource.forEach((item,index)=>{
                    item.key =index;
                })
                this.setState({
                    dataSource,
                    pagination: Object.assign({}, this.state.pagination, {
                        total: data.total,
                        pageSize: data.pageSize,
                        current: data.pageNum
                    })
                })
            }
        });
    }

    export=()=>{
        axios.get('/liveblack/exportexcel',{
            responseType: 'blob',// 表明返回服务器返回的数据类型,
            params: {
                params: JSON.stringify({
                   
                    token: sessionStorage.token
                })
            }
        }).then((res)=>{
            const content = res
            const blob = new Blob([content.data])
            const fileName = '导出黑名单列表.xls'
            console.log("res",res);
            console.log('blob',blob);
            //return;
            if ('download' in document.createElement('a')) { // 非IE下载
              const elink = document.createElement('a')
              elink.download = fileName
              elink.style.display = 'none'
              elink.href = URL.createObjectURL(blob)
              document.body.appendChild(elink)
              elink.click()
              URL.revokeObjectURL(elink.href) // 释放URL 对象
              document.body.removeChild(elink)
            } else { // IE10+下载
              navigator.msSaveBlob(blob, fileName)
            }
        })
       
    }

    componentDidMount() {
        this.search();
    }

    render() {
        console.log("this.state",this.state.dataSource);
        return <div className="page-body">
            <div className="page-title" style={{width: '100%'}}>黑名单</div>
            <Row className="page-header">
                <Col span={6}>
                    <div className="page-title">总数：</div>
                    <div className="page-value">{this.state.pagination.total}</div>
                </Col>
                <Col style={{textAlign: 'right'}}>
                    <Button type="primary" onClick={this.export}>导出Excel</Button>
                    <Button type="primary" onClick={() => {
                        this.search()
                    }}>查询</Button>
                </Col>
            </Row>
            <Table columns={this.columns} className="iplus-table" style={{marginTop: '10px'}} pagination={this.state.pagination} dataSource={this.state.dataSource}/>
        </div>;
    }
}