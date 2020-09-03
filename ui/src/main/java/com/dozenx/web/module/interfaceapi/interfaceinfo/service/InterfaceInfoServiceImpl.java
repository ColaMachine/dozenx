package com.dozenx.web.module.interfaceapi.interfaceinfo.service;

import com.dozenx.common.util.StringUtil;
import com.dozenx.web.module.interfaceapi.interfaceParam.service.InterfaceParamService;
import com.dozenx.web.module.interfaceapi.interfaceinfo.dao.InterfaceInfoMapper;
import com.dozenx.web.module.interfaceapi.interfaceinfo.pojo.InterfaceInfo;
import com.dozenx.web.module.interfaceapi.interfaceParam.dao.InterfaceParamMapper;
import com.dozenx.web.module.interfaceapi.interfaceParam.pojo.InterfaceParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
public class InterfaceInfoServiceImpl implements InterfaceInfoService {
    @Autowired
    private InterfaceInfoMapper interfaceInfoMapper;
    @Autowired
    private InterfaceParamMapper paramsMapper;

    @Override
    public int insertApi(InterfaceInfo record) {
        return interfaceInfoMapper.insertApi(record);
    }


    @Override
    public int deleteApi(Integer id) {
        return interfaceInfoMapper.deleteApi(id);
    }

    @Override
    public List<InterfaceInfo> selectAll() {
        return interfaceInfoMapper.selectAll();
    }


    @Override
    public int updateApi(InterfaceInfo record) {
        //若是修改了version版本号,要将原来的数据设为历史数据,然后新增一条新的版本
        String version = interfaceInfoMapper.getApiById(record.getId()).getVersion(); //查询原数据版本号
        Boolean sda = version.equals(record.getVersion());
        if(!StringUtil.isBlank(version) && !version.equals(record.getVersion())) {
            interfaceInfoMapper.insertApi(record);
            InterfaceInfo temp = new InterfaceInfo();
            temp.setId(record.getId());
            temp.setHistoryFlag(1);//原数据变为历史版本
            interfaceInfoMapper.updateApi(temp);
            return 1;
        }
        else if (!StringUtil.isBlank(version) && version.equals(record.getVersion())){
            return interfaceInfoMapper.updateApi(record);
        }
        return 0;
    }


    @Override
    public List<InterfaceInfo> listByParams(Map map) {
        List<InterfaceInfo> apiList = interfaceInfoMapper.getApiByParams(map);
        return apiList;
    }
    @Override
    public PageInfo<InterfaceInfo> getApiByParams(Map map, int curPage, int pageSize) {
        PageHelper.startPage(curPage, pageSize);
        List<InterfaceInfo> apiList = interfaceInfoMapper.getApiByParams(map);
        PageInfo<InterfaceInfo> pageInfo = new PageInfo(apiList);
        return pageInfo;
    }


    @Override
    public InterfaceInfo getApiById(int id) {
        return interfaceInfoMapper.getApiById(id);
    }

    //接口信息查询，根据项目名称，接口id，版本
    /**
     *@Description:
     * * @param params
     *@return:
     *@Author: 吴勤
     *@date: 2019/11/21
     */
    @Override
    public List<InterfaceInfo> getInterfaceInfo(InterfaceInfo params) {

        return interfaceInfoMapper.selectByPrimaryKeyByparams(params);
    }

    @Override
    public int insertSelective(InterfaceInfo record){
        List<InterfaceParam> paramsadd=record.getInterfaceParams();
        System.out.println("999"+record.getVersion());
        int result=interfaceInfoMapper.insertSelective(record);
        int id=record.getId();
        int result1=0;
        if(paramsadd!=null)
        for(InterfaceParam items : paramsadd ){
//            InterfaceParam params=new InterfaceParam();
//            if (items.getParamName().toString()!=null&&items.getParamName().toString()!="") {
//                params.setParamName(items.get("paramName").toString());
//            }
//            if (items.get("paramType").toString()!=null&&items.get("paramType").toString()!="") {
//                params.setParamType(items.get("paramType").toString());
//            }
//            if (items.get("paramComment").toString()!=null&&items.get("paramComment").toString()!="") {
//                params.setParamComment(items.get("paramComment").toString());
//            }
            items.setInterfaceId(id);
            items.setId(null);
            result1 = paramsMapper.insertSelective(items);
            if (result1==0){
                return 0;
            }
        }
        return result;
    };
    @Override
    public List<Map<String,Object>> selectByInterfaceIdParams(int interfaceid){
        return paramsMapper.selectByInterfaceIdParams(interfaceid);
    }

    @Override
    public int updateByPrimaryKeySelective(InterfaceInfo record){
        List<InterfaceParam> paramsadd=record.getInterfaceParams();
        int resultParams=0;
        HashSet<Integer> idSet =new HashSet<>();
        List<InterfaceParam> oldParams = paramsMapper.selectByInterfaceId(record.getId());

        if(paramsadd!=null){
            for(InterfaceParam params : paramsadd ){
//                InterfaceParam params=new InterfaceParam();
//                params.setId((Integer) items.get("id"));
//                params.setInterfaceId(record.getId());
//                params.setParamName(items.get("paramName").toString());
//                params.setParamType(items.get("paramType").toString());
//                params.setParamComment(items.get("paramComment").toString());
//                params.setCreateTime( Timestamp.valueOf(record.getCreateTime()));
                if(params.getId()!=null) {
                    resultParams = paramsMapper.updateByPrimaryKeySelective(params);
                    idSet.add(params.getId());
                }else {
                    paramsMapper.insert(params);
                }
            }
        }
        if(oldParams!=null)
        for(InterfaceParam interfaceParam : oldParams){
            if(interfaceParam.getId()!=null && !idSet.contains(interfaceParam.getId())){
                paramsMapper.deleteByPrimaryKey(interfaceParam.getId());
            }
        }

        int result= interfaceInfoMapper.updateByPrimaryKeySelective(record);
        return result==resultParams?1:0;
    };
}
