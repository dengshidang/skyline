package com.skyline.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.skyline.common.entity.Attachment;
import com.skyline.common.entity.Carousel;
import com.skyline.common.entity.Newsinfo;
import com.skyline.common.util.MyMapper;

public interface AdditionalFunctionMapper extends MyMapper<Carousel> {

    /**
     * 修改轮播图优先级
     * 
     * @param carousel
     * @return
     */
    int updateCarouselSequence(Carousel carousel);

    /**
     * 设置轮播图优先级
     * 
     * @param carousel
     * @return
     */
    void insertCarouseSetUpCarousel(Carousel carousel);

    /**
     * 查询轮播图片
     * 
     * @param carousel
     * @return
     */
    List<Carousel> pircureShow();

    /**
     * 新闻公告动态设置
     * 
     * @param newsinfo
     * @return
     */
    void updateNewsCarouse(Newsinfo newsinfo);

    /**
     * 图片上传保存数据库
     * 
     * @param newsinfo
     * @return
     */
    void insertAttachment(Attachment attachment);

    /**
     * 轮播信息查询
     */
    List<Carousel> pictureExamine(@Param("startTime") String startTime, @Param("endTime") String endTime,
	    @Param("sequence") Integer sequence);
}
