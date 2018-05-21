/**
 * Created by Stanley Huang 2017.12.21
 */
import React from 'react'
import {Route } from 'react-router-dom'
import List from './list'
import Add from './add'


export default class VersionManager extends React.Component {
    constructor() {
        super();
    }

    render() {
        return (
            <div >
                <Route path="/index/versionManager/list" component={List}/>
                <Route path="/index/versionManager/add" component={Add}/>
            </div>
        );
    }
}

