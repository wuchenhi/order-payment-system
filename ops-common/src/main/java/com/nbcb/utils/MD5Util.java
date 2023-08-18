package com.nbcb.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

/**
 * MD5工具类
 * 客户端：PASS=MD5(明文+固定Salt)
 * 服务端：PASS=MD5(用户输入+随机Salt)
 */
@Component
public class MD5Util {

    public static String md5(String str) {
        return DigestUtils.md5Hex(str);
    }

    private static final String salt = "1a2b3c4d";

    /**
     * 第一次加密
     *
     * @param inputPass
     * @return String
     **/
    public static String inputPassToFromPass(String inputPass) {
        String str = salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    /**
     * 第二次加密
     * @param formPass
     * @param salt
     * @return java.lang.String
     **/
    public static String formPassToDBPass(String formPass, String salt) {
        String str = salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDBPass(String inputPass, String salt) {
        String fromPass = inputPassToFromPass(inputPass);
        String dbPass = formPassToDBPass(fromPass, salt);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFromPass("123456")); //ce21b747de5af71ab5c2e20ff0a60eea
        System.out.println(inputPassToDBPass("123456", "aabbcc")); //0abcca7e997cc341ca6f3d5c4e3894ce
    }

}
