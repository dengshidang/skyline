package com.skyline.webapi.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.skyline.common.constant.Result;
import com.skyline.webapi.config.MultipartSupportConfig;

/**
* <p>Title: FileUploadService</p> 
* <p>Description: 文件上传</p>  
* @author kuangwenqiang  
* @date 2018年7月20日
 */
@FeignClient(value = "skyline-user" , configuration = MultipartSupportConfig.class)
public interface FileUploadService {
	
	@PostMapping(value = "/AdditionalFunction/fileUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Result fileUpload(@RequestPart("file") MultipartFile file);

	
}
