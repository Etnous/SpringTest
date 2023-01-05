package biz.lala.SpringBoot.schedule;

import biz.lala.SpringBoot.service.RankerService;
import biz.lala.SpringBoot.util.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

@Component
@Slf4j
public class ScheduledTask {

    @Autowired
    @Qualifier("rankerPool")
    private ThreadPoolTaskExecutor rankerPool;

    @Autowired
    private RankerService rankerService;

    @Scheduled(fixedRate = 500)
    public void schoolSchedule() {

        if (!ThreadPoolUtil.checkIsAvailable(rankerPool, 200)) {
            log.info("任务队列已满");
            return;
        }
        rankerService.getSchool();
    }
}
