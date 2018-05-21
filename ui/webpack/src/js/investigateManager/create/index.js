
import React from 'react'
import {Button, Table, Modal, Pagination, notification,Form,Input} from 'antd';
import {Link} from 'react-router-dom'
import util from '../../../util/formatTool'
import  Axios from 'axios';
import  QueryString from 'querystring';
import $http from '../../../util/http';
import { setTimeout } from 'timers';
let data=[]
const FormItem = Form.Item;
class VersionList extends React.Component {
    constructor() {
        super();
        this.state = {
           err:true
        }
        this.onEdit=this.onEdit.bind(this);

    }

    componentWillMount() {
        this.HeaderToken = {headers: {token: sessionStorage.token}};
    }

    componentDidMount() {
       
    }
    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFieldsAndScroll((err, values) => {
        	
            if (!err) {
               let title=values.remark;
                setTimeout(()=>{
                    this.onEdit(title);
                },300)
            }
        });
    }
    onEdit=(title)=>{
         let url="/index/investigateManager/add?"+"title="+`${title}`
         window.location.hash=url;
    }
    render() {
        const {getFieldDecorator} = this.props.form;
        return (
            <Form onSubmit={this.handleSubmit} layout='vertical' style={{'maxWidth': '750px'}}>
            <div className="right-management">
                <div className="page-title-me" style={{width: '100%'}}>创建问卷</div>
                <span>问卷名称:</span>
                <FormItem >
                    {getFieldDecorator('remark', {
                        rules: [{required: true, message: '必填!'}],
                    })(
                        <Input placeholder='请输入问卷名称' style={{width:"1208px"}}/>
                    )}
                </FormItem>
                <div className="form">
                    <Button type="primary" className="storageButton" htmlType="submit" 
                    >录入题目</Button>
                </div>
                
            </div>
            </Form>
        );
    }
}
const user_AppForm = Form.create()(VersionList);
export default user_AppForm
