package com.cwb.platform.biz.util;

import com.google.common.eventbus.AsyncEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;

@Component
public class AsyncEventBusUtil {

    @Autowired
    private EventHandler eventHandler;

    private AsyncEventBus asyncEventBus;

    @PostConstruct
    private void init(){
        asyncEventBus = new AsyncEventBus(Executors.newFixedThreadPool(1));
        asyncEventBus.register(eventHandler);
    }

    public void post(Object o){
        asyncEventBus.post(o);
    }

}
