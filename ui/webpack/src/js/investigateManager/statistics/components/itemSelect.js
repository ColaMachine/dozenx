/**
 * Copyright(C),1988-1999,aWiFi Operations Center
 * Author:    作者 Stanley Huang   Version:1.0   Date:2018.1.10
 * Description: 选择题
 * Others:无
 */
import React from 'react';
import ECharts from 'echarts';
import ReactDOM from 'react-dom';
import {QuestType,OptionTitles} from '../../qestConfig';


export function SelectOption({option, total,isMax}) {

    let {name, describe, number} = option;
    let percent = Math.floor(number / total* 100 + .5)  + '%';

    return (
        <div className={["option",isMax?"max":null].join(' ')}>

            <div className="left">
                <label >{name}:</label>
                <span>{describe}</span>
            </div>

            <div className="right">
                <span>{percent}</span>
                <span>{number}人</span>
                <span>共{total}人</span>
            </div>

        </div>);
}


export class StatChart extends React.Component {
    constructor(p) {
        super(p);
    }

    componentDidMount() {
        let {options, total} = this.props.data;

        let dataX = [], dataY = [], max = 10;
        options && options.forEach((opt, index) => {
            dataX.push(OptionTitles[index]);
            let number = parseInt(opt.number || 0);
            max = number > max ? number : max;
            number=Math.floor(number/total*100+0.5);
            dataY.push(number);
        })

        let chartsNode = ReactDOM.findDOMNode(this.refs.chartsNode);
        // 基于准备好的dom，初始化echarts实例
        var myChart = ECharts.init(chartsNode);
        // 绘制图表
        myChart.setOption({
            color: ['#3398DB'],
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            xAxis: {
                data: dataX
            },
            yAxis: [{
                type: 'value',
                name: '百分率',
                min: 0,
                max: 100,
                position: 'right',
                axisLabel: {
                    formatter: '{value} %'
                }
            }],
            series: [{
                name: '百分率%',
                type: 'bar',
                barWidth: '30%',
                data: dataY,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                    ]
                },
            }]
        });
    }


    render() {
        return (
            <div className="chart" ref='chartsNode'  >
            </div>);
    }
}