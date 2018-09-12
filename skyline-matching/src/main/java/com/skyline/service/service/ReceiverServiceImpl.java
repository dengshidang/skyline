package com.skyline.service.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyline.common.constant.TradeConstant;
import com.skyline.common.vo.MessageEntity;
import com.skyline.service.matching.MatchMakingWorker;
/**
 * 撮合接收通知服务
 * @author dengshidang
 *
 */
@Service
public class ReceiverServiceImpl implements ReceiverService{
	public static Map<Integer, ReceiverServiceImpl> queueMap = new HashMap<Integer, ReceiverServiceImpl>();
	List<MessageEntity> sells = new ArrayList<>();

	List<MessageEntity> buys = new ArrayList<>();
	@Autowired
	private MatchMakingWorker matchMakingWorker;
	/**
	 * 新增撮合
	 */
	@Override
	public void addItem(MessageEntity entity) {
		/*MessageList messageList = QueueUtil .queueMap.get(entity.getMarketId());
		if(messageList == null){
			messageList = new MessageList();
			
			QueueUtil.queueMap.put(entity.getMarketId(), messageList);
			
		}*/

		if(entity.getType() == TradeConstant.TRADETYPE_BUY){
			buys.add(entity);

		}else if(entity.getType() == TradeConstant.TRADETYPE_SELL){
			sells.add(entity);
		}
		matchMakingWorker.execute();
	}
	/**
	 * 取消撮合
	 */
	@Override
	public void cancle(Integer id, Integer marketId, Integer type) {
		// TODO Auto-generated method stub

	}
	/**
	 * 撮合队列加入撮合线程
	 */
	@Override
	public void doExecute(List<MessageEntity> buyList, List<MessageEntity> sellList) {
		synchronized (sells) {
			sellList.addAll(sells);
			sells.clear();
			synchronized (buys) {
				buyList.addAll(buys);
				buys.clear();
			}
		}

	}

}
