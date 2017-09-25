package com.simple.library.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * package：    com.simple.library.utils
 * author：     XuShuai
 * date：       2017/9/20  10:00
 * version:     v1.0
 * describe：   文件操作类
 */
public class FileUtils {


    /**
     * Read file by bytes.
     *
     * @param filePath the file path
     */
    public static void readFileByBytes(String filePath) {
        File file = new File(filePath);
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
            int tempByte;
            while ((tempByte = fis.read()) != -1) {
                LogUtils.d(String.valueOf(tempByte));
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getDataSize(long size){
        DecimalFormat format = new DecimalFormat("####.00");
        if(size<1024){
            return size+"bytes";
        }else if(size<1024*1024){
            float kbSize = size/1024f;
            return format.format(kbSize)+"KB";
        }else if(size<1024*1024*1024){
            float mbSize = size/1024f/1024f;
            return format.format(mbSize)+"MB";
        }else if(size<1024*1024*1024*1024L){
            float gbSize = size/1024f/1024f/1024f;
            return format.format(gbSize)+"GB";
        }else{
            return "size: error";
        }
    }

}
