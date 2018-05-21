/**
 * 版本升级管理--新增加
 */

import React from 'react'
import {Form, Input, Button, message, Icon} from 'antd';
import $http from '../../../util/http';

const FormItem = Form.Item;

class StorageSingle extends React.Component {
    constructor() {
        super()
        this.state = {
            version: '',
            model: '',
            company: '',
            submitting: false,
        };
    }


    handleSubmit = (e) => {
        e && e.preventDefault();
        this.props.form.validateFieldsAndScroll((err, values) => {
            console.log('Received values of form: ', values);
            if (err) {
                console.error('校验表单失败: ', err);
                return;
            }

            var config = {
                data: values,
                _that: this,
                _loading: 'submitting',
                _msgTitle: '入库'
            };
            $http.post('/devinfoservice/adddevinfo', config).then((response) => {
                let data = response.data;
                if (data.code == '0') {
                    this.goBack();
                }
            });

        });

    }


    goBack = () => {
        window.history.back();
    }

    render() {
        let {state} = this;
        const {getFieldDecorator} = this.props.form;

        function verifyVersion(rule, value, callback) {
            if (!value) {
                callback('必填')
            }
            else if (!/^V[0-9]\.[0-9]\.[0-9]/.test(value)) {
                callback('版本格式:V1.2.3')
            } else {
                callback();
            }
        }

        return (
            <Form onSubmit={this.handleSubmit} layout="horizontal">
                <FormItem label='设备厂商：'>
                    {getFieldDecorator('company', {
                        rules: [{required: true, message: '必填', whitespace: true}],
                    })(
                        <Input placeholder='请输入设备厂商，不超过20个字符' maxLength='20'/>
                    )}
                </FormItem>
                <FormItem label='设备SN码：'>
                    {getFieldDecorator('mac', {
                        rules: [{required: true, message: '必填', whitespace: true}],
                    })(
                        <Input placeholder='请输入 ' maxLength='20'/>
                    )}
                </FormItem>
                <FormItem label='设备型号：'>
                    {getFieldDecorator('model', {
                        rules: [{required: true, message: '必填', whitespace: true}],
                    })(
                        <Input placeholder='请输入设备型号，不超过40个字符' maxLength='20'/>
                    )}
                </FormItem>
                <FormItem label='固件版本：'>
                    {getFieldDecorator('version', {
                        rules: [{required: true, validator: verifyVersion}],
                    })(
                        <Input placeholder='请输入版本号，格式Vx.x.x  x取值 0-9' maxLength='20'/>
                    )}
                </FormItem>
                <FormItem label='说  明：'>
                    {getFieldDecorator('remark')(
                        <Input placeholder='不多于60字' maxLength='60'/>
                    )}
                </FormItem>


                <div className="btn-area">
                    <Button type="primary" htmlType="submit"
                            loading={state.submitting}>提 交</Button>
                    <Button
                        className="cancel"
                        onClick={this.goBack}>取 消</Button>
                </div>
            </Form>);

    }
}


export default Form.create()(StorageSingle);