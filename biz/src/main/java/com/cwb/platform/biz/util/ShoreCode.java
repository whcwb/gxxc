package com.cwb.platform.biz.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author liushuaic
 * @date 2015/11/18 14:40
 * @desc 邀请码生成工具类
 */
public class ShoreCode {
    /**
     * 生成邀请码
     *
     * @return
     */
    public static String createShareCode() {
        int maxNum = 36;
        int i;
        int count = 0;
        char[] str = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < 6) {
            i = Math.abs(r.nextInt(maxNum));
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }

    public static void main(String[] args) {
        long i = 36 * 36 * 36 * 36 ;
        int count = 0 ;
        List<String> ss = new ArrayList<>();
        for (int j = 0 ; j < i ; j ++){
            String shareCode = createShareCode();
            System.out.println(shareCode);
            if(ss.contains(shareCode)){
                System.out.println("第" + j + "次产生重复");
                count++;
            }
            ss.add(shareCode);
        }
        System.out.println("总共" + i + "   重复" + count);
    }

}
