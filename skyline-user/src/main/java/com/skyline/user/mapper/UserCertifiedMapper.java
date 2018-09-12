package com.skyline.user.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.skyline.common.entity.Bankinfo;
import com.skyline.common.entity.Usercommercial;
import com.skyline.common.entity.UsercommercialResult;
import com.skyline.common.entity.Useridentitie;
import com.skyline.common.util.MyMapper;

public interface UserCertifiedMapper extends MyMapper<Bankinfo> {

    /**
     * 添加实名认证信息
     * 
     * @return
     */
    void realNameCertification(Useridentitie useridentitie);

    /**
     * 查询实名认证信息
     * 
     * @return
     */
    List<Useridentitie> queryCertifiedcation(@Param("name") String name, @Param("idCard") String idCard,
	    @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("status") String status,
	    @Param("userId") String userId);

    /**
     * 审核实名认证
     * 
     * @return
     */
    void updateCertifiedcation(@Param("id") String id, @Param("status") String status, @Param("remark") String remark);

    /**
     * 
     * 查询用户id
     */
    String queryUserIdByid(@Param("id") String id);

    /**
     * 
     * 查询用户id（商户）
     */
    String queryMctIdByid(@Param("id") String id);

    /**
     * 查询用户绑卡信息
     * 
     * @return
     */
    List<Bankinfo> queryBindBankInfo(@Param("userId") String userId);

    /**
     * 绑定银行卡
     */

    int bindBank(Bankinfo bankinfo);

    /**
     * 查询商家申请列表
     */
    List<UsercommercialResult> QueryUsercommercial(Map<String, Object> map);

    /**
     * 修改申请状态
     */
    int updateBusinessStatus(@Param("id") String id, @Param("status") String status, @Param("remark") String remark);

    /**
     * 商户申请
     */
    int businessApplication(Usercommercial usercommercial);
}
