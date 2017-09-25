package com.simple.library.utils;

import java.util.UUID;

/**
 * package：    com.simple.library.utils
 * author：     XuShuai
 * date：       2017/9/25  9:53
 * version:     v1.0
 * describe：   UUID工具类
 */
public class UUIDUtils {
    /**
     * 获得一个UUID
     * @return String UUID
     */
    public static String getUUID(){
        String s = UUID.randomUUID().toString();
        return s.replace("-","");
        //去掉“-”符号
        //return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
    }
    /**
     * 获得指定数目的UUID
     * @param number int 需要获得的UUID的长度
     * @return String[] UUID数组
     */
    public static String[] getUUID(int number){
        if(number < 1){
            return null;
        }
        String[] ss = new String[number];
        for(int i=0;i<number;i++){
            ss[i] = getUUID();
        }
        return ss;
    }
}
