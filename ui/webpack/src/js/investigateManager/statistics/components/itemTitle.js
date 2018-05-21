/**
 * Copyright(C),1988-1999,aWiFi Operations Center
 * Author:    作者 Stanley Huang   Version:1.0   Date:2018.1.10
 * Description: 每个问答题，选择题的标题
 * Others:无
 */

import React from 'react';
import {QuestType, ActiveNumKey} from '../../qestConfig';
import QueryString from 'querystring';

export  default  function ItemTitle({index, remark}) {
    return (
        <div className="quest-title"  >
            <span className="dot">{parseInt(index)}</span>
            <label>{remark}</label>

        </div>
    );
}
