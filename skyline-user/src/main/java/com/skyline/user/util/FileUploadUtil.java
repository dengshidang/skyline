package com.skyline.user.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.skyline.common.constant.AdditionalFunctionStatusCode;
import com.skyline.user.config.FileInitConfig;
import com.skyline.user.exception.BusinessException;

public class FileUploadUtil {
	
	public static  String  fileUpload(MultipartFile file,String filePath) {
		Path path = Paths.get(filePath);
		String fileUrl="";
		try {
			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}
			fileUrl =UUID.randomUUID() + file.getOriginalFilename();
			Path path2 = Paths
					.get(filePath + fileUrl);
			Files.write(path2, file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(AdditionalFunctionStatusCode.E311.getCode(),
					AdditionalFunctionStatusCode.E311.getMsg());
			
		}
		return fileUrl;
	}
}
