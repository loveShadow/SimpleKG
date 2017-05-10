package com.sample.kg.utils;

/**
 * Created by zhaoli on 2017/3/16.
 * 歌曲ID帮助类
 */
public class SongIDUtil {
    public static String toString(int max, int index) {
        int maxBits = getNumberBits(max);
        int indexBits = getNumberBits(index);
        StringBuffer sb = new StringBuffer();
        while (maxBits - indexBits > 0) {
            sb.append("0");
            maxBits --;
        }
        sb.append(index);
        return sb.toString();
    }

    public static int getNumberBits(int number){
        int bits = 1;
        int num = number;
        while (num > 0) {
            bits ++;
            num = num / 10;
        }
        return bits;
    }
}
