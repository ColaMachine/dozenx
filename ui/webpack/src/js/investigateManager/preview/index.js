/**
 * Copyright(C),1988-1999,aWiFi Operations Center
 * Author:    作者 Stanley Huang   Version:1.0   Date:2018.1.10
 * Description: 问卷调查的题目预览
 * Others:无
 */

import React from 'react';

import QItemSelect from './components/itemSelect'
import QItemAnswer from './components/itemAnswer'
import {Form, Button, BackTop} from 'antd';
import Tools from 'util/formatTool';
import QueryString from 'querystring';
import {InvestigateTitleKey, QuestType, InvestSelectKey, InvestAnswerKey, ActiveNumKey} from '../qestConfig';
import $http from '../../../util/httpInvestigate';
import {submitInvestigation} from '../components/commAJAX';

import {InvestStatus} from '../qestConfig';

let {WAIT, OPEN, CLOSED} = InvestStatus;

const IS_ADD = 'isAdd';
export default  class InvestigatePreview extends React.Component {
    constructor(p) {
        super(p);
        this.state = {
            questSelects: [],
            questAnswers: [],
            loadingBegin: false,
            loadingFinish: false,
        };
        // 问卷状态
        this.status = '';
    }

    componentDidMount() {
    }

    goBack = () => {
        window.history.back();
    }

    /**
     * 获取问卷详情，
     * 判断是否可编辑
     *
     */
    componentWillMount() {
        let urlParams = this.urlParams = Tools.getUrlParams() || {};
        let title = this.title = urlParams[InvestigateTitleKey];
        let status = this.status = urlParams[InvestStatus.KEY];
        let id = this.id = urlParams.id;
// debugger
        // 三个参数必须要有
        if (!id || !title || !status) {
            this.goBack();
            return;
        }

        //2.根据问卷id查询
        let url = `/questionnaire/${id}`;

        let config = {
            _this: this,
        }
        $http.get(url, config, ({data}) => {
            console.log('get ok:', data);
            let {questions = []} = data;
            let g_index = 0;
            let questSelects = questions.filter((item, index) => {
                if (item.type == QuestType.selectM || item.type == QuestType.select) {
                    item.index = g_index++;
                    return true;
                }
            })

            g_index = 0;
            let questAnswers = questions.filter((item, index) => {
                if (item.type == QuestType.answer) {
                    item.index = g_index++;

                    return true;
                }
            })
            this.setState({questAnswers, questSelects})
        }, () => {
            console.log('get fail');
        });
    }

    cacheToLocal = () => {
        let {questSelects, questAnswers} = this.state;
        //改变数据结构
        let newSelects = questSelects.map((item) => {
            let {type, questionDescription, options} = item;

            let opt = {}
            //后返回的数据乱搞
            if (!options) {
                console.log('没有option')
                // debugger
                options = [];
            }

            options.forEach((it) => {
                opt[it.name] = it.describe;
            })
            return Object.assign({type, questionDescription}, opt);

        })

        Tools.setLocalStorage(InvestSelectKey, newSelects);
        Tools.setLocalStorage(InvestAnswerKey, questAnswers);
    }

    /**
     * 修改一个
     * @param item 跳转参数
     */
    onEdit = (item) => {
        console.log('onEdit:', item);
        let {index} = item;


        //debugger
        this.cacheToLocal();
        let args = QueryString.stringify({
            id: this.id,
            [ActiveNumKey]: index,
            [QuestType.key]: item.type,
            [InvestigateTitleKey]: this.title
        });
        //debugger
        let url = "#/index/investigateManager/add?" + args;
        window.location.hash = url;
    }

    /**
     * 增加一个
     * @param item 跳转参数
     */
    onAdd = (item) => {
        console.log('onAdd:', item);

        let {type, index} = item;
        index++;
        let newItem = {index, type, options: []};
        if (QuestType.select == type || QuestType.selectM == type) {
            let {questSelects} = this.state;
            // 加入一个空对象
            questSelects.splice(index, 0, newItem);
            Tools.setLocalStorage(InvestSelectKey, questSelects);
            this.onEdit(newItem);
            return;
        }


        let {questAnswers} = this.state;
        // 加入一个空对象
        questAnswers = questAnswers.splice(index, 0, newItem);
        Tools.setLocalStorage(InvestAnswerKey, questAnswers);
        this.onEdit(newItem);
    }

    /**
     * 删除一个,然后提交
     * @param item 跳转参数
     */
    onDelete = (item) => {
        console.log('onDelete:', item);
        let {type, index} = item;
        debugger
        if (QuestType.select == type || QuestType.selectM == type) {
            let {questSelects} = this.state;

            // 删除一个对象
            questSelects.splice(index, 1);
            questSelects.forEach((item, index) => {
                item.index = index;
            })
            // Tools.setLocalStorage(InvestSelectKey, questSelects);
            this.setState({
                questSelects
            })

        } else {

            let {questAnswers} = this.state;
            // 删除一个对象
            questAnswers.splice(index, 1);
            questAnswers.forEach((item, index) => {
                item.index = index;
            })
            // Tools.setLocalStorage(InvestAnswerKey, questAnswers);
            this.setState({
                questAnswers
            })
        }


        this.cacheToLocal();
        //提交后端服务
        let config = {
            _this: this,
        }
// debugger
        // 有id则为编辑
        let promise = submitInvestigation(config, this.title, this.id)
        promise.then(() => {
            console.log('提交成功');
        })
    }

    goList = () => {
        window.location.hash = '#/index/investigateManager/list';
    }

    submitStatus = (status, loadingKey) => {
        let {id, title} = this;
        let url = `/questionnaire/${id}`;
        let data = {id, title, status};
        let config = {
            _this: this,
            loadingKey
        }
        $http.put(url, data, config, () => {
            console.log('submit ok');
            //返回列表
            this.goList();
        }, () => {
            console.log('submit fail');
        });
    }

    submitFinish = () => {

        if (this.status != OPEN || this.state.loadingFinish)return;
        console.log('submitFinish:');
        this.submitStatus(InvestStatus.CLOSED, 'loadingFinish')
    }

    submitBegin = () => {
        if (this.status == OPEN || this.state.loadingBegin)return;
        console.log('submitBegin:');
        this.submitStatus(InvestStatus.OPEN, 'loadingBegin')
    }

    render() {
        let {questAnswers, questSelects, loadingBegin, loadingFinish} = this.state;
        let selectTotal = questSelects.length;

        //是不是显示编辑按钮
        let editAble = this.status == InvestStatus.WAIT;

        // 结束问卷 及 开启问卷 的样式
        let classBegin = ["awifi-btn primary float-right", this.status == OPEN ? "disabled" : '', loadingBegin ? "loading" : ''].join(" ");
        let classFin = ["awifi-btn primary float-right ml-35", this.status != OPEN ? "disabled" : "", loadingFinish ? "loading" : ''].join(" ");

        return (<div className="right-management">
            <div className="investigate-window-wrap" style={{width: 1024}}>
                <div className="content-wrap">
                    <div className="investigate-title flex-between">
                        <h1  >{this.title}</h1>
                        <div>

                            <button
                                onClick={this.submitFinish}
                                className={classFin}>
                                结束问卷
                            </button>
                            <button
                                onClick={this.submitBegin}
                                className={classBegin}>
                                开启问卷
                            </button>
                        </div>
                    </div>

                    {/*选择题目*/}
                    {questSelects.map((quest, index) => {
                        return <QItemSelect data={quest}
                                            editAble={editAble}
                                            index={index}
                                            onEdit={this.onEdit}
                                            onAdd={this.onAdd}
                                            onDelete={this.onDelete}
                                            key={index}/>;
                    })}

                    {/*问答题目*/}
                    {questAnswers.map((quest, index) => {
                        return <QItemAnswer data={quest}
                                            editAble={editAble}
                                            index={index + selectTotal}
                                            onEdit={this.onEdit}
                                            onAdd={this.onAdd}
                                            onDelete={this.onDelete}
                                            key={index}/>;
                    })}

                </div>
            </div>

            <BackTop  />
        </div>);
    }
}





