import React from 'react';
import ReactDOM from 'react-dom';
import 'antd/dist/antd.css'
import './css/reset.css'
import './css/index.scss'
import registerServiceWorker from './registerServiceWorker';

import router from './js/index/mainRouter'

//主index 挂载点
ReactDOM.render(
    <div id="router" data-mark="root">
        {router}
    </div>,
    document.getElementById('root'));
registerServiceWorker();
