package com.skyline.wallet.business;

import java.util.List;
import com.skyline.common.business.BaseBusiness;
import com.skyline.common.constant.Result;
import com.skyline.common.entity.Userbalance;
import com.skyline.common.vo.UserbalanceVO;

public interface UserBalanceBusiness extends BaseBusiness<Userbalance, Integer> {
	
	/**
	 * 查询用户币种总资产
	 * @param userId
	 * @return
	 */
	public Result<List<UserbalanceVO>> getUserBalanceList();
	/**
	 * 
	* @Title: getUserBalance
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author xzj
	* @param @return    参数
	* @return Result<UserbalanceVO>    返回类型
	* @throws
	 */
	public Result<Userbalance> getUserBalance(Integer coinId);
	
	/**
	 * 修改用户币种资产
	 * @param entity
	 * @return
	 */
	public Result<?> updateUserBalance(Userbalance entity);
	
	/**
	 * 查询用户币种单笔资产信息
	 * @param entity
	 * @return
	 */
	public Result<Userbalance> querySeTUserbalanceSingle(Userbalance entity);
}
