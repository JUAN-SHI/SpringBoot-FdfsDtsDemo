package com.example.demo.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Created by shijuan on 2019/7/3.
 */
@Slf4j
public class Util {
    private final static String KEY_MAC = "HmacMD5";

    public static void showAvailableBytes(InputStream in) {
        try {
            System.out.println("当前字节输入流中的字节数为:" + in.available());
        } catch (IOException e) {// 捕获异常
            e.printStackTrace();
        }
    }

    //静态方法，便于作为工具类
    public static String getMd5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] encryptHMAC(byte[] data, String key) {
        SecretKey secretKey;
        byte[] bytes = null;
        try {
            secretKey = new SecretKeySpec(decryptBase64(key), KEY_MAC);
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            bytes = mac.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * 数组转换成十六进制字符串
     *
     * @param
     * @return HexString
     */
    public static final String byteArrayToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }


    public static String getRandomString() {
        StringBuffer sb = new StringBuffer();
        String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        for (int i = 0; i < 24; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 将javabean对象转换成json字符串
     *
     * @param javaBean javaBean对象
     * @return JSON格式字符串
     */
    public static String getJsonFromBean(Object javaBean) {
        String result = "";
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            result = mapper.writeValueAsString(javaBean);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return result;
    }

    /**
     * BASE64 解密
     *
     * @param key 需要解密的字符串
     * @return 字节数组
     * @throws Exception
     */
    public static byte[] decryptBase64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * 以Object类型解析JSON
     *
     * @param jsonString JSON格式字符串
     * @return Object对象
     */
    public static Object getJsonObject(String jsonString) {
        Object jsonObj = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonObj = mapper.readValue(jsonString, Object.class);
        } catch (IOException e) {
            log.error("JsonUtil(getJsonObject) error:" + e.toString());
        }
        return jsonObj;
    }

    /**
     * 将json字符串转换成javabean对象
     *
     * @param jsonString 要转换的json字符串
     * @param classname  javabean的classname
     * @return javabean对象
     */
    public static Object getBeanFromJson(String jsonString, Class<?> classname) {
        Object result = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.readValue(jsonString, classname);
        } catch (JsonParseException e) {
            log.error(e.getMessage());
        } catch (JsonMappingException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return result;
    }

    public static short toShort(byte[] b) {
        return (short) ((b[1] << 8) + (b[0] << 0));
    }


    public static int toInt(byte[] b) {
        return ((unsignedByteToInt(b[3])<<24) + ((unsignedByteToInt(b[2]) << 16)) + (unsignedByteToInt(b[1]) << 8)) + ((unsignedByteToInt(b[0]) << 0));
    }




    public static byte[] read(RandomAccessFile rdf, int pos, int length) throws IOException {
        rdf.seek(pos);
        byte result[] = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = rdf.readByte();
        }
        return result;
    }

    public static int unsignedByteToInt(byte b) {
        return (int) b & 0xFF;
    }

}
