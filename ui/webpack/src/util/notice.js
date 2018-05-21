/**
 * Created by songbo on 2017/8/10.
 */
/*openNotificationWithIcon('success')
openNotificationWithIcon('info')
openNotificationWithIcon('warning')
openNotificationWithIcon('error')*/
import { Button, notification } from 'antd';

const openNotificationWithIcon = (type,info={title:"No title",description:"No description"}) => {
    let {title,description}=info
    notification[type]({
        message:title,
        description: description,
    });
};

export default openNotificationWithIcon