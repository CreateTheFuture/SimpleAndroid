package com.simple.library.utils;

import java.math.BigDecimal;

/**
 * package：    com.simple.library.utils
 * author：     XuShuai
 * date：       2017/9/20  10:00
 * version:     v1.0
 * describe：   数学运算工具类。主要用于数学计算，货币运算使用BigDecimal(String arg1)进行运算
 */
public class MathUtils {

    //默认除法算法精度
    private static final int DEF_DIV_SCALE = 10;

    private MathUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 提供精确无误的加法运算
     *
     * @param arg1 被加数
     * @param arg2 加数
     * @return 结果
     */
    public static double add(double arg1, double arg2) {
        BigDecimal b1 = new BigDecimal(Double.toString(arg1));
        BigDecimal b2 = new BigDecimal(Double.toString(arg2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确无误的减法运算
     *
     * @param arg1 被减数
     * @param arg2 减数
     * @return 结果
     */
    public static double sub(double arg1, double arg2) {
        BigDecimal b1 = new BigDecimal(Double.toString(arg1));
        BigDecimal b2 = new BigDecimal(Double.toString(arg2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确无误的乘法运算
     *
     * @param arg1 被乘数
     * @param arg2 乘数
     * @return 结果
     */
    public static double mul(double arg1, double arg2) {
        BigDecimal b1 = new BigDecimal(Double.toString(arg1));
        BigDecimal b2 = new BigDecimal(Double.toString(arg2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算,当除不尽时四舍五入
     *
     * @param arg1  被除数
     * @param arg2  除数
     * @param scale 小数点保留位数
     * @return 结果
     */
    public static double div(double arg1, double arg2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(arg1));
        BigDecimal b2 = new BigDecimal(Double.toString(arg2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到小数点以后10位，以后的数字四舍五入。
     *
     * @param arg1 被除数
     * @param arg2 除数
     * @return 两个参数的商
     */
    public static double div(double arg1, double arg2) {
        return div(arg1, arg2, DEF_DIV_SCALE);
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param arg1  需要四舍五入的数字
     * @param scale 小数点后保留位数
     * @return 四舍五入后的结果
     */
    public static double round(double arg1, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(arg1));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
