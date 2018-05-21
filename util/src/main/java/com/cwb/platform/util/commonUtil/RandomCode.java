package com.cwb.platform.util.commonUtil;

import java.util.Random;

/**
 *生成随机数
 * Created by Administrator on 2018/5/21.
 */
public class RandomCode {
    /**
     * 产生随机的六位数
     * @return
     */
    public static String getSix(){
        Random rad=new Random();
        String result  = rad.nextInt(1000000) +"";
        if(result.length()!=6){
            return getSix();
        }
        return result;
    }

    public static void main(String[] args) {
        for (int j = 0; j < 1000; j++) {
            System.out.println(getSix());
        }
    }

}
