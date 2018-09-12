package com.skyline.user.business.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.skyline.common.business.impl.BaseBusinessImpl;
import com.skyline.common.constant.AdditionalFunctionStatusCode;
import com.skyline.common.constant.Constants;
import com.skyline.common.constant.Result;
import com.skyline.common.entity.Attachment;
import com.skyline.common.entity.Carousel;
import com.skyline.common.entity.Newsinfo;
import com.skyline.common.entity.Userinfo;
import com.skyline.common.util.DateUtil;
import com.skyline.user.business.AdditionalFunctionBusiness;
import com.skyline.user.config.FileInitConfig;
import com.skyline.user.exception.BusinessException;
import com.skyline.user.mapper.AdditionalFunctionMapper;
import com.skyline.user.mapper.NewsInfoMapper;
import com.skyline.user.util.FileUploadUtil;

/**
 * 
 * <p>
 * Title: AdditionalFunctionImpl
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author kuangwenqiang
 * @date 2018年7月20日
 */
@Service
@Transactional
public class AdditionalFunctionImpl extends BaseBusinessImpl<Userinfo, Integer> implements AdditionalFunctionBusiness {

    @Autowired
    private AdditionalFunctionMapper additionalFunctionMapper;

    @Autowired
    private FileInitConfig fileInitConfig;

    @Autowired
    private NewsInfoMapper newsInfoMapper;

    private static Logger logger = Logger.getLogger(AdditionalFunctionImpl.class);

    /**
     * 首页图片轮播设置
     * 
     * @param carousel
     * @return
     */
    @Override
    public Result pictureCarouselSetUp(Carousel carousel) {
	String message = "首页图片轮播设置";
	try {
	    // 第一步，将图片预设置的优先级(1-5) 将之前优先级相同的数据改成 0
	    additionalFunctionMapper.updateCarouselSequence(carousel);
	    // 第二步 将要设置优先级的图片插入到数据库中。
	    additionalFunctionMapper.insertCarouseSetUpCarousel(carousel);
	} catch (Exception e) {
	    logger.info(message + "失败!");
	    throw new BusinessException(AdditionalFunctionStatusCode.E301.getCode(),
		    AdditionalFunctionStatusCode.E301.getMsg());
	}
	return Result.successResult();
    }

    /**
     * 修改轮播顺序
     */
    @Override
    public Result pictureCarouselUpdate(Carousel carousel) {
	String message = "首页图片轮播修改";
	try {
	    // 第一步，将图片预设置的优先级(1-5) 将之前优先级相同的数据改成 0
	    additionalFunctionMapper.updateCarouselSequence(carousel);
	    // 第二步 修改图片播放顺序优先级
	    additionalFunctionMapper.updateByPrimaryKeySelective(carousel);
	} catch (Exception e) {
	    logger.info(message + "失败!");
	    throw new BusinessException(AdditionalFunctionStatusCode.E301.getCode(),
		    AdditionalFunctionStatusCode.E301.getMsg());
	}
	return Result.successResult();
    }

    @Override
    public Result PictureCarouselDelete(Integer carouselId) {
	String message = "首页图片轮播删除";
	try {
	    additionalFunctionMapper.deleteByPrimaryKey(carouselId);
	} catch (Exception e) {
	    logger.info(message + "失败!");
	    throw new BusinessException(AdditionalFunctionStatusCode.E301.getCode(),
		    AdditionalFunctionStatusCode.E301.getMsg());
	}
	return Result.successResult();
    }

    /**
     * 新闻公告动态设置
     * 
     * @param carousel
     * @return
     */
    @Override
    public Result newsCarouselSetUp(Newsinfo newsinfo) {
	String message = "新闻公告动态设置";
	try {
	    // 修改新闻公告展示状态
	    additionalFunctionMapper.updateNewsCarouse(newsinfo);
	} catch (Exception e) {
	    logger.info(message + "失败!");
	    throw new BusinessException(AdditionalFunctionStatusCode.E301.getCode(),
		    AdditionalFunctionStatusCode.E301.getMsg());
	}
	return Result.successResult();
    }

    /**
     * 附件上传
     * 
     * @param MultipartFile
     * @return
     */
    @Override
    public Result fileUpload(MultipartFile file) {
	String filePath = fileInitConfig.getPath();
	// 此处写入文件
	String fileUrl = FileUploadUtil.fileUpload(file, filePath);
	if (fileUrl == null || fileUrl.equals("")) {
	    throw new BusinessException(AdditionalFunctionStatusCode.E311.getCode(),
		    AdditionalFunctionStatusCode.E311.getMsg());
	}
	// 假如此处发生异常，文件已经保存了
	Attachment attachment = new Attachment();
	attachment.setUrl(fileUrl);
	attachment.setFilesize((int) file.getSize());

	// 此处保存数据库
	additionalFunctionMapper.insertAttachment(attachment);
	return Result.successResult(fileUrl);
    }

    @Override
    public Result<?> pictureShow() {
	// TODO Auto-generated method stub
	List<Carousel> list = additionalFunctionMapper.pircureShow();
	return Result.successResult(list);
    }

    @Override
    public Result<?> queryNewsCarousel(Integer type, Integer status, String content, String startTime, String endTime,
	    Integer pageSize, Integer pageNum) {
	// TODO Auto-generated method stub
	PageHelper.startPage((pageNum == null || pageNum == 0) ? Constants.DEFAULT_PAGE_NO : pageNum,
		pageSize == null ? Constants.DEFAULT_PAGE_SIZE : pageSize);

	List<Newsinfo> list = newsInfoMapper.queryNewsCarousel(type, status, startTime, endTime, content);

	PageInfo<Newsinfo> page = new PageInfo<Newsinfo>(list);
	return Result.successResult(page);

    }

    @Override
    public Result<?> newsCarouselSetSave(Newsinfo newsinfo) {

	String message = "新闻公告添加";
	try {
	    // 修改新闻公告展示状态
	    newsinfo.setCreateTime(DateUtil.getCurTimeString());
	    newsInfoMapper.insertSelective(newsinfo);
	} catch (Exception e) {
	    logger.info(message + "失败!");
	    throw new BusinessException(AdditionalFunctionStatusCode.E301.getCode(),
		    AdditionalFunctionStatusCode.E301.getMsg());
	}
	return Result.successResult();
    }

    @Override
    public Result pictureExamine(String startTime, String endTime, Integer sequence, Integer pageSize,
	    Integer pageNum) {
	PageHelper.startPage(pageNum, pageSize);
	List<Carousel> list = additionalFunctionMapper.pictureExamine(startTime, endTime, sequence);
	PageInfo<Carousel> page = new PageInfo<>(list);
	return Result.successResult(page);
    }

    @Override
    public Result<Newsinfo> getNewsCarousel(Integer id) {
	return Result.successResult(newsInfoMapper.selectByPrimaryKey(id));
    }

}
