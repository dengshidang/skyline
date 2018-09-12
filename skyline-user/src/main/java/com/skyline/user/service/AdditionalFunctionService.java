package com.skyline.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.skyline.common.constant.Result;
import com.skyline.common.entity.Carousel;
import com.skyline.common.entity.Newsinfo;
import com.skyline.user.business.AdditionalFunctionBusiness;

/**
 * 
 * <p>
 * Title: AdditionalFunctionService
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author kuangwenqiang
 * @date 2018年7月20日
 */
@RestController
@RequestMapping(value = "AdditionalFunction")
public class AdditionalFunctionService {

    @Autowired
    private AdditionalFunctionBusiness additionalFunctionBusiness;

    /**
     * @Title: pictureCarouselSetUp
     * @Description TODO(设置图片轮播顺序)
     * @param carousel
     * @return
     */
    @PostMapping(value = "/pictureCarouselSetUp")
    public Result pictureCarouselSetUp(@RequestBody Carousel carousel) {
	return additionalFunctionBusiness.pictureCarouselSetUp(carousel);
    };

    /**
     *
     * <p>
     * Title: pictureShow
     * </p>
     * <p>
     * Description:首页轮播图片展示
     * </p>
     * 
     * @return
     */
    @PostMapping(value = "/pictureShow")
    public Result<?> pictureShow() {

	return additionalFunctionBusiness.pictureShow();
    }

    /**
     * @Title: newsCarouselSetUp
     * @Description TODO(设置图片轮播顺序)
     * @param carousel
     * @return
     */
    @PostMapping(value = "/newsCarouselSetUp")
    public Result newsCarouselSetUp(@RequestBody Newsinfo newsinfo) {
	Result result = additionalFunctionBusiness.newsCarouselSetUp(newsinfo);
	return result;
    };

    /**
     * @Title: newsCarouselSetSave
     * @Description TODO(添加新闻公告)
     * @param carousel
     * @return
     */
    @PostMapping(value = "/newsCarouselSetSave")
    public Result newsCarouselSetSave(@RequestBody Newsinfo newsinfo) {
	Result result = additionalFunctionBusiness.newsCarouselSetSave(newsinfo);
	return result;
    };

    /**
     * @Title: fileUpload
     * @Description TODO(文件上传接口)
     * @param carousel
     * @return
     */
    @PostMapping(value = "/fileUpload")
    public Result fileUpload(MultipartFile file) {
	return additionalFunctionBusiness.fileUpload(file);
    };

    /**
     * 
     * <p>
     * Title: queryNewsCarousel
     * </p>
     * <p>
     * Description:查询新闻公告
     * </p>
     * 
     * @param newsinfo
     * @return
     */
    @PostMapping(value = "/queryNewsCarousel")
    public Result<?> queryNewsCarousel(String startTime, String endTime, String content, Integer status, Integer type,
	    Integer pageSize, Integer pageNum) {
	return additionalFunctionBusiness.queryNewsCarousel(type, status, content, startTime, endTime, pageSize,
		pageNum);

    }

    /**
     * 查询新闻公告信息
     */
    @GetMapping(value = "/getNewsCarousel")
    public Result<Newsinfo> getNewsCarousel(Integer id) {
	return additionalFunctionBusiness.getNewsCarousel(id);
    }

}
