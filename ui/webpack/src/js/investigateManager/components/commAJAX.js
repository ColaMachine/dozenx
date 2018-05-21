/**
 * Created by stanley on 2018/1/16.
 */

import ReactDOM from 'react-dom';
import Tools from 'util/formatTool';
import {QuestTitleKey, InvestStatus, InvestSelectKey, InvestAnswerKey, OptionKeys} from '../qestConfig';
import $http from 'util/httpInvestigate';

/**
 * 提交问卷
 * 从localStorage中提取出已经完成的题目，调用后端接口存入数据库。
 * 如果成功则清空localStorage
 */
export function submitInvestigation(config, title, editId) {
    //1.取出数据

    let questions = [];
    let data = {source: "shixun", title};


    let sData = Tools.getLocalStorage(InvestSelectKey);
    let aData = Tools.getLocalStorage(InvestAnswerKey);

    // 拼装选择题数据结构
    sData && sData.forEach((item, index) => {
        let {type} = item;
        let questionDescription = item[QuestTitleKey];
        let options = [];

        for (let i = 0; i < OptionKeys.length; i++) {
            let name = OptionKeys[i];
            let describe = item[name];
            if (!describe)
                break;
            options.push({name, describe})
        }

        questions.push({questionNumber: index, type, questionDescription, options})
    })

    // 拼装问答题数据结构
    let base = sData.length;
    aData && aData.forEach((item, index) => {
        let {type} = item;
        let questionDescription = item[QuestTitleKey];
        let options = [];
        questions.push({questionNumber: index + base, type, questionDescription, options})
    })

    data.questions = questions;
    //console.log('submitInvestigation:', data);

    return new Promise((resolve, reject) => {
        let url = '/questionnaire';

        if (editId) {
            data.id = editId;
            data[InvestStatus.KEY]=InvestStatus.WAIT;
            $http.put(url, data, config, (data) => {
                resolve(data);
            });
        } else {
            $http.post(url, data, config, (data) => {
                resolve(data);
            });
        }

    })
};


/**
 * transform移动画
 * isNext true:下一个;false：上一个
 */
export function animationAway(isNext, ref) {
    let formPage = ReactDOM.findDOMNode(ref);
    let {style} = formPage;
    let curPosition = 0;
    let callBack = () => {
        let dir = isNext ? -1 : 1;
        switch (curPosition) {
            case 0:
                curPosition = 1;
                style.transition = "500ms";
                style.transform = `translateX(${dir}000px)`;
                break;
            case 1:
                curPosition = -1;
                style.transition = "none";
                style.transform = `translateX(${-1 * dir}000px)`;
                setTimeout(() => {
                    callBack();
                }, 10)
                break;
            case -1:
                formPage.removeEventListener('webkitTransitionEnd', callBack);
                curPosition = 9;
                style.transition = "500ms";
                style.transform = 'translateX(0)';
            default:
                break;
        }
    };
    formPage.addEventListener('webkitTransitionEnd', callBack, false);
    callBack();
};

