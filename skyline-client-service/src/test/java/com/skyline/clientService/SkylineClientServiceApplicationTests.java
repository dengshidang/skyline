package com.skyline.clientService;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;


public class SkylineClientServiceApplicationTests {

	/*@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void post() throws Exception {
		//添加委托消息100条,10000条数据跑不动
		for(int i = 0;i<100;i++){
			String type = (Math.random()>=0.5?1:0)+"";
		    String price = Math.random()*5+1+"";
		    String number = Math.random()*4+1+"";
		    String marketId = (Math.random()>=0.5?8:14)+"";
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost("http://172.16.2.38:9001/trade/addTradeEntrust");

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("userId","1026"));
			params.add(new BasicNameValuePair("type",type));
			params.add(new BasicNameValuePair("price",price));
			params.add(new BasicNameValuePair("number",number));
			params.add(new BasicNameValuePair("marketId",marketId));
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(params,"UTF-8");
			httppost.setEntity(uefEntity);
			CloseableHttpResponse response = httpClient.execute(httppost);
			response.close();
			httpClient.close();
			Thread.sleep(1000);
		}
	}

*/
}
