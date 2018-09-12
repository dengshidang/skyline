package com.skyline.service.matching;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyline.common.vo.MessageEntity;
import com.skyline.service.service.ReceiverService;
import com.skyline.service.util.ListSortUtil;

/**
 * 撮合线程
 */
@Service
public class MatchMakingWorker implements InitializingBean {
   @Autowired
    private ReceiverService receiverService;


    volatile AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    private List<MessageEntity> buys = new ArrayList<>();
    private List<MessageEntity> sells = new ArrayList<>();

    private List<MessageEntity> cancleBuys = new ArrayList<>();
    private List<MessageEntity> cancleSelles = new ArrayList<>();

    public MatchMakingWorker() {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
       /**加载数据*/

        execute();
    }
    /**
     * 二十个线程来
     */
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20);


    /**取消撮合*/
    public void cancle(MessageEntity entity) {
    	
       
    }

    /**
     * 自动执行任务
     */
    public void execute() {
        if (!atomicBoolean.getAndSet(true)) {
            receiverService.doExecute(buys,sells);
            
            
          //  buys.removeAll(cancleBuys);
            synchronized (cancleBuys) {
                buys.removeAll(cancleBuys);
                cancleBuys.clear();
            }
			ListSortUtil.sort(buys, false,"price","sendTime");
            synchronized (cancleSelles) {
                sells.removeAll(cancleSelles);
                cancleSelles.clear();
            }
            ListSortUtil.sort(sells, true,"price","sendTime");
            List delBuys = new ArrayList();
            List delSels = new ArrayList();
            
            for (MessageEntity buy : buys) {
                for (MessageEntity sell : sells) {
                	
                }
            }
            sells.removeAll(delSels);
            buys.removeAll(delBuys);
            atomicBoolean.set(false);
        }
    }
    /**
     * 
     */
    public void task() {
    	 BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);
         ThreadPoolExecutor pool = new ThreadPoolExecutor(3, 5, 60, TimeUnit.MICROSECONDS, queue);
    	
    }
    
    

}
