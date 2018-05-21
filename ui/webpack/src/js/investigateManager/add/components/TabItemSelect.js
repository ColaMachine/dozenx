import ReactDOM from 'react-dom';
import React from 'react'
import {Input, Button, message, Icon, Form} from 'antd';
import $http from 'util/http';
import {Radio} from 'antd';
import  OptionBar from './optionBar'
import {Tabs} from 'antd';
import {QuestTitleKey, QuestType, OptionTitles, OptionKeys} from '../../qestConfig';

const FormItem = Form.Item;
const {TextArea} = Input;


export default class TabItemSelect extends React.Component {

    constructor(p) {
        super(p);

        this.state = {
            selectType: this.getType(this.props),

        };

    }

    getType(props) {
        let {questItem} = props;
        return questItem ? questItem.type : QuestType.select;
    }

    componentWillMount() {

    }

    /**
     * 动态增加一个选项
     */
    addFormItem = () => {
        this.props.addFormItem();
        return;
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
     * 判断父组件给了什么类型的题目，多单，
     * 需要创建多少个option
     * @param nextProp
     */
    componentWillReceiveProps(nextProp) {
        return;
        let {questItem} = nextProp;
        if (!questItem)
            return;
        let selectType = this.getType(nextProp);

        // if(questItem)
        console.log('nextProp -> questItem:', questItem);
        let dynamicItems = [
            {label: '选项C:', key: 'C'},
            {label: '选项D:', key: 'D'},
        ];

        //判断，是否有EFGH
        for (let i = 5; i < OptionKeys.length; i++) {
            let key = OptionKeys[i];
            if (questItem[key]) {
                let label = OptionTitles[i];
                dynamicItems.push({
                    label,
                    key
                });
            } else {
                break;
            }
        }
        this.setState({
            dynamicItems,selectType
        })
    }

    /**
     * 动态减少一个选项
     */
    popFormItem = () => {
        this.props.popFormItem();
        return;
        let {dynamicItems} = this.state;

        dynamicItems.pop();
        this.setState({
            dynamicItems
        })
    }


    render() {

        let {getFieldDecorator, questItem, onTypeChange,dynamicItems,myQuestType} = this.props;
        // let {selectType} = this.state;


        return (
            <div>
                {/*切换多选、单选；增加，删除选项按钮*/}
                <OptionBar onTypeChange={onTypeChange}
                           onAdd={this.addFormItem}
                           onDelete={this.popFormItem}
                           selectType={myQuestType == QuestType.selectM}/>

                <label className="remark red-dot">题目描述:</label>

                <div className="item">
                    <FormItem >
                        {getFieldDecorator([QuestTitleKey], {
                            rules: [{required: true, message: '必填!', whitespace: true}],
                            initialValue: questItem ? questItem[QuestTitleKey] : ''
                        })(
                            <TextArea maxLength='60' placeholder='请输入题目描述'/>
                        )}
                    </FormItem>
                    <span className="red-dot">选项A:</span>
                    <FormItem >
                        {getFieldDecorator('A', {
                            rules: [{required: true, message: '必填!', whitespace: true}],
                            initialValue: questItem ? questItem.A : ''
                        })(
                            <Input placeholder='请输入'/>
                        )}
                    </FormItem>
                    <span className="red-dot">选项B:</span>
                    <FormItem >
                        {getFieldDecorator('B', {
                            rules: [{required: true, message: '必填!', whitespace: true}],
                            initialValue: questItem ? questItem.B : ''
                        })(
                            <Input placeholder='请输入'/>
                        )}
                    </FormItem>

                    {/*动态添加的表单项*/}
                    {dynamicItems.map((option, index) => {
                        let {label, key} = option;
                        return (<div key={index}>
                            <span>{label}</span>
                            <FormItem >
                                {getFieldDecorator(`${key}`, {initialValue: questItem ? (questItem[key] || '') : ''})(
                                    <Input placeholder='请输入'/>
                                )}
                            </FormItem>
                        </div> );
                    })}
                </div>
            </div>

        );
    }
}
