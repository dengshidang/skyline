package com.skyline.common.business;

import java.util.LinkedHashMap;
import java.util.List;

import com.github.pagehelper.PageInfo;

public interface BaseBusiness<T, M> {

        T queryById(M id);

        List<T> queryAll();

        T queryOne(T record);

        List<T> queryListByWhere(T record);

        List<T> queryListByWhereM(Class<T> calzz, String property, String value);

        PageInfo<T> queryPageListByWhere(Integer pageNum, Integer pageSize, T record);

        Integer save(T t);

        Integer saveSelective(T t);

        Integer update(T t);

        Integer updateSelective(T t);

        Integer saveOrUpdate(T t, M m);

        Integer deleteById(M id);

        Integer deleteByIds(Class<T> calzz, String property, List<Object> values);

        Integer deleteByWhere(T record);

        List<LinkedHashMap<String, Object>> queryBySql(String sql);

        PageInfo<LinkedHashMap<String, Object>> queryPageBySql(Integer pageNum, Integer pageSize, String sql);

        List<LinkedHashMap<String, Object>> queryBySqlParam(String table, List<String> params, String sortStr);

        PageInfo<LinkedHashMap<String, Object>> queryPageListBySqlParam(Integer pageNum, Integer pageSize,
                        String table, List<String> param, String sortStr);
}
