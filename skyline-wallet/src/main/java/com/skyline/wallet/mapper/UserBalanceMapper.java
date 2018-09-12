package com.skyline.wallet.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.skyline.common.entity.Userbalance;
import com.skyline.common.util.MyMapper;
import com.skyline.common.vo.UserbalanceVO;

public interface UserBalanceMapper extends MyMapper<Userbalance>{
	
	/**
	 * 查询用户的余额信息
	 * @param seTUserbalanceEntity
	 * @return
	 */
	public Userbalance queryUserBalance(Userbalance userbalance);
	
	public UserbalanceVO getUserBalance(@Param("userId")Integer userId ,@Param("coinId") Integer coinId);

	public List<UserbalanceVO> getUserBalanceList(@Param("userId")Integer userId);
	
	public void updateUserBalance(Userbalance entity);
	
	public Userbalance querySeTUserbalanceSingle(Userbalance entity);
}
