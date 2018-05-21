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
            list: [],
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
        let url = 'questionnaire/list';
        url = url + '?'+"source=shixun"+"&"+`pageNum=${this.pageNo}`+"&"+`pageSize=${this.pageSize}`;
        Axios.get(url, this.HeaderToken).then(({data}) => {
           let{list=[],total=0}=data.data;
           list.forEach((item, index) => {
                item.key = index + this.pageSize * (this.pageNo - 1) + 1;
            })
           this.setState({
                list, total
             });
            console.log(list)
        }).catch((err) => {
            console.error('获取列表失败')
        })
    }
   
    render() {
        const columns = [
            {
                title: '问卷名称',
                dataIndex: 'title',
            }, {
                title: '状态',
                dataIndex: 'status',
                render:(text)=>{
                    return text==1?"未开始":""||text==2?"进行中":""||text==3?"已结束":""
                }
            }, {
                title: '参与人数',
                dataIndex: 'answerNumber',
            }, {
                title: '题目数量',
                dataIndex: 'questionNumber',
            }, {
                title: '统计分析',
                render: (text, record, index) =>  {
                    return <Link to={
                       `/index/investigateManager/statistics?id:${text.id}&title:${text.title}`
                    }>统计分析</Link>
                } 
            }, {
                title: '操作',
                render: (text, record, index) => {
                    return <Link to={
                       `/index/investigateManager/add?id:${text.id}`
                    }>编辑</Link>
                }
            }];

        return (
            <div className="right-management">
                <div className="page-title-me" style={{width: '100%'}}>调查问卷</div>
                <div className="form">
                    <Button type="primary" className="storageButton"
                    ><Link to="/index/investigateManager/create">创建问卷</Link></Button>
                </div>
                <div className="table">
                    <Table columns={columns} className="dev-table"
                           style={{backgroundColor: '#fff'}}
                           dataSource={this.state.list}
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

