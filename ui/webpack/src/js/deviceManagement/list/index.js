/**
 * Created by Stanley Huang on 2017/8/22.
 */
import React from 'react'
import {Form, Input, Button, Select, Table, Modal, Tabs, Pagination} from 'antd';
import $http from '../../../util/http'

import DeviceStorage from './singleDeviceStorage'
import MultipleDeviceStorage from './multipleDeviceStorage'
import util from '../../../util/formatTool'

const FormItem = Form.Item;
const {Option, OptGroup} = Select;
const TabPane = Tabs.TabPane;
class deviceManagement extends React.Component {
    constructor() {
        super();
        this.state = {
            tableData: [],
            registerWindow: false,
            submitting: false,
        }
        this.statChange = this.statChange.bind(this);
        this.onPageSizeChange = this.onPageSizeChange.bind(this);
        this.onPageChange = this.onPageChange.bind(this);

        this.pageNo = 1;
        this.pageSize = 10;
        this.totalPage = 0;
        this.totalRecord = 0;
    }
    componentDidMount() {
        this.handleSubmit();
    }
    onPageSizeChange(current, size) {
        this.pageSize = size;
        this.pageNo=current;
        this.handleSubmit();
    }

    onPageChange(page, pageSize) {
        this.pageNo = page;
        this.handleSubmit();
    }

    //查询
    handleSubmit(e) {
        if (e)
            e.preventDefault();

        this.props.form.validateFields((err, values) => {
            if (err)
                return;

            let format = (data) => {
                if (util.itemTypeOf(data) != 'array')
                    return;

                data.forEach((item, index) => {
                    item.key = index;
                    if(!item.company)item.company='-';

                    if (item.status == 2) {
                        item.$status = '已绑定';
                        item.$bindTime = util.detailTimeFromStamp(item.bindTime);
                    } else {
                        item.$status = '未绑定';
                        item.$bindTime = '-';
                    }
                    item.$createTime = util.detailTimeFromStamp(item.createTime);
                });
            };

            var config = {
                params: {
                    mac: values.mac,
                    status: this.status,
                    pageNum: this.pageNo,
                    pageSize: this.pageSize
                },
                _key: 'tableData',
                _that: this,
                _loading: 'submitting'
            };
            if(e)config._msgTitle= '查询';

            $http.list('/devinfoservice/searchdevice', config, format);
        });
    }

    handleChange(value) {
        //console.log(`selected ${value}`);
    }

    statChange(val) {
        console.log(`status ${val}`);
        this.status = val;
    }

    showRegisterWindow() {
        window.location.hash='#/index/device/storage';
        return;

        this.setState({
            registerWindow: true,
        });
    }

    handleOk(e) {
        this.setState({
            registerWindow: false,
        });
    }

    handleCancel(e) {
        this.setState({
            registerWindow: false,
        });
    }

    callback(key) {
        console.log(key);
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
                title: '厂商',
                dataIndex: 'company',
            },  {
                title: '设备SN码',
                dataIndex: 'sn',
            }, {
                title: '设备型号',
                dataIndex: 'model',
            }, {
                title: '固件版本',
                dataIndex: 'version',
            }, {
                title: '绑定状态',//1-未绑定，2-已绑定
                dataIndex: '$status',
            }, {
                title: '入库时间',
                dataIndex: '$createTime',
            }, {
                title: '绑定时间',
                dataIndex: '$bindTime',
            }];
        const {getFieldDecorator} = this.props.form;
        const formItemLayout = {
            labelCol: {
                xs: {span: 12},
                sm: {span: 0},
            },
            wrapperCol: {
                xs: {span: 12},
                sm: {span: 0},
            },
        };

        return (
            <div className="right-management">
                <div className="page-title-me" style={{width: '100%'}}>设备管理</div>
                <div className="form">
                    {/*表格头部*/}
                    <Form onSubmit={this.handleSubmit.bind(this)} layout="inline">
                        <ul className="search-bar">
                            <li>
                                <FormItem>
                                    <Button type="primary" className="storageButton"
                                            onClick={this.showRegisterWindow.bind(this)}>设备入库</Button>
                                </FormItem>
                            </li>
                            <li className="pull-right" >
                                <FormItem>
                                    <Button style={{marginLeft:'20px'}} type="primary" className="searchButton" loading={this.state.submitting}
                                            htmlType="submit">查询</Button>
                                </FormItem>
                            </li>
                            <li className="pull-right">
                                <FormItem
                                    {...formItemLayout}
                                    label="设备状态"
                                >
                                    <Select
                                        style={{width: "95px", borderRadius: "15px"}}
                                        showSearch
                                        className="select"
                                        placeholder="请选择"
                                        optionFilterProp="children"
                                        onChange={this.statChange}
                                        onFocus={this.handleFocus}
                                        onBlur={this.handleBlur}
                                        filterOption={(input, option) => option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0}
                                    >
                                        <Option value="1">未绑定</Option>
                                        <Option value="2">已绑定</Option>
                                    </Select>
                                </FormItem>
                            </li>
                            <li className="pull-right">
                                <FormItem
                                    {...formItemLayout}
                                    label="设备SN码"
                                >{getFieldDecorator('mac', {
                                    rules: [{required: false, message: '请输入设备MAC'}],
                                })(
                                    <Input style={{width:120}} placeholder="请输入"/>
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
                    {/*设备入库弹窗*/}
                    <Modal
                        title="设备入库"
                        height={395}
                        width={640}
                        visible={this.state.registerWindow}
                        onOk={this.handleOk.bind(this)}
                        onCancel={this.handleCancel.bind(this)}
                        footer={null}>
                        <Tabs defaultActiveKey="1" onChange={this.callback}>
                            <TabPane tab="单个入库" key="1"><DeviceStorage
                                onCancel={this.handleCancel.bind(this)}/></TabPane>
                            <TabPane tab="批量入库" key="2"><MultipleDeviceStorage/></TabPane>
                        </Tabs>
                    </Modal>
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


const devicePage = Form.create()(deviceManagement);
export default devicePage