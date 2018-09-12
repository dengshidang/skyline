/**
 * 
 */
package com.skyline.common.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author chenzilong
 * @Description TODO(邮箱工具类)
 * @date 2018年7月13日上午10:55:52
 */
public class MailUtil {
	public static final String FROM="18674086971@163.com";//发件人的email
	public static final String PWD ="19900308czl";//发件人密码--授权码
	public static final int TIMELIMIT = 30*60; //验证码过期时间30分钟
    public static final String TITLE = "[传奇]请验证您的邮箱";
    public static final String HOST = "smtp.163.com";
    public static final String SMTP = "smtp";
    /**
     * 
     * @Title: send_mali 
     * @Description TODO
     * @param to
     * @param title
     * @param content
     * @param code
     * @return 
     * @throws AddressException
     * @throws MessagingException
     * @date 2018年7月13日下午3:19:20
     */
	public static boolean send_mail(String to,String title,String content) throws AddressException, MessagingException{
		//创建连接对象，连接到邮件服务器
			Properties props = new Properties();//可以加载一个配置文件
			//使用smtp：简单邮件传输协议
		    props.put("mail.smtp.host", HOST);//存储发送邮件服务器的信息  
	        props.put("mail.smtp.auth", "true");//同时通过验证  
	        Session session = Session.getInstance(props);//根据属性新建一个邮件会话  
	        //session.setDebug(true); //有他会打印一些调试信息。  
	        MimeMessage message = new MimeMessage(session);//由邮件会话新建一个消息对象  
	        message.setFrom(new InternetAddress(FROM));//设置发件人的地址  
	        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));//设置收件人,并设置其接收类型为TO  
	        message.setSubject(title);//设置标题  
	        //设置信件内容  
	        //message.setText(mailContent); //发送 纯文本 邮件 todo  
	        message.setContent(content, "text/html;charset=gbk"); //发送HTML邮件，内容样式比较丰富  
	        message.setSentDate(new Date());//设置发信时间  
	        //发送邮件 
	        message.saveChanges();//存储邮件信息    
	        Transport transport = session.getTransport(SMTP);  
	        transport.connect(FROM, PWD);
	        transport.sendMessage(message, message.getAllRecipients());//发送邮件,其中第二个参数是所有已设好的收件人地址  
	        transport.close();
			return true;  
	}
	
	public static boolean registerSendMail(String mail, String code) throws AddressException, MessagingException {
		 String  content = "<p>您好O(∩_∩)O~~<br>"
	        		+ "尊敬的用户您好，您本次注册的验证码是:"+code+"请在30分钟内使用.<p>";
		 return 	send_mail(mail, "注册邮箱", content);
	}
}
