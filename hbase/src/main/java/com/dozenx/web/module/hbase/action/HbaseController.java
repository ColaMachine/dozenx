package com.dozenx.web.module.hbase.action;

import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIResponse;
import com.cpj.swagger.annotation.APIs;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.hbase.service.DcHBaseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 11:06 2018/3/1
 * @Modified By:
 */


@APIs(description = "用户模块")
@Controller
@RequestMapping("/hbase")

public class HbaseController extends BaseController {

    @API(summary = "hbase测试接口",
            consumes = "application/x-www-form-urlencoded",
            description = "hbase测试接口", parameters = {
    })
    @APIResponse(value = "{ \"r\": 0, \"data\": { \"id\": 123, \"username\": \"123\", \"password\": \"123\", \"nkname\": \"123\", \"status\": 1, \"telno\": \"13969696969\", \"idcard\": \"23\", \"sex\": 0, \"birth\": \"Feb 1, 2018 12:00:00 AM\", \"integral\": 123, \"address\": \"123\", \"wechat\": \"123\", \"qq\": 123, \"face\": \"static/img/timg.jpeg\", \"remark\": \"123\", \"createTime\": \"Feb 6, 2018 3:23:10 PM\", \"updateTime\": \"Feb 6, 2018 3:23:10 PM\" } }")
    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResultDTO testHbase() {
        try {
            boolean flag = DcHBaseService.testHbase();
            if(flag){
                return this.getResult(0);
            }else{
                return this.getResult(1,"hbase测试失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return this.getResult(2,"hbase异常"+e.getMessage());
        }

    }


    @API(summary = "hbase接口强制重连接口",
            consumes = "application/x-www-form-urlencoded",
            description = "hbase测试接口", parameters = {
            //         @Param(name = "id", description = "/view/{id}", dataType = DataType.STRING, in="path",required = true),
    })
    @APIResponse(value = "{ \"r\": 0, \"data\": { \"id\": 123, \"username\": \"123\", \"password\": \"123\", \"nkname\": \"123\", \"status\": 1, \"telno\": \"13969696969\", \"idcard\": \"23\", \"sex\": 0, \"birth\": \"Feb 1, 2018 12:00:00 AM\", \"integral\": 123, \"address\": \"123\", \"wechat\": \"123\", \"qq\": 123, \"face\": \"static/img/timg.jpeg\", \"remark\": \"123\", \"createTime\": \"Feb 6, 2018 3:23:10 PM\", \"updateTime\": \"Feb 6, 2018 3:23:10 PM\" } }")
    @RequestMapping(value = "/reconnect", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResultDTO reconnect() {
        DcHBaseService dcHBaseService = new DcHBaseService();
        try {
            dcHBaseService.ForceGetConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回角色信息
        return this.getResult();
    }

}
