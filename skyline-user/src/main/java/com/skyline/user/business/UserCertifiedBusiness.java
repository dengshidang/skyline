package com.skyline.user.business;

import com.skyline.common.constant.Result;
import com.skyline.common.entity.Bankinfo;
import com.skyline.common.entity.Usercommercial;
import com.skyline.common.entity.Useridentitie;

/**
 * 
 * <p>
 * Title: UserCertifiedBusiness
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author kuangwenqiang
 * @date 2018年7月26日
 */
public interface UserCertifiedBusiness {
    /**
     * 添加实名认证信息
     * 
     * @param
     * @return
     */
    public Result realNameCertification(Useridentitie useridentitie);

    /**
     * 认证信息查询
     * 
     * @param
     * @return
     */
    public Result queryCertifiedcation(String name, String idCard, String startTime, String endTime, String userId,
	    String status, int pageSize, int pageNum);

    /**
     * 认证审核
     * 
     * @param
     * @return
     */
    public Result updateCertifiedcation(String id, String status, String remark);

    /**
     * 
     * <p>
     * Title: queryBindBankInfo
     * </p>
     * <p>
     * Description:查询用户关联的银行卡类型信息
     * </p>
     */
    public Result queryBindBankInfo(String userId);

    /**
     * 
     * <p>
     * Description: 绑定银行卡信息
     * </p>
     * 
     * @return
     */
    public Result bindBank(Bankinfo bankinfo, String payPwd);

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
    public Result queryUsercommercial(String name, String idCard, String startTime, String endTime, Integer userId,
	    Integer status, Integer pageSize, Integer pageNum);

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
    public Result updateBusinessStatus(Integer id, String status, String remark);

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
    public Result businessApplication(Usercommercial usercommercial);

    /**
     * 
     * <p>
     * Title: updatePayWay
     * </p>
     * <p>
     * Description:修改支付方式
     * </p>
     * 
     * @param id
     * @param account
     * @param imgUrl
     * @param payPwd
     * @param type
     * @return
     */
    public Result updatePayWay(String id, String account, String imgUrl, String payPwd, String type);

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
    public Result updateBankPayWay(Bankinfo bankinfo, String payPwd);

}
