package com.skyline.user.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyline.common.constant.Result;
import com.skyline.common.entity.LoginHistory;
import com.skyline.user.business.LoginHistoryBusiness;
import com.skyline.user.mapper.LoginHistoryMapper;

@Service
public class LoginHistoryBusinessImpl  implements LoginHistoryBusiness{

	
	@Autowired
	private LoginHistoryMapper loginHistoryMapper;
	
	@Override
	public Result queryLoginHistory(String userId) {
		List<LoginHistory> list =  loginHistoryMapper.queryLoginHistory(userId);
		if(list==null||list.size()==0){
			return Result.successResult();
		}
		// TODO Auto-generated method stub
		return Result.successResult(list);
	}

}
