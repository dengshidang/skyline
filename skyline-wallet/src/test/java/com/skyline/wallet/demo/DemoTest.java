package com.skyline.wallet.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.web3j.utils.Convert;

import com.skyline.common.constant.Result;
import com.skyline.common.entity.CointransactionEntity;
import com.skyline.wallet.SkylineWalletApplication;
import com.skyline.wallet.business.WalletBusiness;

import net.sf.json.JSONObject;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=SkylineWalletApplication.class) //加载应用程序上下文
public class DemoTest {

	@Autowired
	private WalletBusiness walletBusiness;
	
	@Test
	public void test(){
		
		CointransactionEntity entity = new CointransactionEntity();
		entity.setCoinName("BT");
		Result<?> a = walletBusiness.getWalletAddress(5);
		System.out.println(JSONObject.fromObject(a));
	}
	
	
}
