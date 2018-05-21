/**
 * Created by songbo on 2017/8/22.
 */
import React from 'react'
import {Form, Input, Button, Select} from 'antd';
import $http from '../../util/http'
const FormItem = Form.Item;
const {Option} = Select;


class searchBar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            submitting: false, // 查询中变量
            liveDeviceCount: 0,
            deviceCount: 0,
        };
        this.typeChanged = this.onSelectChanged.bind(this, 'liveType');
        this.timesChanged = this.onSelectChanged.bind(this, 'illegalCount');
        this.contentChanged = this.onInputChanged.bind(this, 'remark');
        this.deviceChanged = this.onInputChanged.bind(this, 'deviceName');

        this.searhData = {};//检索条件
        this.pageNo = 1;
        this.pageSize = 20;
    }

    onSelectChanged(key, val) {
        this.searhData[key] = val;
    }

    onInputChanged(key, event) {
        console.log(key,':',event.target.value)
        this.searhData[key] = event.target.value;
    }

    componentDidMount() {
        this.handleSubmit();
    }

    componentWillReceiveProps(nextProp) {
        let next = nextProp.changePage;
        if (next.update === this.update)
            return;

        this.pageNo = next.pageNo;
        this.pageSize = next.pageSize;
        this.update = next.update;
        this.handleSubmit();
    }

    //查询
    handleSubmit(e) {
        if (e)
            e.preventDefault();

        this.props.form.validateFields((err, values) => {
            if (err)
                return;
            // 向父类推送数据
            let callback = (data) => {
                let {liveDeviceCount = 0, deviceCount = 0} = data || {};

                this.setState({
                    liveDeviceCount, deviceCount
                })
                this.props.dataCallback(data);
            };
            var config = {
                params: Object.assign({
                    pageNo: this.pageNo,
                    pageSize: this.pageSize,
                }, this.searhData),
                _that: this,
                _loading: 'submitting'
            };

            if(e)config._msgTitle = '查询';
            $http.list('/livedevice/info', config, '', callback);
        });
    }

    render() {
        return (
            <div className="form">
                <Form onSubmit={this.handleSubmit.bind(this)} layout="inline">
                    <ul className="search-bar">
                        <li>
                            <span className="label-block">设备总数:</span>
                            <span className="label-block-text">{this.state.deviceCount}</span>
                        </li>
                        <li>
                            <span className="label-block">直播总数:</span>
                            <span className="label-block-text">{this.state.liveDeviceCount}</span>
                        </li>
                        <li className="pull-right">
                            <FormItem>
                                <Button type="primary" className="searchButton" loading={this.state.submitting}
                                        htmlType="submit">查询</Button>
                            </FormItem>
                        </li>
                        <li className="pull-right">
                            <FormItem>
                                <Input style={{width: 120}} placeholder="请输入设备名称"
                                       onChange={this.contentChanged}/>
                            </FormItem>
                        </li>
                        <li className="pull-right">
                            <FormItem>
                                <Input style={{width: 120}} placeholder="查找内容" onChange={this.deviceChanged} maxLength='20'/>
                            </FormItem>
                        </li>
                        <li className="pull-right">
                            <FormItem  >
                                <Select
                                    style={{width: "95px", borderRadius: "15px"}}
                                    showSearch
                                    className="select"
                                    placeholder="直播类型"
                                    onChange={this.typeChanged}>
                                    <Option value="推荐">推荐</Option>
                                    <Option value="最新">最新</Option>
                                    <Option value="商业">商业</Option>
                                    <Option value="附近">附近</Option>
                                    <Option value="生活">生活</Option>
                                    <Option value="街景">街景</Option>
                                </Select>
                            </FormItem>
                        </li>
                        <li className="pull-right">
                            <FormItem  >
                                <Select
                                    style={{width: "95px", borderRadius: "15px"}}
                                    className="select"
                                    placeholder="违规次数"
                                    onChange={this.timesChanged}>
                                    <Option value="1">1</Option>
                                    <Option value="2">2</Option>
                                    <Option value="3">3</Option>
                                </Select>
                            </FormItem>
                        </li>

                    </ul>
                </Form>

                <div style={{clear: "both"}}></div>
            </div>
        );
    }
}


let searchB = Form.create()(searchBar);
export default searchB