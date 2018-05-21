package com.dozenx.web.module.calendar.dao;
/**
 * @Title: ActivityDao
 * @Package cola.machine.calendar.activity.dao
 * @Description: ActivityDao接口
 * @author: 371452875@qq.com
 * @date: 2014年2月24日 19:03:34
 * @version: V1.0
 */

import com.dozenx.web.module.calendar.bean.Activity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dozen.zhang on 2017/3/29.
 */

@Component("activityDao")
public interface ActivityDao {

    int deleteByPrimaryKey(Long id);


    int insert(Activity record);


    int insertSelective(Activity record);


    Activity  selectByPrimaryKey(Long id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param activity
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(Activity activity);

    /**
     * 说明:根据主键修改record完整内容
     * @param activity
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(Activity activity);

    /**
     * 说明:根据map查找bean结果集
     * @param map
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Activity> listByParams(Map map);

    /**
     * 说明:根据bean查找bean结果集
     * @param map
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Activity> listByParams4Page(Map map);

    /**
     * 说明:根据map查找map结果集
     * @param Activity
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
/* List<Map> selectMapByBean4Page(Activity record);*/


    /**
     * 说明:根据map查找map结果集
     * @param map
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
/*List<Activity> selectBeanByMap4Page(HashMap map);

int countByBean(Activity record);*/

    int countByParams(HashMap map);


    List<HashMap> selectActivityBetween2Date(Map map);

    List<HashMap> selectActivityBetween2DateByGroup(Map map);
    }

