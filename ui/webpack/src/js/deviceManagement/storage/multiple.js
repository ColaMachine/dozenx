/**
 * 版本升级管理--新增加
 */

import React from 'react'
import {Form, Input, Button, message, Icon} from 'antd';
import $http from '../../../util/http';

const FormItem = Form.Item;
const MAX_SIZE = 1;//文件最大体积(M)
const TIS_DESC = {
    errFormat: '文件格式错误!',
    errSize: `大小不能超过${MAX_SIZE}M`,
    errBlank: '请选择选择excel文件',
};

/**
 * 文件选择器，
 */
class ExcelUploader extends React.Component {
    constructor(p) {
        super(p);
        this.state = {
            uploadFileName: '',
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
        this.setState({
            submitting: false,
            uploadFileName: '',
        });
        this.triggerChange();
    }

    openInput() {
        this.fileInput.click();
    }

    onFileChange(e) {
        let {target: input} = e;
        this.triggerChange(input.files[0]);
    }


    triggerChange = (file) => {
        this.setState({
            uploadFileName: file ? file.name : ''
        });
        const onChange = this.props.onChange;
        onChange && onChange(file);
    }


    render() {
        return (  <div className="ant-row">
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
                   onClick={this.resetInput}
                   accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                   style={{display: 'none'}}
                   ref={(ele) => {
                       this.fileInput = ele;
                   }}/>
        </div> );
    }
}

class StorageMultiple extends React.Component {
    constructor(p) {
        super(p);
        this.state = {
            submitting: false,
            showDownTemplate: true
        };
    }

    goBack = () => {
        window.history.back();
    }

    handleSubmit = (e) => {
        e && e.preventDefault();
        this.props.form.validateFieldsAndScroll((err, values) => {
            //console.log('Received values of form: ', values);
            if (err) {
                //console.error('校验表单失败: ', err);
                return;
            }

            let {version, company, model} = values;
            let _urlParams = {version, company, model};
            let url = "/devinfoservice/batchdevinfo";
            let config = {
                _loading: 'submitting',
                _that: this,
                _formData: {file: values.file},
                _urlParams,
                _msgTitle: '批量入库上传文件',
            };

            $http.postForm(url, config).then((rsp) => {
                if (rsp.code == '0') {
                    //console.log('成功')
                    this.goBack();
                }
            });

        });

    }


    render() {
        let {state} = this;
        const {getFieldDecorator} = this.props.form;

        let verifyVersion = (rule, value, callback) => {
            if (!value) {
                callback('必填')
            }
            else if (!/^V[0-9]\.[0-9]\.[0-9]/.test(value)) {
                callback('版本格式:V1.2.3')
            } else {
                callback();
            }
        }

        let verifyExcel = (rule, value, callback) => {
            console.log(value);
            let {showDownTemplate} = this.state;
            this.state.showDownTemplate = true;
            if (!value) {
                callback('必填')
            }
            else if (!/.xls$/.test(value.name) && !/.xlsx$/.test(value.name)) {
                callback(TIS_DESC.errFormat);
            }
            else if (value.size > MAX_SIZE * 1024 * 1024) {
                callback(TIS_DESC.errSize);
            } else {
                this.state.showDownTemplate = false;
                callback();
            }

            if (showDownTemplate != this.state.showDownTemplate)
                this.forceUpdate();

        }

        return ( <Form onSubmit={this.handleSubmit} layout="horizontal">
            <FormItem label='设备厂商：'>
                {getFieldDecorator('company', {
                    rules: [{required: true, message: '必填', whitespace: true}],
                })(
                    <Input placeholder='请输入设备厂商，不超过20个字符' maxLength='20'/>
                )}
            </FormItem>
            <FormItem label='设备型号：'>
                {getFieldDecorator('model', {
                    rules: [{required: true, message: '必填', whitespace: true}],
                })(
                    <Input placeholder='请输入设备型号，不超过40个字符' maxLength='40'/>
                )}
            </FormItem>
            <FormItem label='固件版本：'>
                {getFieldDecorator('version', {
                    rules: [{required: true, validator: verifyVersion}],
                })(
                    <Input placeholder='请输入版本号，格式Vx.x.x  x取值 0-9' maxLength='20'/>
                )}
            </FormItem>

            <div style={{position: 'relative'}}>
                <FormItem label='上传Excel清单:'>
                    {getFieldDecorator('file', {
                        rules: [{required: true, validator: verifyExcel}],
                    })(
                        <ExcelUploader/>
                    )}
                </FormItem>

                {state.showDownTemplate ? <div className="template-down">
                    <a href="/devinfo.xlsx">下载导入模板</a>
                </div> : null }
            </div>


            <div className="btn-area">
                <Button type="primary" htmlType="submit"
                        loading={state.submitting}>提 交</Button>
                <Button
                    className="cancel"
                    onClick={this.goBack}>取 消</Button>
            </div>
        </Form> );
    }
}


export default Form.create()(StorageMultiple);