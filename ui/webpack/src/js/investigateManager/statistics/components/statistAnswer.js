/**
 * Copyright(C),1988-1999,aWiFi Operations Center
 * Author:    作者 Stanley Huang   Version:1.0   Date:2018.1.10
 * Description: 选择题
 * Others:无
 */
import React from 'react';
import  Axios from 'axios';
import {Button, Table, Modal, Pagination, notification} from 'antd';
import AnswerOption from './itemAnswer'
import $http from 'util/http';
/**
 * 选择题统计Tab内容
 */
export  default  class StatSelect extends React.Component {
    constructor(p) {
        super(p);

        this.state = {
           list:[],
           total:0,
           questionDescription:'',
           id:0,
           yellow:'blue'
          
        };
        this.onPageSizeChange = this.onPageSizeChange.bind(this);
        this.onPageChange = this.onPageChange.bind(this);

        this.pageNo = 1;
        this.pageSize = 10;
      
    }
    componentWillMount() {
        this.search();
       
    }
    componentDidMount=()=>{
       console.log("node",this.refs.boy.children); 
       let select=this.refs.boy.children;
       Array.from(select).map((item1,index)=>{
          
          Array.from(item1.children).map((item2,index)=>{
            let mun= item2.innerHTML;
            this.search(mun);
                item1.onclick=()=>{
                    item1.style.background="blue";
                    //item1.style.background="";
                    console.log("item1",item1);
                    this.props.data.map((item,index)=>{
                            if(mun==item.id){
                                let questionDescription=item.questionDescription;
                                let questionNumber=item.questionNumber;
                                console.log("questionDescription",questionDescription);
                                let id=mun;
                                var url = 'questionnaire/statistics/answers';
                                url = url + '?'+`id=${mun}`+"&"+`pageNum=${this.pageNo}`+"&"+`pageSize=${this.pageSize}`+"&"+`type=1`;
                                Axios.get(url, this.HeaderToken).then(({data}) => {
                                    console.log(data);
                                let{list=[],total=0}=data.data;
                                list.forEach((item, index) => {
                                        item.key = index + this.pageSize * (this.pageNo - 1) + 1;
                                    })
                                    console.log("ddddd",this.props.data);
                                    this.setState({
                                        list, total,questionDescription,id,questionNumber
                                    });
                                }).catch((err) => {
                                    
                                }) 
                               
                            }
                        })
                  
                }
           })
       
       })
    }
   
    onPageSizeChange(current, size) {
        this.pageSize = size;
        this.search(this.state.id);
    }

    onPageChange(page, pageSize) {
        this.pageNo = page;
        this.search(this.state.id);
    }
    search=(mun)=>{
          for(let i=0;i<this.props.data.length;i++){
                let id=this.props.data[0]
                let questionDescription=id.questionDescription;
                let questionNumber=id.questionNumber;
                console.log("id",id.id);
                if(mun==id.id){
                    var url = 'questionnaire/statistics/answers';
                    url = url + '?'+`id=${id.id}`+"&"+`pageNum=${this.pageNo}`+"&"+`pageSize=${this.pageSize}`+"&"+`type=2`;
                    Axios.get(url, this.HeaderToken).then(({data}) => {
                        console.log(data);
                       let{list=[],total=0}=data.data;
                       list.forEach((item, index) => {
                            item.key = index + this.pageSize * (this.pageNo - 1) + 1;
                        })
                       this.setState({
                            list, total,questionDescription,id,questionNumber
    
                         });
                        console.log(list)
                    }).catch((err) => {
                        
                    })
                }else{
                    var url = 'questionnaire/statistics/answers';
                    url = url + '?'+`id=${mun}`+"&"+`pageNum=${this.pageNo}`+"&"+`pageSize=${this.pageSize}`+"&"+`type=1`;
                    Axios.get(url, this.HeaderToken).then(({data}) => {
                        console.log(data);
                       let{list=[],total=0}=data.data;
                       list.forEach((item, index) => {
                            item.key = index + this.pageSize * (this.pageNo - 1) + 1;
                        })
                        let id=mun;
                       this.setState({
                            list, total,questionDescription,id,questionNumber
    
                         });
                        console.log(list)
                    }).catch((err) => {
                        
                    })
                }
               
          }
             
    }
    GoodAdvice=(id)=>{
        console.log("id",id);
        let url = '/questionnaire/answer/ordinary';
        url = url +`/${id}`;
        Axios.put(url, this.HeaderToken).then(({data}) => {
           if(data.code==0){
             notification['success']({
                message: '提示',
                description: '好建议库',
              });
           }
           this.props.search(this.props.id)
        }).catch((err) => {
            console.error('获取列表失败')
        })
 
     }
     handle=(e)=>{
        let select=this.refs.boy.children;
        for(var i=0;i<select.length;i++){
            select[i].style.background="";
        }
        e.target.style.background="#4AA0ED"
     }
    render() {
        console.log("type",this.props.data);
            var data=[];
            this.props.data.map((item,index)=>{
                console.log("item",item);
                data.push(<li key={index}>{item.questionNumber}<span style={{display:"none"}}>{item.id}</span></li>);
            })
        return (
            <div>
                 { <ul className="select-labels" style={{cursor: 'pointer',height:'20px'}} ref="boy" onClick={this.handle}>
                    {data}
                </ul> }
                    <div className="quest-title">
                    <span className="dot">{this.state.questionNumber}</span>
                    <label style={{marginLeft:"20px"}}>{this.state.questionDescription}</label>
                    <div className="line"></div>
                    <Button type="primary" className="storageButton" onClick={this.GoodAdvice.bind(this,this.state.id)} >好建议库</Button>
                    </div>
                  {this.state.list.map((item,index)=>{
                      return <AnswerOption data={item} key={index} style={{marginTop:"30px"}} search={this.search} pageNum={this.pageNo} pageSize={this.pageSize}  id={this.state.id}/>
                  })}
                {/*分页*/}
                {this.state.total ? <Pagination
                    className="pagination"
                    showSizeChanger
                    onShowSizeChange={this.onPageSizeChange}
                    onChange={this.onPageChange}
                    total={this.state.total}
                    itemRender={this.pageItemRender}/> : ''}
            </div>
            );
    }
}




