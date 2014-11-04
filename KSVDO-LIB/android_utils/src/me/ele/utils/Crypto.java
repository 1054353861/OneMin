package me.ele.utils;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

public class Crypto {
    
    
    private static final String UTF_8 = "utf-8";
    private static final String MD5 = "MD5";
    private static final String AES = "AES";
    private static final String AES_CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";
    private static final String IV = "0102030405060708";

    private Crypto() {
        
    }
    
    public static String encryptByAes(String key, String src) throws Exception {
        key = encryptByMd5(key);
        if (StringUtil.isBlank(key)) {
            throw new IllegalAccessException("key is empty");
        }
        Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, generateSecretKeySpec(key), generateIvParameterSpec());
        return toBase64(cipher.doFinal(src.getBytes(UTF_8)));
    }

    public static String decryptByAes(String key, String src) throws Exception {
        key = encryptByMd5(key);
        if (StringUtil.isBlank(key)) {
            throw new IllegalAccessException("key is empty");
        }
        Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, generateSecretKeySpec(key), generateIvParameterSpec());
        return new String(cipher.doFinal(fromBase64(src)), UTF_8);
    }

    private static IvParameterSpec generateIvParameterSpec() throws Exception {
        return new IvParameterSpec(IV.getBytes(UTF_8));
    }
    
    private static SecretKeySpec generateSecretKeySpec(String key) throws Exception {
        return new SecretKeySpec(key.getBytes(UTF_8), AES);
    }
    
    public static String toBase64(byte[] bytes) throws Exception {
        return new String(Base64.encode(bytes, Base64.NO_WRAP), UTF_8);
    }

    public static byte[] fromBase64(String base64) throws Exception {
        return Base64.decode(base64.getBytes(UTF_8), Base64.NO_WRAP);
    }

    public static String encryptByMd5(String str) {
        if (StringUtil.isBlank(str)) {
            return "";
        }
        try {
            return HexConverter.toHex(MessageDigest.getInstance(MD5).digest(str.getBytes(UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    

}
