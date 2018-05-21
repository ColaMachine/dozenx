/**
 * Copyright(C),1988-1999,aWiFi Operations Center
 * Author:    作者 Stanley Huang   Version:1.0   Date:2018.1.10
 * Description: 选择题
 * Others:无
 */
import React from 'react';
import {SelectOption, StatChart} from './itemSelect';
import QTitle from './itemTitle';

/**
 * 选择题统计Tab内容
 */
export  default  class StatSelect extends React.Component {
    constructor(p) {
        super(p);

        this.state = {
            answers: []
        };
    }

    componentWillMount() {
        console.log('StatSelect componentWillMount:', this.props)
        let {data} = this.props;
        data && data.length && this.setData(data);
    }


    setData(answers) {
        this.setState({
            answers
        })
    }

    componentWillReceiveProps(np) {
        console.log('StatSelect componentWillReceiveProps:', np);
        let {data} = np;
        data && data.length && this.setData(data);
    }


    render() {
        let {answers} = this.state;
        return (
            <div >
                {answers.map((item, index) => {
                    return (
                        <ItemSelect key={index} data={item}/>
                    );
                })}
            </div>);
    }
}

/**
 * 每个选择题统计项目
 */
class ItemSelect extends React.Component {
    constructor(p) {
        super(p);

        this.state = {
            data: this.props.data || {}
        };
    }

    componentWillReceiveProps(np) {
        let {data} = np;
        if (data) {
            this.setState({
                data
            })
        }
    }

    render() {
        let {data} = this.state;
        let {options = [], total, questionNumber, questionDescription} = data;
        let max = 0;
        options.forEach((opt, index) => {
            max = opt.number > max ? opt.number : max
        });
        return ( <div className="stat-select">
            <div className="stat-select-detail">

                {/*选项*/}
                <span className="options">
                <QTitle index={questionNumber+1} remark={questionDescription}/>
                    {options.map((option, index) => {
                        return <SelectOption key={index} option={option} total={total} isMax={option.number == max}/>
                    })}
                </span>

                {/*图表*/}
                <span className="e-chart">
                    <StatChart data={data} v/>
                </span>
            </div>
        </div>);
    }
}
