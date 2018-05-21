/**
 * Created by stanley on 2018/1/16.
 */

// 问卷的标题的 key
export const InvestigateTitleKey = 'title';

//1?"未开始":""||text==2?"进行中":""||text==3?"已结束":""
export const InvestStatus={
    KEY :'status',
    WAIT:1,
    OPEN:2,
    CLOSED:3,
};


// 问题的内容的 key
export const QuestTitleKey = 'questionDescription';

// 问题的类型
export const QuestType = {
    key:'QuestType',
    select:'1',
    selectM:'2',
    answer:'3'
}

// 存在缓冲中的选择题的key
export const InvestSelectKey = 'investigateSelect';
// 存在缓冲中的问答题的key
export const InvestAnswerKey = 'investigateAnswer';
// 当前正在录入的题目的编号
export const ActiveNumKey = 'activeNum';

// 选项名
export const OptionTitles = ['选项A', '选项B', '选项C', '选项D', '选项E', '选项F', '选项G', '选项H'];
// 选项key
export const OptionKeys = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'];
