

import React from 'react'
import TabItemControl from './TabItemControl';
import TabItemAnswer from './TabItemAnswer';
import { InvestAnswerKey} from '../../qestConfig';


export default class TabAnswer extends React.Component {

    constructor(p) {
        super(p);
        this.state = {

        }
    }
    componentDidMount() {
        console.log('TabAnswer mount:' )
    }

    render() {
        return (
            <TabItemControl Element={TabItemAnswer}
                            cacheKey={InvestAnswerKey}  {...this.props}/>
        );
    }
}
