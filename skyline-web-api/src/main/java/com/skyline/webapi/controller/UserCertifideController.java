package com.skyline.webapi.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skyline.common.constant.Result;
import com.skyline.common.constant.StatusCode;
import com.skyline.common.constant.SystemConstants;
import com.skyline.common.entity.Bankinfo;
import com.skyline.common.entity.Usercommercial;
import com.skyline.common.entity.Useridentitie;
import com.skyline.common.util.ObjectTool;
import com.skyline.webapi.service.UserCertifiedService;


/**
 * 
* <p>Title: UserCertifideController</p>  
* <p>Description: 用户认证</p>  
* @author kuangwenqiang  
* @date 2018年7月21日
 */
@RestController
@RequestMapping(value="userCertified")
public class UserCertifideController {
	
	@Autowired
	  private UserCertifiedService userCertifiedService;              
	
	/**
	 * <p>Title: realNameCertification</p>  
	 * <p>Description: 提交实名认证信息</p>  
	 * @return
	 */
	@PostMapping(value="/realNameCertification")
	public  Result realNameCertification(Useridentitie useridentitie){
		if(ObjectTool.isBlank(useridentitie.getUserId(),useridentitie.getIdcardImg1(),
				useridentitie.getIdcardImg2(),useridentitie.getIdcardImg3(),useridentitie.getIdCard(),useridentitie.getType())){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return userCertifiedService.realNameCertification(useridentitie);
	};
	/**
	 * 
	 * <p>Title: queryCertifiedcation</p>  
	 * <p>Description: 查询用户认证信息</p>  
	 * @param userId
	 * @return
	 */
	@PostMapping(value ="/queryCertifiedcation")
	public Result  queryCertifiedcation(String userId,String status,@RequestParam int  pageSize,@RequestParam int pageNum){
		if(ObjectTool.isBlank(pageSize,pageNum)){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return userCertifiedService.queryCertifiedcation(userId,status, pageSize, pageNum);
	}
	
	
	/**
	 * 
	 * <p>Title: queryCertifiedcation</p>  
	 * <p>Description: 修改用户认证状态</p>  
	 * @param userId
	 * @return 
	 */
	@PostMapping(value ="/updateCertification")
	public Result  updateCertifiedcation(@RequestParam("id") String id,@RequestParam("status") String status,@RequestParam("remark") String remark){
		if(ObjectTool.isBlank(id,status)){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return userCertifiedService.updateCertifiedcation(id, status,remark);
	}
	
	
	/**
	 * 
	 * <p>Title: queryBindBankInfo</p>  
	 * <p>Description: 查询用户所绑定的银行卡信息</p>  
	 * @param userId
	 * @return
	 */
	@PostMapping(value="/queryBindBankInfo")
	public Result queryBindBankInfo(@RequestParam("userId") String userId){
		if(ObjectTool.isBlank(userId)){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return userCertifiedService.queryBindBankInfo(userId);
	};
	/**
	 * 
	 * <p>Title: bindBank</p>  
	 * <p>Description:绑定银行卡信息 </p>  
	 * @param bankinfo
	 * @param payPwd
	 * @return
	 */
	@PostMapping(value="/bindBank")
	public Result bindBank(Bankinfo  bankinfo,String payPwd){
		if(ObjectTool.isBlank(bankinfo.getType(),bankinfo.getUserId(),payPwd,bankinfo.getAccount())){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		if(StringUtils.equals(bankinfo.getType()+"", SystemConstants.BIND_BANK_TYPE_0)||
				StringUtils.equals(bankinfo.getType()+"", SystemConstants.BIND_BANK_TYPE_1)
				){
			if(ObjectTool.isBlank(bankinfo.getImgUrl())){
				return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
			}
			}
		if(StringUtils.equals(bankinfo.getType()+"", SystemConstants.BIND_BANK_TYPE_2)){
			if(ObjectTool.isBlank(bankinfo.getBankName(),bankinfo.getName(),bankinfo.getAddress())){
				return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
			}
		}
		return userCertifiedService.bindBank(bankinfo, payPwd);
	}

	/**
	 * 
	 * <p>Title: updateBankPayWay</p>  
	 * <p>Description:修改银行卡绑定信息 </p>  
	 * @param bankinfo
	 * @param payPwd
	 * @return
	 */
	@PostMapping(value="/updateBankPayWay")
	public Result updateBankPayWay(Bankinfo  bankinfo,String payPwd){
		if(ObjectTool.isBlank(bankinfo.getId(),bankinfo.getName(),bankinfo.getAccount(),bankinfo.getType(),
				payPwd,bankinfo.getBankName(),bankinfo.getAddress())){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return userCertifiedService.updateBankPayWay(bankinfo, payPwd);
	}
	
	
	/**
	 * 
	 * <p>Title: updatePayWay</p>  
	 * <p>Description:修改微信或 支付宝绑定信息 </p>  
	 * @param id
	 * @param account
	 * @param imgUrl
	 * @param payPwd
	 * @param type
	 * @return
	 */
	@PostMapping(value="/updatePayWay")
	public Result updatePayWay(@RequestParam("id")String id,@RequestParam("account")String account,@RequestParam("imgUrl")String imgUrl,
			@RequestParam("payPwd")String payPwd,@RequestParam("type") String type){
		if(ObjectTool.isBlank(id,account,imgUrl,payPwd,type)){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return userCertifiedService.updatePayWay(id, account, imgUrl, payPwd, type);
	}
	
	
	/**
	 * 
	 * <p>Title: QueryUsercommercial</p>  
	 * <p>Description:查询商家申请记录 </p>  
	 * @param usercommercial
	 * @return
	 */
	@PostMapping(value="/queryUsercommercial")
	public Result queryUsercommercial(String startTime,String endTime,Integer userId,Integer status,
			@RequestParam("pageSize")Integer pageSize,@RequestParam("pageNum") Integer pageNum){
		if(ObjectTool.isBlank(pageSize,pageNum)){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return userCertifiedService.queryUsercommercial(startTime,endTime,userId,status,pageSize, pageNum);
		
	}
	
	
	/**
	 * 
	 * <p>Title: QueryUsercommercial</p>  
	 * <p>Description:修改商家审核状态 </p>  
	 * @param usercommercial
	 * @return
	 */
	@PostMapping(value="/updateBusinessStatus")
	public Result updateBusinessStatus(@RequestParam("id") Integer id,@RequestParam("status") String status,@RequestParam("remark") String remark){
		if(ObjectTool.isBlank(id,status)){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return userCertifiedService.updateBusinessStatus(id, status, remark);
	}
	/**
	 * 
	 * <p>Title: businessApplication</p>  
	 * <p>Description:商家申请 </p>  
	 * @param usercommercial
	 * @return
	 */
	@PostMapping(value="/businessApplication")
	public Result businessApplication(@RequestBody Usercommercial usercommercial){
		return userCertifiedService.businessApplication(usercommercial);
	}

}
