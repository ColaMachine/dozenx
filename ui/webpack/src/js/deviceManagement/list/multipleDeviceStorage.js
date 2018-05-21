/**
 * Created by songbo on 2017/9/4.
 */

import React from 'react';
import $http from '../../../util/http';
import querystring from 'querystring';
import {message, Button, Input, Icon, Row, Col} from 'antd';
import {config} from '../../../util/config'

export default class multipleDeviceStorage extends React.Component {
    constructor() {
        super()
        this.state = {
            errInfo: '',
            version: '',
            model: '',
            company: '',
            uploadFileName: '',
            submitting: false,
            submitDisabled: true,
        };
        this.getTemplate = this.getTemplate.bind(this);
        this.onFileChange = this.onFileChange.bind(this);
        this.companyChange = this.infoChange.bind(this, 'company');
        this.modelChange = this.infoChange.bind(this, 'model');
        this.versionChange = this.infoChange.bind(this, 'version');
        this.openInput = this.openInput.bind(this);
        this.clearFile = this.clearFile.bind(this);
        this.resetInput = this.resetInput.bind(this);
    }

    resetInput() {
        this.fileInput.value = '';
    }

    clearFile() {
        this.fileInput.value = '';
        this.file=null;
        this.setState({
            submitDisabled: true,
            submitting: false,
            uploadFileName: '',
            errInfo: ''
        });
    }

    infoChange(key, e) {
        var it = {};
        it[key] = e.target.value;
        this.setState(it);
    }

    verify() {

        var x = !this.state.company;
        var y = this.state.company !== 0;
        if (x && y) {
            this.setState({errInfo: '请填写设备厂商'});
            return false;
        }
        x = !this.state.model;
        y = this.state.model !== 0;
        if (x && y) {
            this.setState({errInfo: '请填写设备型号'});
            return false;
        }

        if (!this.state.version && this.state.version !== 0) {
            this.setState({errInfo: '请填写固件版本'});
            return false;
        }

        return true;
    }

    openInput() {
        //校验输入
        if (!this.verify())return;

        if (this.state.errInfo)
            this.setState({errInfo: ''});

        this.fileInput.click();
    }

    submit = () => {
        let {file}=this;
        let url = "/devinfoservice/batchdevinfo";
        let config = {
            _loading: 'submitting',
            _that: this,
            _formData: {file},
            _urlParams: {
                version: this.state.version,
                company: this.state.company,
                model: this.state.model,
            },
            _msgTitle: '批量入库上传文件'
        };

        $http.postForm(url, config).then((rsp) => {
            this.setState({uploadFileName: file.name});
            if (rsp.code == 0) {
                this.clearFile();
                this.setState({errInfo: ''});
            } else {
                this.setState({errInfo: rsp.msg});
            }
        });
    }

    onFileChange(e) {
        const MAX_SIZE = 1.5;//文件最大体积(M)
        const DESC = {
            ok: '入库成功',
            errFormat: '文件格式错误!',
            errSize: `大小不能超过${MAX_SIZE}M`,
            errBlank: '请选择EXCEL文件',
        };


        let file = (e.target.files[0]);
        if (!file) {
            message.error(DESC.errBlank);
            return;
        }

        this.setState({uploadFileName: ''});
        if (!/.xls$/.test(file.name) && !/.xlsx$/.test(file.name)) {
            message.error(DESC.errFormat);
            return;
        }

        if (file.size > MAX_SIZE * 1024 * 1024) {
            message.error(DESC.errSize);
            return;
        }

        this.file = file;
        this.setState({
            submitDisabled: false,
            uploadFileName:file.name
        });
    }

    getTemplate(e) {
        let params = {token: sessionStorage.token};
        let suffix = {params: JSON.stringify(params)};
        suffix = querystring.encode(suffix);

        let url = `http://${config.fileServer}/devinfoservice/templatedownload?${suffix}`;
        window.location.href = url;
    }

    render() {

        return (
            <div className="registerDevBatch">
                <Row>
                    <Col span={6} style={{textAlign: 'right'}}>* 设备厂商：&nbsp;&nbsp;</Col>
                    <Col span={14}>
                        <Input placeholder="请输入" onChange={this.companyChange}/>
                    </Col>
                </Row>
                <Row>
                    <Col span={6} style={{textAlign: 'right'}}>* 设备型号：&nbsp;&nbsp;</Col>
                    <Col span={14}>
                        <Input placeholder="请输入" onChange={this.modelChange}/>
                    </Col>
                </Row>
                <Row>
                    <Col span={6} style={{textAlign: 'right'}}>* 固件版本：&nbsp;&nbsp;</Col>
                    <Col span={14}>
                        <Input placeholder="请输入" onChange={this.versionChange}/>
                    </Col>
                </Row>

                <Row>
                    <Col span={14} offset={6}>
                        <label style={{color: 'red'}}> {this.state.errInfo}</label>
                    </Col>
                </Row>
                <Row style={{display: 'none'}}>
                    <Col >
                        <input type="file" onChange={this.onFileChange} onClick={this.resetInput} ref={(ele) => {
                            this.fileInput = ele;
                        }}/>
                    </Col>
                </Row>

                <Row>
                    <Col span={6} style={{textAlign: 'right'}}>上传设备清单Excel&nbsp;&nbsp;</Col>
                    <Col span={14}>
                        <div className="upload-device" style={{position: 'relative'}}>
                            <div className="upload-base">
                                <span style={{color: (this.state.errInfo ? 'red' : 'green')}}>
                                    <Icon type="close" onClick={this.clearFile}/>
                                    {this.state.uploadFileName}
                                    </span>
                                <Button className="upload-btn" onClick={this.openInput}  >选择</Button>
                            </div>
                        </div>
                    </Col>
                </Row>

                <Row>
                    <Col span={14} offset={6}>
                        <a href="/devinfo.xlsx">下载导入模板</a>
                        {/*<a href="/devinfo.xlsx" onClick={this.getTemplate}>下载导入模板</a>*/}
                    </Col>
                </Row>
                <Row className="register-save">
                    <Col span={14} offset={6}>
                        <Button className="blue" onClick={this.submit}
                                disabled={this.state.submitDisabled}
                                loading={this.state.submitting}>提交</Button>
                    </Col>
                </Row>
            </div>
        );
    }
}