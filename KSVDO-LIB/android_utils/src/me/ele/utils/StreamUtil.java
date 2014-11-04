package me.ele.utils;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * @author: chensimin;
 * @Description: TODO;  
 * @date: 2012-8-16 下午6:18:00;  
 */
public class StreamUtil {
    
    private static final int CHAR_SIZE = 128;
    private static final int BYTE_SIZE = 1024;
    private static final String DECODING = "utf-8";
    
    private StreamUtil() { }
    
    /**
     * 将流转化为byte数组
     * 
     * @param is
     *            ：输入流
     * @return：byte数组
     */
    public static byte[] convertInputStream2Bytes(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = 0;
        byte[] b = new byte[BYTE_SIZE];
        try {
            while ((len = is.read(b, 0, b.length)) != -1) {
                baos.write(b, 0, len);
            }
            baos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIO(baos);
        }
        return baos.toByteArray();
    }
    
    public static String convertStream2String(InputStream is) {
        return convertStream2String(is, null);
    }
    /**
     * 将流解码为字符串
     * 
     * @param is
     *            ：输入流
     * @param decoding
     *            ：解码字符集
     * @return：解码后字符串
     */
    public static String convertStream2String(InputStream is, String decoding) {
        if (is != null) {
            StringBuilder sb = new StringBuilder();
            InputStreamReader isr = null;
            try {
                if (!StringUtil.isBlank(decoding)) {
                    isr = new InputStreamReader(is, decoding);
                } else {
                    isr = new InputStreamReader(is, DECODING);
                }
            } catch (UnsupportedEncodingException e) {
                return null;
            }
            char[] buf = new char[CHAR_SIZE];
            int hasRead = 0;
            try {
                while ((hasRead = isr.read(buf)) > 0) {
                    sb.append(buf, 0, hasRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        } else {
            return null;
        }
    }
    /**
     * 关闭流
     * @param io
     */
    public static void closeIO(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
