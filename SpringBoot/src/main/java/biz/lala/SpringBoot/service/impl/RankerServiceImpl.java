package biz.lala.SpringBoot.service.impl;

import biz.lala.SpringBoot.cache.ProxyCache;
import biz.lala.SpringBoot.dao.ranker.*;
import biz.lala.SpringBoot.mapper.ranker.*;
import biz.lala.SpringBoot.service.RankerService;
import biz.lala.SpringBoot.util.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class RankerServiceImpl implements RankerService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private QueueMapper queueMapper;

    @Autowired
    private SchoolMapper schoolMapper;

    @Autowired
    private CollegeMapper collegeMapper;

    @Autowired
    private MajorMapper majorMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private ProxyCache proxyCache;

    @Override
    @Async("rankerPool")
    public void getSchool() {
        // TODO: 从redis取出队列放入工作队列，若完成则删除工作队列，若失败则放回redis队列
        String finalUrl = (String) redisTemplate.opsForList().rightPop("schoolQueue");
        try {

            if (Objects.isNull(finalUrl)) return;
            log.info("获取任务队列:[{}]",finalUrl);
            queueMapper.insert(new QueuePojo(null, finalUrl, 0));
            JSONObject finalObject = JSON.parseObject(finalUrl);
            String s = HttpUtil.httpGetRequest(finalObject.getString("schoolUrl"), null, proxyCache.getProxy());
            JSONObject retObj = JSON.parseObject(s);
            JSONArray retArray = retObj.getJSONArray("data");
            List<SchoolPojo> schoolPojos = JSONObject.parseArray(retArray.toJSONString(), SchoolPojo.class);
            schoolPojos.forEach(item -> {
                item.setProvinceId(finalObject.getInteger("code"));
                schoolMapper.insert(item);
            });
            Map<String, Object> columnMap = new HashMap<String, Object>();
            columnMap.put("content", finalUrl );
            queueMapper.deleteByMap(columnMap);
        } catch (Exception exception) {
            exception.printStackTrace();
            redisTemplate.opsForList().leftPush("schoolQueue", finalUrl);
            Map<String, Object> columnMap = new HashMap<String, Object>();
            columnMap.put("content", finalUrl );
            queueMapper.deleteByMap(columnMap);
        }
    }

    @Override
    @Async("rankerPool")
    public void getCollege() {
        // TODO: 从redis取出队列放入工作队列，若完成则删除工作队列，若失败则放回redis队列
        QueuePojo queuePojo;
        synchronized (this) {
            queuePojo = queueMapper.selectOneItem();
            if (Objects.isNull(queuePojo)) {
                log.info("线程关闭" + System.currentTimeMillis());
                return;
            }
            log.info("获取任务队列:[{}]",queuePojo.getContent());
            queuePojo.setStatus(2);
            queueMapper.updateById(queuePojo);
        }
        try {
            JSONObject finalObject = JSON.parseObject(queuePojo.getContent());
            String s = HttpUtil.httpGetRequest(finalObject.getString("collegeUrl"), null, proxyCache.getProxy());
            JSONObject retObj = JSON.parseObject(s);
            JSONArray retArray = retObj.getJSONArray("data");
            List<CollegePojo> collegePojos = JSONObject.parseArray(retArray.toJSONString(), CollegePojo.class);
            collegePojos.forEach(item -> {
                item.setSchoolId(finalObject.getInteger("code"));
                collegeMapper.insert(item);
            });

            queueMapper.deleteById(queuePojo.getId());
        } catch (Exception exception) {
            exception.printStackTrace();
            redisTemplate.opsForList().leftPush("collegeQueue", queuePojo.getContent());
            queueMapper.deleteById(queuePojo.getId());
        }
    }

    @Override
    @Async("rankerPool")
    public void getMajor() {
        // TODO: 从redis取出队列放入工作队列，若完成则删除工作队列，若失败则放回redis队列
        QueuePojo queuePojo;
        synchronized (this) {
            queuePojo = queueMapper.selectOneItem();
            if (Objects.isNull(queuePojo)) {
//                log.info("线程关闭" + System.currentTimeMillis());
                return;
            }
            log.info("获取任务队列:[{}]",queuePojo.getContent());
            queuePojo.setStatus(2);
            queueMapper.updateById(queuePojo);
        }
        try {
            JSONObject finalObject = JSON.parseObject(queuePojo.getContent());
            String s = HttpUtil.httpGetRequest(finalObject.getString("majorUrl"), null, proxyCache.getProxy());
            JSONObject retObj = JSON.parseObject(s);
            JSONArray retArray = retObj.getJSONArray("data");
            List<MajorPojo> majorPojos = JSONObject.parseArray(retArray.toJSONString(), MajorPojo.class);
            majorPojos.forEach(item -> {
                item.setCollegeId(finalObject.getInteger("code"));
                majorMapper.insert(item);
            });

            queueMapper.deleteById(queuePojo.getId());
        } catch (Exception exception) {
            exception.printStackTrace();
            redisTemplate.opsForList().leftPush("majorQueue", queuePojo.getContent());
            queueMapper.deleteById(queuePojo.getId());
        }
    }

    @Override
    @Async("rankerPool")
    public void getSubject() {
        // TODO: 从redis取出队列放入工作队列，若完成则删除工作队列，若失败则放回redis队列
        QueuePojo queuePojo;
        synchronized (this) {
            queuePojo = queueMapper.selectOneItem();
            if (Objects.isNull(queuePojo)) {
//                log.info("线程关闭" + System.currentTimeMillis());
                return;
            }
            log.info("获取任务队列:[{}]",queuePojo.getContent());
            queuePojo.setStatus(2);
            queueMapper.updateById(queuePojo);
        }
        try {
            JSONObject finalObject = JSON.parseObject(queuePojo.getContent());
            String s = HttpUtil.httpGetRequest(finalObject.getString("subjectUrl"), null, proxyCache.getProxy());
            JSONObject retObj = JSON.parseObject(s);
            JSONArray retArray = retObj.getJSONArray("data");
            if (retArray.size() != 0) {
                retArray.forEach( item -> {
                    SubjectPojo subjectPojo = new SubjectPojo(null, (String) item, finalObject.getInteger("code"));
                    subjectMapper.insert(subjectPojo);
                });
            }
            queueMapper.deleteById(queuePojo.getId());
        } catch (Exception exception) {
            exception.printStackTrace();
            redisTemplate.opsForList().leftPush("subjectQueue", queuePojo.getContent());
            queueMapper.deleteById(queuePojo.getId());
        }
    }
}
