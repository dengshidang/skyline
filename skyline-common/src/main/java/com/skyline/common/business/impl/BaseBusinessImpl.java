package com.skyline.common.business.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.skyline.common.business.BaseBusiness;
import com.skyline.common.constant.Constants;
import com.skyline.common.mapper.GenMapper;
import com.skyline.common.util.MyMapper;

import tk.mybatis.mapper.entity.Example;

public class BaseBusinessImpl<T, M> implements BaseBusiness<T, M> {

        @Autowired
        private MyMapper<T> mapper;

        // 扩展
        @Autowired
        private GenMapper genMapper;

        /**
         * 通用方法：根据主键查询
         * 
         * @param id
         * @return
         */
        public T queryById(M id) {
                return this.mapper.selectByPrimaryKey(id);
        }

        /**
         * 通用方法：查询所有数据
         * 
         * @return
         */
        public List<T> queryAll() {
                return this.mapper.select(null);
        }

        /**
         * 通用方法：根据条件查询一条数据
         * 
         * @param record
         * @return
         */
        public T queryOne(T record) {
                return this.mapper.selectOne(record);
        }

        /**
         * 通用方法：根据条件查询返回多条数据
         * 
         * @param record
         * @return
         */
        public List<T> queryListByWhere(T record) {
                return this.mapper.select(record);
        }

        /**
         * 通用方法：模糊查询
         * 
         * @param record
         * @return
         */
        public List<T> queryListByWhereM(Class<T> calzz,String property,String value ) {
                Example example = new Example(calzz);
                // 设置条件
                example.createCriteria().andLike(property, value);
                return this.mapper.selectByExample(example);
        }

        /**
         * 通用方法：PageInfo： mybatis分页助手类
         * 
         * @param pageNum 页码
         * @param pageSize 每页显示数量
         * @param record 查询条件
         * @return 分页之后的数据列表
         */
        public PageInfo<T> queryPageListByWhere(Integer pageNum, Integer pageSize, T record) {
                // 设置分页参数
                PageHelper.startPage((pageNum == null || pageNum == 0) ? Constants.DEFAULT_PAGE_NO : pageNum,
                                pageSize == null ? Constants.DEFAULT_PAGE_SIZE : pageSize);
                List<T> list = this.mapper.select(record);
                return new PageInfo<T>(list);
        }

        /**
         * 通用方法：新增数据
         * 
         * @param t
         * @return 首影响的行数
         */
        public Integer save(T t) {
                return this.mapper.insert(t);
        }

        /**
         * 通用方法：有选择的保存，选择不为null的字段作为插入的字段
         * 
         * @param t
         * @return
         */
        public Integer saveSelective(T t) {
                return this.mapper.insertSelective(t);
        }

        /**
         * 通用方法：根据主键，修改数据
         * 
         * @param t
         * @return 受影响的行数
         */
        public Integer update(T t) {
                return this.mapper.updateByPrimaryKey(t);
        }

        /**
         * 通用方法：根据主键，有选择的修改，选择不为null的字段作为插入的字段
         * 
         * @param t
         * @return
         */
        public Integer updateSelective(T t) {
                return this.mapper.updateByPrimaryKeySelective(t);
        }

        /**
         * 通用方法：根据主键删除数据
         * 
         * @param id
         * @return
         */
        public Integer deleteById(M id) {
                return this.mapper.deleteByPrimaryKey(id);
        }

        /**
         * 通用方法：根据主键做插入或更新
         */
        public Integer saveOrUpdate(T t, M m) {
                if (null == m) {
                        return this.save(t);
                } else {
                        return this.updateSelective(t);
                }
        }

        /**
         * 通用方法： 通用mapper做批量删除
         * 
         * @return 根据多个主键删除数据 (返回受影响的行数)
         * @property 字段名
         * @values 批量删除的数据
         */
        public Integer deleteByIds(Class<T> calzz, String property, List<Object> values) {
        		
        		if (values.size() == 0) {
					return 0;
				}
                // 下面的效果等价于 delete from 表 where id in(1,2,3);
                Example example = new Example(calzz);
                // 设置条件
                example.createCriteria().andIn(property, values);
                return this.mapper.deleteByExample(example);
        }

        /**
         * 通用方法：根据条件删除数据
         * 
         * @param record
         * @return
         */
        public Integer deleteByWhere(T record) {
                return this.mapper.delete(record);
        }

        public List<LinkedHashMap<String, Object>> queryBySql(String sql) {
                return genMapper.queryBySql(sql);
        }

        public PageInfo<LinkedHashMap<String, Object>> queryPageBySql(Integer pageNum, Integer pageSize, String sql) {
                // 设置分页参数
                PageHelper.startPage((pageNum == null || pageNum == 0) ? Constants.DEFAULT_PAGE_NO : pageNum,
                                pageSize == null ? Constants.DEFAULT_PAGE_SIZE : pageSize);
                List<LinkedHashMap<String, Object>> queryBySqlParam = this.queryBySql(sql);
                return new PageInfo<LinkedHashMap<String, Object>>(queryBySqlParam);
        }

        public List<LinkedHashMap<String, Object>> queryBySqlParam(String table, List<String> params, String sortStr) {
                return genMapper.queryBySqlParam(table, params, sortStr);
        }

        public PageInfo<LinkedHashMap<String, Object>> queryPageListBySqlParam(Integer pageNum, Integer pageSize,
                        String table, List<String> param, String sortStr) {
                // 设置分页参数
                PageHelper.startPage((pageNum == null || pageNum == 0) ? Constants.DEFAULT_PAGE_NO : pageNum,
                                pageSize == null ? Constants.DEFAULT_PAGE_SIZE : pageSize);
                List<LinkedHashMap<String, Object>> queryBySqlParam = this.queryBySqlParam(table, param, sortStr);
                return new PageInfo<LinkedHashMap<String, Object>>(queryBySqlParam);
        }

}
