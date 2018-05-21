/**
 * Created by Stanley Huang 2017.12.21
 */
import React from 'react'
import {Route } from 'react-router-dom'
 import List from './list'
import Preview from './preview'
import Add from './add'
import Statistics from './statistics'
import Create from './create'
import {
    HashRouter
} from 'react-router-dom'
import {withRouter} from 'react-router-dom';

 class InvestigateManager extends React.Component {
    constructor() {
        super();
    }

     componentDidMount() {
         console.log('InvestigateManager mount:',this.props )
     }

    routerWillLeave=(nextLocation)=> {
        console.log('routerWillLeave:', nextLocation);
        alert(1);

    }
    render() {
        return (
            <HashRouter  routerWillLeave={this.routerWillLeave}>
            <div >
                <Route path="/index/investigateManager/list" component={List}  routerWillLeave={this.routerWillLeave}/>
                <Route path="/index/investigateManager/preview" component={Preview}/>
                <Route path="/index/investigateManager/add" component={Add}/>
                <Route path="/index/investigateManager/statistics" component={Statistics}/>
                {/* <Route path="/index/investigateManager/list" component={MainPage}/> */}
                <Route path="/index/investigateManager/create" component={Create}/>
            </div>
            </HashRouter>
        );
    }
}

export default withRouter(InvestigateManager);