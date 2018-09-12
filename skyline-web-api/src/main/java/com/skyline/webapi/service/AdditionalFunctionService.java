package com.skyline.webapi.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.skyline.common.constant.Result;
import com.skyline.common.entity.Carousel;
import com.skyline.common.entity.Newsinfo;

@FeignClient(value = "skyline-user")
public interface AdditionalFunctionService {
	
	
	/**
	 * 
	* @Title: 
	* @Description: 首页轮播图动态设置
	* @author kwq
	* @param @param carousel对象
	* @param @return    参数
	* @return Result    返回类型
	* @throws
	 */
	@PostMapping(value="/AdditionalFunction/pictureCarouselSetUp")
	public Result pictureCarouselSetUp(@RequestBody Carousel carousel);
	
	
	/**
	 *
	 * <p>Title: pictureShow</p>  
	 * <p>Description:首页轮播图片展示 </p>  
	 * @return
	 */
	@PostMapping(value="/AdditionalFunction/pictureShow")
	public Result<?> pictureShow();
	
	/**
	 * 
	* @Title: 
	* @Description: 新闻公告动态设置
	* @author kwq
	* @param @param Newsinfo对象
	* @param @return    参数
	* @return Result    返回类型
	* @throws
	 */
	@PostMapping(value="/AdditionalFunction/newsCarouselSetUp")
	public Result newsCarouselSetUp(@RequestBody Newsinfo newsinfo);
	
	
	
	
	/**
	 * 
	 * <p>Title: queryNewsCarousel</p>  
	 * <p>Description:查询新闻公告 </p>  
	 * @param newsinfo
	 * @return
	 */
	@PostMapping(value="/AdditionalFunction/queryNewsCarousel")
	public Result<?> queryNewsCarousel(
			@RequestParam("startTime")String startTime,@RequestParam("endTime")String  endTime,
			@RequestParam("content")String content,@RequestParam("status")Integer status,
			@RequestParam("type")Integer type,
			@RequestParam("pageSize")Integer pageSize,@RequestParam("pageNum")Integer pageNum);
	
	  /**
     * 
    * @Title: getNewsCarousel
    * @Description: TODO(查询单条新闻公告)
    * @param @param id
    * @param @return    参数
    * @return Result<Newsinfo>    返回类型
    * @throws
     */
    @GetMapping(value="/AdditionalFunction/getNewsCarousel")
    public Result<Newsinfo> getNewsCarousel(@RequestParam("id")Integer id);
}
