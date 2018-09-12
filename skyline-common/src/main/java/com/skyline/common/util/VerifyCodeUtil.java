package com.skyline.common.util;

import java.awt.image.BufferedImage;
import java.util.Properties;

import javax.imageio.ImageIO;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;

public class VerifyCodeUtil {
	static Producer kaptchaProducer = null;
	 static {
			Properties props = new Properties();
		 	ImageIO.setUseCache(false);
	        // 验证码图片的边框 yes和no
	        props.setProperty("kaptcha.border", "yes");
	        // 验证码图片的边框的颜色
	        props.setProperty("kaptcha.border.color", "105,179,90");
	        // 验证码的颜色
	        props.setProperty("kaptcha.textproducer.font.color", "blue");
	        // 整个验证码在图片中的宽度
	        props.setProperty("kaptcha.image.width", "110");
	        // 整个验证码在图片中的高度
	        props.setProperty("kaptcha.image.height", "40");
	        // 验证码在图片中的大小
	        props.setProperty("kaptcha.textproducer.font.size", "35");
	        // 验证码长度
	        props.setProperty("kaptcha.textproducer.char.length", "4");
	        // 验证码的字体和样式
	        props.setProperty("kaptcha.textproducer.font.names", "微软雅黑");//宋体,楷体,
	        Config config = new Config(props);
	        kaptchaProducer = config.getProducerImpl();
	}
	 public static String createText() {
		 return kaptchaProducer.createText();
	 }
	 public static BufferedImage createImage(String capText) {
		 return kaptchaProducer.createImage(capText);
	 }
}
