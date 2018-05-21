/**
 * Copyright(C),1988-1999,aWiFi Operations Center
 * Author:    作者 Stanley Huang   Version:1.0   Date:2018.1.22
 * Description: 问卷调查的题目预览
 * Others:无
 */

import React from 'react';
import {Button, BackTop, Tabs} from 'antd';
import Tools from 'util/formatTool';
import QueryString from 'querystring';
// import {QuestType, InvestSelectKey, InvestAnswerKey} from '../qestConfig';
import StatSelect from './components/statistSelect'
import StatAnswer from './components/statistAnswer'
import $http from 'util/http';
import {InvestigateTitleKey,  InvestStatus, OptionTitles, OptionKeys} from '../qestConfig';

const TabPane = Tabs.TabPane;

export  default  class Statistics extends React.Component {
    constructor(p) {
        super(p);

        this.state = {
            selectResult: [],
            answerResult: []
        };

    }


    componentDidMount() {
    }

    goBack = () => {
        window.history.back();
    }

    componentWillMount() {
        let urlParams = this.urlParams = Tools.getUrlParams() || {};
        let title = this.title = urlParams[InvestigateTitleKey];
        let status = this.status = urlParams[InvestStatus.KEY];
        let id = this.id = urlParams.id;

        console.log('Statistics componentWillMount###')
        let url = '/questionnaire/statistics'+"?id="+`${this.id}`;
        let config = {
            _this: this,
            failMsg: '查询失败'
        }

        $http.get(url, config).then(({data}) => {
            if(data.code !=='0')
                return;
                console.log("data",data);
            let {data:answers}=data;
            if(!answers||!answers.length)
                return;

           let selectResult = answers.filter((item) => {
                return item.type == 1 || item.type == 2;
            })
            let answerResult = answers.filter((item) => {
                return item.type == 3;
            })

            this.setState({
                selectResult,
                answerResult,
            })
        })
    }

    // 问题类型发生改变时
    onQuestTypeChange = (key) => {

    }

    render() {
        let {selectResult,answerResult}=this.state;

        return (<div className="right-management">
            <div className="page-title-me" style={{width: '100%'}}>调查问卷&nbsp;/&nbsp;{this.title}--统计分析</div>
            <div className="investigate-stat-wrap"  >
                <Tabs defaultActiveKey={this.currQuestType } onChange={this.onQuestTypeChange} type="card">

                    <TabPane tab="选择题" key="0">
                        <StatSelect  data={selectResult}/>
                    </TabPane>

                    <TabPane tab="简答题" key="1">
                       <StatAnswer data={answerResult}/>
                    </TabPane>
                </Tabs>
            </div>
            <BackTop  />
        </div>);
    }
}





