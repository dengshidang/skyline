package com.skyline.user.business.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.skyline.common.business.impl.BaseBusinessImpl;
import com.skyline.common.constant.AdditionalFunctionStatusCode;
import com.skyline.common.constant.Constants;
import com.skyline.common.constant.Result;
import com.skyline.common.constant.SystemConstants;
import com.skyline.common.entity.Bankinfo;
import com.skyline.common.entity.Usercommercial;
import com.skyline.common.entity.UsercommercialResult;
import com.skyline.common.entity.Useridentitie;
import com.skyline.common.entity.Userinfo;
import com.skyline.common.util.CryptUtil;
import com.skyline.common.util.DateUtil;
import com.skyline.common.util.ImgSplitUrlUtil;
import com.skyline.common.util.ObjectTool;
import com.skyline.user.business.UserCertifiedBusiness;
import com.skyline.user.config.FileInitConfig;
import com.skyline.user.exception.BusinessException;
import com.skyline.user.mapper.UserCertifiedMapper;
import com.skyline.user.mapper.UseridentitieMapper;
import com.skyline.user.mapper.UserinfoMapper;

/**
 * 
 * <p>
 * Title: UserCertifiedBusinessImpl
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author kuangwenqiang
 * @date 2018年7月21日
 */
@Service
@Transactional
public class UserCertifiedBusinessImpl extends BaseBusinessImpl<Bankinfo, Integer> implements UserCertifiedBusiness {

    public static Logger logger = Logger.getLogger(UserCertifiedBusinessImpl.class);

    @Autowired
    private UserCertifiedMapper userCertifiedMappder;
    @Autowired
    private UserinfoMapper userinfoMapper;

    @Autowired
    private UseridentitieMapper useridentitieMapper;

    @Autowired
    private FileInitConfig fileInitConfig;

    /*
     * @Autowired private BankinfoMapper bankinfoMapper;
     */

    @Override
    public Result realNameCertification(Useridentitie useridentitie) {
	Useridentitie user = new Useridentitie();
	user.setUserId(useridentitie.getUserId());
	user.setStatus(SystemConstants.CERTIFIEDCATION_TYPE_0);
	int num = useridentitieMapper.selectCount(user);
	if (num > 0) {
	    throw new BusinessException(AdditionalFunctionStatusCode.E316.getCode(),
		    AdditionalFunctionStatusCode.E316.getMsg());
	}
	user.setStatus(SystemConstants.CERTIFIEDCATION_TYPE_2);
	List<Useridentitie> lt = useridentitieMapper.select(user);
	if (lt.size() > 0) {
	    for (Useridentitie userident : lt) {
		userident.setSign(1);
		useridentitieMapper.updateByPrimaryKey(userident);
	    }
	}
	useridentitie.setIdcardImg1(ImgSplitUrlUtil.splitUrl(useridentitie.getIdcardImg1()));
	useridentitie.setIdcardImg2(ImgSplitUrlUtil.splitUrl(useridentitie.getIdcardImg2()));
	useridentitie.setIdcardImg3(ImgSplitUrlUtil.splitUrl(useridentitie.getIdcardImg3()));
	useridentitie.setStatus(SystemConstants.CERTIFIEDCATION_TYPE_0);
	useridentitie.setCreateTime(DateUtil.getNowDate());
	useridentitie.setSign(0);
	useridentitieMapper.insert(useridentitie);
	return Result.successResult();
    }

    @Override
    public Result queryCertifiedcation(String name, String idCard, String startTime, String endTime, String userId,
	    String status, int pageSize, int pageNum) {
	String message = "查询认证信息";
	try {
	    // 使用分页插件,核心代码就这一行
	    PageHelper.startPage(pageNum, pageSize);
	    List<Useridentitie> list = userCertifiedMappder.queryCertifiedcation(name,

		    idCard, startTime, endTime, status, userId);
	    PageInfo<Useridentitie> page = new PageInfo<Useridentitie>(list);
	    return Result.successResult(page);
	} catch (Exception e) {
	    logger.info(message + "失败!");
	    throw new BusinessException(AdditionalFunctionStatusCode.E301.getCode(),
		    AdditionalFunctionStatusCode.E301.getMsg());
	}
    }

    @Override
    public Result updateCertifiedcation(String id, String status, String remark) {
	String userId = userCertifiedMappder.queryUserIdByid(id);
	if (ObjectTool.isBlank(userId)) {
	    throw new BusinessException(AdditionalFunctionStatusCode.E307.getCode(),
		    AdditionalFunctionStatusCode.E307.getMsg());
	}
	// 审核通过
	if (StringUtils.equals(status, SystemConstants.CERTIFIEDCATION_TYPE_1 + "")) {
	    userCertifiedMappder.updateCertifiedcation(id, status, remark);
	    userinfoMapper.updateIdSign(userId, SystemConstants.USERINFO_ISSIGN_1 + "");
	} else if (StringUtils.equals(status, SystemConstants.CERTIFIEDCATION_TYPE_2 + "")) {
	    if (ObjectTool.isBlank(remark)) {
		throw new BusinessException(AdditionalFunctionStatusCode.E309.getCode(),
			AdditionalFunctionStatusCode.E309.getMsg());
	    }
	    userCertifiedMappder.updateCertifiedcation(id, status, remark);
	} else {
	    throw new BusinessException(AdditionalFunctionStatusCode.E309.getCode(),
		    AdditionalFunctionStatusCode.E309.getMsg());
	}
	return Result.successResult();
    }

    @Override
    public Result queryBindBankInfo(String userId) {
	List<Bankinfo> list_bankInfo = userCertifiedMappder.queryBindBankInfo(userId);
	if (list_bankInfo == null || list_bankInfo.size() == 0) {
	    return Result.successResult();
	}
	return Result.successResult(list_bankInfo);
    }

    @Override
    public Result bindBank(Bankinfo bankinfo, String payPwd) {
	String pwd = userinfoMapper.isSetPayPwd(bankinfo.getUserId() + "");
	if (ObjectTool.isBlank(pwd)) {
	    return Result.errorResult(AdditionalFunctionStatusCode.E303.getCode(),
		    AdditionalFunctionStatusCode.E303.getMsg());
	}
	if (!CryptUtil.crypt(payPwd, "MD5").equals(pwd)) {
	    return Result.errorResult(AdditionalFunctionStatusCode.E304.getCode(),
		    AdditionalFunctionStatusCode.E304.getMsg());
	}
	// 通过userid 查询实名认证表
	Useridentitie useridentitie = new Useridentitie();
	useridentitie.setUserId(bankinfo.getUserId());
	useridentitie.setStatus(SystemConstants.CERTIFIEDCATION_TYPE_1);
	useridentitie = useridentitieMapper.selectOne(useridentitie);

	if (useridentitie == null || ObjectTool.isBlank(useridentitie.getName())) {
	    return Result.errorResult(AdditionalFunctionStatusCode.E322.getCode(),
		    AdditionalFunctionStatusCode.E322.getMsg());
	}
	Bankinfo info = new Bankinfo();
	info.setType(bankinfo.getType());
	info.setUserId(bankinfo.getUserId());
	int count = userCertifiedMappder.selectCount(info);
	if (count > 0) {
	    if (StringUtils.equals(SystemConstants.BIND_BANK_TYPE_0, bankinfo.getType() + "")) {
		throw new BusinessException(AdditionalFunctionStatusCode.E318.getCode(),
			AdditionalFunctionStatusCode.E318.getMsg());
	    }
	    if (StringUtils.equals(SystemConstants.BIND_BANK_TYPE_1, bankinfo.getType() + "")) {
		throw new BusinessException(AdditionalFunctionStatusCode.E319.getCode(),
			AdditionalFunctionStatusCode.E319.getMsg());
	    }
	    if (StringUtils.equals(SystemConstants.BIND_BANK_TYPE_2, bankinfo.getType() + "")) {
		throw new BusinessException(AdditionalFunctionStatusCode.E320.getCode(),
			AdditionalFunctionStatusCode.E320.getMsg());
	    }
	}
	// 微信,支付宝
	if (StringUtils.equals(SystemConstants.BIND_BANK_TYPE_0, bankinfo.getType() + "")
		|| StringUtils.equals(SystemConstants.BIND_BANK_TYPE_1, bankinfo.getType() + "")) {

	    int result = userCertifiedMappder.bindBank(bankinfo);
	    System.out.println("==================>" + result);
	} else if (StringUtils.equals(SystemConstants.BIND_BANK_TYPE_2, bankinfo.getType() + "")) {

	    if (!StringUtils.equals(useridentitie.getName(), bankinfo.getName())) {
		return Result.errorResult(AdditionalFunctionStatusCode.E306.getCode(),
			AdditionalFunctionStatusCode.E306.getMsg());
	    }
	    int result = userCertifiedMappder.bindBank(bankinfo);
	    System.out.println("==================>" + result);
	} else {
	    return Result.errorResult(AdditionalFunctionStatusCode.E308.getCode(),
		    AdditionalFunctionStatusCode.E308.getMsg());
	}
	// TODO Auto-generated method stub
	return Result.successResult();
    }

    @Override
    public Result queryUsercommercial(String name, String idCard, String startTime, String endTime, Integer userId,
	    Integer status, Integer pageSize, Integer pageNum) {
	PageHelper.startPage((pageNum == null || pageNum == 0) ? Constants.DEFAULT_PAGE_NO : pageNum,
		pageSize == null ? Constants.DEFAULT_PAGE_SIZE : pageSize);
	// TODO Auto-generated method stub
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("startTime", startTime);
	map.put("endTime", endTime);
	map.put("userId", userId);
	map.put("status", status);
	map.put("name", name);
	map.put("idCard", idCard);
	List<UsercommercialResult> list_usercommercial = userCertifiedMappder.QueryUsercommercial(map);
	PageInfo<UsercommercialResult> page = new PageInfo<UsercommercialResult>(list_usercommercial);
	return Result.successResult(page);
    }

    @Override
    public Result updateBusinessStatus(Integer id, String status, String remark) {
	String userId = userCertifiedMappder.queryMctIdByid(id + "");
	if (ObjectTool.isBlank(userId)) {
	    throw new BusinessException(AdditionalFunctionStatusCode.E307.getCode(),
		    AdditionalFunctionStatusCode.E307.getMsg());
	}
	// 审核通过
	if (StringUtils.equals(status, SystemConstants.CERTIFIEDCATION_TYPE_1 + "")) {
	    userCertifiedMappder.updateBusinessStatus(id + "", status, remark);
	    userinfoMapper.updateMctSign(userId, SystemConstants.USERINFO_ISSIGN_1 + "");
	} else if (StringUtils.equals(status, SystemConstants.CERTIFIEDCATION_TYPE_2 + "")) {
	    if (ObjectTool.isBlank(remark)) {
		throw new BusinessException(AdditionalFunctionStatusCode.E310.getCode(),
			AdditionalFunctionStatusCode.E310.getMsg());
	    }
	    userCertifiedMappder.updateBusinessStatus(id + "", status, remark);
	} else {
	    throw new BusinessException(AdditionalFunctionStatusCode.E309.getCode(),
		    AdditionalFunctionStatusCode.E309.getMsg());
	}
	return Result.successResult();
    }

    @Override
    public Result businessApplication(Usercommercial usercommercial) {
	Integer userId = usercommercial.getUserId();
	// 根据用户ID 查询 userinfo 表 用户是否已经通过实名认证，只有通过了实名认证的用户才能申请商户
	Userinfo userInfo = userinfoMapper.queryUserInfoById(userId);
	// 判断用户实名认证是否已经通过
	if (userInfo == null || userInfo.getMctSign() == null || userInfo.getMctSign() != 1) {
	    throw new BusinessException(AdditionalFunctionStatusCode.E314.getCode(),
		    AdditionalFunctionStatusCode.E314.getMsg());
	}
	userCertifiedMappder.businessApplication(usercommercial);
	return Result.successResult();
    }

    @Override
    public Result updatePayWay(String id, String account, String imgUrl, String payPwd, String type) {
	Bankinfo bankinfo = userCertifiedMappder.selectByPrimaryKey(Integer.parseInt(id));
	if (bankinfo == null) {
	    throw new BusinessException(AdditionalFunctionStatusCode.E315.getCode(),
		    AdditionalFunctionStatusCode.E315.getMsg());
	}
	Userinfo userInfo = userinfoMapper.queryUserInfoById(bankinfo.getUserId());
	if (userInfo == null) {
	    throw new BusinessException(AdditionalFunctionStatusCode.E307.getCode(),
		    AdditionalFunctionStatusCode.E307.getMsg());
	}
	if (!StringUtils.equals(CryptUtil.crypt(payPwd, "MD5"), userInfo.getPayPwd())) {

	    throw new BusinessException(AdditionalFunctionStatusCode.E304.getCode(),
		    AdditionalFunctionStatusCode.E304.getMsg());
	}
	bankinfo.setImgUrl(imgUrl);
	bankinfo.setType(Integer.parseInt(type));
	bankinfo.setAccount(account);
	userCertifiedMappder.updateByPrimaryKey(bankinfo);
	// TODO Auto-generated method stub
	return Result.successResult();
    }

    @Override
    public Result updateBankPayWay(Bankinfo bankinfo, String payPwd) {
	Bankinfo info = userCertifiedMappder.selectByPrimaryKey(bankinfo.getId());
	if (info == null) {
	    throw new BusinessException(AdditionalFunctionStatusCode.E315.getCode(),
		    AdditionalFunctionStatusCode.E315.getMsg());
	}
	String pwd = userinfoMapper.isSetPayPwd(info.getUserId() + "");
	if (ObjectTool.isBlank(pwd)) {
	    throw new BusinessException(AdditionalFunctionStatusCode.E303.getCode(),
		    AdditionalFunctionStatusCode.E303.getMsg());
	}
	if (!CryptUtil.crypt(payPwd, "MD5").equals(pwd)) {
	    throw new BusinessException(AdditionalFunctionStatusCode.E304.getCode(),
		    AdditionalFunctionStatusCode.E304.getMsg());
	}
	// 通过userid 查询实名认证表
	Useridentitie useridentitie = new Useridentitie();
	useridentitie.setUserId(bankinfo.getUserId());
	useridentitie.setStatus(SystemConstants.CERTIFIEDCATION_TYPE_1);
	useridentitie = useridentitieMapper.selectOne(useridentitie);

	if (useridentitie == null || ObjectTool.isBlank(useridentitie.getName())) {
	    return Result.errorResult(AdditionalFunctionStatusCode.E322.getCode(),
		    AdditionalFunctionStatusCode.E322.getMsg());
	}

	if (!StringUtils.equals(useridentitie.getName(), bankinfo.getName())) {
	    return Result.errorResult(AdditionalFunctionStatusCode.E306.getCode(),
		    AdditionalFunctionStatusCode.E306.getMsg());
	}
	info.setAccount(bankinfo.getAccount());
	info.setAddress(bankinfo.getAddress());
	info.setBankName(bankinfo.getBankName());
	info.setName(info.getName());
	String date = DateUtil.getNowDate();
	bankinfo.setUpdateTime(date);
	userCertifiedMappder.updateByPrimaryKey(info);
	return Result.successResult();
    }

}
