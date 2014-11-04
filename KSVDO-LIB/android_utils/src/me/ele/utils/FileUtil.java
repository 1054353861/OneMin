package me.ele.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import android.os.Environment;
import android.os.StatFs;

/**
 * @author: chensimin;
 * @Description: TODO;  
 * @date: 2012-8-16 下午6:14:22;  
 */
public class FileUtil {
    
    private static final String CHARSET = "UTF-8";
    private static final String TMP_SUFFIX = ".tmp";
    private static final String DECODING = CHARSET;
    private static final String ENCODING = CHARSET;
    private static final int BUFFER = 1024;
    
    private FileUtil() { }

    /**
     * 获取文件
     * 
     * @param fileName
     *            文件名
     * @param dir
     *            文件目录
     * @return
     */
    public static File getFile(String fileName, String dir) {
        if (!dir.endsWith(File.separator)) {
            dir += File.separator;
        }
        return getFile(dir + fileName);
    }
    
    public static File getFile(String name, File dir) {
        if (dir.getPath().endsWith(File.separator)) {
            return getFile(dir.getPath() + name);
        }
        return getFile(dir.getPath() + File.separator + name);
    }

    /**
     * 获取文件
     * 
     * @param path
     *            文件路径
     * @return
     */
    public static File getFile(String path) {
        File file = new File(path);
        File dirFile = file.getParentFile();
        if (!dirFile.exists()) {
            if (!dirFile.mkdirs()) {
                return null;
            }
        }
        return file;
    }

    /**
     * 删除文件（深度删除）
     * 
     * @param file
     * @return
     */
    public static boolean deleteFile(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    deleteFile(f);
                }
            } else {
                return file.delete();
            }
        }
        return file.delete();
    }

    /**
     * 删除文件（深度删除）
     * 
     * @param path
     *            文件路径
     * @return
     */
    public static boolean deleteFile(String path) {
        return deleteFile(new File(path));
    }

    /**
     * 保存文件
     * 
     * @param source
     *            数据源
     * @param fileName
     *            保存文件名
     * @param dir
     *            保存文件路径
     * @return
     * @throws AppException
     */
    public static File saveFile(List<byte[]> source, String fileName, String dir) throws IOException {
        return saveFile(listByte2ByteArray(source), fileName, dir);
    }

    public static String file2String(File file) throws IOException {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String str = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), DECODING));
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            StreamUtil.closeIO(br);
        }
        return sb.toString();
    }
    
    /**
     * 文件读取为字符串
     * 
     * @param path
     * @return
     * @throws AppException
     */
    public static String file2String(String path) throws IOException {
        return file2String(getFile(path));
    }

    /**
     * 文件读取为字节流
     * @param file
     * @return
     * @throws AppException
     */
    public static byte[] file2ByteArray(String path) throws IOException {
        byte[] bytes = null;
        try {
            FileInputStream fis = new FileInputStream(getFile(path));
            bytes = StreamUtil.convertInputStream2Bytes(fis);
        } catch (FileNotFoundException e) {
            throw e;
        }
        return bytes;
    }
    
    /**
     * List<byte[]>转化为字节流
     * @param data
     * @return
     */
    public static byte[] listByte2ByteArray(List<byte[]> data) {
        int length = 0;
        for (byte[] b : data) {
            length += b.length;
        }
        byte[] dataArray = new byte[length];
        int flag = 0;
        for (int i = 0, listLen = data.size(); i < listLen; i++) {
            for (int j = 0, byteLen = data.get(i).length; j < byteLen; j++) {
                dataArray[flag] = data.get(i)[j];
                flag++;
            }
        }
        return dataArray;
    }

    /**
     * 保存文件
     * 
     * @param source
     *            数据源
     * @param fileName
     *            保存文件名
     * @param dir
     *            保存文件路径
     * @return
     * @throws AppException
     */
    public static File saveFile(byte[] source, String fileName, String dir) throws IOException {
        File file = getFile(fileName, dir);
        return saveFile(source, file);
    }

    /**
     *  保存文件
     * @param source
     * @param file
     * @return
     * @throws AppException
     */
    public static File saveFile(byte[] source, File file) throws IOException {
        File temp = getFile(file.getName().replace(getFileExtension(file.getName()), TMP_SUFFIX), file.getParent());
        if (file.exists()) {
            file.delete();
        }
        if (temp.exists()) {
            temp.delete();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(temp);
            fos.write(source);
            temp.renameTo(file);
        } catch (IOException e) {
            throw e;
        } finally {
            StreamUtil.closeIO(fos);
        }
        return file;
    }

    /**
     * 保存文件
     * 
     * @param source
     *            数据源
     * @param fileName
     *            保存文件名
     * @param dir
     *            保存文件路径
     * @return
     * @throws AppException
     */
    public static File saveFile(InputStream source, String fileName, String dir) throws IOException {
        return saveFile(StreamUtil.convertInputStream2Bytes(source), fileName, dir);
    }

    public static File saveFile(InputStream source, File file) throws IOException {
        return saveFile(StreamUtil.convertInputStream2Bytes(source), file);
    }
    /**
     * 保存字符串为文件
     * 
     * @param content
     * @param fileName
     * @param dir
     * @return
     * @throws UnsupportedEncodingException
     * @throws AppException
     */
    public static File saveFile(String content, String fileName, String dir) throws UnsupportedEncodingException, IOException {
        return saveFile(content.getBytes(ENCODING), fileName, dir);
    }

    /**
     * 获取文件扩展名
     * 
     * @param path
     *            文件路径
     * @return
     */
    public static String getFileExtension(String path) {
        return path.substring(path.lastIndexOf("."), path.length());
    }

    /**
     * 获取SD卡路径
     * 
     * @return
     * @throws AppException 
     */
    public static String getSDCardPath() {
        if (hasSDCardMounted()) {
            return Environment.getExternalStorageDirectory() + File.separator;
        } else {
            throw new RuntimeException("no sdcard");
        }
    }

    /**
     * SD卡是否可用
     * 
     * @return
     */
    public static boolean hasSDCardMounted() {
        return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    @SuppressWarnings("deprecation")
    public static long getAvailaleSize() {
        if (hasSDCardMounted()) {
            File path = Environment.getExternalStorageDirectory(); 
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
            // (availableBlocks * blockSize)/1024 KIB 单位
            // (availableBlocks * blockSize)/1024 /1024 MIB单位
        }
        return -1;
    }
    /**
     * 生成crc32文件名（唯一）
     * 
     * @param url
     * @return
     * @throws
     */
    public static String generateFileName(String url) throws IOException {
        String fname = getFileName(url);
        if (fname != null) {
            try {
                CRC32 crc = new CRC32();
                crc.update(url.getBytes(CHARSET));
                fname = Long.toHexString(crc.getValue()) + "_" + fname;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return fname;
    }

    /**
     * 获取路径下的文件名
     * @param path
     * @return
     */
    public static String getFileName(String path) {
        return new File(path).getName();
    }
    /**
     * 压缩文件
     * @param source
     * @param dest
     * @throws AppException 
     */
    public static void zip(File source, File dest) throws IOException {
        if (!source.exists()) {
            return;
        }
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(dest));
            zos.setMethod(ZipOutputStream.DEFLATED);
            zos.setLevel(Deflater.BEST_COMPRESSION);
            compress(source, zos, dest.getAbsolutePath());
        } catch (IOException e) {
            throw e;
        } finally {
            StreamUtil.closeIO(zos);
        }
    }
    private static void compress(File source, ZipOutputStream zos, String basePath) throws IOException {
        if (source.exists()) {
            BufferedInputStream bis = null;
            if (source.isDirectory()) {
                File[] files = source.listFiles();
                for (File f : files) {
                    compress(f, zos, basePath);
                }
            } else {
                ZipEntry entry = new ZipEntry(source.getAbsolutePath().substring(basePath.length() + 1));
                entry.setSize(source.length());
                zos.putNextEntry(entry);
                bis = new BufferedInputStream(new FileInputStream(source));
                byte []data = new byte[BUFFER];
                int hasRead;
                while ((hasRead = bis.read(data, 0, BUFFER)) != -1) {
                    zos.write(data, 0, hasRead);
                }
            }
            StreamUtil.closeIO(bis);
        }
    }
    
    public static void renameTo(File src, String newName) {
        src.renameTo(new File(src.getParent() + File.separator + newName));
    }
    
    public interface ProgressCallback {
        void onPercentChange(int percent);
    }
    
}
