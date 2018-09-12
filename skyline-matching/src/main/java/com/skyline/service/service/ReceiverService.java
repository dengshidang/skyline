package com.skyline.service.service;

import java.util.List;

import com.skyline.common.vo.MessageEntity;

public interface ReceiverService {
	 /**
     * 新增交易
     *
     * @param entity
     */
    public void addItem(MessageEntity entity);

    /**
     * 取消撮合
     * @param id 委托id
     * @param marketId 行情id
     * @param type  0 买入/1 卖出类型
     */
    public void cancle(Integer id,Integer marketId,Integer type);

    /**
     * 对撮合接收到的数据加入撮合线程
     * @param buyList
     * @param sellList
     */
    public void doExecute(List<MessageEntity> buyList,List<MessageEntity> sellList);
}
