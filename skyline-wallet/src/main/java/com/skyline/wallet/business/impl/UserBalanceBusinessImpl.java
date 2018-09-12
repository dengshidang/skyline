package com.skyline.wallet.business.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.skyline.common.business.impl.BaseBusinessImpl;
import com.skyline.common.constant.Result;
import com.skyline.common.entity.Coininfo;
import com.skyline.common.entity.Userbalance;
import com.skyline.common.entity.Userinfo;
import com.skyline.common.util.DateUtil;
import com.skyline.common.vo.UserbalanceVO;
import com.skyline.wallet.business.UserBalanceBusiness;
import com.skyline.wallet.filter.RequestUtil;
import com.skyline.wallet.mapper.CoininfoMapper;
import com.skyline.wallet.mapper.UserBalanceMapper;

@Service
public class UserBalanceBusinessImpl extends BaseBusinessImpl<Userbalance, Integer> implements UserBalanceBusiness {
	
	@Autowired
	private UserBalanceMapper userbalancemapper;
	@Autowired
	private CoininfoMapper coininfoMapper;
	
	@Override
	public Result<List<UserbalanceVO>> getUserBalanceList() {
		Userinfo user=RequestUtil.getCurrentUser();
		List<Coininfo>  coinList= coininfoMapper.selectAll();
		List<UserbalanceVO> list = userbalancemapper.getUserBalanceList(user.getId());
		for (Coininfo coininfo : coinList) {
			int t=0;
			for(UserbalanceVO ub :list) {
				if(coininfo.getId().equals(ub.getCoinId())) {
					t=1;
					break;
				}
			}
			if(t==0) {
				Userbalance item=new Userbalance();
				item.setCoinId(coininfo.getId());
				item.setFrozenNum(0d);
				item.setTotalNum(0d);
				item.setValidNum(0d);
				item.setUserId(user.getId());
				item.setStatus(0);
				String time=DateUtil.getCurTimeString();
				item.setUpdateTime(time);
				item.setCreateTime(time);
				if(userbalancemapper.insert(item)==1) {
					UserbalanceVO vo=new UserbalanceVO(item,coininfo.getName(),0d,0d,0d);
					list.add(vo);
				}
			}
		}
		return Result.successResult(list);
	}
	/**
	 * 
	* @Title: getUserBalance
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author xzj
	* @param @return    参数
	* @return Result<UserbalanceVO>    返回类型
	* @throws
	 */
	public Result<Userbalance> getUserBalance(Integer coinId){
		Userinfo user=RequestUtil.getCurrentUser();
		Userbalance ub=new Userbalance();
		ub.setUserId(user.getId());
		ub.setCoinId(coinId);
		ub=userbalancemapper.selectOne(ub);
		if(ub==null) {
			Userbalance item=new Userbalance();
			item.setCoinId(coinId);
			item.setFrozenNum(0d);
			item.setTotalNum(0d);
			item.setValidNum(0d);
			item.setUserId(user.getId());
			item.setStatus(0);
			String time=DateUtil.getCurTimeString();
			item.setUpdateTime(time);
			item.setCreateTime(time);
			userbalancemapper.insert(item);
			return Result.successResult(item);
		}else {
			return Result.successResult(ub);
		}
	}
	
	@Override
	public Result<?> updateUserBalance(Userbalance entity) {
		userbalancemapper.updateUserBalance(entity);
		 return Result.successResult();
	}

	@Override
	public Result<Userbalance> querySeTUserbalanceSingle(Userbalance entity) {
		Userbalance ubalance=null;
		ubalance=userbalancemapper.querySeTUserbalanceSingle(entity);
		 return Result.successResult(ubalance);
	}
}
