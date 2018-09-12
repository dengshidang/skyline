/*
 * @文件 DES3.java    版本 1.0  日期 2010-6-1下午01:53:38
 * 版权所有 2010 SFA 保留所有权利.
 * SFA 专有/保密， 任何使用者均应遵守许可证条款。
 */
package com.skyline.common.util;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.sun.crypto.provider.SunJCE;

/**
 *  3DES加密解密算法
 * @author 山人
 * @version 1.0, 2010-6-1
 * @since SFA_framework 1.0
 */
public class DES3 {
    
    /** 定义 加密算法,可用 DES,DESede,Blowfish*/
    private static final String Algorithm = "DESede";
    /**24字节的密钥 定义*/
    private  static  final byte[] keyBytes = { 0x11, 0x22, 0x4F, 0x58, (byte) 0x88, 0x10,
            0x40, 0x38, 0x28, 0x25, 0x79, 0x51, (byte) 0xCB, (byte) 0xDD,
            0x55, 0x66, 0x77, 0x29, 0x74, (byte) 0x98, 0x30, 0x40, 0x36,
            (byte) 0xE2 };
    /**
     * 
     * 功能: 对字符串进行3DES加密
     * @param keybyte 为加密密钥，长度为24字节
     * @param src 被加密的数据缓冲区（源）
     * @return
     * @since  1.0 by 山人
     */
    public static byte[] encryptMode(byte[] keybyte, byte[] src) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

            // 加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
//          e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
//          e2.printStackTrace();
        } catch (java.lang.Exception e3) {
//          e3.printStackTrace();
        }
        return null;
    }
     /**
     * 功能:对字符串进行3DES解密
     * @param keybyte 为加密密钥，长度为24字节
     * @param src 加密后的缓冲区
     * @return
     * @since  1.0 by 山人
     */
    public static byte[] decryptMode(byte[] keybyte, byte[] src) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

            // 解密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
//          e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
//          e2.printStackTrace();
        } catch (java.lang.Exception e3) {
//          e3.printStackTrace();
        }
        return null;
    }

    
    /**byteHEX()，用来把一个byte类型的数转换成十六进制的ASCII表示，
    　因为java中的byte的toString无法实现这一点，我们又没有C语言中的
      sprintf(outbuf,"%02X",ib)
    */
       public static String byteHEX(byte ib) {
               char[] Digit = { '0','1','2','3','4','5','6','7','8','9',
               'A','B','C','D','E','F' };
               char [] ob = new char[2];
               ob[0] = Digit[(ib >>> 4) & 0X0F];
               ob[1] = Digit[ib & 0X0F];
               String s = new String(ob);
               return s;
       }
       /**byteHEX()，用来把一个byte类型的数转换成十六进制的ASCII表示，
    　因为java中的byte的toString无法实现这一点，我们又没有C语言中的
      sprintf(outbuf,"%02X",ib)
    */
         public static String byteHEX(byte[] ib) {
             StringBuffer str=new StringBuffer();
                for(int i=0;i<ib.length;i++)
                {
                    str.append(byteHEX(ib[i]));
                }
                return str.toString();
         }

    /**
     * 
     * 功能:对字符串进行3DES算法加密
     * @param str 需要加密的字符串
     * @return  加密后的字符串
     * @since  1.0 by 山人
     */
    public static String encrypt3DES(String str)
    {
        Security.addProvider(new SunJCE());
        byte[] encoded = encryptMode(keyBytes, str.getBytes());
        return  byteHEX(encoded);
    }
    
    /**
     * 
     * 功能:对字符串进行3DES算法加密
     * @param keyBytes 24字节的密钥
     * @param str 需要加密的字符串
     * @return  加密后的字符串
     * @since  1.0 by 山人
     */
    public static String encrypt3DES(String str,byte[] keyBytes)
    {
        Security.addProvider(new SunJCE());
        byte[] encoded = encryptMode(keyBytes, str.getBytes());
        return  byteHEX(encoded);
    }
    /**
     * 
     * 功能:解密字符串
     * @param des3Str 已加密的字符串
     * @return 源字符串
     * @since  1.0 by 山人
     */
    public static String decrypt3DES(String des3Str)
    {
        Security.addProvider(new SunJCE());
         byte[] srcBytes = decryptMode(keyBytes, HexString2Bytes(des3Str));
         if(null!=srcBytes)
            return new String(srcBytes);
         else
             return "";
    }
    /**
     * 
     * 功能:解密字符串
     * @param keyBytes 24字节的密钥
     * @param des3Str 已加密的字符串
     * @return 源字符串
     * @since  1.0 by 山人
     */
    public static String decrypt3DES(String des3Str,byte[] keyBytes)
    {
        Security.addProvider(new SunJCE());
         byte[] srcBytes = decryptMode(keyBytes, HexString2Bytes(des3Str));
         if(null!=srcBytes)
        return new String(srcBytes);
         else
             return "";
    }
    
    private static byte uniteBytes(byte src0, byte src1) {
         byte _b0 = Byte.decode("0x" + new String(new byte[] {src0})).byteValue();
         _b0 = (byte) (_b0 << 4);
         byte _b1 = Byte.decode("0x" + new String(new byte[] {src1})).byteValue();
         byte ret = (byte) (_b0 | _b1);
         return ret;
    }
    
    /**
     * 
     * 功能:将16进制字符串转化为byte数组
     * @param src 16进制字符串
     * @return
     * @since  1.0 by 山人
     */
    public static byte[] HexString2Bytes(String src)
    {
       byte[] ret = new byte[src.length()/2];
       byte[] tmp = src.getBytes();
       for(int i=0; i<src.length()/2; ++i )
       {
        ret[i] = uniteBytes(tmp[i*2], tmp[i*2+1]);
          }
       return ret;
    }
    /*测试*/
    public static void main(String[] args) {

        String szSrc = "aaaaaa加密aaaa";
        System.out.println("加密前的字符串:" + szSrc+"，字符长度："+szSrc.length());
        String s=encrypt3DES(szSrc);
        System.out.println("加密后的字符串:" +s +"，字符长度："+s.length() );
        System.out.println("解密后的字符串:" + (decrypt3DES(s)));
        
        
    }

}
