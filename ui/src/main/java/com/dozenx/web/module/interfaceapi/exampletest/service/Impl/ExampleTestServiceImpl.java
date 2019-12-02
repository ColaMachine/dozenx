package com.dozenx.web.module.interfaceapi.exampletest.service.Impl;


import com.dozenx.common.util.HttpRequestUtil;
import com.dozenx.common.util.JsonUtil;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.interfaceapi.exampletest.dao.ExampleTestMapper;
import com.dozenx.web.module.interfaceapi.exampletest.pojo.ExampleTest;
import com.dozenx.web.module.interfaceapi.exampletest.service.ExampleTestService;
import com.dozenx.web.module.interfaceapi.interfaceinfo.dao.InterfaceInfoMapper;
import com.dozenx.web.module.interfaceapi.interfaceinfo.pojo.InterfaceInfo;
import com.dozenx.web.module.interfaceapi.interfacelog.dao.InterfaceLogMapper;
import com.dozenx.web.module.interfaceapi.interfacelog.pojo.InterfaceLog;
import com.dozenx.web.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class ExampleTestServiceImpl implements ExampleTestService {
    private static Logger logger = LoggerFactory.getLogger(ExampleTestServiceImpl.class);
    @Autowired
    private ExampleTestMapper exampleTestMapper;
    @Autowired
    private InterfaceInfoMapper interfaceInfoMapper;
    @Autowired
    private InterfaceLogMapper interfaceLogMapper;

    // 运行接口
    @Override
    public ResultDTO callInterface(Integer id,String paramsJson) {
        Map vparams = JsonUtil.toMap(paramsJson);
        String resultMsg = null;
        //Integer id = MapUtils.getInteger(vparams,"id");
        logger.info("id:"+id+",params:"+paramsJson);
        InterfaceInfo api = interfaceInfoMapper.getApiById(id);
        try{
            Map data = JsonUtil.toMap(paramsJson);//vparams.get("params").toString());
            String url = api.getUrl();
            String httptype = api.getHttpType();// vparams.get("httptype").toString();
            String version = api.getVersion();// vparams.get("version").toString();
            String method =  api.getInterfaceId();// vparams.get("method").toString();
            logger.info(httptype+","+version+","+method);
            if(api.getSplicing()==1){
                Map splicingMap = new HashMap();
                splicingMap.put("params",JsonUtil.toJsonString(data));
                resultMsg = RequestMethod(url,httptype,splicingMap,method);
            }else{
                resultMsg = RequestMethod(url,httptype,data,method);
            }
        }catch (Exception e){
            logger.info("exception:"+e);
            return ResultUtil.getResult(0,"调试失败");
        }
        return ResultUtil.getResult(1,resultMsg,"调试成功");
    }

    // 运行测试用例
    @Override
    public void Test() {
        List<ExampleTest> testExample = exampleTestMapper.select(new HashMap());
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10,15,
                60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        long startTime = System.currentTimeMillis();
        for(final ExampleTest e :testExample) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    Map map = JsonUtil.toMap(e.getParams());
                    if(e.getInterfaceInfo().getSplicing()==1){
                        map.clear();
                        map.put("params",e.getParams());
                    }
                    try {
                        String result = RequestMethod(e.getInterfaceInfo().getUrl(),e.getInterfaceInfo().getHttpType(),map,e.getInterfaceInfo().getContentType());
                        InterfaceLog interfaceLog = new InterfaceLog(e.getId(),result);
                        interfaceLogMapper.insert(interfaceLog);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });
        }
        threadPool.shutdown();
        long time1;
        while (true){
            if(threadPool.isTerminated()){
                time1 = System.currentTimeMillis()-startTime;
                break;
            }
        }
        logger.info("run time:"+time1);

    }

    // 添加测试用例
    @Override
    public ResultDTO addTestCase(Integer id,String params) {
        Map vparams = JsonUtil.toMap(params);
        try {
            //Integer id = MapUtils.getInteger(vparams,"id");
            InterfaceInfo interfaceInfo = interfaceInfoMapper.getApiById(id);
            if(interfaceInfo==null){
                return ResultUtil.getResult(0,"interface is not exist");
            }
            ExampleTest exampleTest = new ExampleTest(interfaceInfo,params);
            exampleTestMapper.insert(exampleTest);
        }catch (Exception e){
            return ResultUtil.getResult(0,"添加失败");
        }
        return ResultUtil.getResult(1,"添加成功");
    }

    // 删除测试用例
    @Override
    public ResultDTO deleteTestCase(Integer id) {
        try{
            exampleTestMapper.delete(id);
        }catch (Exception e){
            return ResultUtil.getResult(0,"删除失败");
        }
        return ResultUtil.getResult(1,"删除成功");
    }

    // 更新测试用例
    @Override
    public ResultDTO updateTestCase(Integer id , String vcparams) {
        //Map vparams = JsonUtil.toMap(params);
        try {
            //Integer id = MapUtils.getInteger(vparams, "id");
            //String vcparams = MapUtils.getString(vparams, "params");
            ExampleTest exampleTest = new ExampleTest();
            exampleTest.setId(id);
            exampleTest.setParams(vcparams);
            exampleTestMapper.update(exampleTest);
        }catch (Exception e){
            return ResultUtil.getResult(0,"更新失败");
        }
        return ResultUtil.getResult(1,"更新成功");
    }




    //根据不同请求方式请求接口
    private String RequestMethod(String url,String httpType , Map map,String method) throws Exception {
        String result = null;
        if(httpType.equals("GET")){
            result = HttpRequestUtil.sendGet(url,map);
        }else{
            result = HttpRequestUtil.sendPost(url,map);
        }
        return result;
    }
}
