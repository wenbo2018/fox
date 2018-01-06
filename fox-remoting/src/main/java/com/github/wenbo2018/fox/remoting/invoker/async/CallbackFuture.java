package com.github.wenbo2018.fox.remoting.invoker.async;

import com.github.wenbo2018.fox.common.bean.InvokeRequest;
import com.github.wenbo2018.fox.common.bean.InvokeResponse;
import com.github.wenbo2018.fox.common.common.FoxConstants;
import com.github.wenbo2018.fox.remoting.enums.ReturnEnum;
import com.github.wenbo2018.fox.remoting.exception.RequestTimeOutException;
import com.github.wenbo2018.fox.remoting.invoker.api.CallFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by shenwenbo on 2017/3/25.
 */
public class CallbackFuture implements Callback {


    private static final Logger logger = LoggerFactory.getLogger(CallbackFuture.class);

    private CallFuture future;
    private boolean done = false;
    private boolean concelled = false;
    private boolean success = false;
    private InvokeResponse response;
    private InvokeRequest request;

    private final Lock lock = new ReentrantLock();

    private final Condition condition = lock.newCondition();


    @Override
    public void run() {
        lock.lock();
        try {
            this.done = true;
            if (condition != null) {
                condition.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    public InvokeResponse get() throws InterruptedException {
        return get(2000);
    }

    public InvokeResponse get(long timeoutMillis) throws InterruptedException {
        if (response != null && response.getReturnType() == ReturnEnum.SERVICE.ordinal()) {
            return response;
        }

        lock.lock();
        try {
            long start = request.getCreateMillisTime();
            long timeoutLeft = timeoutMillis;
            while (!isDone()) {
                condition.await(timeoutLeft, TimeUnit.MILLISECONDS);
                long timeoutPassed = System.currentTimeMillis() - start;

                if (isDone() || timeoutPassed >= timeoutMillis) {
                    break;
                } else {
                    timeoutLeft = timeoutMillis - timeoutPassed;
                }
            }
        } finally {
            lock.unlock();
        }
        if (!isDone()) {
            throw new RequestTimeOutException(
                    "request timeout, current time:" + System.currentTimeMillis() + "\r\nrequest:" + request);
        }
        return response;
    }

    @Override
    public void callback(InvokeResponse response) {
        this.response = response;
    }

    @Override
    public void setRequest(InvokeRequest request) {
        this.request = request;
    }

    @Override
    public void dispose() {

    }

    public boolean isCancelled() {
        return this.concelled;
    }

    public boolean isDone() {
        return this.done;
    }
}
