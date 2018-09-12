package com.skyline.webapi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.skyline.common.constant.C2cStatusCode;
import com.skyline.common.constant.Result;
import com.skyline.common.util.FileUtil;
import com.skyline.common.util.StringTool;
@RestController
public class UploadController {
	
	@Value("${upload.filePath}")
	private String filePath;
	
	
	
	@PostMapping("upload")
    public Result<?> fileUpload(@RequestParam("file") MultipartFile file){
        if(file.isEmpty()){
            return Result.errorResult(C2cStatusCode.E00231.getCode(), C2cStatusCode.E00231.getMsg());
        }
       String fileName= FileUtil.upload(file,filePath);
       if(StringTool.isBlank(fileName)) {
    	   return Result.systemErrorResult();
       }else {
    	   return Result.successResult(fileName);
       }
    }
}
