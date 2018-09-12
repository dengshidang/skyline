package com.skyline.webapi.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.skyline.common.constant.Result;
import com.skyline.common.constant.StatusCode;
import com.skyline.common.entity.Carousel;
import com.skyline.common.entity.Newsinfo;
import com.skyline.common.util.ObjectTool;
import com.skyline.webapi.annotation.NotLogin;
import com.skyline.webapi.service.AdditionalFunctionService;
import com.skyline.webapi.service.FileUploadService;
@RestController
@RequestMapping(value="additional")
public class AdditionalFunctionController {

	@Autowired
	private AdditionalFunctionService  additionalFunctionService;
	
	@Autowired
	private FileUploadService fileUploadService;
	/**
	 * 
	 * <p>Title: pictureCarouselSetUp</p>  
	 * <p>Description:设置图片轮播 </p>  
	 * @param carousel
	 * @return
	 */
	@PostMapping(value="/pictureCarouselSetUp")
	public Result pictureCarouselSetUp(Carousel carousel) {
		if(carousel==null){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		if(ObjectTool.isBlank(carousel.getSequence(),carousel.getUrl())){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return additionalFunctionService.pictureCarouselSetUp(carousel);
	}
	
	/**
	 *
	 * <p>Title: pictureShow</p>  
	 * <p>Description:首页轮播图片展示 </p>  
	 * @return
	 */
	@NotLogin
	@PostMapping(value="/pictureShow")
	public Result<?> pictureShow(){
		return additionalFunctionService.pictureShow();
	}
	
	
	/**
	 * 
	 * <p>Title: newsCarouselSetUp</p>  
	 * <p>Description:新闻公告动态设置 </p>  
	 * @param newsinfo
	 * @return
	 */
	@PostMapping(value="/newsCarouselSetUp")
	public Result newsCarouselSetUp(Newsinfo newsinfo) {
		if(newsinfo==null){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		if(ObjectTool.isBlank(newsinfo.getStatus(),newsinfo.getTitle(),newsinfo.getContent(),newsinfo.getType())){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return additionalFunctionService.newsCarouselSetUp(newsinfo);
	}
	
	
	/**
	 * 
	 * <p>Title: queryNewsCarousel</p>  
	 * <p>Description:查询新闻公告 </p>  
	 * @param newsinfo
	 * @return
	 */
	@NotLogin
	@PostMapping(value="/queryNewsCarousel")
	public Result<?> queryNewsCarousel(String startTime,String  endTime,String content,Integer status,Integer type,Integer pageSize,Integer pageNum) {
		return additionalFunctionService.queryNewsCarousel(startTime, endTime, content, status, type, pageSize, pageNum);
	}
	
	/**
	 * 
	 * <p>Title: fileUpload</p>  
	 * <p>Description: 附件上传</p>  
	 * @param file
	 * @return
	 */
	@PostMapping(value = "/fileUpload")
	public Result  fileUpload(MultipartFile file){
		if(file==null){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return fileUploadService.fileUpload(file);
	}
	/**
     * 
    * @Title: getNewsCarousel
    * @Description: TODO(查询单条新闻公告)
    * @param @param id
    * @param @return    参数
    * @return Result<Newsinfo>    返回类型
    * @throws
     */
	@NotLogin
	@GetMapping(value="/getNewsCarousel")
    public Result<Newsinfo> getNewsCarousel(Integer id){
    	if(id==null) {
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
    	}
    	return additionalFunctionService.getNewsCarousel(id);
    }
	
}
