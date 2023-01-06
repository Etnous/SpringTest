package biz.lala.SpringBoot.schedule;

import biz.lala.SpringBoot.cache.ProxyCache;
import biz.lala.SpringBoot.dao.ranker.QueuePojo;
import biz.lala.SpringBoot.mapper.ranker.QueueMapper;
import biz.lala.SpringBoot.service.RankerService;
import biz.lala.SpringBoot.util.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.Executor;

@Component
@Slf4j
public class ScheduledTask {

    @Autowired
    @Qualifier("rankerPool")
    private ThreadPoolTaskExecutor rankerPool;

    @Autowired
    private RankerService rankerService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private QueueMapper queueMapper;

    @Autowired
    private ProxyCache proxyCache;

    @Scheduled(fixedRate = 10)
    public void schoolSchedule() {

        if (!ThreadPoolUtil.checkIsAvailable(rankerPool, 200)) {
//            log.info("任务队列已满");
            return;
        }
//        rankerService.getSchool();
//        String finalUrl = (String) redisTemplate.opsForList().rightPop("collegeQueue");
//        String finalUrl = (String) redisTemplate.opsForList().rightPop("majorQueue");
        String finalUrl = (String) redisTemplate.opsForList().rightPop("subjectQueue");
        if (Objects.isNull(finalUrl)) return;
        queueMapper.insert(new QueuePojo(null, finalUrl, 1));

    }

    @Scheduled(fixedRate = 10)
    public void workSchedule() {
        if (!ThreadPoolUtil.checkIsAvailable(rankerPool, 200)) {
//            log.info("任务队列已满");
            return;
        }
//        rankerService.getCollege();
//        rankerService.getMajor();
        rankerService.getSubject();
    }
    @Scheduled(fixedRate = 1000)
    public void checkProxy() throws Exception {
        proxyCache.updateProxy();
    }
}
