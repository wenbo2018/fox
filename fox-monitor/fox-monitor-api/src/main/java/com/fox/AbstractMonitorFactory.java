package com.fox;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by shenwenbo on 2017/4/1.
 */
public abstract class AbstractMonitorFactory implements  MonitorFactory{

    private static final Map<String, Monitor> MONITORS = new ConcurrentHashMap<String, Monitor>();
    private static final ReentrantLock LOCK = new ReentrantLock();

    @Override
    public Monitor getMonitor(String address) {
        LOCK.lock();
        try {
            Monitor monitor = MONITORS.get(address);
            if (monitor != null) {
                return monitor;
            }
            monitor = createMonitor(address);
            if (monitor == null) {
                throw new IllegalStateException("Can not create monitor " + address);
            }
            MONITORS.put(address, monitor);
            return monitor;
        } finally {
            LOCK.unlock();
        }
    }

    protected abstract Monitor createMonitor(String address);

}
