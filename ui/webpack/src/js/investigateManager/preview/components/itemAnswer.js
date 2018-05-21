/**
 * Copyright(C),1988-1999,aWiFi Operations Center
 * Author:    作者 Stanley Huang   Version:1.0   Date:2018.1.10
 * Description: 问答题
 * Others:无
 */
import React from 'react';
import QTitle from './itemTitle';
import { Input } from 'antd';
const { TextArea } = Input;


export  default  class QuestSelectItem extends React.Component {
    constructor(p) {
        super(p);

        this.state = {
            value:this.props.value||''
        };
    }

    componentDidMount() {

    }

    onChange=(e)=>{
        let {value}=e.target;
        this.setState({
            value
        })
        this.triggerChange(value);
    }

    triggerChange = (value) => {
        const onChange = this.props.onChange;
        onChange && onChange(value);
    }
    render() {
        let {index, data,disabled,editAble} = this.props;

        return (
            <div>
                <QTitle index={index}
                        editAble={editAble}
                        onEdit={this.props.onEdit}
                        onAdd={this.props.onAdd}
                        onDelete={this.props.onDelete}
                        data={data}/>


                {/*输入文字内容*/}
                <div className="answer-text">
                    <TextArea placeholder="请输入10-300个字符"
                              value = {this.state.value}
                              onChange={this.onChange}
                              autosize={{ minRows: 4, maxRows: 4}} maxLength="300" />
                </div>
            </div>);
    }
}
