import React from 'react'
import $http from '../../../util/http'
import notice from '../../../util/notice'
import {Form, Input, Select, Button,Row,Col} from 'antd';
const FormItem = Form.Item;
const Option = Select.Option;
class deviceStorage extends React.Component {
    constructor() {
        super()
        this.state = {
            confirmDirty: false,
            autoCompleteResult: [],
            submitting: false
        };
        this.handleSubmit=this.handleSubmit.bind(this);
    }

    handleSubmit(e) {
        e.preventDefault();
        this.props.form.validateFieldsAndScroll((err, values) => {
            if (err)
                return;

            var config = {
                data: values,
                _that: this,
                _loading:'submitting',
                _msgTitle:'入库'
            };
            $http.post('/devinfoservice/adddevinfo', config).then((response) => {
                let data = response.data;
                if (data.code == '0') {
                    this.props.onCancel();
                }
            });
        });
    }

    render() {
        const {getFieldDecorator} = this.props.form;
        const formItemLayout = {
            labelCol: {
                xs: {span: 24},
                sm: {span: 6},
            },
            wrapperCol: {
                xs: {span: 24},
                sm: {span: 14},
            },
        };
        const tailFormItemLayout = {
            wrapperCol: {
                xs: {
                    span: 24,
                    offset: 0,
                },
                sm: {
                    span: 14,
                    offset: 6,
                },
            },
        };
        return (
            <Form onSubmit={this.handleSubmit}>
                <FormItem
                    {...formItemLayout}
                    label={( <span>设备厂商&nbsp; </span> )}>
                    {getFieldDecorator('company', {
                        rules: [{required: true, message: '请输入', whitespace: true}],
                    })(
                        <Input />
                    )}
                </FormItem>
                <FormItem
                    {...formItemLayout}
                    label={( <span> 设备SN码&nbsp; </span> )}>
                    {getFieldDecorator('mac', {
                        rules: [{required: true, message: '请输入设备SN码!', whitespace: true}],
                    })(
                        <Input />
                    )}
                </FormItem>
                <FormItem
                    {...formItemLayout}
                    label={( <span> 设备型号&nbsp; </span> )}>
                    {getFieldDecorator('model', {
                        rules: [
                            {required: true, message: '请输入设备型号'/*小于位的数字*/},
                        ],
                    })(
                        <Input />
                    )}
                </FormItem>

                <FormItem
                    {...formItemLayout}
                    label={( <span> 固件版本&nbsp; </span> )}>
                    {getFieldDecorator('version', {
                        rules: [{required: true, message: '请输入固件版本', whitespace: true}],
                    })(
                        <Input />
                    )}
                </FormItem>
                <FormItem
                    {...formItemLayout}
                    label={( <span> 备注&nbsp; </span> )}>
                    {getFieldDecorator('remark', {
                        rules: [{required: false, message: '请输入备注内容', whitespace: true}],
                    })(
                        <Input />
                    )}
                </FormItem>
            <div >
                <Row className="register-save">
                    <Col span={14} offset={6}>

                    <Button className="blue" type="primary" htmlType="submit" loading={this.state.submitting} size="large"
                        style={{with:'100%'}}>
                        提交
                </Button>
                    </Col>
                </Row>
            </div>
            </Form>
        );
    }
}
const device_Storages = Form.create()(deviceStorage);
export default device_Storages
