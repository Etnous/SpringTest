package biz.lala.SpringBoot.util;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

public class ThreadPoolUtil {

    public synchronized static boolean checkIsAvailable(ThreadPoolTaskExecutor executor, Integer maxQueueSize) {
        return maxQueueSize > executor.getThreadPoolExecutor().getQueue().size();
    }
}
