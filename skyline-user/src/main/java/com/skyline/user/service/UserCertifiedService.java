package com.skyline.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skyline.common.constant.Result;
import com.skyline.common.entity.Bankinfo;
import com.skyline.common.entity.Usercommercial;
import com.skyline.common.entity.Useridentitie;
import com.skyline.user.business.UserCertifiedBusiness;

@RestController
@RequestMapping(value = "userCertified")
public class UserCertifiedService {

    @Autowired
    private UserCertifiedBusiness userCertifiedBusiness;

    /**
     * 
     * <p>
     * Title: realNameCertification
     * </p>
     * <p>
     * Description: 添加实名认证信息
     * </p>
     * 
     * @param useridentitie
     * @return
     */
    @PostMapping(value = "/realNameCertification")
    public Result realNameCertification(@RequestBody Useridentitie useridentitie) {

	return userCertifiedBusiness.realNameCertification(useridentitie);
    };

    /**
     * 
     * <p>
     * Title: certification
     * </p>
     * <p>
     * Description: 认证信息查询
     * </p>
     * 
     * @return
     */
    @PostMapping(value = "/queryCertifiedcation")
    public Result queryCertifiedcation(String name, String idCard, String startTime, String endTime, String userId,
	    String status, @RequestParam int pageSize, @RequestParam int pageNum) {
	return userCertifiedBusiness.queryCertifiedcation(name, idCard, startTime, endTime, userId, status, pageSize,
		pageNum);
    };

    /**
     * 
     * <p>
     * Title: certification
     * </p>
     * <p>
     * Description: 认证审核
     * </p>
     * 
     * @return
     */
    @PostMapping(value = "/updateCertifiedcation")
    public Result updateCertifiedcation(@RequestParam String id, @RequestParam String status,
	    @RequestParam("remark") String remark) {
	return userCertifiedBusiness.updateCertifiedcation(id, status, remark);
    };

    /**
     * 
     * <p>
     * Title: queryBindBankInfo
     * </p>
     * <p>
     * Description:查询用户关联的银行卡类型信息
     * </p>
     * 
     * @param userId
     * @return
     */
    @PostMapping(value = "/queryBindBankInfo")
    public Result queryBindBankInfo(@RequestParam("userId") String userId) {
	return userCertifiedBusiness.queryBindBankInfo(userId);
    };

    /**
     * 
     * <p>
     * Title: bindBank
     * </p>
     * <p>
     * Description: 绑定银行卡信息
     * </p>
     * 
     * @param bankinfo
     * @param payPwd
     * @return
     */
    @PostMapping(value = "/bindBank")
    public Result bindBank(@RequestBody Bankinfo bankinfo, @RequestParam("payPwd") String payPwd) {
	return userCertifiedBusiness.bindBank(bankinfo, payPwd);
    };

    /**
     * 
     * <p>
     * Title: QueryUsercommercial
     * </p>
     * <p>
     * Description:查询商家申请记录
     * </p>
     * 
     * @param usercommercial
     * @return
     */
    @PostMapping(value = "/queryUsercommercial")
    public Result queryUsercommercial(String name, String idCard, String startTime, String endTime, Integer userId,
	    Integer status, @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNum") Integer pageNum) {
	return userCertifiedBusiness.queryUsercommercial(name, idCard, startTime, endTime, userId, status, pageSize,
		pageNum);
    };

    /**
     * 
     * <p>
     * Title: QueryUsercommercial
     * </p>
     * <p>
     * Description:修改商家审核状态
     * </p>
     * 
     * @param usercommercial
     * @return
     */
    @PostMapping(value = "/updateBusinessStatus")
    public Result updateBusinessStatus(@RequestParam("id") Integer id, @RequestParam("status") String status,
	    @RequestParam("remark") String remark) {

	return userCertifiedBusiness.updateBusinessStatus(id, status, remark);
    };

    /**
     * 
     * <p>
     * Title: businessApplication
     * </p>
     * <p>
     * Description:商家申请
     * </p>
     * 
     * @param usercommercial
     * @return
     */
    @PostMapping(value = "/businessApplication")
    public Result businessApplication(@RequestBody Usercommercial usercommercial) {

	return userCertifiedBusiness.businessApplication(usercommercial);
    };

    /**
     * 
     * <p>
     * Title: updatePayWay
     * </p>
     * <p>
     * Description:修改微信或 支付宝绑定信息
     * </p>
     * 
     * @param id
     * @param account
     * @param imgUrl
     * @param payPwd
     * @param type
     * @return
     */
    @PostMapping(value = "/updatePayWay")
    public Result updatePayWay(@RequestParam("id") String id, @RequestParam("account") String account,
	    @RequestParam("imgUrl") String imgUrl, @RequestParam("payPwd") String payPwd,
	    @RequestParam("type") String type) {

	return userCertifiedBusiness.updatePayWay(id, account, imgUrl, payPwd, type);
    }

    /**
     * 
     * <p>
     * Title: updateBankPayWay
     * </p>
     * <p>
     * Description:修改银行卡绑定信息
     * </p>
     * 
     * @param bankinfo
     * @param payPwd
     * @return
     */
    @PostMapping(value = "/updateBankPayWay")
    public Result updateBankPayWay(@RequestBody Bankinfo bankinfo, @RequestParam("payPwd") String payPwd) {
	return userCertifiedBusiness.updateBankPayWay(bankinfo, payPwd);
    }

}
