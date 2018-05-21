import React from 'react'
import {Input, Form} from 'antd';
import {QuestTitleKey} from '../../qestConfig';

const FormItem = Form.Item;
const {TextArea} = Input;

export default class TabItemAnswer extends React.Component {

    constructor(p) {
        super(p);
        this.state = {}
    }

    componentWillMount() {

    }

    componentDidMount() {

    }


    render() {
        let {getFieldDecorator, questItem} = this.props;
        return (
            <div>
                <label className="remark red-dot">题目描述:</label>
                <FormItem >
                    {getFieldDecorator([QuestTitleKey], {
                        rules: [{required: true, message: '必填!', whitespace: true}],
                        initialValue: questItem ? questItem[QuestTitleKey] : ''
                    })(
                        <TextArea maxLength='60' placeholder='请输入题目描述'
                                  autosize={{minRows: 10, maxRows: 12}}/>
                    )}
                </FormItem>
            </div>

        );
    }
}
