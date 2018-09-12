package com.skyline.service.handler;



import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.skyline.common.vo.MessageEntity;
import com.skyline.service.mapper.SeTTradeinfoMapper;

/**
 * 初始化方案 1 ;程序启动，加载用户委托数据到mq队列 (多个节点注意只保留一个加载节点)		
 * @author dengshidang
 *
 */
@WebListener
public class TradeListening implements ServletContextListener{
	Logger logger = Logger.getLogger(TradeListening.class);
	@Autowired
	private AmqpTemplate  rabbitTemplate;/** rabbitMQ操作对象 **/
	@Autowired
	private SeTTradeinfoMapper seTTradeinfoMapper;
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
		
	}

	@Override
	@Async	
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("The smart stamp and transaction queue information are initialized:---------------------");
		//消息队列信息推送

		List<MessageEntity> entities = seTTradeinfoMapper.queryTradeEnstrut();
		int count=0;
		if(entities!=null && !entities.isEmpty()){
			for (MessageEntity entity :entities) {
				if(entity!=null){
					//消息队列信息推送
					rabbitTemplate.convertAndSend(RabbitConfig.exchange,RabbitConfig.binding,entity);
					count++;
				}
			}
		}
		logger.info("Successful program startup!Total load :"+count+" Article delegate data...list:"+entities);
		
		
		
		/*初始化方案 2：存在问题，添加节点时，缓存map不能数据共享
		 * List<MessageEntity> querbuy = seTTradeinfoMapper.queryTradeMessage(TradeConstant.TRADETYPE_BUY);
		 * List<MessageEntity> querysell = seTTradeinfoMapper.queryTradeMessage(TradeConstant.TRADETYPE_SELL);
		//买方列表
		if(querybuy!=null&&!querybuy.isEmpty()){
			for (MessageEntity entity:querybuy){				
				if(QueueUtil.queueMap.containsKey(entity.getMarketId())){
					MessageList messageList = QueueUtil.queueMap.get(entity.getMarketId());
					List<MessageEntity> buys = messageList.getBuyList();
					if(buys==null){
						messageList.setBuyList(new ArrayList<MessageEntity>());
						buys = messageList.getBuyList() ;
					}
					buys.add(entity);
				}else{
					QueueUtil.queueMap.put(entity.getMarketId(),new MessageList());
					MessageList messageList = QueueUtil.queueMap.get(entity.getMarketId());
					List<MessageEntity> buys = messageList.getBuyList();
					if(buys==null){
						messageList.setBuyList(new ArrayList<MessageEntity>());
						buys = messageList.getBuyList() ;
					}
					buys.add(entity);
				}
			}
		}
		//卖方列表
		if(querysell!=null&&!querysell.isEmpty()){
			for (MessageEntity entity:querysell){				
				if(QueueUtil.queueMap.containsKey(entity.getMarketId())){
					MessageList messageList = QueueUtil.queueMap.get(entity.getMarketId());
					List<MessageEntity>sells = messageList.getBuyList();
					if(sells==null){
						messageList.setBuyList(new ArrayList<MessageEntity>());
						sells=messageList.getSellList();
					}
					sells.add(entity);
				}else{
					QueueUtil.queueMap.put(entity.getMarketId(),new MessageList());

					MessageList messageList = QueueUtil.queueMap.get(entity.getMarketId());
					List<MessageEntity> sells = messageList.getBuyList();
					if(sells==null){
						messageList.setBuyList(new ArrayList<MessageEntity>());
						sells=messageList.getSellList();
					}
					sells.add(entity);
				}
			}

		}*/

	}


}
