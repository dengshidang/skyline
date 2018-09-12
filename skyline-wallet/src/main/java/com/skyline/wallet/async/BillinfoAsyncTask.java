package com.skyline.wallet.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.skyline.common.entity.Billinfo;
import com.skyline.wallet.mapper.BillinfoMapper;

@Component
public class BillinfoAsyncTask {

	@Autowired
	BillinfoMapper billinfoMapper;
	
    protected static Logger logger = LoggerFactory.getLogger(BillinfoAsyncTask.class);
    @Async
    public void save(Billinfo  Billinfo){
        logger.info("son"+Thread.currentThread());
        billinfoMapper.insert(Billinfo);
    }
}

