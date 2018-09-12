package com.skyline.wallet.eth;

import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;

public class AdminClient {


    private AdminClient(){}

   
    private volatile static Admin admin;


    public static final  Admin getParity(String ip){
    	if(admin==null){
            synchronized (AdminClient.class){
                if(admin==null){
                	admin = Admin.build(new HttpService(ip));
                }
            }
        }
        return admin;
    }
}
