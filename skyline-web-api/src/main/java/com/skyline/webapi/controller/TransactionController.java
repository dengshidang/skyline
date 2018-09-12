package com.skyline.webapi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.skyline.common.constant.Constants;
import com.skyline.common.constant.Result;
import com.skyline.common.constant.StatusCode;
import com.skyline.common.entity.Bankinfo;
import com.skyline.common.entity.Transaction;
import com.skyline.common.entity.Userinfo;
import com.skyline.common.util.ObjectTool;
import com.skyline.common.util.StringTool;
import com.skyline.common.vo.TransactionVO;
import com.skyline.webapi.annotation.NotLogin;
import com.skyline.webapi.filter.RequestUtil;
import com.skyline.webapi.from.TransactionFrom;
import com.skyline.webapi.service.TransactionService;
import com.skyline.webapi.service.UserinfoService;
@RestController
@RequestMapping("transaction")
public class TransactionController {
		
	@Autowired 
	TransactionService transactionService;
	@Autowired
	UserinfoService  userinfoService;
	/**
	 * 
	* @Title: release
	* @Description: TODO(发布c2c交易)
	* @param @param transaction
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	@PostMapping(value= "/release")
	public Result<?> release(TransactionFrom  transactionFrom){
		if(ObjectTool.isBlank(transactionFrom.getMax(),transactionFrom.getMin(),
				transactionFrom.getPayWay(),transactionFrom.getPrice(),
				transactionFrom.getTransactiontypeId(),transactionFrom.getTotalNum())){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		Transaction transaction=new Transaction();
		BeanUtils.copyProperties(transactionFrom,transaction);

		return transactionService.release(transaction);
	}
	
	/**
	 * 
	* @Title: uploadTransaction
	* @Description: TODO(修改订单)
	* @author xzj
	* @param @param transaction
	* @param @return    参数
	* @return Result<?>    返回类型
	* @throws
	 */
	@PostMapping(value= "/updateTransaction")
	public Result<?> updateTransaction(TransactionFrom  transactionFrom){
		if(ObjectTool.isBlank(transactionFrom.getId(),transactionFrom.getMax(),transactionFrom.getMin(),
				transactionFrom.getPayWay(),transactionFrom.getPrice(),
				transactionFrom.getTransactiontypeId(),transactionFrom.getTotalNum())){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		Transaction transaction=new Transaction();
		BeanUtils.copyProperties(transactionFrom,transaction);
		return transactionService.updateTransaction(transaction);
	};
	/**
	 * 
	* @Title: cancel
	* @Description: TODO(撤销c2c交易)
	* @param @param id
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	@PostMapping(value= "/cancel")
	public Result<?> cancel(Integer transactionId,HttpServletRequest request){
		if(transactionId==null){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return transactionService.cancel(transactionId);
	}
	/**
	 * 
	* @Title: findById
	* @Description: TODO(查询单条c2c交易信息)
	* @param @param id
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	@GetMapping(value= "/findById")
	public Result<TransactionVO> findById(Integer id){
		if(id==null){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return transactionService.findById(id);
	}
	/**
	 * 
	* @Title: getTransactiontList
	* @Description: TODO(查询c2c列表)
	* @param @param tansactiontypeId
	* @param @param pageNum
	* @param @param pageSize
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	@NotLogin
	@GetMapping(value= "/getTransactionList")
	public Result<PageInfo<TransactionVO>> getTransactionList(Integer tansactiontypeId, Integer pageNum,
            	Integer pageSize,String  payWay,Double money){
		if(ObjectTool.isBlank(tansactiontypeId,pageNum,pageSize)){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return transactionService.getTransactionList(tansactiontypeId, pageNum, pageSize,payWay,money);
	}
	
	 /**
	  * 
	 * @Title: getTransactionListByUser
	 * @Description: TODO(查询各人发布的广告)
	 * @author xzj
	 * @param @return    参数
	 * @return Result    返回类型
	 * @throws
	  */
	@GetMapping(value= "/getTransactionListByUser")
	 public Result<PageInfo<TransactionVO>> getTransactionListByUser(Integer pageNum, Integer pageSize,
			 String startTime,String endTime,Integer coinId) {
		if(ObjectTool.isBlank(pageNum,pageSize)){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		} 
		return transactionService.getTransactionListByUser(pageNum, pageSize, startTime, endTime, coinId);
	}

	
	 /**
	  * 
	 * @Title: getBankinfoByUser
	 * @Description: TODO(查询广告可支付方式)
	 * @author xzj
	 * @param @return    参数
	 * @return Result<Bankinfo>    返回类型
	 * @throws
	  */
	@GetMapping(value= "/getBankinfoByUser")
	 public Result<List<Bankinfo>>  getBankinfoByUser(){
		return transactionService.getBankinfoByUser();
	}
	
	@GetMapping(value= "/getBankinfoByTransactionNo")
	 public Result<List<Bankinfo>>  getBankinfoByTransactionNo(@RequestParam("transactionNo")String transactionNo){
		return transactionService.getBankinfoByTransactionNo(transactionNo);
	}
	
	/**
	  * 
	 * @Title: saveBankinfo
	 * @Description: TODO(添加支付方式)
	 * @author xzj
	 * @param @return    参数
	 * @return Result<?>    返回类型
	 * @throws
	  */
	@PostMapping(value= "/saveBankinfo")
	 public Result<?>  saveBankinfo(Bankinfo bank,String payPwd){
		 if(ObjectTool.isBlank(bank.getType(),payPwd)) {
				return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		 }
		 Result<?> result= userinfoService.checkPayPwd(payPwd);
		 if(!StatusCode.SUCCESS.getCode().equals(result.getCode())) {
				return result;
		 }
		 if(bank.getType()==Constants.BANK_INFO_TYPE_0 ||bank.getType()==Constants.BANK_INFO_TYPE_1) {
			 if(ObjectTool.isBlank(bank.getAccount(),bank.getName(),bank.getImgUrl())) {
					return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
			 }
		 }else {
			 if(ObjectTool.isBlank(bank.getAccount(),bank.getName(),bank.getBankName(),bank.getAddress())) {
					return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
			 }
		 }
		 return transactionService.saveBankinfo(bank);
	 }
}
