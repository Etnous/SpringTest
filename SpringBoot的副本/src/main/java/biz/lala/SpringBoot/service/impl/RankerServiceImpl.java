package biz.lala.SpringBoot.service.impl;

import biz.lala.SpringBoot.cache.ProxyCache;
import biz.lala.SpringBoot.dao.ranker.ProvincePojo;
import biz.lala.SpringBoot.dao.ranker.QueuePojo;
import biz.lala.SpringBoot.dao.ranker.SchoolPojo;
import biz.lala.SpringBoot.mapper.ranker.QueueMapper;
import biz.lala.SpringBoot.mapper.ranker.SchoolMapper;
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
    private ProxyCache proxyCache;

    @Override
    @Async("rankerPool")
    public void getSchool() {
        // TODO: 从redis取出队列放入工作队列，若完成则删除工作队列，若失败则放回redis队列
        String schoolUrl = (String) redisTemplate.opsForList().rightPop("schoolQueue");
        try {

            if (Objects.isNull(schoolUrl)) return;
            log.info("获取任务队列:[{}]", schoolUrl);
            queueMapper.insert(new QueuePojo(null, schoolUrl, 0));
            String s = HttpUtil.httpGetRequest(schoolUrl, null, proxyCache.getProxy());
            JSONObject retObj = JSON.parseObject(s);
            JSONArray retArray = retObj.getJSONArray("data");
            List<SchoolPojo> schoolPojos = JSONObject.parseArray(retArray.toJSONString(), SchoolPojo.class);
            schoolPojos.forEach(item -> {
                String[] split = schoolUrl.split("=");
                item.setProvinceId(Long.valueOf(split[split.length - 1]));
                schoolMapper.insert(item);
            });
            Map<String, Object> columnMap = new HashMap<String, Object>();
            columnMap.put("content", schoolUrl );
            queueMapper.deleteByMap(columnMap);
        } catch (Exception exception) {
            exception.printStackTrace();
            redisTemplate.opsForList().leftPush("schoolQueue", schoolUrl);
            Map<String, Object> columnMap = new HashMap<String, Object>();
            columnMap.put("content", schoolUrl );
            queueMapper.deleteByMap(columnMap);
        }
    }
}
