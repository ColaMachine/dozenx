/**
 * Copyright(C),1988-1999,aWiFi Operations Center
 * Author:    作者 Stanley Huang   Version:1.0   Date:2018.1.10
 * Description: 单选多选复选框
 * Others:无
 */

import React from 'react';
import {QuestType, OptionTitles} from '../../qestConfig';
import {Checkbox} from 'antd';


export  default  class OptionBar extends React.Component {
    constructor(p) {
        super(p);
        this.state = {checked: this.props.value};
    }

    /**
     * 判断父组件给了什么类型的题目，多单，
     * 需要创建多少个option
     * @param nextProp
     */
    componentWillReceiveProps(nextProp) {
        let {value} = nextProp;
        this.setState({
            // checked:value
        })
    }

    onChange = (e) => {
        let {checked} = e.target;

        //构造一个假的event对象
        let event = {
            target: {
                value: checked ? QuestType.selectM : QuestType.select
            },
            isNotEvent:true
        };
        console.log('OptionBar:', event)
        this.props.onTypeChange(event);
    }

    render() {
        let {onAdd, onDelete, selectType} = this.props;
        return (
            <div className="quest-title">
                <Checkbox
                    checked={selectType}
                    onChange={this.onChange}>多选题</Checkbox>

                <div className="line"></div>
                <a className="awifi-btn add" onClick={onAdd}>增加选项</a>
                <a className="awifi-btn" onClick={onDelete}>减少选项</a>
            </div>
        );
    }
}
