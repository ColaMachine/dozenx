/**
 * Created by songbo on 2017/8/22.
 */
import React from 'react'
import {Form, Input, Button, Select, Table,   Pagination, DatePicker, notification} from 'antd';
import $http from '../../util/http'
import util from '../../util/formatTool'

const FormItem = Form.Item;
const {Option, OptGroup} = Select;
class userManagement extends React.Component {
    constructor() {
        super();
        this.state = {
            tableData: [],
            registerWindow: false,
            submitting: false,
            userCount: 0,
            onlineCount: 0
        }
        this.statChange = this.statChange.bind(this);
        this.onPageSizeChange = this.onPageSizeChange.bind(this);
        this.onPageChange = this.onPageChange.bind(this);

        this.page = 1;
        this.pageSize = 10;
        this.totalPage = 0;
        this.totalRecord = 0;
    }

    componentDidMount() {
        this.handleSubmit();
    }

    onPageSizeChange(current, size) {
        this.pageSize = size;
        this.handleSubmit();
    }

    onPageChange(page, pageSize) {
        this.page = page;
        this.handleSubmit();
    }

    //查询
    handleSubmit(e) {
        if (e)
            e.preventDefault();

        this.props.form.validateFieldsAndScroll((err, fieldsValue) => {
           
            if (err) {
                console.error('err:', err)
                return;
            }
            const values = {
                ...fieldsValue,
                'startTime':fieldsValue['startTime']?fieldsValue['startTime'].format('YYYY-MM-DD'):'',
                'endTime':fieldsValue['endTime']?fieldsValue['endTime'].format('YYYY-MM-DD'):'',
              }
              console.log("values",values);
              
              let strStart = values.startTime; 
              strStart = strStart.replace(/-/g,'/');
              let date1 = new Date(strStart);
              console.log('date1',date1.getTime());
              let strEnd = values.endTime; 
              strEnd = strEnd.replace(/-/g,'/');
              let date2 = new Date(strEnd);
             console.log('date2',date2.getTime());
            
              if(strStart>strEnd){
                notification['error']({
                    message: '警告',
                    description: '开始时间必须小于结束时间',
                  });
              }else if(strStart==strEnd&&strStart!=0){
                notification['error']({
                    message: '警告',
                    description: '开始时间和结束时间不能相同',
                  });
              }
              
            let format = (data) => {
                if (util.itemTypeOf(data) != 'array')
                    return;

                data.forEach((item, index) => {
                    item.key = index;
                    item.$loginTime = util.detailTimeFromStamp(item.loginTime);
                });
            };

            //取得在线用户数量，因为数据格式特别，所以在回调中获取
            let getOnlineCount = ({userCount=0, onlineCount=0}) => {
                let obj = {userCount, onlineCount};
                console.log('obj:', obj)
                this.setState(obj);
            };
            var config = {
                params: {
                    isillegal: this.status,//0无1有
                    page: this.page,
                    pageSize: this.pageSize,
                    startTime:date1.getTime(),
                    endTime:date2.getTime()
                },
                _key: 'tableData',
                _that: this,
                _loading: 'submitting'
            };
            if(e)config._msgTitle= '查询';

            $http.list('/userservice/selectbycondition', config, format, getOnlineCount);
        });
    }

    statChange(val) {
        this.status = val;
    }

    pageItemRender(current, type, originalElement) {
        if (type === 'prev') {
            return <a>上一页</a>;
        } else if (type === 'next') {
            return <a>下一页</a>;
        }
        return originalElement;
    }

    render() {
        const columns = [
            {
                title: '用户名称',
                dataIndex: 'userName',
            }, {
                title: '账号',
                dataIndex: 'phone',
            }, {
                title: '设备数量',
                dataIndex: 'devCount',
                render: (text) => {
                    return text+'台';
                },
            }, {
                title: '直播数量',
                dataIndex: 'liveCount',
                render: (text) => {
                    return text+'台';
                }
            }, {
                title: '违规直播',
                dataIndex: 'isIllegal',
                render: (text) => {
                    return text?'有':'无';
                }
            }, {
                title: '最近一次登录时间',
                dataIndex: '$loginTime',
            },{
                title:'登录次数',
                dataIndex:'loginCount',
                render: (text) => {
                    return text+'次';
                }
            },{
                title:'在线时长',
                dataIndex:'duration',
                render: (text) => {
                    return text+'分钟';
                }
            }];
        const {getFieldDecorator} = this.props.form;

        return (
            <div className="right-management">
                <div className="page-title-me" style={{width: '100%'}}>用户管理</div>

                <div className="form">
                    {/*表格头部*/}
                    <Form onSubmit={this.handleSubmit.bind(this)} layout="inline">
                        <ul className="search-bar">
                            <li>
                                <span className="label-block">用户总数:</span>
                                <span className="label-block-text">{this.state.userCount}</span>

                            </li>
                            <li>
                                <span className="label-block">在线用户:</span>
                                <span className="label-block-text">{this.state.onlineCount}</span>
                            </li>

                            <li className="pull-right">
                                <FormItem>
                                    <Button type="primary" className="searchButton" loading={this.state.submitting}
                                            htmlType="submit">查询</Button>
                                </FormItem>
                            </li>
                            <li className="pull-right">
                                <FormItem
                                >
                                    <Select
                                        style={{width: "95px", borderRadius: "15px"}}
                                        placeholder="违规直播"
                                        optionFilterProp="children"
                                        onChange={this.statChange}
                                    >
                                        <Option value="1">有</Option>
                                        <Option value="0">无</Option>
                                    </Select>
                                </FormItem>
                            </li>

                            <li className="pull-right">
                                <FormItem label={(<span>开始时间</span>)} >
                                    {getFieldDecorator('startTime')(
                                        <DatePicker format='YYYY/MM/DD' style={{'width':'100%'}}/>
                                )}
                                </FormItem>

                                <FormItem label={(<span>结束时间</span>)}>
                                    {getFieldDecorator('endTime')(
                                        <DatePicker format='YYYY/MM/DD' style={{'width':'100%'}}/>
                                    )}
                                </FormItem>
                            </li>
                        </ul>
                    </Form>

                    <div style={{clear: "both"}}></div>
                </div>

                <div className="table">
                    <Table columns={columns} className="dev-table" style={{backgroundColor: '#fff'}}
                           dataSource={this.state.tableData} pagination={false}/>

                </div>
                {/*分页*/}
                {this.totalRecord ? <Pagination
                    className="pagination"
                    showSizeChanger
                    onShowSizeChange={this.onPageSizeChange}
                    onChange={this.onPageChange}
                    total={this.totalRecord}
                    itemRender={this.pageItemRender}/> : ''}
            </div>
        );
    }
}


const userPage = Form.create()(userManagement);
export default userPage