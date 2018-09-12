package com.skyline.clientService.service;

import java.util.Date;

import org.junit.Test;

public class Etest {
	@Test
	public void Etest(){
		 Date d = new Date();
		 long c= System.currentTimeMillis();
		String str = c+"";
		show(str);
		
	}

	private void show(String mills) {
		Long e12 = 1000000000L;
		Double d = Double.valueOf(mills)/e12;
		
	   System.out.println(d);
	 
	  
	}

}
