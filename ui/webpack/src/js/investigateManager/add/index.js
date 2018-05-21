/**
 * 在线问卷调查 管理--新增加
 */

import React from 'react';
import {Input} from 'antd';
import $http from 'util/http';
import TabSelect from './components/TabSelect';
import TabAnswer from './components/TabAnswer';
import Tools from 'util/formatTool';
import QueryString from 'querystring';
import {ActiveNumKey, QuestType, InvestigateTitleKey, InvestAnswerKey, InvestSelectKey} from '../qestConfig';
import {submitInvestigation} from '../components/commAJAX';

import {Radio} from 'antd';
const RadioButton = Radio.Button;
const RadioGroup = Radio.Group;


export  default class InvestigateAdd extends React.Component {
    constructor() {
        super()
        this.state = {
            title: '-',
            editing: false,
            submitting: false,
            type: QuestType.select//问题的类型
        };

        this.actIndexSelect = 0;//当前激活的问题序号
        this.actIndexAnswer = 0;
    }

    componentWillMount() {

        let params = Tools.getUrlParams();
        if (!params)
            return

        this.urlParams = params;
        //当前激活的问题类型
        let type = this.state.type = params[QuestType.key] || QuestType.select;

        if (type === QuestType.answer) {
            this.actIndexAnswer = params[ActiveNumKey] || 0;
        } else {
            this.actIndexSelect = params[ActiveNumKey] || 0;//当前激活的问题序号
        }

        //问卷的标题
        this.state.title = params[InvestigateTitleKey] || '未知问卷';
    }

    goList = () => {
        // debugger
        window.location.hash = '#/index/investigateManager/list';
    }

    // 问题序号发生改变时
    onIndexChange = (key, index) => {
        this[key] = index;
        this.makeHashArgs();
    }

    // 问题类型发生改变时,Tabs和ItemSelect都会调用
    // 通过控制type来，调整单选多选，optionBar的状态在这时同步
    onQuestTypeChange = (e) => {
        //console.log(e)
        let {value: type} = e.target;
        console.log(e);

        //正常更新ui
        //改变url hash
        let general = () => {
            this.makeHashArgs();
            this.setState({
                type
            });
        }
        //如果只是单选多选切换，则只更新type
        if (e.isNotEvent) {
            general();
            return;
        }

        //在选择题和问答之间切换
        // 保存表单数据，失败则阻止事件
        let ctl = this.tabController;

        // 无需检查保存数据
        if (ctl.isLastOne()) {
            general();
            return;
        }
        //e.persist();
        ctl.submitCommon().then(general).catch(() => {
            console.log('回调 termination stop')
           // e.preventDefault();
        });
    }

    /**
     * 给hash赋值，使页面url具有唯一性，
     * /add? activeNum=1 & QuestType=1
     */
    makeHashArgs = () => {
        let {hash} = window.location;
        if (!hash)
            return;
        hash = hash.split('?');
        hash = hash[0];

        let {actIndexSelect, actIndexAnswer} = this;
        let {type} = this.state;
        let actIndex = type === QuestType.answer ? actIndexAnswer : actIndexSelect;

        let args = QueryString.stringify({
            [ActiveNumKey]: actIndex,
            [QuestType.key]: type,
            [InvestigateTitleKey]: this.state.title
        });
        window.location.hash = hash + '?' + args;
    }

    editTitle = () => {
        this.setState({
            editing: true
        })
    }
    editFinish = () => {
        this.setState({
            editing: false
        })
    }

    titleChange = (e) => {
        let {value} = e.target;
        let title = value.trim();
        this.setState({
            title
        })
    }

    onSubmit = (config) => {
        console.log(' ADD　＝＝submitAll');

        // 有id则为编辑
        let promise = submitInvestigation(config, this.state.title, this.urlParams.id)
        promise.then(() => {
            console.log('提交成功');

            // 清空缓存
            Tools.removeLocalStorage(InvestSelectKey);
            Tools.removeLocalStorage(InvestAnswerKey);

            //返回列表
            this.goList();
        })
    }

    componentDidMount() {
        console.log('InvestigateAdd mount:',this.props)
        // debugger;

    }

    achieveTabCtl = (obj) => {
        this.tabController = obj;
        console.log('achieveTabCtl:', obj)

    }

    render() {
        let {type, title, editing} = this.state;
        console.log('ADD:', this.state)
        return (
            <div className="right-management">
                <div className="investigate-title">
                    {editing ?
                        <Input ref="titleInput" value={title} onChange={this.titleChange} onBlur={this.editFinish}
                               onPressEnter={this.editFinish}/> :
                        <h1 onClick={this.editTitle}>{title}</h1>}
                </div>


                <div className="quest-tabs-wrap">
                    <TabsRadio activeKey={type } onChange={this.onQuestTypeChange}/>
                    { type == QuestType.select || type == QuestType.selectM ? <TabSelect urlParams={this.urlParams}
                                                                                         achieveTabCtl={this.achieveTabCtl}
                                                                                         myQuestType={type}
                                                                                         onSubmit={this.onSubmit}
                                                                                         onTypeChange={this.onQuestTypeChange}
                                                                                         onIndexChange={this.onIndexChange.bind(this, 'actIndexSelect')}/> :
                        < TabAnswer urlParams={this.urlParams}
                                    achieveTabCtl={this.achieveTabCtl}
                                    myQuestType={QuestType.answer}
                                    onSubmit={this.onSubmit}
                                    onIndexChange={this.onIndexChange.bind(this, 'actIndexAnswer')}/>}
                </div>

            </div>
        );
    }
}


function TabsRadio({activeKey, onChange}) {

    // 选择题包括单选和多选
    if (activeKey == QuestType.selectM)
        activeKey = QuestType.select;

    return (
        <RadioGroup onChange={onChange} value={activeKey}>
            <RadioButton value={QuestType.select}>选择题</RadioButton>
            <RadioButton value={QuestType.answer}>简答题</RadioButton>
        </RadioGroup>
    );
}