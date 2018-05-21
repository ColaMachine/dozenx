package com.dozenx.web.module.hbase.action;

import com.cpj.swagger.annotation.*;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.hbase.HbaseUtilTest;
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
@RequestMapping("/advertsrv/hbase")

public class HbaseController extends BaseController{

    @API(summary = "hbase测试接口",

            consumes = "application/x-www-form-urlencoded",
            description = "hbase测试接口", parameters = {

   //         @Param(name = "id", description = "/view/{id}", dataType = DataType.STRING, in="path",required = true),
    })
    @APIResponse(value = "{ \"r\": 0, \"data\": { \"id\": 123, \"username\": \"123\", \"password\": \"123\", \"nkname\": \"123\", \"status\": 1, \"telno\": \"13969696969\", \"idcard\": \"23\", \"sex\": 0, \"birth\": \"Feb 1, 2018 12:00:00 AM\", \"integral\": 123, \"address\": \"123\", \"wechat\": \"123\", \"qq\": 123, \"face\": \"static/img/timg.jpeg\", \"remark\": \"123\", \"createTime\": \"Feb 6, 2018 3:23:10 PM\", \"updateTime\": \"Feb 6, 2018 3:23:10 PM\" } }")

    @RequestMapping(value = "/test" ,method= RequestMethod.GET,produces="application/json")
    @ResponseBody
    public ResultDTO viewRestFul() {

        try {
            HbaseUtilTest.main(new String[]{"123"});
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回角色信息
        return this.getResult();

    }

    @API(summary = "hbase测试接口",

            consumes = "application/x-www-form-urlencoded",
            description = "hbase测试接口", parameters = {

            //         @Param(name = "id", description = "/view/{id}", dataType = DataType.STRING, in="path",required = true),
    })
    @APIResponse(value = "{ \"r\": 0, \"data\": { \"id\": 123, \"username\": \"123\", \"password\": \"123\", \"nkname\": \"123\", \"status\": 1, \"telno\": \"13969696969\", \"idcard\": \"23\", \"sex\": 0, \"birth\": \"Feb 1, 2018 12:00:00 AM\", \"integral\": 123, \"address\": \"123\", \"wechat\": \"123\", \"qq\": 123, \"face\": \"static/img/timg.jpeg\", \"remark\": \"123\", \"createTime\": \"Feb 6, 2018 3:23:10 PM\", \"updateTime\": \"Feb 6, 2018 3:23:10 PM\" } }")

    @RequestMapping(value = "/test2" ,method= RequestMethod.GET,produces="application/json")
    @ResponseBody
    public ResultDTO viewRestFul2() {
        DcHBaseService dcHBaseService = new DcHBaseService();
        try {
            dcHBaseService.createTable("testzzw","s");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回角色信息
        return this.getResult();

    }

}
