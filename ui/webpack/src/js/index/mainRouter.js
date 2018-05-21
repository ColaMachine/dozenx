/**
 * Created by songbo on 2017/8/22.
 */
import React from 'react'
import {Route, Redirect} from 'react-router'
import {
    HashRouter, Switch
} from 'react-router-dom'
import Login from '../login/login';
import Index from './index'

var router = (
    <HashRouter>
        <div className="mainRouter">
            <Route path="/" exact={true} component={() => <Redirect to="/login"/>}/>
            <Route path="/login" component={Login}/>
            <Route path="/index" component={Index}/>
            {/*<Redirect from="/" to="/login"></Redirect>*/}
        </div>
    </HashRouter>)
export default router

