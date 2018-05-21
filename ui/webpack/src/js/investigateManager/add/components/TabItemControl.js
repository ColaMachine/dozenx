/**
 * Copyright(C),1988-1999,aWiFi Operations Center
 * Author:    作者 Stanley Huang   Version:1.0   Date:2018.1.16
 * Description: 这是个控制器，智能组件，它可以适合木偶组件TabItem变成相应的完整组件TabSelect,TabAnswer
 *   实现代码高度复用
 * Others:无
 */
import React from 'react'
import {Button, Form} from 'antd';
import $http from 'util/http';
import {animationAway} from '../../components/commAJAX';

import Tools from 'util/formatTool';
import {QuestTitleKey, QuestType, ActiveNumKey, OptionTitles, OptionKeys} from '../../qestConfig';

class TabControl extends React.Component {

    constructor(p) {
        super(p);
        this.state = {
            questDataArray: [],
            activeIndex: 0,
            submitting: false,
            dynamicItems: [ //这个成员是专用选择题设计的。
                {label: '选项C:', key: 'C'},
                {label: '选项D:', key: 'D'},
            ]
        }
    }

    //加载缓冲的据
    componentWillMount() {
        let data = Tools.getLocalStorage(this.props.cacheKey);
        console.log(this.props.cacheKey, '--CTL加载数据：', data)

        let {length} = data || 0;
        if (!data || !length)
            return;

        // 有缓冲数据
        this.state.questDataArray = data;
        let {urlParams, myQuestType} = this.props;
        if (!urlParams)
            return;

        let ActiveNum = urlParams[ActiveNumKey];


        // 取出hash中的ActiveNum，设置激活
        ActiveNum = parseInt(ActiveNum);

        if (isNaN(ActiveNum))
            ActiveNum = 0;

        if (ActiveNum > length || ActiveNum < 0)
            this.state.activeIndex = 0;
        else
            this.state.activeIndex = ActiveNum;

        //加入除A,B外的动态表单项
        this.loadDynamicItems(data[ActiveNum]);
    }


    componentDidMount() {
        console.log('TabControl mount:')
        this.props.achieveTabCtl(this);
    }

    deleteCurrent = () => {
        let {questDataArray, activeIndex} = this.state;

        //当前是一个空表单，或者未提交
        if (questDataArray.length === activeIndex) {
            //this.props.form.resetFields();
            this.goPrevious();
            return;
        }

        questDataArray.splice(activeIndex, 1);
        // 缓存数据
        this.cacheData(questDataArray);
        this.setState({
            questDataArray
        });
        this.goPrevious();
    }
    /**
     * 是否处理最后一个问题的编辑中
     * @return boolean ,是最后一个，true
     * */
    isLastOne = () => {
        let {activeIndex, questDataArray} = this.state;
        return !(questDataArray[activeIndex]);
    }
    /**
     * 题目跳转，使用事件冒泡实现点击事件
     * 跳转前会保存数据
     * @param e
     */
    labelClicked = (e) => {

        let {nodeName, innerText} = e.target;
        if ('LI' != nodeName)
            return;

        //正常更新ui,及完成问题跳转的功能
        let general = () => {
            console.log('回调 termination go ')

            // 取出序号
            let index = parseInt(innerText);
            if (isNaN(index))
                return;

            // 跳到相应的题号
            this.goIndex(index - 1, true);
        }

        // 无需检查保存数据
        if (this.isLastOne()) {
            general();
            return;
        }
        console.log(e)
        e.persist();
        this.submitCommon().then(general).catch(() => {
            console.log('回调 termination stop')
            e.preventDefault();
        });
    }

    goIndex = (index, keepData) => {
        let {activeIndex, questDataArray} = this.state;
        // 重新表单
        this.props.form.resetFields();
        if (index === activeIndex)
            return;

        !keepData && this.props.form.resetFields();

        //通知父组件，更改hash
        this.props.onIndexChange(index);

        //取出当前的问题，这个问题新建时是空的，刷新和编辑时有值
        let questItem = questDataArray[index];
        let {onTypeChange, myQuestType} = this.props;
        console.log('this.props:', this.props)
        let newType = questItem ? questItem.type : myQuestType;
        // 如果当前类型不一样，则通知父组件，改变hash,刷新子组件
        if (newType != myQuestType)
            onTypeChange && onTypeChange({target: {value: newType}});

        //加入除A,B外的动态表单项
        this.loadDynamicItems(questItem);

        this.setState({
            activeIndex: index
        });
        animationAway(index > activeIndex, this.refs.formPage)
    }
    /**
     * 加载动态数据，从c项开始都属于动态数据
     *   加入除A,B外的表单项，动态创建，这样才能展示数据，它本身与数据无关
     */
    loadDynamicItems = (questItem = {}) => {
        let dynamicItems = [];
        //从c开始
        for (var i = 2; i < OptionKeys.length; i++) {
            let key = OptionKeys[i];
            let val = questItem[key];
            if (!val)
                break;

            //加入除A,B外的选 项
            let label = OptionTitles[i];
            dynamicItems.push({label, key});

        }

        // 新建题目时，questItem为空
        if (i == 2) {
            dynamicItems.push({label: '选项C:', key: 'C'});
            dynamicItems.push({label: '选项D:', key: 'D'});
        } else if (i == 3) {
            dynamicItems.push({label: '选项D:', key: 'D'});
        }

        this.setState({dynamicItems})
    }
    goPrevious = () => {
        let {activeIndex} = this.state;
        if (activeIndex === 0) {
            return;
        }

        this.goIndex(activeIndex - 1, true);
    }

    /**
     * 去除空的值，把有数据的选项往前移
     * 把 {A:'喜欢',B:'不喜欢',C:'',D:'一般'}转为{A:喜欢,B:不喜欢,C:'一般'}
     * @return object 返回一个新的obj
     */
    removeBlankAndPack(values) {
        let ret = {};

        for (let i = 0; i < OptionKeys.length; i++) {
            let value = values[OptionKeys[i]];
            if (!value)
                continue;

            value = value.trim();

            if (!value)
                continue;

            // 当前已经有了几个选项
            let index = Object.keys(ret).length;
            ret[OptionKeys[index]] = value
        }
        ret[QuestTitleKey] = values[QuestTitleKey];
        ret.type = this.props.myQuestType;
        //debugger
        return ret;
    }

    /**
     * 选择问答题的表单提交都在这里
     * 提交后，转到下一题，并把数据缓存到localStorage
     */
    handleSubmit = (e) => {
        e.preventDefault();

        // 校验表单，存入cache,动画切换
        this.submitCommon(true).then(() => {
            let {activeIndex} = this.state;
            this.goIndex(activeIndex + 1, true);
        }).catch(() => {
        });
    }

    submitAll = () => {
        console.log('submitAll');
        let _this = this;

        let general =()=>{
            let config = {
                _this,
                okMsg: '问卷入库成功',
                failMsg: '问卷入库失败',
                loadingKey: 'submitting'
            }
            this.props.onSubmit(config);
        }

        // 无需检查保存数据
        if (this.isLastOne()) {
            general();
            return;
        }

        // 校验表单，存入cache,
        // 再回调父组件方法后端入库
         this.submitCommon().then(() => {
             general();
        });
    }

    /**
     * 完成本个form的数据校验及数据存贮
     * @param callback 完成后的回调事件；参数(err),true表示校验失败
     * @param isUpdate 是不是要更新ui
     */
    submitCommon = (isUpdate) => {
        return new Promise((resolve, reject) => {


            this.props.form.validateFieldsAndScroll((err, values) => {
                console.log('form.validateFieldsAndScroll:', values)
                if (err) {
                    // callback(err);
                    reject();
                    return;
                }

                //去除中间的空项,并增加type字段
                let val = this.removeBlankAndPack(values);
                //保存到组件状态中
                let {questDataArray, activeIndex} = this.state;
                questDataArray[activeIndex] = val;
debugger

                // 缓存数据
                this.cacheData(questDataArray);


                // 更新ui
                isUpdate && this.setState({
                    questDataArray,
                });
                // callback();
                resolve();
            });

        });
    }


    /**
     * 给每个题目增加一个id,类型，然后存到locaStorage里
     * @param data
     */
    cacheData = (data) => {
        let {cacheKey, myQuestType} = this.props;

        data = data.filter((item, index) => {
            if (myQuestType == QuestType.select || myQuestType == QuestType.selectM)
                return item[QuestTitleKey] && item.A && item.B;

            if (myQuestType == QuestType.answer)
                return item[QuestTitleKey];
        });

        data.forEach((item, index) => {
            item.id = index;
        });
        Tools.setLocalStorage(cacheKey, data);
    }

    /**
     * //这个成员是专用选择题设计的。
     */
    addFormItem = () => {
        let {dynamicItems} = this.state;
        // 数量限制
        let len = dynamicItems.length + 2
        if (len >= OptionKeys.length)
            return;

        let label = OptionTitles[len];
        let key = OptionKeys[len];
        dynamicItems.push({label, key});
        this.setState({
            dynamicItems
        });
    }

    /**
     * //这个成员是专用选择题设计的。
     */
    popFormItem = () => {
        let {dynamicItems} = this.state;

        dynamicItems.pop();
        this.setState({
            dynamicItems
        })
    }

    render() {
        const {getFieldDecorator} = this.props.form;
        let {questDataArray, activeIndex} = this.state;

        //取出当前的问题，这个问题新建时是空的，刷新和编辑时有值
        let questItem = questDataArray[activeIndex];
        let {Element, onTypeChange, myQuestType} = this.props;

        //console.log('from render')

        /**
         * 生成代表问卷题目的小方块的数组，里面放着sx对象
         */
        let getSelectLabels = () => {
            let count = questDataArray.length;
            let selectLabels = [];

            for (let i = 0; i < count + 1; i++) {
                let clsName = '';

                if (i === activeIndex) {    //是当前激活的
                    clsName = "active";
                } else if (i == count) {     //空的，但不是当前状态
                    clsName = "blank";
                } else {  //普通状态
                }
                selectLabels.push(<li className={clsName} key={i}>{i + 1}</li>)
            }
            return selectLabels;
        }

        return (
            <div className="question-wrap">

                {/*代表问卷题目的小方块 style={{cursor: 'pointer'}} onClick={this.labelClicked}*/}
                <ul className="select-labels"  >
                    {getSelectLabels()}
                </ul>

                <Form onSubmit={this.handleSubmit} layout='vertical'>

                    {/*动画容器*/}
                    <div ref="formPage" className="items-wrap">
                        {/*选择题目或者问答*/}
                        <Element getFieldDecorator={getFieldDecorator}
                                 questItem={questItem}
                                 addFormItem={this.addFormItem}
                                 popFormItem={this.popFormItem}
                                 myQuestType={myQuestType}
                                 dynamicItems={this.state.dynamicItems}
                                 onTypeChange={onTypeChange}></Element>
                    </div>

                    <div className="submit-btns">
                        <button className="awifi-btn primary" type="submit">下一题</button>
                        <a className="awifi-btn primary" disabled={this.state.submitting} onClick={this.submitAll}>录入完成提交</a>
                    </div>
                </Form>
            </div>
        );
    }

}
const user_AppForm = Form.create()(TabControl);
export default user_AppForm