package com.skyline.common.mapper;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 类名称：通用Mapper的扩展 <br/>
 * 类描述：支持sql查询 <br/>
 * 创建人： lishanglong <br/>
 * 
 * @updateRemark 修改备注：
 */
public interface GenMapper {

    /**
     * @param sql 执行sql
     * @return
     * @author lishanglong
     */
    List<LinkedHashMap<String, Object>> queryBySql(@Param("sql")String sql);

    /**
     * 单表操作
     * @param table 表名称
     * @param params 表的参数名称可以为null
     * @param sortStr 排序字段名称可以为null
     * @return
     * @author lishanglong
     */
    List<LinkedHashMap<String, Object>> queryBySqlParam(@Param("table") String table,
            @Param("params") List<String> params,@Param("sortSrt") String sortStr);
    
    

}
