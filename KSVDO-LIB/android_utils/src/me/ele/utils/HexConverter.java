package me.ele.utils;


public class HexConverter {
    
    private static final int HEXADECIMAL = 16;
    private static final int BIT_SIZE_FOR_BYTE = 8;
    
    private HexConverter() {
        
    }
    
    public static String toHex(String txt) throws Exception {
        return toHex(txt.getBytes("UTF-8"));
    }

    public static String fromHex(String hex) {
        return new String(toByte(hex));
    }

    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++) {
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), HEXADECIMAL).byteValue();
        }
        return result;
    }

    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(Integer.toHexString((b >> (BIT_SIZE_FOR_BYTE / 2)) & (HEXADECIMAL - 1)))
            .append(Integer.toHexString(b & (HEXADECIMAL - 1)));
    }
}
