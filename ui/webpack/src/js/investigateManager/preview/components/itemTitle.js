/**
 * Copyright(C),1988-1999,aWiFi Operations Center
 * Author:    作者 Stanley Huang   Version:1.0   Date:2018.1.10
 * Description: 每个问答题，选择题的标题
 * Others:无
 */

import React from 'react';
import {QuestType, ActiveNumKey, QuestTitleKey} from '../../qestConfig';
import QueryString from 'querystring';

export  default  function ItemTitle({index, editAble, data, onAdd, onDelete, onEdit}) {
    if (!editAble) {
        return (
            <div className="quest-title" id={index}>
                <span className="dot">{parseInt(index) + 1}</span>
                <label>{data[QuestTitleKey]}</label>
            </div>
        );
    }

    return (
        <div className="quest-title" id={index}>
            <span className="dot">{parseInt(index) + 1}</span>
            <label>{data[QuestTitleKey]}</label>
            <div className="line"></div>
            <a className="awifi-btn ml-35" onClick={onEdit.bind('', data)}>修改</a>
            <a className="awifi-btn ml-35" onClick={onDelete.bind('', data)}>删除</a>
            <a className="awifi-btn ml-35" onClick={onAdd.bind('', data)}>增加</a>
        </div>
    );
}
