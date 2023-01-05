package biz.lala.SpringBoot.util;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class ThreadPoolUtil {

    public synchronized static boolean checkIsAvailable(ThreadPoolTaskExecutor executor, Integer maxQueueSize) {
        return maxQueueSize > executor.getThreadPoolExecutor().getQueue().size();
    }
}
