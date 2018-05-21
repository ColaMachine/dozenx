/**
 * Created by songbo on 2017/8/22.
 * modify Stanley Huang
 */
import React from 'react'
import { Button, Table, Modal, Pagination, Icon} from 'antd';
import './liveVideo.scss'
import notice from '../../util/notice'
import Monitor from './video'
import Search from './search'
import axios from 'axios'
import  queryString from  'querystring'
import $http from '../../util/http'

class BroadcastList extends React.Component {
    constructor() {
        super();
        this.state = {
            tableData: [],
            canceling: false,
            registerWindow: false,
            refuseModel: false, //取消直播确认框
            monitorModel: false, //打开监控视频窗口
            totalRecord: 0,
            pagination: {update: 0, pageNo: 1, pageSize: 20}
        };

        this.monitor = null; // monitor组件通讯
        this.onPaging = this.onPaging.bind(this);
        this.setupData = this.setupData.bind(this);
    }



    setupData(data) {
        let {list: tableData, total: totalRecord} = data || {};

        var name = [2, 3, 5];
        tableData.forEach((item, index) => {
            item.key = index;
            item.canceling = false;
            let n = name[index % 3];
            item.videoUrl = `/data/gear/${n}.m3u8`;
        })

        this.setState({
            tableData, totalRecord
        })
    }

    onPaging(pageNo, pageSize) {
        if(!pageSize){
            var {pageNo,pageSize}= this.state.pagination;
        }
        let update = ++this.state.pagination.update;
        this.setState({
            pagination: {update, pageNo,pageSize}
        });
    }

    pageItemRender(current, type, originalElement) {
        if (type === 'prev') {
            return <a>上一页</a>;
        } else if (type === 'next') {
            return <a>下一页</a>;
        }
        return originalElement;
    }

    refuseBroadcast(record) {
        this.refuseItem = record;
        this.setState({
            refuseModel: true
        });
    }

    /**
     * 发起后台请求，数据放在this.refuseItem内缓存
     * isRefuse {boolean} true 表示取消直播
     */
    doRefuse(isRefuse) {
        this.setState({
            refuseModel: false
        });
        if (!isRefuse)
            return;

        this.setState({
            canceling: true
        });
        this.refuseItem.canceling = true;

        var {id, mac, illegalCount, phone} = this.refuseItem;
        let params = JSON.stringify({id, mac, illegalCount, phone});
        let url = 'livedevice/cancle?' + queryString.stringify({params});
        axios.post(url, JSON.stringify({token: sessionStorage.token}), {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            }
        }).then((response) => {
            this.refuseItem.canceling = false;
            this.setState({
                canceling: false
            });
            let data = response.data;
            if (!(data.code === "0")) {
                notice("error", {title: '操作失败:', description: data.msg});
            } else {
                notice("success", {title: '操作成功:', description: data.msg});
            }

            this.onPaging();
        }).catch(function (err) {
            this.refuseItem.canceling = false;
            this.setState({
                canceling: false
            });
            notice("error", {title: '操作失败', description: '网络故障'});
        });

    }

    doMonitor(info) {
        this.monitorItem = info;
        this.setState({
            monitorModel: true
        });
        if (this.monitor)
            this.monitor.loadVideo(info.videoUrl);
    }

    exitMonitor() {
        if (this.monitor)
            this.monitor.stop();
        this.setState({
            monitorModel: false
        });
    }

    render() {
        const columns = [
            {
                title: '设备SN码',
                dataIndex: 'sn',
            }, {
                title: '违规次数',
                dataIndex: 'illegalCount',
            }, {
                title: '所属帐户',
                dataIndex: 'phone',
            }, {
                title: '监测视频',//1-未绑定，2-已绑定
                render: (text, record) => {
                    return (
                        <span>
                        <Button className="play-btn" type="primary" shape="circle"
                                onClick={this.doMonitor.bind(this, record)}>
                        <Icon type="play-circle-o"/>
                        </Button>
                    </span>
                    )
                }
            }, {
                title: '直播审核',//status = 1:需要重新申请，2:直播中，3:未直播，4:审核中，5:审核通过，6:禁播,
                dataIndex: 'audit',
                render: (text, record) => {
                    return (
                        <Button className="play-btn" type="ghost"
                                onClick={this.refuseBroadcast.bind(this, record)} loading={record.canceling}
                                data-loading={this.state.canceling}>取消直播
                        </Button>
                    )
                }
            }, {
                title: '直播类型',
                dataIndex: 'liveType',
            }];

        return (
            <div className="right-management">
                <div className="page-title-me" style={{width: '100%'}}>直播管理</div>

                {/*条件查询*/}
                <Search dataCallback={this.setupData} changePage={this.state.pagination} ref={(search) => {
                    this.search = search;
                }}/>

                {/*表格*/}
                <div className="table">
                    <Table columns={columns} className="dev-table" style={{backgroundColor: '#fff'}}
                           dataSource={this.state.tableData} pagination={false}/>
                </div>

                {/*分页*/}
                {this.state.totalRecord ? <Pagination
                    className="pagination"
                    showSizeChanger
                    onShowSizeChange={this.onPaging}
                    onChange={this.onPaging}
                    total={this.state.totalRecord}
                    itemRender={this.pageItemRender}/> : ''}
                {/*取消直播确认弹窗*/}
                <Modal
                    title="取消直播"
                    wrapClassName="vertical-center-modal"
                    visible={this.state.refuseModel}
                    onOk={this.doRefuse.bind(this, true)}
                    onCancel={this.doRefuse.bind(this, false)}>
                    <br/>
                    <p>您确定取消直播吗</p>
                    <br/>
                </Modal>

                {/*监视视频窗口*/}
                <Modal
                    footer={null}
                    wrapClassName="vertical-center-modal monitor-modal"
                    visible={this.state.monitorModel}
                    onOk={this.doRefuse.bind(this, true)}
                    onCancel={this.exitMonitor.bind(this)}>
                    <div >
                        <Monitor videoInfo={this.monitorItem} ref={(el) => {
                            this.monitor = el;
                        }}/>
                    </div>
                </Modal>

            </div>
        );
    }
}


// const bList = Form.create()(BroadcastList);
export default BroadcastList