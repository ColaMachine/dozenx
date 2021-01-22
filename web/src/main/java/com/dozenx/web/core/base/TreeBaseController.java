package com.dozenx.web.core.base;

import com.dozenx.web.core.log.ResultDTO;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 19:00 2019/12/11
 * @Modified By:
 */
public interface TreeBaseController {

    //增加子节点
    public ResultDTO addChild(String name,int parentId);

    public ResultDTO removeItem(int id);


}
