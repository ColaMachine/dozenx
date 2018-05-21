/**
 * Created by songbo on 2017/8/21.
 */
import {Form, Icon, Input, Button} from 'antd';
import axios from 'axios'
import 'whatwg-fetch'
import React from 'react'
import notice from '../../util/notice'
import {Row, Col} from 'antd';
import $http from '../../util/http'
const FormItem = Form.Item;

class Login extends React.Component {
    constructor() {
        super()
        this.state = {
            isFirstLogin: true,
            picCode: 'n/a'
        }
        this.token = '';
    }

    componentDidMount() {
        //this.getAuthCode();
    }

    //获取验证码
    getAuthCode() {
        let url = '/userservice/photocode';

        axios({
            url: url,
            method: 'get',
        }).then((response) => {
            this.setState({
                picCode: response.data.code,
                isFirstLogin: false
            });
            this.token = response.data.token;

        }).catch(function (error) {
            notice("error", {title: '获取验证码失败', description: '网络故障: ' + error.toString()});
            this.setState({
                picCode: 'n/a',
                isFirstLogin: false
            });
            this.token = '';
        });

    }

    verifyAuthCode(code) {
        return new Promise((resolve, reject) => {
            if (this.state.isFirstLogin) {
                resolve();
                return;
            }

            //验证码校验
            let data = {
                code,
                phototoken: this.token //为空时会被过滤掉
            };

            let url = '/userservice/validphotocode';
            axios.post(url, JSON.stringify(data), {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                }
            }).then((response) => {

                let data = response.data;
                if (!(data.code == "0")) {
                    notice("error", {title: '登录失败', description: data.msg});
                    reject();
                } else {
                    resolve();
                }
            }).catch(function (err) {
                notice("error", {title: "登录失败", description: '网络故障: ' + err.toString()});
                reject();
            });


        });
    }

    doLogin(params) {
        axios.post("/userservice/login", JSON.stringify(params), {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            }
        }).then(({data}) => {
            //console.log('doLogin data:', data)
            if (data.code == '0') {
                $http.authorSuccess(data.msg.token, data.msg.phone);

                //成功后跳转
                window.location.hash = "#/index/home"
            } else {
                notice("error", {title: "登录失败", description: data.msg + data.code})
                setTimeout(() => {
                    this.getAuthCode();
                }, this.state.isFirstLogin ? 0 : 1000);
            }
        }).catch((err) => {
            notice("error", {title: "登录失败", description: '网络故障: ' + err.toString()})
        });
    }

    handleSubmit(e) {
        e.preventDefault();
        //获取表单值
        //var a= this.props.form.getFieldsValue()
        this.props.form.validateFields((err, values) => {
            console.log('values:', values)
            if (err) {
                console.log('表单验证失败')
                return;
            }

            $http.authorQuite();
            this.verifyAuthCode(values.authCode).then(() => {
                this.doLogin(values);
            }).catch(() => {
                setTimeout(() => {
                    this.getAuthCode();
                }, 1000);
                return;
            });

        });
    }

    render() {
        const {getFieldDecorator} = this.props.form;
        return (
            <div className="loginWrap">
                <div className="title">i+直播运营管理平台</div>
                <div className="login">
                    <Form onSubmit={this.handleSubmit.bind(this)} className="login-form">
                        <FormItem className={"formitem"}>
                            {getFieldDecorator('phone', {
                                rules: [{required: true, message: '请输入账号'}],
                            })(
                                <Input maxLength="20" suffix={<Icon type="user" style={{fontSize: 13}}/>}
                                       placeholder="请输入账号"/>
                            )}
                        </FormItem>
                        <FormItem className={"formitem"}>
                            {getFieldDecorator('password', {
                                rules: [{required: true, message: '请输入密码'}],
                            })(
                                <Input suffix={<Icon type="lock" style={{fontSize: 13}} maxLength="20"/>}
                                       type="password"
                                       placeholder="请输入密码"/>
                            )}
                        </FormItem>
                        {/*第一次登录不用验证码*/}
                        {this.state.isFirstLogin ? "" :
                            <FormItem className={"formitem"}>
                                <Row>
                                    <Col span={16}> {getFieldDecorator('authCode', {
                                        rules: [{required: true, message: '请输入验证码'}],
                                    })(
                                        <Input type="text" placeholder="请输入验证码"></Input>
                                    )}</Col>
                                    <Col span={8}>
                                        <div className="verification_code" onClick={this.getAuthCode.bind(this)}>
                                            {this.state.picCode}
                                        </div>
                                    </Col>
                                </Row>
                            </FormItem>}
                        <FormItem>
                            <Button type="primary" htmlType="submit" style={{width: "100%"}}>
                                登录
                            </Button>
                        </FormItem>
                        <FormItem>
                            <a href="" style={{float: 'right',marginRight:10,color:'#d1e7f5'}}>忘记密码</a>
                        </FormItem>
                    </Form>
                </div>
            </div>
        );
    }
}

const Warplogin = Form.create()(Login);
export default Warplogin