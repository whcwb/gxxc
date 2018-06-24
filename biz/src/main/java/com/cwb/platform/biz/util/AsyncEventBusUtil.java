package com.cwb.platform.biz.util;

import com.google.common.eventbus.AsyncEventBus;

import java.util.concurrent.Executors;

public class AsyncEventBusUtil {

    private static AsyncEventBus asyncEventBus = null;

    public static AsyncEventBus getInstance(){
        if (asyncEventBus == null){
            asyncEventBus = new AsyncEventBus(Executors.newFixedThreadPool(1));
        }
        return asyncEventBus;
    }
    private AsyncEventBusUtil(){

    }
}
