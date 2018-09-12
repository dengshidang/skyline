package com.skyline.user.business;

import org.springframework.web.multipart.MultipartFile;

import com.skyline.common.business.BaseBusiness;
import com.skyline.common.constant.Result;
import com.skyline.common.entity.Carousel;
import com.skyline.common.entity.Newsinfo;
import com.skyline.common.entity.Userinfo;

public interface AdditionalFunctionBusiness extends BaseBusiness<Userinfo, Integer> {

    /**
     * 首页图片轮播设置
     * 
     * @param
     * @return
     */
    public Result pictureCarouselSetUp(Carousel carousel);

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
    public Result<?> pictureShow();

    /**
     * 新闻公告动态设置
     * 
     * @param
     * @return
     */
    public Result newsCarouselSetUp(Newsinfo newsinfo);

    /**
     * 文件上传
     * 
     * @param
     * @return
     */
    public Result fileUpload(MultipartFile file);

    /**
     * 查询新闻公告
     * 
     * @param
     * @return
     */
    public Result<?> queryNewsCarousel(Integer type, Integer status, String content, String startTime, String endTime,
	    Integer pageSize, Integer pageNum);

    /**
     * 添加新闻公告
     * 
     * @param
     * @return
     */
    public Result<?> newsCarouselSetSave(Newsinfo newsinfo);

    /**
     * 
     * @Title: getNewsCarousel @Description: TODO(查询单条数据) @param @param
     *         id @param @return 参数 @return Result<Newsinfo> 返回类型 @throws
     */
    public Result<Newsinfo> getNewsCarousel(Integer id);

    /**
     * 轮播信息查询
     * 
     * @param sequencee
     * @param pageSize
     * @param pageNum
     * @return
     */
    public Result pictureExamine(String startTime, String endTime, Integer sequence, Integer pageSize, Integer pageNum);

    /**
     * 修改轮播顺序
     * 
     * @param carousel
     * @return
     */
    public Result pictureCarouselUpdate(Carousel carousel);

    /**
     * 删除轮播信息
     * 
     * @param carouselId
     * @return
     */
    public Result PictureCarouselDelete(Integer carouselId);

}
