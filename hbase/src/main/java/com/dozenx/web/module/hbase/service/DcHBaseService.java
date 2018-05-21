package com.dozenx.web.module.hbase.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dozenx.web.module.hbase.connection.DcHBaseConnection;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;


/**
 * @ClassName: DcHBaseUtil
 * @Description: HBase工具类
 * @author: Dingxc
 * @date: 2016年12月22日 上午9:46:36
 * @version: 1.0
 */

public class DcHBaseService {

    /** 日志 */
    private static final Logger LOG = LoggerFactory.getLogger(DcHBaseService.class);
    /** connection */
    private static final Connection CONN = DcHBaseConnection.getConnection();

    public static Connection getCONN() {
        return CONN;
    }

    /**
     * @Title: createTable
     * @Description: 创建HBase表，单列名
     * @param tableName 表名
     * @param familyName 列名
     */
    public static void createTable(String tableName, String familyName) {
        HBaseAdmin admin = null;
        try {
            LOG.debug("hbase connection:" + CONN);
            //获得admin，创建表
            admin = (HBaseAdmin) CONN.getAdmin();
            // 判断表是否存在，如果不存在，创建
            if (!admin.tableExists(tableName)) {
                LOG.debug("creating HBase table...");
                HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf(tableName));
                LOG.debug("add family to HBase table...");
                //加列族
                tableDesc.addFamily(new HColumnDescriptor(familyName));
                admin.createTable(tableDesc);
            } else {
                LOG.debug("table is exists...");
            }
            LOG.debug("create HBase table OK...table name:" + tableName + ",family name:" + familyName);
        } catch (IOException e) {
            LOG.debug("create HBase table error... message:" + e.getMessage());
        } finally {
            if (null != admin) {
                try {
                    admin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @Title: createTable
     * @Description: 创建HBase Table，多列
     * @param tableName 表名
     * @param familyNames 列名List
     */
    public static void createTable(String tableName, List<String> familyNames) {
        HBaseAdmin admin = null;
        try {
            LOG.debug("hbase connection:" + CONN);
            admin = (HBaseAdmin) CONN.getAdmin();
            // 判断表是否存在，如果不存在，创建
            if (!admin.tableExists(tableName)) {
                LOG.debug("creating HBase table...");
                HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf(tableName));
                LOG.debug("add family to HBase table...");
                for (String familyName : familyNames) {
                    tableDesc.addFamily(new HColumnDescriptor(familyName));
                }
                admin.createTable(tableDesc);
            } else {
                LOG.debug("table is exists...");
            }
            LOG.debug("create HBase table OK...table name:" + tableName + ",family names:" + familyNames.toString());
        } catch (IOException e) {
            LOG.error("create HBase table error... message:" + e.getMessage(),e);
        } finally {
            if (null != admin) {
                try {
                    admin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @Title: insert
     * @Description: 新增到HBase库
     * @param tableName String
     * @param puts List<Put>
     * @return: void
     */
    public static void insert(String tableName, List<Put> puts) {

        HTable table = null;
        try {
            if(puts!=null && puts.size()>0){
                LOG.debug("inserting into HBase...");
                table = (HTable) CONN.getTable(TableName.valueOf(tableName));
                table.setAutoFlushTo(false);// 设置不自动flush
                table.put(puts);
                table.flushCommits();
            }
        } catch (IOException e) {
            // e.printStackTrace();
            LOG.error("insert into HBase error... message:" + e.getMessage(),e);
        } finally {
            if (null != table) {
                try {
                    table.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static boolean checkAndPut(String tableName,byte[] row, byte[] family, byte[] qualifier,
                                   byte[] value, Put put) {
        HTable table = null;
        try {
            LOG.debug("inserting into HBase...");
            table = (HTable) CONN.getTable(TableName.valueOf(tableName));
            table.setAutoFlushTo(false);// 设置不自动flush
            boolean success= table.checkAndPut(row,  family,  qualifier, value,  put);
            table.flushCommits();
            return success;
        } catch (IOException e) {
            LOG.error("insert into HBase error... message:" + e.getMessage(),e);
        } finally {
            if (null != table) {
                try {
                    table.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static void delete(String tableName, List<Delete> deletes) {

        HTable table = null;
        try {
            if(deletes!=null && deletes.size()>0){
                LOG.debug("delete  HBase row ...");
                table = (HTable) CONN.getTable(TableName.valueOf(tableName));
                table.setAutoFlushTo(false);// 设置不自动flush
                table.delete(deletes);
                table.flushCommits();
            }
        } catch (IOException e) {
            // e.printStackTrace();
            LOG.error("delete  HBase rows error... message:" + e.getMessage(),e);
        } finally {
            if (null != table) {
                try {
                    table.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据列前缀获取列，不分列簇
     * @param tableName
     * @param rowkey
     * @return
     */
    public static Result getColumnValue(String tableName,String rowkey,byte[] family,byte[] column){
        try {
            HTable table=(HTable) CONN.getTable(TableName.valueOf(tableName));
            Get get=new Get(Bytes.toBytes(rowkey));
            get.addColumn(family,column);
            Result result=table.get(get);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 
     * @Title: scanAll
     * @Description: 查询表里面所有数据
     * @param tableName String
     * @return List<Object>（Json?）
     */
    public static List<Object> scanAll(String tableName) {
        List<Object> res = null;
        HTable table = null;
        Scan scan = null;
        try {
            LOG.debug("Scan HBase,table name:" + tableName);
            table = (HTable) CONN.getTable(TableName.valueOf(tableName));
            scan = new Scan();
            ResultScanner scanRes = table.getScanner(scan);
            res = new ArrayList<Object>();
            for (Result result : scanRes) {
                for (Cell cell : result.rawCells()) {
                    res.add(new String(CellUtil.cloneValue(cell)));
                }
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            LOG.debug("Scan HBase error... message:" + e.getMessage());
            return null;
        } finally {
            if (null != table) {
                try {
                    table.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    /**
     * 
     * @Title: scanAllBySubstringKeyWordPaged
     * @Description: 根据条件分页查询HBase包含param的结果
     * @param params Map<String, Object>
     * @param: @param tableName
     * @param: @param startRow
     * @param: @param endRow
     * @param: @param keyWord
     * @return List<Object>（json串）
     */
    public static List<Object> scanAllBySubstringKeyWordPaged(Map<String, Object> params) {
        List<Object> res = null;
        HTable table = null;
        Scan scan = null;
        res = new ArrayList<Object>();
        /** 从Map中获取参数 */
        String tableName = (String) params.get("tableName");
        Integer startRow = (Integer) params.get("startRow");
        Integer rows = (Integer) params.get("rows");
        String keyWord = (String) params.get("keyWord");
        Long begindate = (Long) params.get("begindate");
        Long enddate = (Long) params.get("enddate");
        /** tableName不能为空 */
        if (null != tableName && !("".equals(tableName))) {
            try {
                LOG.debug("Scan HBase,table name:" + tableName);
                /** 获取table连接 */
                table = (HTable) CONN.getTable(TableName.valueOf(tableName));
                /** 获取扫描工具 */
                scan = new Scan();
                /** rowKey过滤器 */
                RowFilter rowFilter = null;
                /** 设置查询关键字 */
                if (null != keyWord && !("".equals(keyWord)) && !("null".equals(keyWord))) {
                    rowFilter = new RowFilter(CompareOp.EQUAL, new SubstringComparator(keyWord));
                    scan.setFilter(rowFilter);
                }
                /** 设置查询时间范围 */
                if (null != begindate && 0L != begindate && null != enddate && 0L != enddate) {
                    scan.setTimeRange(begindate, enddate);
                }
                /** 获得所有查询结果 */
                ResultScanner scanRes = table.getScanner(scan);
                int i = 0;
                /** 解析过滤查询结果 */
                // 如果分页条件不为空
                if (null != startRow && null != rows && 0 != rows) {
                    int endRow = startRow + rows;
                    for (Result result : scanRes) {
                        if (i < startRow) {
                            i++;
                            continue;
                        } else {
                            if (i >= endRow) {
                                break;
                            }
                            for (Cell cell : result.rawCells()) {
                                res.add(new String(CellUtil.cloneValue(cell)));
                            }
                            i++;
                        }
                    }
                } else {
                    /** 分页条件为空（没有输入查询条数） */
                    for (Result result : scanRes) {
                        for (Cell cell : result.rawCells()) {
                            res.add(new String(CellUtil.cloneValue(cell)));
                        }
                    }
                }
                return res;
            } catch (Exception e) {
                e.printStackTrace();
                LOG.debug("Scan HBase error... message:" + e.getMessage());
                return null;
            } finally {
                if (null != table) {
                    try {
                        table.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            // 没有指定tableName
            return null;
        }
    }


    /**
     * 根据SingleColumnValueFilter  和rowkey前缀来查询，查询返回结果调用callBack回调
     * @param tableName
     * @param rowPrefix rowkey的前缀
     * @param callBack
     * @param valueFilter SingleColumnValueFilter
     * @param familys
     */
    public static  void columnValueScan(String tableName, String rowPrefix, CallBack callBack,
                                        SingleColumnValueFilter valueFilter,Integer limit,
                                        Integer cache,byte[] ...familys ){

        HTable table= null;
        if(limit==null ||limit==0){
            limit=Integer.MAX_VALUE;
        }
        try {
            table = (HTable) DcHBaseService.getCONN().getTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        Scan scan=new Scan();
        if(familys!=null){
            for(int i=0;i<familys.length;i++){
                scan.addFamily(familys[i]);
            }
        }

        List<Filter> filters=new ArrayList<>();

        Filter filter=new PrefixFilter(Bytes.toBytes(rowPrefix));
        filters.add(filter);
        filters.add(valueFilter);

        FilterList filterList=new FilterList(FilterList.Operator.MUST_PASS_ALL,filters);
        scan.setFilter(filterList);
        /**设置查询缓存**/
        if(cache!=null){
            scan.setCaching(cache);
        }
        ResultScanner scanner=null;
        try {
            scanner=table.getScanner(scan);
            Result result=null;
            int count=0;
            while ((result=scanner.next())!=null){
                if(result!=null){
                    count++;
                    callBack.apply(result);
                    if(count>limit){
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            if(scanner!=null){
                scanner.close();
            }
            //放在最后，不影响scanner的超时时间
            callBack.apply();
        }

    }


    /**
     *
     * @param tableName
     * @param rowPrefix rowkey前缀
     * @param pageSize
     * @param callBack
     * @param cache  查询缓存
     * @param familys
     */
    public static  void pageScan(String tableName, String rowPrefix, int pageSize,
                                 CallBack callBack, Integer cache,byte[] ...familys ){

        byte[] POSTFIX = new byte[] { 0x00 };
        HTable table= null;
        try {
            table = (HTable) DcHBaseService.getCONN().getTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        Scan scan=new Scan();
        if(familys!=null){
            for(int i=0;i<familys.length;i++){
                scan.addFamily(familys[i]);
            }
        }
        List<Filter> filters=new ArrayList<>();
        Filter pageFilter=new PageFilter(pageSize);
        filters.add(pageFilter);
        Filter filter=new PrefixFilter(Bytes.toBytes(rowPrefix));
        filters.add(filter);

        FilterList filterList=new FilterList(FilterList.Operator.MUST_PASS_ALL,filters);
        scan.setFilter(filterList);

        /**设置查询缓存**/
        if(cache!=null){
            scan.setCaching(cache<pageSize?cache:pageSize);
        }

        ResultScanner scanner=null;
        byte[] lastRow=null;
        while (true){
            if(lastRow!=null){
                byte[] startRow=Bytes.add(lastRow,POSTFIX);
                scan.setStartRow(startRow);
            }
            try {
                scanner=table.getScanner(scan);
                Result result=null;
                int localRows=0;
                while ((result=scanner.next())!=null){
                    localRows++;
                    lastRow=result.getRow();
                    callBack.apply(result);
                }
                if(localRows==0)break;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }finally {
                if(scanner!=null){
                    scanner.close();
                }
                try {
                    //放在最后，不影响 scanner超时时间
                    callBack.apply();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据rowKey查询一条记录
     * 
     * @param rowKey
     */
    public static List<Cell> queryByRowKey(String tableName, String rowKey) throws Exception {
        Get get = new Get(rowKey.getBytes());
        HTable table= null;
        try {
            table = (HTable) DcHBaseService.getCONN().getTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        
        Result r = table.get(get);
        
        if (!r.isEmpty()) {
            /*System.out.println("获得rowKey:" + new String(r.getRow()));
            for (Cell cell : r.rawCells()) {
                System.out.println("\t列簇:" + new String(CellUtil.cloneFamily(cell)) + "\t列:"
                        + new String(CellUtil.cloneQualifier(cell)) + "\t值:" + new String(CellUtil.cloneValue(cell)));
            }*/
            return r.listCells();
        }
        return Lists.newArrayList();
    }
    
    /**
     * 是否存在rowKey
     * 
     * @param rowKey
     */
    public static Boolean isExistenceByRowKey(String tableName, String rowKey) throws Exception {
        Get get = new Get(rowKey.getBytes());
        HTable table= null;
        try {
            table = (HTable) DcHBaseService.getCONN().getTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } 
        Result r = table.get(get);
        if (!r.isEmpty()) {
            return true;
        }
        return false;
    }
}
