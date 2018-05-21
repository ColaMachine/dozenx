/**
 * Copyright(C),1988-1999,aWiFi Operations Center
 * Author:    作者 Stanley Huang   Version:1.0   Date:2018.1.10
 * Description: 选择题
 * Others:无
 */
import React from 'react';
import QTitle from './itemTitle';
import {Radio,Checkbox } from 'antd';
import {ActiveNumKey, QuestType, InvestigateTitleKey, InvestAnswerKey, InvestSelectKey} from '../../qestConfig';


const RadioGroup = Radio.Group;

export  default  class ItemSelect extends React.Component {
    constructor(p) {
        super(p);

        this.state = {
            value: this.props.value || 0
        };
    }

    componentDidMount() {

    }

    onChange = (e) => {
        let {value} = e.target;
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
        let {index, data,editAble} = this.props;
        let {options=[],type}=data;
        console.log('type:',type);
       /* console.log(options);
        console.log(this.props);*/

        return (
            <div>
                <QTitle index={index}
                        editAble={editAble}
                        onEdit={this.props.onEdit}
                        onAdd={this.props.onAdd}
                        onDelete={this.props.onDelete}
                        data={data}/>

                {/*四个选项*/}
                <div className="answer-4">
                    <RadioGroup onChange={this.onChange}
                                value={this.state.value} >
                        {options.map((item,index)=>{

                            if(type==QuestType.selectM){
                                return (<Checkbox size='large' value={item.name} key={index}>
                                    <span className="mark">选项{item.name}:</span>
                                    <label >{item.describe}</label>
                                </Checkbox>);
                            }
                            {/*<Checkbox onChange={onChange}>Checkbox</Checkbox>*/}

                            return (<Radio size='large' value={item.name} key={index}>
                                        <span className="mark">选项{item.name}:</span>
                                        <label >{item.describe}</label>
                                    </Radio>);
                        })}
                    </RadioGroup>
                </div>
            </div>);
    }
}
