import React from 'react'
import TabItemControl from './TabItemControl';
import TabItemSelect from './TabItemSelect';
import {InvestSelectKey} from '../../qestConfig';


export default class TabSelect extends React.Component {

    constructor(p) {
        super(p);
        this.state = {}
    }

    render() {
        let {props} = this;
        return ( <TabItemControl Element={TabItemSelect} cacheKey={InvestSelectKey}
                                 wrappedComponentRef={(inst) => this.formRef = inst}
                                 achieveTabCtl={this.props.achieveTabCtl}
                                 urlParams={props.urlParams}
                                 myQuestType={props.myQuestType}
                                 onTypeChange={props.onTypeChange}
                                 onSubmit={props.onSubmit}
                                 onIndexChange={props.onIndexChange}/>  );
    }
}
