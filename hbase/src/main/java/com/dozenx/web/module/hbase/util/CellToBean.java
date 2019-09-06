package com.dozenx.web.module.hbase.util;

import java.lang.reflect.Field;
import java.util.List;

import com.dozenx.common.util.StringUtil;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.util.ReflectionUtils;


public class CellToBean {
    
    private static ConversionService conversionService = new DefaultConversionService();
    
    
    public static <T> T convert(List<Cell> cells, T o) throws NoSuchFieldException, SecurityException {
        for (Cell cell : cells) {
            
            /*System.out.println("\t列簇:" + new String(CellUtil.cloneFamily(cell)) + "\t列:"
                    + new String(CellUtil.cloneQualifier(cell)) + "\t值:" + new String(CellUtil.cloneValue(cell)));*/
            
            Field field = o.getClass().getDeclaredField(new String(CellUtil.cloneQualifier(cell)));
            ReflectionUtils.makeAccessible(field);
            String sval = new String(CellUtil.cloneValue(cell));
            if(StringUtil.isNotBlank(sval)){
                Object newval = conversionService.convert(sval, field.getType());
                ReflectionUtils.setField(field, o, newval);
            }
        }
        return o;
    }
    
}
