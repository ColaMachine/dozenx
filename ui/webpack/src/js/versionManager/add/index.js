/**
 * 版本升级管理--新增加
 */

import React from 'react'
import {Form, Input, Button,message,Icon} from 'antd';
import $http from '../../../util/http';

const FormItem = Form.Item;
const MAX_SIZE = 15;//文件最大体积(M)
const TIS_DESC = {
    errFormat: '文件格式错误!',
    errSize: `大小不能超过${MAX_SIZE}M`,
    errBlank: '请选择选择固件*.bin文件',
};

class VersionAdder extends React.Component {
    constructor() {
        super()
        this.state = {
            version: '',
            model: '',
            company: '',
            uploadFileName: '',
            submitting: false,
        };
        this.onFileChange = this.onFileChange.bind(this);
        this.openInput = this.openInput.bind(this);
        this.resetInput = this.resetInput.bind(this);
    }

    resetInput() {
        this.fileInput.value = '';
    }

    clearFile = () => {
        this.fileInput.value = '';
        this.file = null;
        this.setState({
            submitting: false,
            uploadFileName: '',
        });
    }

    openInput() {
        this.fileInput.click();
    }

    handleSubmit = (e) => {
        e && e.preventDefault();
        this.props.form.validateFieldsAndScroll((err, values) => {
            console.log('Received values of form: ', values);
            if (err) {
                console.error('校验表单失败: ', err);
                return;
            }

            let {file} = this;
            if (!file) {
                message.error(TIS_DESC.errBlank);
                return;
            }

            let url = "/upgradesrv/addfirmware";
            let _formData = Object.assign({file}, values);
            if (!_formData.desc)
                delete _formData.desc;

            let config = {
                _loading: 'submitting',
                _that: this,
                _formData,
                _msgTitle: '添加固件版本'
            };

            $http.postForm(url, config).then((rsp) => {
                this.setState({uploadFileName: file.name});
                if (rsp.code == 0) {
                    this.clearFile();
                    this.goBack();
                }
            });

        });

    }

    onFileChange(e) {
        let {target: fileInput} = e.target;
        console.log(fileInput);


        let file = (e.target.files[0]);
        if (!file) {
            message.error(TIS_DESC.errBlank);
            return;
        }


        if (!/.bin$/.test(file.name)) {
            message.error(TIS_DESC.errFormat);
            return;
        }

        if (file.size > MAX_SIZE * 1024 * 1024) {
            message.error(TIS_DESC.errSize);
            return;
        }

        this.file = file;
        this.setState({
            uploadFileName: file.name
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
            <div className="right-management">
                <div className="page-title-me" style={{width: '100%'}}>版本管理&nbsp;/&nbsp;新增固件</div>
                <div className="upload-window-wrap">
                    <Form onSubmit={this.handleSubmit} layout="horizontal">
                        <FormItem label='设备厂商：'>
                            {getFieldDecorator('corporation', {
                                rules: [{required: true, message: '必填', whitespace: true}],
                            })(
                                <Input placeholder='请输入设备厂商，不超过20个字符' maxLength='20'/>
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
                            {getFieldDecorator('desc')(
                                <Input placeholder='不多于60字' maxLength='20'/>
                            )}
                        </FormItem>

                        <div className="ant-row">
                            <div className="ant-form-item-label">
                                选择固件：
                            </div>

                            <div>
                                <div className="upload-device" style={{position: 'relative'}}>
                                    <div className="upload-base">
                                <span >
                                    <Icon type="close" onClick={this.clearFile}/>
                                    {this.state.uploadFileName}
                                    </span>
                                        <Button className="upload-btn" onClick={this.openInput}>选择</Button>
                                    </div>
                                </div>
                            </div>
                            <input type="file" onChange={this.onFileChange}
                                   accept="application/octet-stream"
                                   style={{display: 'none'}}
                                   ref={(ele) => {
                                       this.fileInput = ele;
                                   }}/>
                        </div>


                        <div className="btn-area">
                            <Button type="primary" htmlType="submit"
                                    loading={state.submitting}>提 交</Button>
                            <Button
                                className="cancel"
                                onClick={this.goBack}>取 消</Button>
                        </div>
                    </Form>
                </div>
            </div>
        );
    }
}


export default Form.create()(VersionAdder);