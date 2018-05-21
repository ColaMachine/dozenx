/**
 * 版本升级管理列表
 */
import React from 'react'
import {Button, Table, Modal, Pagination, notification} from 'antd';
import {Link} from 'react-router-dom'

import util from '../../../util/formatTool'
import  Axios from 'axios';
import  QueryString from 'querystring';
import $http from '../../../util/http';


export default class VersionList extends React.Component {
    constructor() {
        super();
        this.state = {
            tableData: [],
            registerWindow: false,
            submitting: false,
            showDelModal: false,
            total: 0
        }
        this.onPageSizeChange = this.onPageSizeChange.bind(this);
        this.onPageChange = this.onPageChange.bind(this);

        this.pageNo = 1;
        this.pageSize = 10;

    }

    componentWillMount() {
        this.HeaderToken = {headers: {token: sessionStorage.token}};
    }

    componentDidMount() {
        this.search();
    }

    onPageSizeChange(current, size) {
        this.pageSize = size;
        this.search();
    }

    onPageChange(page, pageSize) {
        this.pageNo = page;
        this.search();
    }

    //查询
    search() {

        let url = '/upgradesrv/list';//?params={“model”：“”，“pageNo”:1，”pageSize":10...}
        let pms = {
            params: JSON.stringify({pageNo: this.pageNo, pageSize: this.pageSize})
        }
        url = url + '?' + QueryString.stringify(pms);
        Axios.get(url, this.HeaderToken).then(({data}) => {
            console.log(data)
            if ($http.needLogin(data.code))
                return;

            let {records: tableData = [], total = 0} = data.data;
            tableData.forEach((item, index) => {
                item.key = index;
                item.$uploadDate = util.detailTimeFromStamp(item.updateDate);
            })
            this.setState({
                tableData, total
            });

        }).catch((err) => {
            console.error('获取列表失败')
        })
    }

    pageItemRender(current, type, originalElement) {
        if (type === 'prev') {
            return <a>上一页</a>;
        } else if (type === 'next') {
            return <a>下一页</a>;
        }
        return originalElement;
    }

    push(data) {
        if (data.status !== 0)
            return;

        let url = '/upgradesrv/pushfirmware/' + data.id;
        Axios.put(url, null, this.HeaderToken).then(({data}) => {
            if (data.code !== '0') {
                notification.warning({message: '推送失败', description: data.msg});
                return;
            }
            notification.info({
                message: '推送成功'
            });
            this.search();
        }).catch((err) => {
            notification.warning({message: '推送失败', description: JSON.stringify(err)});
        });
    }

    delModal = (data) => {
        console.log(data)
        this.deletItem = data;
        this.setState({
            showDelModal: true
        });
    }
    cancelDel = () => {
        this.setState({
            showDelModal: false
        });
    }


    doDelete = () => {
        let url = '/upgradesrv/firmWare/' + this.deletItem.id;
        Axios.delete(url, this.HeaderToken).then(({data}) => {
            if (data.code !== '0') {
                notification.warning({message: '删除失败', description: data.msg});
                return;
            }
            notification.info({
                message: '删除成功'
            });
            this.search();
        }).catch((err) => {
            notification.warning({message: '删除失败', description: JSON.stringify(err)});
        });

        this.setState({
            showDelModal: false
        });
    }

    render() {
        const columns = [
            {
                title: '厂商',
                dataIndex: 'corporation',
            }, {
                title: '设备型号',
                dataIndex: 'model',
            }, {
                title: '版本号',
                dataIndex: 'version',
            }, {
                title: '上传日期',
                dataIndex: '$uploadDate',
            }, {
                title: '版本说明',
                dataIndex: 'versionDescription',
            }, {
                title: '操作',
                width: '10%',
                render: (text, record, index) => {
                    let disabled = record.status !== 0;
                    return <span>
              <a href="javascript:;" onClick={this.push.bind(this, record)}
                 style={{color: disabled ? '#cbcbcb' : 'blue', width: 55, display: 'inline-block'}}>
                  {disabled ? '已推送' : '推送'}</a>
              <a href="javascript:;" onClick={this.delModal.bind(this, record)} style={{color: 'red'}}>删除</a>
            </span>
                }
            }];

        return (
            <div className="right-management">
                <div className="page-title-me" style={{width: '100%'}}>版本管理</div>
                <div className="form">
                    <Button type="primary" className="storageButton"
                    ><Link to="/index/versionManager/add">添加版本</Link></Button>
                </div>
                <Modal title="提示"
                       width={320}
                       visible={this.state.showDelModal}
                       footer={[
                           <Button key="back" size="large" onClick={this.cancelDel}>取消</Button>,
                           <Button key="submit" type="primary" size="large" onClick={this.doDelete}>确定</Button>
                       ]}
                       onCancel={this.cancelDel}>
                    <p>确定删除这个固件版本升级包吗？</p>
                </Modal>

                <div className="table">
                    <Table columns={columns} className="dev-table"
                           style={{backgroundColor: '#fff'}}
                           dataSource={this.state.tableData}
                           pagination={false}/>
                </div>
                {/*分页*/}
                {this.state.total ? <Pagination
                    className="pagination"
                    showSizeChanger
                    onShowSizeChange={this.onPageSizeChange}
                    onChange={this.onPageChange}
                    total={this.state.total}
                    itemRender={this.pageItemRender}/> : ''}
            </div>
        );
    }
}

